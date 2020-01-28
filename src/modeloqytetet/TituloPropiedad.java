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
public class TituloPropiedad {
    private String nombre;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private int numHoteles;
    private int numCasas;
    private Jugador propietario;
    
    
     // Constructor con parametros
    public TituloPropiedad(String nombre1, int precioCompra1, int alquilerBase1, float factorRevalorizacion1, int hipotecaBase1, int precioEdificar1){
        this.nombre = nombre1;
        this.hipotecada = false;
        this.precioCompra = precioCompra1;
        this.alquilerBase = alquilerBase1;
        this.factorRevalorizacion = factorRevalorizacion1;
        this.hipotecaBase = hipotecaBase1;
        this.precioEdificar = precioEdificar1;
        this.numHoteles = 0;
        this.numCasas = 0;
        this.propietario = null;
    }   

    int calcularCosteCancelar(){
        int costeCancelar = (this.calcularCosteHipotecar() + (this.calcularCosteHipotecar() / 10));
        return costeCancelar;
    }
    
    int calcularCosteHipotecar(){
        int costeHipoteca = this.getHipotecaBase() + ( this.getNumCasas() * ( this.getHipotecaBase() / 2)) + (this.getNumHoteles() * this.getHipotecaBase());
        return costeHipoteca;
    }
    
    int calcularImporteAlquiler(){
        int costeAlquiler = this.alquilerBase + (100 * (this.numCasas / 2 + this.numHoteles * 2));
        return costeAlquiler;
    }
    
    int calcularPrecioVenta(){
        int precioVenta= (int) (this.getPrecioCompra() + 
                (getNumCasas() + getNumHoteles())
                *getPrecioEdificar()* getFactorRevalorizacion());
        return precioVenta;
    }
    
    void cancelarHipoteca(){
        this.setHipotecada(false);
    }
    
    void setNumHoteles(int numHoteles) {
        this.numHoteles = numHoteles;
    }

    void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }
    
    void edificarCasa(){
        this.setNumCasas(numCasas+1);
    }
    
    void edificarHotel(){
        this.setNumHoteles(numHoteles+1);
    }
    
    // Métodos get o consultores
    String getNombre() {
        return nombre;
    }

    Boolean getHipotecada() {
        return hipotecada;
    }

    int getPrecioCompra() {
        return precioCompra;
    }
    
    int getAlquilerBase() {
        return alquilerBase;
    }

    float getFactorRevalorizacion() {
        return factorRevalorizacion;
    }

    int getHipotecaBase() {
        return hipotecaBase;
    }

    int getPrecioEdificar() {
        return precioEdificar;
    }

    int getNumHoteles() {
        return numHoteles;
    }

    int getNumCasas() {
        return numCasas;
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    int hipotecar(){
        this.setHipotecada(true);
        int costeHipoteca = this.calcularCosteHipotecar();
        return costeHipoteca;
    }
    
    int pagarAlquiler(){
        int costeAlquiler= this.calcularImporteAlquiler();
        this.getPropietario().modificarSaldo(costeAlquiler);
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado(){
        return getPropietario().getEncarcelado();
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
    // Método cambiar hipotecada
    void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }
    
    void setPropietario(Jugador propietario){
        this.propietario = propietario;
    }
    
    boolean tengoPropietario(){
        return (this.getPropietario() != null);
    }
    
    // Método toString: devuelve un String con e estado del objeto correspondiente
    @Override
    public String toString(){
        if (this.tengoPropietario()){
            return "TituloPropiedad{" + "nombre=" + nombre + ", propietario=" + propietario.getNombre() +  ", hipotecada=" + hipotecada +
                    ", precioCompra=" + precioCompra + " €, alquilerBase=" + alquilerBase + 
                    " €, factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase +
                    " €, precioEdificar=" + precioEdificar + " €, numHoteles=" + numHoteles +
                    ", numCasas="+ numCasas + "}\n";            
        }
        else return "TituloPropiedad{" + "nombre=" + nombre + ", hipotecada=" + hipotecada +
                    ", precioCompra=" + precioCompra + " €, alquilerBase=" + alquilerBase + 
                    " €, factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase +
                    " €, precioEdificar=" + precioEdificar + " €, numHoteles=" + numHoteles +
                    ", numCasas="+ numCasas + "}\n";
    }    
}
