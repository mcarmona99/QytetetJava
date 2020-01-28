/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author joseymanuel
 */
public class Qytetet {
    public static int MAX_JUGADORES;
    static int NUM_SORPRESAS;
    public static int NUM_CASILLAS;
    static int PRECIO_LIBERTAD;
    static int SALDO_SALIDA;
    static private Tablero tablero;
    private Sorpresa cartaActual;
    private ArrayList<Sorpresa> mazo;
    private Dado dado;
    private Jugador jugadorActual;
    private ArrayList<Jugador> jugadores;
    private EstadoJuego estadoJuego;
    private int indiceJA;
    
    // Para construir
    private static final Qytetet instance = new Qytetet();
        
    // Constructor privado para asegurar que no se puede instanciar desde otras clases
    private Qytetet() {
        tablero = new Tablero(); 
        MAX_JUGADORES = 4;
        NUM_SORPRESAS = 10;
        NUM_CASILLAS = 20;
        PRECIO_LIBERTAD = 200;
        SALDO_SALIDA = 1000;    
        mazo = new ArrayList<> ();
        cartaActual = null;
        dado = Dado.getInstance();
        jugadores = new ArrayList<> ();
        jugadorActual = null;
        estadoJuego = null;
        indiceJA = 0;
    }
    
    // Método para obtener objeto creado
    public static Qytetet getInstance(){
        return instance;
    }
    
    void actuarSiEnCasillaEdificable(){
        boolean deboPagar = this.getJugadorActual().deboPagarAlquiler();
        if (deboPagar){
            this.getJugadorActual().pagarAlquiler();
            if(getJugadorActual().getSaldo()<=0)
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
        Casilla casilla = this.obtenerCasillaJugadorActual();
        boolean tengoPropietario = ((Calle)casilla).tengoPropietario();
        if (tengoPropietario){
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        else {
            this.setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
        }
    }
    
    void actuarSiEnCasillaNoEdificable(){
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla casillaActual = this.getJugadorActual().getCasillaActual();
        if (casillaActual.getTipo() == TipoCasilla.IMPUESTO){
            this.getJugadorActual().pagarImpuesto();
            if (this.getJugadorActual().getSaldo() <= 0){
                this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
        }
        else {
            if (casillaActual.getTipo() == TipoCasilla.JUEZ){
                this.encarcelarJugador();
            }
            else {
                if (casillaActual.getTipo() == TipoCasilla.SORPRESA){
                    cartaActual = this.getMazo().remove(0);
                    this.setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
                }
            }
        }
    }
    
    public void aplicarSorpresa(){
        Sorpresa cartaActual = this.getCartaActual();
        int valor = cartaActual.getValor();
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        if (cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL) {
            this.getJugadorActual().setCartaLibertad(cartaActual);
            
        }
        else {
            this.getMazo().add(cartaActual);
        if (cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR){
            this.getJugadorActual().modificarSaldo(valor);
            if (this.getJugadorActual().getSaldo() <= 0){
                this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
        }
        else if (cartaActual.getTipo() == TipoSorpresa.IRACASILLA){
            boolean casillaCarcel = this.getTablero().esCasillaCarcel(valor);
            if (casillaCarcel){
                this.encarcelarJugador();
            }
            else {
                this.mover(valor);
            }
        }
        else if (cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL){
            int numeroTotal = this.getJugadorActual().cuantasCasasHotelesTengo();
            this.getJugadorActual().modificarSaldo(valor * numeroTotal);
                if (this.getJugadorActual().getSaldo() <= 0){
                    this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }            
        }
        else if (cartaActual.getTipo() == TipoSorpresa.PORJUGADOR){
            for (int i = 0; i < this.getJugadores().size(); i++){
                Jugador jugador = this.getJugadores().get(i);
                if (jugador != this.getJugadorActual()){
                    jugador.modificarSaldo(-valor);
                    if (jugador.getSaldo() < 0){
                        this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                    }
                    this.jugadorActual.modificarSaldo(valor);
                    if (this.getJugadorActual().getSaldo() <= 0){
                        this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);                       
                    }
                }
            }
        }
        else if (cartaActual.getTipo() == TipoSorpresa.CONVERTIRME){
            Especulador nuevo = this.jugadorActual.convertirme(valor);
            this.getJugadores().set(indiceJA, this.getJugadorActual());
            this.setJugadorActual(nuevo);
        }
        }        
    }
    
    public boolean cancelarHipoteca(int numeroCasilla){        
        Casilla casilla = this.getTablero().obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        boolean cancelada = this.getJugadorActual().cancelarHipoteca(titulo);
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);  
        return cancelada;
    }
    
    public boolean comprarTituloPropiedad(){
        boolean comprado= getJugadorActual().comprarTituloPropiedad();
        if (comprado)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return comprado;  
    }
    
    public boolean edificarCasa(int numeroCasilla){
        boolean edificada=false;
        Casilla casilla=getTablero().obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo= casilla.getTitulo();
        edificada=getJugadorActual().edificarCasa(titulo);
        if (edificada)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return edificada;     
    }
    
    public boolean edificarHotel(int numeroCasilla){
        boolean edificada=false;
        Casilla casilla=getTablero().obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo= casilla.getTitulo();
        edificada=getJugadorActual().edificarHotel(titulo);
        if (edificada)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return edificada;       
    }
    
    /*public Boolean jugadorActualEnCalleLibre(){
        return getJugadorActual().estoyEnCalleLibre();
    }    
    
    public Boolean jugadorActualEncarcelado(){
        return getJugadorActual().getEncarcelado();
    } */
    //LOS DOS METODOS ANTERIORES SON ELIMINADOS
    //EN EL GUION P3 V5
    
    void encarcelarJugador(){
        if (this.getJugadorActual().deboIrACarcel()){
            Casilla casillaCarcel = this.getTablero().getCarcel();
            this.getJugadorActual().irACarcel(casillaCarcel);
            this.setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else {
            Sorpresa carta = this.getJugadorActual().devolverCartaLibertad();
            this.getMazo().add(carta);
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
            
    public Sorpresa getCartaActual(){
        return cartaActual;
    }
    
    Dado getDado(){
        return dado;
    }
    
    public EstadoJuego getEstadoJuego(){
        return estadoJuego;
    }
    
    public Jugador getJugadorActual(){
        return jugadorActual;
    }
    
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    
    // Método get mazo o consultor con visibilidad de paquete
    ArrayList<Sorpresa> getMazo(){
        return mazo;
    }
    
    public int getValorDado(){
        return getDado().getValor();
    }

    public int getPrecioLibertad(){
        return PRECIO_LIBERTAD;
    }
    
    public void hipotecarPropiedad(int numeroCasilla){
        Casilla casilla = this.getTablero().obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        this.getJugadorActual().hipotecarPropiedad(titulo);
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR); 
    }
    
    private void inicializarCartasSorpresa(){
        mazo.add(new Sorpresa("Has encontrado la tumba de Tutankamón, saqueas su tesoro. Ganas 500.", 500, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Te has perdido en el desierto y te han robado unos nómadas. Pierdes 1000.", -1000, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Enhorabuena, tienes una audiencia con el faraón. Ve a la casilla 5.", 5, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Has sugerido que la tierra podria ser redonda. Vas a la carcel.", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Te apetece hacer turismo, y visitas las piramides de Giza. Vas a Giza.", 19, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Nunca viene mal ser el cuñado del Faraón. Carta Libertad.", 0, TipoSorpresa.SALIRCARCEL));
        mazo.add(new Sorpresa("La sequia de este año ha echado a perder tus cosechas. Pierdes 100 por cada edificación.", -100, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("El dios Ra te ha bendecido con buenas cosechas. Ganas 200 por cada edificación.", 200, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Durante tu viaje a Giza, tus vecinos aprovecharon para robarte. Pierdes 100 por cada jugador.", -100, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Te haces pasar por el faraón y cobras impuestos a tus vecinos. Ganas 100 por cada jugador.", 100, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Te gusta demasiado el dinero, ahora eres un especulador", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("¿Quien dice que no puede haber una burbuja inmobiliaria en el Antiguo Egipto? Ahora eres un especulador", 5000, TipoSorpresa.CONVERTIRME));
        Collections.shuffle(mazo);
    }  
    
    public void inicializarJuego(ArrayList<String> nombres){
        inicializarJugadores(nombres);
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    
    private void inicializarJugadores(ArrayList<String> nombres){
        for (int i = 0; i < nombres.size(); i++){
            jugadores.add(new Jugador(nombres.get(i)));
        }
    }
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){
        if (metodo == MetodoSalirCarcel.TIRANDODADO){
            int resultado = this.getDado().tirar();
            if (resultado >= 5){
                this.getJugadorActual().setEncarcelado(false);
            }
        }
        else {
            if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
                this.getJugadorActual().pagarLibertad(this.getPrecioLibertad());
            }
        }
        boolean encarcelado = this.getJugadorActual().getEncarcelado();
        if (encarcelado) {
            this.setEstadoJuego(EstadoJuego.JA_ENCARCELADO);            
        }
        else {
            this.setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }
        return !encarcelado;        
    }
    
    public void jugar(){
        int valordado= tirarDado() ;
        int casillaFinal = this.getTablero().obtenerCasillaFinal(this.getJugadorActual().getCasillaActual(),valordado).getNumeroCasilla();
        mover(casillaFinal);
    }
    
    void mover(int numCasillaDestino){
        Casilla casillaInicial = obtenerCasillaJugadorActual();
        Casilla casillaFinal = getTablero().obtenerCasillaNumero(numCasillaDestino);
        getJugadorActual().setCasillaActual(casillaFinal);
        
        if (numCasillaDestino<casillaInicial.getNumeroCasilla())
            getJugadorActual().modificarSaldo(SALDO_SALIDA);
        
        if (casillaFinal.soyEdificable())
            actuarSiEnCasillaEdificable();
        else
            actuarSiEnCasillaNoEdificable();
    }
    
    public Casilla obtenerCasillaJugadorActual(){
        Casilla casilla = this.getJugadorActual().getCasillaActual();
        return casilla;
    }
    
    public ArrayList<Casilla> obtenerCasillasTablero(){
        return (this.getTablero().getCasillas());
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<TituloPropiedad> titulos = getJugadorActual().getPropiedades();
        ArrayList<Integer> propiedades = new ArrayList<>();
        int cuantos=titulos.size();
        int i=0;
        int j=0;
        while (i<cuantos && j<getTablero().getCasillas().size()){
            if (getTablero().getCasillas().get(j).getTitulo() == titulos.get(i) ){
                propiedades.add(getTablero().getCasillas().get(j).getNumeroCasilla());
                i++;
            }
            j++;
        }
        return propiedades;
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<TituloPropiedad> titulos = getJugadorActual().getPropiedades();
        ArrayList<Integer> propiedades = new ArrayList<>();
        int cuantos=titulos.size();
        int i=0;
        int j=0;
        while (i<cuantos && j<getTablero().getCasillas().size()){
            if (getTablero().getCasillas().get(j).getTitulo() == titulos.get(i) ){
                if (titulos.get(i).getHipotecada() == estadoHipoteca ){
                    propiedades.add(getTablero().getCasillas().get(j).getNumeroCasilla());}
                i++;
            }
            j++;
        }
        return propiedades;
    }
    
    public void obtenerRanking(){
        Collections.sort(jugadores);   
        System.out.println(jugadores); 
    }
        
    public int obtenerSaldoJugadorActual(){
        return getJugadorActual().getSaldo();
    }
    
    private void salidaJugadores(){
        for (Jugador t: this.getJugadores()){
            t.setCasillaActual(this.getTablero().getCasillas().get(0));
        }
        Random rand = new Random();
        int jugador_sorteado=(rand.nextInt(getJugadores().size()));
        setJugadorActual(getJugadores().get(jugador_sorteado));
        indiceJA=jugador_sorteado;
        setEstadoJuego(EstadoJuego.JA_PREPARADO);
    }
    
    private void setCartaActual(Sorpresa cartaActual){
        this.cartaActual = cartaActual;
    }
    
    private void setJugadorActual(Jugador jugador){
        this.jugadorActual=jugador;
    }    
    
    public void setEstadoJuego(EstadoJuego estado1){
        estadoJuego = estado1;
    }
    
    public void siguienteJugador(){
        indiceJA=((indiceJA + 1) % getJugadores().size());
        setJugadorActual(getJugadores().get(indiceJA));
        if(getJugadorActual().getEncarcelado())
            setEstadoJuego(EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD);
        else
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
    }
    
    int tirarDado(){
        return getDado().tirar();
    }
    
    public void venderPropiedad(int numeroCasilla){
        Casilla casilla=this.getTablero().obtenerCasillaNumero(numeroCasilla);
        getJugadorActual().venderPropiedad(casilla);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
    }
    
    // Método get tablero o consultor con visibilidad de paquete
    public Tablero getTablero(){
        return tablero;
    }    
    
    @Override
    public String toString(){
        return "QYTETET:\n" + this.getTablero().toString() + "MAX JUGADORES=" + this.MAX_JUGADORES + ", NUM SORPRESAS=" + this.NUM_SORPRESAS + ", NUM CASILLAS=" + this.NUM_CASILLAS +
                ", PRECIO LIBERTAD=" + this.PRECIO_LIBERTAD + ", SALDO SALIDA=" + this.SALDO_SALIDA + "\nJUGADORES:" + this.getJugadores().toString() + "\nMAZO:" + this.getMazo().toString();
    }     
}