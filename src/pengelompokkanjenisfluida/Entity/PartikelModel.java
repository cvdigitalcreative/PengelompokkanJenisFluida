/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pengelompokkanjenisfluida.Entity;

/**
 *
 * @author shaff
 */
public class PartikelModel {
    private FluidaMinyakBumiModel[] posisi; 
    private FluidaMinyakBumiModel[] kecepatan; 
    private FluidaMinyakBumiModel[] pbest; 
    
    public PartikelModel() {
    }

    public PartikelModel(FluidaMinyakBumiModel[] posisi, FluidaMinyakBumiModel[] kecepatan, FluidaMinyakBumiModel[] pbest) {
        this.posisi = posisi;
        this.kecepatan = kecepatan;
        this.pbest = pbest;
    }

    public FluidaMinyakBumiModel[] getPosisi() {
        return posisi;
    }

    public void setPosisi(FluidaMinyakBumiModel[] posisi) {
        int i;
        
        this.posisi = new FluidaMinyakBumiModel[posisi.length];
        
        for(i=0; i<posisi.length; i++){
            this.posisi[i] = posisi[i];
        }
    }

    public FluidaMinyakBumiModel[] getKecepatan() {
        return kecepatan;
    }

    public void setKecepatan(FluidaMinyakBumiModel[] kecepatan) {
        int i;
        
        this.kecepatan = new FluidaMinyakBumiModel[kecepatan.length];
        
        for(i=0; i<kecepatan.length; i++){
            this.kecepatan[i] = kecepatan[i];
        }
    }

    public FluidaMinyakBumiModel[] getPbest() {
        return pbest;
    }

    public void setPbest(FluidaMinyakBumiModel[] pbest) {
        int i;
        
        this.pbest = new FluidaMinyakBumiModel[pbest.length];
        
        for(i=0; i<pbest.length; i++){
            this.pbest[i] = pbest[i];
        }
    }
    
    
}
