/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;

import java.util.ArrayList;
import java.util.Scanner;
import modeloqytetet.Qytetet;
import modeloqytetet.EstadoJuego;
import modeloqytetet.MetodoSalirCarcel;

/**
 *
 * @author joseYmanuel
 */
public class ControladorQytetet {

    private static final Scanner in = new Scanner(System.in);
    private Qytetet modelo = Qytetet.getInstance();
    private static final ControladorQytetet instance = new ControladorQytetet();
    private ArrayList<String> nombreJugadores = new ArrayList<>();

    // Constructor privado para asegurar que no se puede instanciar desde otras clases
    private ControladorQytetet() {

    }
    
    public Qytetet getModelo(){
        return modelo;
    }

    public void setNombreJugadores(ArrayList<String> jugadores) {
        nombreJugadores = jugadores;
    }

    public ArrayList<Integer> obtenerOperacionesJuegoValidas() {
        ArrayList<Integer> operaciones = new ArrayList<>();
        if (modelo.getJugadores().isEmpty()) {
            operaciones.add(OpcionMenu.INICIARJUEGO.ordinal());
        } else {

            switch (modelo.getEstadoJuego()) {

                case JA_PREPARADO:
                    operaciones.add(OpcionMenu.JUGAR.ordinal());
                    //operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    break;

                case ALGUNJUGADORENBANCARROTA:
                    operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    //ops.add("TerminarJuego");
                    break;
                case JA_ENCARCELADO:
                    operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                    //operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    break;
                case JA_CONSORPRESA:
                    operaciones.add(OpcionMenu.APLICARSORPRESA.ordinal());
                    //operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    break;
                case JA_PUEDEGESTIONAR:
                    operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                    operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    //operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    break;

                case JA_PUEDECOMPRAROGESTIONAR:
                    operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                    operaciones.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    //operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    break;

                case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                    operaciones.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                    operaciones.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                    //operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    break;

            }
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        }
        return operaciones;
    }

    public boolean necesitaElegirCasilla(int opcionMenu) {
        OpcionMenu opcion = OpcionMenu.values()[opcionMenu];
        boolean necesita = false;
        if ((opcion == OpcionMenu.HIPOTECARPROPIEDAD)
                || (opcion == OpcionMenu.CANCELARHIPOTECA)
                || (opcion == OpcionMenu.EDIFICARCASA)
                || (opcion == OpcionMenu.EDIFICARHOTEL)
                || (opcion == OpcionMenu.VENDERPROPIEDAD)) {
            necesita = true;
        }
        return necesita;

    }

    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu) {
        OpcionMenu opcion = OpcionMenu.values()[opcionMenu];
        ArrayList<Integer> casillasValidas = new ArrayList<>();
        if (opcion == OpcionMenu.HIPOTECARPROPIEDAD) {
            casillasValidas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
        } else if (opcion == OpcionMenu.CANCELARHIPOTECA) {
            casillasValidas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
        } else if ((opcion == OpcionMenu.EDIFICARCASA)
                || (opcion == OpcionMenu.EDIFICARHOTEL)
                || (opcion == OpcionMenu.VENDERPROPIEDAD)) {
            casillasValidas = modelo.obtenerPropiedadesJugador();
        }
        return casillasValidas;
    }

    public String realizarOperacion(int opcionElegida, int casillaElegida) {
        OpcionMenu opcion = OpcionMenu.values()[opcionElegida];
        String resultado = "";
        switch (opcion) {
            case INICIARJUEGO:
                modelo.inicializarJuego(nombreJugadores);
                resultado = "\nJuego iniciado\n";
                break;
            case JUGAR:
                modelo.jugar();
                resultado = "\nValor del dado: " + modelo.getValorDado() + ".Casilla actual: " + modelo.getJugadorActual() + "\n";
                break;
            case APLICARSORPRESA:
                modelo.aplicarSorpresa();
                resultado = modelo.getJugadorActual().toString();
                break;
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                if (modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD)) {
                    resultado = "\nSe ha salido de la cárcel.\n";
                } else {
                    resultado = "\nNO se ha salido de la cárcel.\n";
                }
                break;
            case INTENTARSALIRCARCELTIRANDODADO:
                if (modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO)) {
                    resultado = "\nSe ha salido de la cárcel.\n";
                } else {
                    resultado = "\nNO se ha salido de la cárcel.\n";
                }
                break;
            case COMPRARTITULOPROPIEDAD:
                if (modelo.comprarTituloPropiedad()) {
                    resultado = "\nSe ha comprado la propiedad.\n";
                } else {
                    resultado = "\nNO se ha comprado la propiedad.\n";
                }
                break;
            case HIPOTECARPROPIEDAD:
                modelo.hipotecarPropiedad(casillaElegida);
                resultado = "\nSe ha hipotecado la propiedad.\n";
                break;
            case CANCELARHIPOTECA:
                if (modelo.cancelarHipoteca(casillaElegida)) {
                    resultado = "\nSe ha cancelado la hipoteca.\n";
                } else {
                    resultado = "\nNO se ha cancelado la hipoteca.\n";
                }
                break;
            case EDIFICARCASA:
                if (modelo.edificarCasa(casillaElegida)) {
                    resultado = "\nSe ha edificado una casa.\n";
                } else {
                    resultado = "\nNO se ha edificado casa.\n";
                }
                break;
            case EDIFICARHOTEL:
                if (modelo.edificarHotel(casillaElegida)) {
                    resultado = "\nSe ha edificado un hotel.\n";
                } else {
                    resultado = "\nNO se ha edificado hotel.\n";
                }
                break;
            case VENDERPROPIEDAD:
                modelo.venderPropiedad(casillaElegida);
                resultado = "\nSe ha vendido la propiedad.\n";
                break;
            case PASARTURNO:
                modelo.siguienteJugador();
                resultado = "\nPasando turno.\n";
                break;
            case OBTENERRANKING:
                modelo.obtenerRanking();
                resultado = "\nMostrando Ranking...\n";
                break;
            case TERMINARJUEGO:
                modelo.obtenerRanking();
                resultado = "\nJuego acabado.\n";
                System.exit(0);
            case MOSTRARJUGADORES:
                System.out.println(modelo.getJugadores());
                resultado = "\nMostrando jugadores...\n";
                break;
            case MOSTRARJUGADORACTUAL:
                System.out.println(modelo.getJugadorActual());
                resultado = "\nMostrando jugador actual...\n";
                break;
            case MOSTRARTABLERO:
                System.out.println(modelo.getTablero());
                resultado = "\nMostrando tablero...\n";
                break;
        }
        return resultado;
    }

    // Método para obtener objeto creado
    public static ControladorQytetet getInstance() {
        return instance;
    }

}
