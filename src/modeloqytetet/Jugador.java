/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author joseymanuel
 */
public class Jugador implements Comparable {

    protected boolean encarcelado;
    protected String nombre;
    protected int saldo;
    protected Sorpresa cartaLibertad;
    protected Casilla casillaActual;
    protected ArrayList<TituloPropiedad> propiedades;

    // Constructor sin parametros
    public Jugador(String nombre) {
        encarcelado = false;
        this.nombre = nombre;
        saldo = 7500;
        propiedades = new ArrayList<>();
        casillaActual = null;
        this.setCartaLibertad(null);
    }

    // Contrusctor de copia
    protected Jugador(Jugador otroJugador) {
        encarcelado = otroJugador.getEncarcelado();
        this.nombre = otroJugador.getNombre();
        saldo = otroJugador.getSaldo();
        propiedades = otroJugador.getPropiedades();
        casillaActual = otroJugador.getCasillaActual();
        this.setCartaLibertad(otroJugador.getCartaLibertad());
    }

    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - this.obtenerCapital();
    }

    protected Especulador convertirme(int fianza) {
        Especulador especulador = new Especulador(this, fianza);
        return especulador;
    }

    protected boolean puedoEdificarCasa(TituloPropiedad titulo) {
        boolean puedoconstruir = false;
        int numCasas = titulo.getNumCasas();
        int costeEdificarCasa = titulo.getPrecioEdificar();
        if (numCasas < 4 && tengoSaldo(costeEdificarCasa)) {
            puedoconstruir = true;
        }
        return puedoconstruir;
    }

    protected boolean puedoEdificarHotel(TituloPropiedad titulo) {
        boolean puedoconstruir = false;
        int numCasas = titulo.getNumCasas();
        int numHoteles = titulo.getNumHoteles();
        int costeEdificarHotel = titulo.getPrecioEdificar();
        if (numCasas == 4 && numHoteles < 4 && tengoSaldo(costeEdificarHotel)) {
            puedoconstruir = true;
        }
        return puedoconstruir;
    }

    protected boolean deboIrACarcel() {
        return !(this.tengoCartaLibertad());
    }

    boolean cancelarHipoteca(TituloPropiedad titulo) {
        int costeCancelar = titulo.calcularCosteCancelar();
        boolean tengoSaldo = this.tengoSaldo(costeCancelar);
        if (tengoSaldo) {
            titulo.cancelarHipoteca();
            this.modificarSaldo(-costeCancelar);
        }
        return !(titulo.getHipotecada());
    }

    boolean comprarTituloPropiedad() {
        boolean comprado = false;
        TituloPropiedad titulo;
        int costeCompra = getCasillaActual().getCoste();
        if (costeCompra < getSaldo()) {
            ((Calle) getCasillaActual()).asignarPropietario(this);
            comprado = true;
            this.getPropiedades().add(getCasillaActual().getTitulo());
            this.modificarSaldo(-costeCompra);
        }
        return comprado;
    }

    // Devuelve el total de casas y hoteles que tiene ese jugador en todas sus propiedades.
    int cuantasCasasHotelesTengo() {
        int contador = 0;
        for (int i = 0; i < propiedades.size(); i++) {
            contador = contador + propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles();
        }
        return contador;
    }

    boolean deboPagarAlquiler() {
        boolean esDeMiPropiedad = esDeMiPropiedad(getCasillaActual().getTitulo());
        boolean tienePropietario = false;
        boolean encarcelado = false;
        boolean estaHipotecada = false;
        boolean deboPagar = false;

        if (!esDeMiPropiedad) {
            tienePropietario = ((Calle) getCasillaActual()).tengoPropietario();
        }
        if ((!esDeMiPropiedad) & (tienePropietario)) {
            encarcelado =(getCasillaActual()).getTitulo().propietarioEncarcelado();
            estaHipotecada = getCasillaActual().getTitulo().getHipotecada();
        }

        deboPagar = ((!esDeMiPropiedad) & (tienePropietario) & (!encarcelado) & (!estaHipotecada));
        return deboPagar;
    }

    // Devuelve la carta Sorpresa cartaLibertad, pues el jugador ya ha hecho uso de ella.
    Sorpresa devolverCartaLibertad() {
        Sorpresa cartaLibertad = this.getCartaLibertad();
        this.setCartaLibertad(null);
        return cartaLibertad;
    }

    boolean edificarCasa(TituloPropiedad titulo) {
        boolean edificada = false;
        if (this.puedoEdificarCasa(titulo)) {
            int costeEdificarCasa = titulo.getPrecioEdificar();
            titulo.edificarCasa();
            this.modificarSaldo(-costeEdificarCasa);
            edificada = true;
        }
        return edificada;
    }

    boolean edificarHotel(TituloPropiedad titulo) {
        boolean edificada = false;
        if (this.puedoEdificarHotel(titulo)) {
            int costeEdificarHotel = titulo.getPrecioEdificar();
            titulo.edificarHotel();
            this.modificarSaldo(-costeEdificarHotel);
            edificada = true;
            int casasActual = titulo.getNumCasas() - 4;
            titulo.setNumCasas(casasActual);
        }
        return edificada;
    }

    private void eliminarDeMisPropiedades(TituloPropiedad titulo) {
        this.getPropiedades().remove(titulo);
        titulo.setPropietario(null);
    }

    // Cierto si el jugador tiene entre sus propiedades el título de propiedad pasado como argumento.
    private boolean esDeMiPropiedad(TituloPropiedad titulo) {
        boolean condicion = false;
        int numprops = this.propiedades.size();
        int i = 0;
        while ((!condicion) && (i < numprops)) {
            if (propiedades.get(i) == titulo) {
                condicion = true;
            }
            i++;
        }
        return condicion;
    }

    boolean estoyEnCalleLibre() {
        return (getCasillaActual().soyEdificable() == true)
                && ((getCasillaActual().getTitulo().getPropietario() == null)
                || (getCasillaActual().getTitulo() == null));
    }

    Sorpresa getCartaLibertad() {
        return cartaLibertad;
    }

    public Casilla getCasillaActual() {
        return casillaActual;
    }

    boolean getEncarcelado() {
        return encarcelado;
    }

    public String getNombre() {
        return nombre;
    }

    ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    public int getSaldo() {
        return saldo;
    }

    void setSaldo(int cantidad) {
        saldo = cantidad;
    }

    boolean hipotecarPropiedad(TituloPropiedad titulo) {
        int costeHipoteca = titulo.hipotecar();
        this.modificarSaldo(costeHipoteca);
        return titulo.getHipotecada();
    }

    void irACarcel(Casilla casilla) {
        this.setCasillaActual(casilla);
        this.setEncarcelado(true);
    }

    // Añade al saldo la cantidad del argumento. Si el argumento es negativo, 
    // el saldo quedará reducido. Devuelve el nuevo saldo.
    int modificarSaldo(int cantidad) {
        int tengo = this.getSaldo();
        this.setSaldo(tengo + cantidad);
        return this.getSaldo();
    }

    /*Devuelve el capital del que dispone el jugador, que es igual a su
    saldo más la suma de los valores de todas sus propiedades. El valor de una propiedad es
    la suma de su precio de compra más el número de casas y hoteles que haya construidos
    por el precio de edificación. Si la propiedad estuviese hipotecada, se le restará el valor de
    la hipoteca base.*/
    int obtenerCapital() {
        int capital = this.getSaldo();
        int valorpropiedad;
        int numprop = propiedades.size();
        for (int i = 0; i < numprop; i++) {
            valorpropiedad = 0;
            valorpropiedad = valorpropiedad + ((propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles()) * propiedades.get(i).getPrecioEdificar());
            valorpropiedad = valorpropiedad + propiedades.get(i).getPrecioCompra();
            if (propiedades.get(i).getHipotecada()) {
                valorpropiedad = valorpropiedad - propiedades.get(i).getHipotecaBase();
            }
            capital = capital + valorpropiedad;
        }
        return capital;
    }

    // Devuelve los títulos de propiedad del jugadorActual según el estado de hipotecado indicado (true o false)
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada) {
        ArrayList<TituloPropiedad> nuevo = new ArrayList<>();
        int numprops = this.getPropiedades().size();
        for (int i = 0; i < numprops; i++) {
            if (this.getPropiedades().get(i).getHipotecada() == hipotecada) {
                nuevo.add(this.getPropiedades().get(i));
            }
        }
        return nuevo;
    }

    void pagarAlquiler() {
        int costeAlquiler = ((Calle)getCasillaActual()).pagarAlquiler();
        this.modificarSaldo(-costeAlquiler);
    }

    // Se reduce el saldo en la cantidad indicada en el atributo coste de la casillaActual.
    protected void pagarImpuesto() {
        int aPagar = this.getCasillaActual().getCoste();
        int tengo = this.getSaldo();
        this.setSaldo(tengo - aPagar);
    }

    void pagarLibertad(int cantidad) {
        boolean tengoSaldo = this.tengoSaldo(cantidad);
        if (tengoSaldo) {
            this.setEncarcelado(false);
            this.modificarSaldo(-cantidad);
        }
    }

    void setCartaLibertad(Sorpresa carta) {
        cartaLibertad = carta;
    }

    void setCasillaActual(Casilla casilla) {
        casillaActual = casilla;
    }

    void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    // Cierto sólo si cartaLibertad no es nula.
    boolean tengoCartaLibertad() {
        return (this.getCartaLibertad() != null);
    }

    // Devuelve verdadero si el saldo del jugador es superior a cantidad.
    protected boolean tengoSaldo(int cantidad) {
        return (this.getSaldo() > cantidad);
    }

    void venderPropiedad(Casilla casilla) {
        TituloPropiedad titulo = casilla.getTitulo();
        this.eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        this.modificarSaldo(precioVenta);

    }

    // Método toString
    @Override
    public String toString() {
        if (cartaLibertad == null) {
            return "Jugador: " + this.getNombre() + "\nEncarcelado=" + this.getEncarcelado() + ", saldo=" + this.getSaldo() + ", capital=" + this.obtenerCapital() + ", casilla actual=" + this.getCasillaActual() + ", carta de libertad = NO\n"
                    + "Propiedades:\n" + this.getPropiedades().toString() + "\n";
        } else {
            return "Jugador: " + this.getNombre() + "\nEncarcelado=" + this.getEncarcelado() + ", saldo=" + this.getSaldo() + ", capital=" + this.obtenerCapital() + ", casilla actual=" + this.getCasillaActual() + ", carta de libertad =" + this.getCartaLibertad().toString() + "\n"
                    + "Propiedades:\n" + this.getPropiedades().toString() + "\n";
        }
    }
}
