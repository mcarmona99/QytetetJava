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
public class OtraCasilla extends Casilla {
    private TipoCasilla tipo;

    public OtraCasilla(int numeroCasilla1, int coste1, TipoCasilla tipo1) {
        super(numeroCasilla1, coste1);
        tipo=tipo1;
    }
    
    @Override
    protected TipoCasilla getTipo(){
        return tipo;
    }
    
    @Override
    protected boolean soyEdificable(){
        return false;
    }
    
    @Override
    protected TituloPropiedad getTitulo(){
        return null;
    }

    @Override
    public String toString() {
        return "OtraCasilla{" + super.toString() + "tipo=" + tipo + '}';
    }
    
    
}