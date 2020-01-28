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
public class Especulador extends Jugador {
    private int fianza;
    
    Especulador(Jugador jugador, int fianza){
        super(jugador);
        this.fianza = fianza;
    }
    
    int getFianza(){
        return this.fianza;
    }
    
    @Override
    protected Especulador convertirme (int fianza){
        return this;
    }
    
    @Override
    protected void pagarImpuesto() {
        int aPagar = (this.getCasillaActual().getCoste()) / 2;
        int tengo = this.getSaldo();
        this.setSaldo(tengo - aPagar);
    }    
    
    @Override
    protected boolean deboIrACarcel(){
        boolean deboir = true;
        if (this.tengoCartaLibertad()){
            deboir = false;
        } else {
            deboir = !(this.pagarFianza());
        }
        return deboir;
    }    
    
    private boolean pagarFianza(){
        boolean pagado = false;
        if (this.getSaldo() > this.getFianza()){
            this.setSaldo(this.getSaldo() - this.getFianza());
            pagado = true;
        }
        return pagado;
    }

    @Override
    public String toString() {
        return "Especulador{" + super.toString() + "fianza=" + fianza + '}';
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean puedoconstruir = false;
        int numCasas = titulo.getNumCasas();
        int costeEdificarCasa = titulo.getPrecioEdificar();
        if (numCasas < 8 && tengoSaldo(costeEdificarCasa)){
            puedoconstruir = true;
        }
        return puedoconstruir;
    }
       
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean puedoconstruir = false;
        int numCasas = titulo.getNumCasas();
        int numHoteles = titulo.getNumHoteles();
        int costeEdificarHotel = titulo.getPrecioEdificar();
        if (numCasas >= 4 && numHoteles < 8 && tengoSaldo(costeEdificarHotel)){
            puedoconstruir = true;
        }
        return puedoconstruir;
    }        
    
}
