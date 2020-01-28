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
public class Sorpresa {
    private String texto;
    private TipoSorpresa tipo;
    private int valor;
    
    // Constructor con parametros
    public Sorpresa(String texto, int valor, TipoSorpresa tipo){
        this.texto = texto;
        this.tipo = tipo;
        this.valor = valor;
    }
    
    // Metodos get o consultores con visibilidad de paquete
    String getTexto(){
        return texto;
    }
    TipoSorpresa getTipo(){
        return tipo;
    }
    int getValor(){
        return valor;
    }
    
    // Método toString: devuelve un String con e estado del objeto correspondiente
    @Override
    public String toString(){
        return "Sorpresa{" + "texto=" + texto + ", valor=" + Integer.toString(valor) + ", tipo=" + tipo + "}\n";
    }
}
