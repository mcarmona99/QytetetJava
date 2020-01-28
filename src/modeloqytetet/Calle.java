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
public class Calle extends Casilla{
    private TituloPropiedad titulo;
    
    public Calle(int numeroCasilla1, int coste1, TituloPropiedad titulo1) {
        super(numeroCasilla1, coste1);
        titulo=titulo1;
    }

    @Override
    protected TipoCasilla getTipo() {
        return TipoCasilla.CALLE;
    }

    @Override
    protected TituloPropiedad getTitulo() {
        return titulo;
    }

    @Override
    protected boolean soyEdificable() {
        return true;
    }
    
    public boolean tengoPropietario(){
        return getTitulo().tengoPropietario();
    }
    
    public int pagarAlquiler(){
        int costeAlquiler= this.getTitulo().pagarAlquiler();
        return costeAlquiler;
    }
    
    private void setTitulo(TituloPropiedad titulo1) {
        this.titulo = titulo1;
        setCoste(titulo1.getPrecioCompra());
    }
    
    public void asignarPropietario (Jugador jugador){
        this.getTitulo().setPropietario(jugador);
    }
    
   
    @Override
    public String toString() {
        return "Calle{" + super.toString() + "titulo=" + titulo + '}';
    }
    
    
}