/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;

import java.util.ArrayList;
import java.util.Scanner;
import controladorqytetet.ControladorQytetet;
import controladorqytetet.OpcionMenu;

/**
 *
 * @author joseYmanuel
 */
public class VistaTextualQytetet {

    ControladorQytetet controlador = ControladorQytetet.getInstance();
    private static final Scanner in = new Scanner(System.in);

    public ArrayList<String> obtenerNombreJugadores() {
        ArrayList<String> nombres = new ArrayList<>();
        int numero_jugadores;
        do {
            System.out.println("Introduzca número de jugadores (2 a 4): ");
            numero_jugadores = in.nextInt();
            in.nextLine();
        } while ((numero_jugadores < 2) || (numero_jugadores > 4));
        for (int i = 0; i < numero_jugadores; i++) {
            System.out.println("Introduzca el nombre del jugador " + (i + 1) + ": ");
            nombres.add(in.nextLine());
        }
        return nombres;
    }

    public int elegirCasilla(int opcionMenu) {
        int elegida;
        ArrayList<Integer> casillasValidas = controlador.obtenerCasillasValidas(opcionMenu);
        if (casillasValidas.size() == 0) {
            elegida = -1;
        } else {
            ArrayList<String> lista = new ArrayList();
            for (int i = 0; i < casillasValidas.size(); i++) {
                lista.add(casillasValidas.get(i).toString());
            }
            String operacionS = leerValorCorrectoCasilla(lista);
            elegida = Integer.parseInt(operacionS);
        }
        return elegida;
    }

    public String leerValorCorrectoCasilla(ArrayList<String> valoresCorrectos) {
        System.out.println("Introduce número de casilla para operación indicada\n");
        int aux;
        for (int i = 0; i < valoresCorrectos.size(); i++) {
            aux = Integer.parseInt(valoresCorrectos.get(i));
            System.out.println("Número de casilla: " + aux);
        }
        String introducido;
        introducido = in.nextLine();
        while (!(valoresCorrectos.contains(introducido))) {
            System.out.println("Introduce número de casilla para operación indicada\n");
            for (int i = 0; i < valoresCorrectos.size(); i++) {
                aux = Integer.parseInt(valoresCorrectos.get(i));
                System.out.println("Número de casilla: " + aux);
            }
            introducido = in.nextLine();
        }
        return introducido;
    }

    public String leerValorCorrectoOperacion(ArrayList<String> valoresCorrectos) {
        int aux;
        String introducido = " ";
        int casillaDeJugador = 0;
        String nombre;
        if (controlador.getModelo().getJugadores().size() == 0) {
            nombre = "(juego no iniciado)";
            casillaDeJugador = 0;
        } else {
            nombre = controlador.getModelo().getJugadorActual().getNombre();
            casillaDeJugador = controlador.getModelo().getJugadorActual().getCasillaActual().getNumeroCasilla();
        }
        while (!(valoresCorrectos.contains(introducido))) {
            System.out.println("Jugador: " + nombre + " en casilla " + casillaDeJugador + " --> Elige una opcion\n");
            for (int i = 0; i < valoresCorrectos.size(); i++) {
                aux = Integer.parseInt(valoresCorrectos.get(i));
                OpcionMenu opcion = OpcionMenu.values()[aux];
                System.out.println("Opcion: " + aux + " " + opcion);
            }
            introducido = in.nextLine();
        }
        return introducido;
    }

    public int elegirOperacion() {
        ArrayList<Integer> casillasValidas = controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> listaValidas = new ArrayList();
        for (int i = 0; i < casillasValidas.size(); i++) {
            listaValidas.add(casillasValidas.get(i).toString());
        }
        String operacionS = leerValorCorrectoOperacion(listaValidas);
        int operacionInt = Integer.parseInt(operacionS);
        return operacionInt;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorQytetet controlador = ControladorQytetet.getInstance();
        VistaTextualQytetet ui = new VistaTextualQytetet();
        controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida;
        int casillaElegida = 0;
        boolean necesitaElegirCasilla;

        do {
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = controlador.necesitaElegirCasilla(operacionElegida);
            if (necesitaElegirCasilla) {
                casillaElegida = ui.elegirCasilla(operacionElegida);
            }
            if (!necesitaElegirCasilla || casillaElegida >= 0) {
                System.out.println(controlador.realizarOperacion(operacionElegida, casillaElegida));
            }
        } while (true);
    }
}
