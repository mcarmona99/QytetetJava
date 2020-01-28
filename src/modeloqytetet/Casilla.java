/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author joseymanuel
 */
abstract public class Casilla {

    private int numeroCasilla;
    private int coste;
    //private TipoCasilla tipo;
    //private TituloPropiedad titulo;

    public Casilla(int numeroCasilla1,
            int coste1) {
        numeroCasilla = numeroCasilla1;
        coste = coste1;

    }

    /*
    // Constructor de casillas tipo CALLE
    public Casilla(int numeroCasilla1, TituloPropiedad titulo1) {
        this.numeroCasilla = numeroCasilla1;
        setTitulo(titulo1);
        this.tipo = TipoCasilla.CALLE;
    }
    
    // Constructor de casillas tipo NO CALLE
    public Casilla(int numeroCasilla1, TipoCasilla tipo1) {
        this.titulo = null;
        this.numeroCasilla = numeroCasilla1;
        this.coste = 0;
        this.tipo = tipo1;
    }  
    
    // Constructor de casillas de impuesto
    public Casilla(int numeroCasilla1, int coste1) {
        this.titulo = null;
        this.numeroCasilla = numeroCasilla1;
        this.coste = coste1;
        this.tipo = TipoCasilla.IMPUESTO;
    }*/

 /*TituloPropiedad asignarPropietario (Jugador jugador){
        this.getTitulo().setPropietario(jugador);
        return this.getTitulo();
    }*/
    // Métodos get o consultores
    public int getNumeroCasilla() {
        return numeroCasilla;
    }

    int getCoste() {
        return coste;
    }

    abstract protected TipoCasilla getTipo();

    abstract protected TituloPropiedad getTitulo();

    /*int pagarAlquiler(){
        int costeAlquiler= this.getTitulo().pagarAlquiler();
        return costeAlquiler;
    }*/
 /*boolean propietarioEncarcelado(){
        return getTitulo().propietarioEncarcelado();
    }*/
    // Método setTitulo
    /* private void setTitulo(TituloPropiedad titulo1) {
        this.titulo = titulo1;
        this.coste = titulo1.getPrecioCompra();
    }*/
    abstract protected boolean soyEdificable();/* {
        return getTipo() == TipoCasilla.CALLE;
    }*/

    /*boolean tengoPropietario(){
        return getTitulo().tengoPropietario();
    }*/
    // Método toString
    /*@Override
    public String toString(){
        if (tipo == TipoCasilla.CALLE) {
            return "Casilla{" + "numeroCasilla=" + this.getNumeroCasilla() + ", coste=" + this.getCoste() + ", TipoCasilla=" + this.tipo + ", TituloPropiedad=" + this.getTitulo().toString() + "}\n";
        }
        else {  return "Casilla{" + "numeroCasilla=" + this.getNumeroCasilla() + ", TipoCasilla=" + this.tipo + "}\n";
        }
    }*/

    @Override
    public String toString() {
        return "Casilla{" + "numeroCasilla=" + numeroCasilla + ", coste=" + coste + '}';
    }
    
    

    public void setCoste(int coste) {
        this.coste=coste;
    }
}