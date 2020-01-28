/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.Random;

/**
 *
 * @author joseymanuel
 */
public class Dado {
    private static final Dado instance = new Dado();
    private int valor;
    
    private Dado(){
    }
    
    public static Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        Random rand = new Random();
        this.valor = rand.nextInt(5) + 1;
        return valor;
    }
    
    public int getValor(){
        return valor;
    }
}
