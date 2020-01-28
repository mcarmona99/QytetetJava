/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Scanner;
import static modeloqytetet.TipoSorpresa.*;

/**
 *
 * @author joseymanuel
 */
public class PruebaQytetet {
    private static final Scanner in = new Scanner(System.in);
    
    // Método que devuelve vector de sorpresas positivas
    private static ArrayList<Sorpresa> positivos(Qytetet juego){
        ArrayList<Sorpresa> mazoPos = new ArrayList<> ();
        for (Sorpresa t: juego.getMazo()){
            if (t.getValor() > 0){
                mazoPos.add(t);        
            }
        }
        return mazoPos;
    }
    
    // Método que devuelve vector de sorpresas tipo IRACASILLA
    private static ArrayList<Sorpresa> movimientosacasilla(Qytetet juego){
        ArrayList<Sorpresa> mazoMov = new ArrayList<> ();
        for (Sorpresa t: juego.getMazo()){
            if (t.getTipo() == IRACASILLA){
                mazoMov.add(t);        
            }
        }
        return mazoMov;
    } 
        
    // Método que devuelve vector de sorpresas del tipo especificado en el parametro
    private static ArrayList<Sorpresa> sorpresastipo(Qytetet juego, TipoSorpresa param){
        ArrayList<Sorpresa> mazoTipo = new ArrayList<> ();
        for (Sorpresa t: juego.getMazo()){
            if (t.getTipo() == param){
                mazoTipo.add(t);        
            }
        }
        return mazoTipo;
    }     
    
    static ArrayList<String> getNombreJugadores(){
        ArrayList<String> nombres = new ArrayList<> ();
        int numero_jugadores;
        do {
            System.out.println("Introduzca número de jugadores (2 a 4): ");
            numero_jugadores = in.nextInt();            
        } while ((numero_jugadores < 2) || (numero_jugadores > 4));
        for (int i = 0; i < numero_jugadores; i++){
            System.out.println("Introduzca el nombre del jugador " + (i+1) + ": ");            
            nombres.add(in.next());
        }
        return nombres;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Qytetet juego = Qytetet.getInstance();
        ArrayList<String> nombres = getNombreJugadores();
        juego.inicializarJuego(nombres);

        System.out.println("\n\n----------ELEGIR OPCION-----------\n\n");
        Scanner in = new Scanner(System.in);
        int opcion = 1;
        while (opcion != -1){
            System.out.println("\n\n\n\n");
            System.out.println("1: Prueba el método mover\n");
            System.out.println("2: Prueba el método pagarAlquiler\n");  
            System.out.println("3: Prueba las sorpresas\n");   
            System.out.println("4: Pruebas con titulo de propiedad (hipotecar, cancelar hipoteca y vender)\n");
            System.out.println("5: Prueba a salir de la carcel\n");    
            System.out.println("6: Ranking\n"); 
            System.out.println("7: Pruebas con tipo casilla impuesto y juez\n"); 
            System.out.println("8: Pruebas con especulador y construcciones\n"); 
            System.out.println("0: Exit\n");            
            opcion = in.nextInt(); 
            switch (opcion) {
                      case 1:
                          System.out.println("Prueba del método mover\n");
                          System.out.println("Estado inicial de jugadores:\n");      
                          System.out.println(juego.getJugadores().toString());
                          System.out.println("Un movimiento de cada jugador:\n"); 
                          juego.jugar();
                          juego.siguienteJugador();
                          juego.jugar();
                          System.out.println(juego.getJugadores().toString());
                          System.out.println("Otro movimiento de cada jugador:\n"); 
                          juego.siguienteJugador();
                          juego.jugar();
                          juego.siguienteJugador();
                          juego.jugar();
                          System.out.println(juego.getJugadores().toString());
                          break;
                      case 2:
                          System.out.println("Prueba del método pagar alquiler\n");
                          System.out.println(juego.getJugadores().toString());                          
                          juego.mover(10);
                          juego.comprarTituloPropiedad();
                          juego.siguienteJugador();
                          juego.mover(10);
                          System.out.println(juego.getJugadores().toString());
                          System.out.println("Prueba con casas/hoteles edificados\n");
                          juego.siguienteJugador();
                          juego.edificarCasa(10);
                          juego.edificarHotel(10);
                          juego.siguienteJugador();
                          juego.mover(10);
                          System.out.println(juego.getJugadores().toString());
                          break;   
                      case 3:
                          System.out.println("Prueba de sorpresas\nEstado inicial del jugador:\n");
                          System.out.println(juego.getJugadorActual().toString());                          
                          for (int i = 0; i < 10; i++){
                            juego.mover(3);                              
                            juego.aplicarSorpresa();
                            System.out.println("Sorpresa " + i + "\n"); 
                            System.out.println(juego.getCartaActual().toString());                          
                            System.out.println(juego.getJugadorActual().toString());
                          } 
                          break;  
                      case 4:
                          System.out.println("Pruebas con titulo de propiedad (hipotecar, cancelar hipoteca y vender):\n");
                          juego.edificarCasa(10); //LAS CASAS SE EDIFICAN ANTES DE COMPRAR EL TITULO!!!!
                          juego.mover(10);
                          juego.comprarTituloPropiedad();
                          System.out.println(juego.getJugadorActual().toString());
                          juego.hipotecarPropiedad(10);
                          System.out.println(juego.getJugadorActual().toString());
                          juego.cancelarHipoteca(10);
                          System.out.println(juego.getJugadorActual().toString());
                          juego.venderPropiedad(10);
                          System.out.println(juego.getJugadorActual().toString());

                          break;      
                      case 5:
                          System.out.println("Prueba a salir de la carcel:\n");
                          juego.mover(11);
                          System.out.println("Jugador tras caer en juez\n");
                          System.out.println(juego.getJugadorActual().toString());
                          if (juego.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO)){
                            System.out.println("Jugador sale de carcel tirando dado\n");
                          }else
                            System.out.println("Jugador no sale de carcel tirando dado\n");
                          System.out.println(juego.getJugadorActual().toString());

                          juego.mover(11);

                          if (juego.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD)){
                            System.out.println("Jugador sale de carcel pagando libertad\n");
                          }else
                            System.out.println("Jugador no sale de carcel pagando libertad\n");
                          System.out.println(juego.getJugadorActual().toString());


                          break;   
                      case 6:
                          System.out.println("Ranking:\n");
                          juego.getJugadorActual().setCasillaActual(juego.getTablero().obtenerCasillaNumero(10));
                          juego.comprarTituloPropiedad();
                          juego.siguienteJugador();
                          juego.mover(10);         
                          juego.obtenerRanking();
                          break; 
                      case 7:
                          System.out.println("Pruebas con tipo casilla impuesto y juez\n");
                          juego.mover(5);
                          System.out.println("Jugador tras pagar impuesto\n");
                          System.out.println(juego.getJugadorActual().toString());  
                          juego.mover(11);
                          System.out.println("Jugador tras caer en juez\n");
                          System.out.println(juego.getJugadorActual().toString());  
                          break;
                      case 8:
                          System.out.println("Pruebas con especulador y construcciones\n");
                          juego.mover(3);
                          juego.aplicarSorpresa();
                          System.out.println(juego.getJugadorActual().toString()); 
                          juego.mover(1);
                          juego.comprarTituloPropiedad();
                          juego.edificarCasa(1);
                          juego.edificarCasa(1);
                          juego.edificarHotel(1);
                          System.out.println("Jugador tras comprar 2 casas y 1 hotel\n");
                          System.out.println(juego.getJugadorActual().toString());  
                          juego.edificarCasa(1);
                          juego.edificarCasa(1);
                          juego.edificarCasa(1);
                          juego.edificarHotel(1);
                          juego.edificarCasa(1);
                          juego.edificarCasa(1);   
                          juego.edificarCasa(1);
                          juego.edificarCasa(1); 
                          juego.edificarCasa(1);
                          juego.edificarCasa(1);     
                          juego.edificarHotel(1);
                          System.out.println("Jugador tras comprar 5 casas y 1 hotel\n");
                          System.out.println(juego.getJugadorActual().toString());                            
                          break;                          
                      case 0:
                          System.out.println("Exit\n");
                          opcion = -1;
                          break;                                
                  }       
        }
    }
}
