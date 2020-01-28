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
public class Tablero {
    private ArrayList<Casilla> casillas = new ArrayList<> ();
    private Casilla carcel;
    private static int NUM_CASILLAS = 20;
    
    public Tablero(){
        this.inicializar();
    }
    
    boolean esCasillaCarcel(int numeroCasilla){
        return (casillas.get(numeroCasilla).getTipo() == TipoCasilla.CARCEL);
    }
    
    // Método get casillas o consultor con visibilidad de paquete
    public ArrayList<Casilla> getCasillas(){
        return casillas;
    }  
    
    Casilla getCarcel(){
        return carcel;
    }
    
    // Metodo mio para poder probar
    void setCarcel(Casilla casilla){
        carcel = casilla;
    }
    
    /*
    Casilla: 12 Calle + 3 sorpresa + 1 carcel + 1 juez + 1 parking + 1 impuesto + 1 salida     
    TituloPropiedad(String nombre1 
    int precioCompra1 = (500-1000)
    int alquilerBase1 = (50-100)
    float factorRevalorizacion1 = (-0.2 to -0.1) or (0.1-0.2)
    int hipotecaBase1 = (150-1000)
    int precioEdificar1 = (250-750))
    */
    private void inicializar() {
        casillas.add(new OtraCasilla(0, 0, TipoCasilla.SALIDA));
        casillas.add(new Calle(1, 500, new TituloPropiedad("Memphis", 500, 50, (float) -0.2, 150, 250)));
        casillas.add(new Calle(2, 550, new TituloPropiedad("Abu Simbel", 550, 55, (float) -0.15, 200, 300)));
        casillas.add(new OtraCasilla(3, 0, TipoCasilla.SORPRESA));
        casillas.add(new Calle(4, 600, new TituloPropiedad("Cairo", 600, 60, (float) -0.10, 250, 350)));
        casillas.add(new OtraCasilla(5, 300, TipoCasilla.IMPUESTO));
        casillas.add(new Calle(6, 650, new TituloPropiedad("Amarha", 650, 65, (float) 0.1, 300, 400)));
        casillas.add(new Calle(7, 700, new TituloPropiedad("Karnak", 700, 70, (float) 0.15, 350, 450)));
        casillas.add(new Calle(8, 750, new TituloPropiedad("Koptos", 750, 75, (float) 0.2, 400, 500)));
        casillas.add(new OtraCasilla(9, 0, TipoCasilla.SORPRESA));
        casillas.add(new Calle(10, 1000, new TituloPropiedad("Nagada", 1000, 100, (float) 0.2, 1000, 750)));
        casillas.add(new OtraCasilla(11, 0, TipoCasilla.JUEZ));
        casillas.add(new Calle(12, 800, new TituloPropiedad("Abydos", 800, 80, (float) 0.1, 450, 550)));
        casillas.add(new Calle(13, 850, new TituloPropiedad("Rosetta", 850, 85, (float) 0.1, 500, 600)));
        casillas.add(new OtraCasilla(14, 0, TipoCasilla.PARKING));
        casillas.add(new Calle(15, 900, new TituloPropiedad("Alejandría", 900, 90, (float) 0.15, 750, 700)));
        carcel = new OtraCasilla(16, 0, TipoCasilla.CARCEL);
        casillas.add(carcel);
        casillas.add(new Calle(17, 950, new TituloPropiedad("Luxor", 950, 90, (float) 0.2, 900, 750)));
        casillas.add(new OtraCasilla(18, 0, TipoCasilla.SORPRESA));
        casillas.add(new Calle(19, 1000, new TituloPropiedad("Giza", 1000, 100, (float) 0.2, 1000, 750)));
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){
        int nueva_posicion = (casilla.getNumeroCasilla() + desplazamiento) % this.getCasillas().size();
        return casillas.get(nueva_posicion);       
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla){
        return casillas.get(numeroCasilla);
    }
    
    @Override
    public String toString(){
        String resultado = "Tablero\n";
        for (int i = 0; i < this.getCasillas().size(); i++){
             resultado = resultado + this.getCasillas().get(i).toString();
        }
        return resultado;
    }        
}
