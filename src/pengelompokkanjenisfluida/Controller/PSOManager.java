/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pengelompokkanjenisfluida.Controller;

import java.util.Random;
import pengelompokkanjenisfluida.Entity.FluidaMinyakBumiModel;
import pengelompokkanjenisfluida.Entity.PartikelModel;

/**
 *
 * @author shaff
 */
public class PSOManager {
    private int nPartikel;
    private double w;
    private double c1;
    private double c2;
    private double r1;
    private double r2;
    private double minimum_fitness;
    private int iterasi;
    private boolean flag_kondisi;
    private FluidaMinyakBumiModel[] GbestBefore;
    private FluidaMinyakBumiModel[] Gbest;
    private FluidaMinyakBumiModel[] dataModel;
    PartikelModel[] partikels;

    public PSOManager() {
    }

    public PSOManager(int nPartikel, double w, double c1, double c2, double r1, double r2, int iterasi) {
        this.nPartikel = nPartikel;
        this.w = w;
        this.c1 = c1;
        this.c2 = c2;
        this.r1 = r1;
        this.r2 = r2;
        this.iterasi = iterasi;
        minimum_fitness = 10000;
        flag_kondisi = false;
        Gbest = new FluidaMinyakBumiModel[3];
        GbestBefore = new FluidaMinyakBumiModel[3];
    }
    
    public void inisialisasiDataPSO(FluidaMinyakBumiModel[] dataModel){
        this.dataModel = dataModel;
    }
    
    public void setPartikelPSO(){
        int i, j;
        FluidaMinyakBumiModel[] dataTemp;
        
        partikels = new PartikelModel[nPartikel];
        
        for(i=0; i<nPartikel; i++){
            partikels[i] = new PartikelModel();
            
            dataTemp = getInisialisationPartikel("posisi");
            
            partikels[i].setPosisi(dataTemp);
            partikels[i].setKecepatan(getInisialisationPartikel("kecepatan"));
            partikels[i].setPbest(dataTemp);
        }
        
        Gbest = getInisialisationPartikel("Gbest");
    }
    
    public FluidaMinyakBumiModel[] getInisialisationPartikel(String inisialisasi){
        FluidaMinyakBumiModel dataFluidaChF, dataFluidaCoF, dataFluidaCDO;
        FluidaMinyakBumiModel[] dataCentroid;
        
        dataCentroid = new FluidaMinyakBumiModel[3];
        
        dataFluidaChF = new FluidaMinyakBumiModel();
        dataFluidaCoF = new FluidaMinyakBumiModel();
        dataFluidaCDO = new FluidaMinyakBumiModel();
        Random rand = new Random();
        
        if(inisialisasi.equals("posisi")){
            dataFluidaChF.setViscosity(2.1 + (3 - 2.1) * rand.nextDouble());
            dataFluidaChF.setApi_gravity(1.17 + (2.92 - 1.17) * rand.nextDouble());
            dataFluidaChF.setSalinity(1 + (2.97 - 1) * rand.nextDouble());
            dataFluidaChF.setPorosity(1.83 + (3 - 1.83) * rand.nextDouble());
            dataFluidaChF.setPermeability(1.04 + (2.31 - 1.04) * rand.nextDouble());
            dataCentroid[0] = dataFluidaChF;
            
            dataFluidaCoF.setViscosity(1 + (1.14 - 1) * rand.nextDouble());
            dataFluidaCoF.setApi_gravity(1 + (2.67 - 1) * rand.nextDouble());
            dataFluidaCoF.setSalinity(1.3 + (3 - 1.3) * rand.nextDouble());
            dataFluidaCoF.setPorosity(1 + (2.83 - 1) * rand.nextDouble());
            dataFluidaCoF.setPermeability(2.23 + (3 - 2.23) * rand.nextDouble());
            dataCentroid[1] = dataFluidaCoF;

            dataFluidaCDO.setViscosity(1.2 + (1.32 - 1.2) * rand.nextDouble());
            dataFluidaCDO.setApi_gravity(1.83 + (3 - 1.83) * rand.nextDouble());
            dataFluidaCDO.setSalinity(1.37 + (2.11 - 1.37) * rand.nextDouble());
            dataFluidaCDO.setPorosity(1.33 + (2.5 - 1.33) * rand.nextDouble());
            dataFluidaCDO.setPermeability(1 + (2.61 - 1) * rand.nextDouble());
            dataCentroid[2] = dataFluidaCDO;
        }
        else{
            dataFluidaChF.setViscosity(0);
            dataFluidaChF.setApi_gravity(0);
            dataFluidaChF.setSalinity(0);
            dataFluidaChF.setPorosity(0);
            dataFluidaChF.setPermeability(0);
            dataCentroid[0] = dataFluidaChF;
            
            dataFluidaCoF.setViscosity(0);
            dataFluidaCoF.setApi_gravity(0);
            dataFluidaCoF.setSalinity(0);
            dataFluidaCoF.setPorosity(0);
            dataFluidaCoF.setPermeability(0);
            dataCentroid[1] = dataFluidaCoF;

            dataFluidaCDO.setViscosity(0);
            dataFluidaCDO.setApi_gravity(0);
            dataFluidaCDO.setSalinity(0);
            dataFluidaCDO.setPorosity(0);
            dataFluidaCDO.setPermeability(0);
            dataCentroid[2] = dataFluidaCDO;
        }
        
        return dataCentroid;
    }
    
    public void hitungFitnessPSO(){
        int i, j, k;
        double sum, min_fitness;
        double[] fitness_posisi, fitness_pbest;
        
        fitness_posisi = new double[nPartikel];
        fitness_pbest = new double[nPartikel];
        sum = 0;
        
        for(i=0; i<nPartikel; i++){
            for(j=0; j<3; j++){
                for(k=0; k<dataModel.length; k++){
                    sum += Math.pow(dataModel[k].getViscosity()-partikels[i].getPosisi()[j].getViscosity(), 2)
                            +Math.pow(dataModel[k].getApi_gravity()-partikels[i].getPosisi()[j].getApi_gravity(), 2)
                            +Math.pow(dataModel[k].getSalinity()-partikels[i].getPosisi()[j].getSalinity(), 2)
                            +Math.pow(dataModel[k].getPorosity()-partikels[i].getPosisi()[j].getPorosity(), 2)
                            +Math.pow(dataModel[k].getPermeability()-partikels[i].getPosisi()[j].getPermeability(), 2);
                }
            }
            
            fitness_posisi[i] = sum/(double)dataModel.length;
        }
        
        sum = 0;
        for(i=0; i<nPartikel; i++){
            for(j=0; j<3; j++){
                for(k=0; k<dataModel.length; k++){
                    sum += Math.pow(dataModel[k].getViscosity()-partikels[i].getPbest()[j].getViscosity(), 2)
                            +Math.pow(dataModel[k].getApi_gravity()-partikels[i].getPbest()[j].getApi_gravity(), 2)
                            +Math.pow(dataModel[k].getSalinity()-partikels[i].getPbest()[j].getSalinity(), 2)
                            +Math.pow(dataModel[k].getPorosity()-partikels[i].getPbest()[j].getPorosity(), 2)
                            +Math.pow(dataModel[k].getPermeability()-partikels[i].getPbest()[j].getPermeability(), 2);
                }
            }
            
            fitness_pbest[i] = sum/(double)dataModel.length;
        }
        
        min_fitness = 10000;
        for(i=0; i<nPartikel; i++){
            if(fitness_pbest[i] > fitness_posisi[i]){
                partikels[i].setPbest(partikels[i].getPosisi());
                
                if(min_fitness > fitness_posisi[i]){
                    min_fitness = fitness_posisi[i];
                    
                    if(minimum_fitness > min_fitness){
                        minimum_fitness = min_fitness;
                        Gbest = partikels[i].getPbest();
                        GbestBefore = Gbest;
                    }
                    else if(minimum_fitness == min_fitness){
                        flag_kondisi = true;
                    }
                }
            }
            else{
                if(min_fitness > fitness_pbest[i]){
                    min_fitness = fitness_pbest[i];
                    
                    if(minimum_fitness > min_fitness){
                        minimum_fitness = min_fitness;
                        Gbest = partikels[i].getPbest();
                        GbestBefore = Gbest;
                    }
                    else if(minimum_fitness == min_fitness){
                        flag_kondisi = true;
                    }
                }
            }
        }
    }
    
     public void updateKecepatanNPosisi(){
        int i, j;
        FluidaMinyakBumiModel[] Temp, Temp1, Temp2, kecepatan_baru, posisi_baru;
        
        Temp = new FluidaMinyakBumiModel[3];
        Temp1 = new FluidaMinyakBumiModel[3];
        Temp2 = new FluidaMinyakBumiModel[3];
        kecepatan_baru = new FluidaMinyakBumiModel[3];
        
        for(i=0; i<nPartikel; i++){
            for(j=0; j<3; j++){
                Temp[j] = new FluidaMinyakBumiModel();
                Temp1[j] = new FluidaMinyakBumiModel();
                Temp2[j] = new FluidaMinyakBumiModel();
                kecepatan_baru[j] = new FluidaMinyakBumiModel();
                
                Temp[j].setViscosity(w*partikels[i].getKecepatan()[j].getViscosity());
                Temp[j].setApi_gravity(w*partikels[i].getKecepatan()[j].getApi_gravity());
                Temp[j].setSalinity(w*partikels[i].getKecepatan()[j].getSalinity());
                Temp[j].setPorosity(w*partikels[i].getKecepatan()[j].getPorosity());
                Temp[j].setPermeability(w*partikels[i].getKecepatan()[j].getPermeability());
                
                Temp1[j].setViscosity(c1*r1*(partikels[i].getPbest()[j].getViscosity()-partikels[i].getPosisi()[j].getViscosity()));
                Temp1[j].setApi_gravity(c1*r1*(partikels[i].getPbest()[j].getApi_gravity()-partikels[i].getPosisi()[j].getApi_gravity()));
                Temp1[j].setSalinity(c1*r1*(partikels[i].getPbest()[j].getSalinity()-partikels[i].getPosisi()[j].getSalinity()));
                Temp1[j].setPorosity(c1*r1*(partikels[i].getPbest()[j].getPorosity()-partikels[i].getPosisi()[j].getPorosity()));
                Temp1[j].setPermeability(c1*r1*(partikels[i].getPbest()[j].getPermeability()-partikels[i].getPosisi()[j].getPermeability()));
                
                Temp2[j].setViscosity(c2*r2*(Gbest[j].getViscosity()-partikels[i].getPosisi()[j].getViscosity()));
                Temp2[j].setApi_gravity(c2*r2*(Gbest[j].getApi_gravity()-partikels[i].getPosisi()[j].getApi_gravity()));
                Temp2[j].setSalinity(c2*r2*(Gbest[j].getSalinity()-partikels[i].getPosisi()[j].getSalinity()));
                Temp2[j].setPorosity(c2*r2*(Gbest[j].getPorosity()-partikels[i].getPosisi()[j].getPorosity()));
                Temp2[j].setPermeability(c2*r2*(Gbest[j].getPermeability()-partikels[i].getPosisi()[j].getPermeability()));
                
                kecepatan_baru[j].setViscosity(Temp[j].getViscosity()+Temp1[j].getViscosity()+Temp2[j].getViscosity());                
                kecepatan_baru[j].setApi_gravity(Temp[j].getApi_gravity()+Temp1[j].getApi_gravity()+Temp2[j].getApi_gravity());
                kecepatan_baru[j].setSalinity(Temp[j].getSalinity()+Temp1[j].getSalinity()+Temp2[j].getSalinity());
                kecepatan_baru[j].setPorosity(Temp[j].getPorosity()+Temp1[j].getPorosity()+Temp2[j].getPorosity());
                kecepatan_baru[j].setPermeability(Temp[j].getPermeability()+Temp1[j].getPermeability()+Temp2[j].getPermeability());
                
                switch (j) {
                    case 0:
                        if(kecepatan_baru[j].getViscosity() < 2.1 || kecepatan_baru[j].getViscosity() > 3){
                            if(kecepatan_baru[j].getViscosity() < 2.1){
                                kecepatan_baru[j].setViscosity(2.1);
                            }
                            
                            if(kecepatan_baru[j].getViscosity() > 3){
                                kecepatan_baru[j].setViscosity(3);
                            }
                        }
                        
                        if(kecepatan_baru[j].getApi_gravity() < 1.17 || kecepatan_baru[j].getApi_gravity() > 2.92){
                            if(kecepatan_baru[j].getApi_gravity() < 1.17){
                                kecepatan_baru[j].setApi_gravity(1.17);
                            }
                            
                            if(kecepatan_baru[j].getApi_gravity() > 2.92){
                                kecepatan_baru[j].setApi_gravity(2.92);
                            }
                        }
                        
                        if(kecepatan_baru[j].getSalinity() < 1 || kecepatan_baru[j].getSalinity() > 2.97){
                            if(kecepatan_baru[j].getSalinity() < 1){
                                kecepatan_baru[j].setSalinity(1);
                            }
                            
                            if(kecepatan_baru[j].getSalinity() > 2.97){
                                kecepatan_baru[j].setSalinity(2.97);
                            }
                        }
                        
                        if(kecepatan_baru[j].getPorosity() < 1.83 || kecepatan_baru[j].getPorosity() > 3){
                            if(kecepatan_baru[j].getPorosity() < 1.83){
                                kecepatan_baru[j].setPorosity(1.83);
                            }
                            
                            if(kecepatan_baru[j].getPorosity() > 3){
                                kecepatan_baru[j].setPorosity(3);
                            }
                        }
                        
                        if(kecepatan_baru[j].getPermeability() < 1.04 || kecepatan_baru[j].getPermeability() > 2.31){
                            if(kecepatan_baru[j].getPermeability() < 1.04){
                                kecepatan_baru[j].setPermeability(1.04);
                            }
                            
                            if(kecepatan_baru[j].getPermeability() > 2.31){
                                kecepatan_baru[j].setPermeability(2.31);
                            }
                        }
                        
                        break;
                    case 1:
                        if(kecepatan_baru[j].getViscosity() < 1 || kecepatan_baru[j].getViscosity() > 1.14){
                            if(kecepatan_baru[j].getViscosity() < 1){
                                kecepatan_baru[j].setViscosity(1);
                            }
                            
                            if(kecepatan_baru[j].getViscosity() > 1.14){
                                kecepatan_baru[j].setViscosity(1.14);
                            }
                        }
                        
                        if(kecepatan_baru[j].getApi_gravity() < 1 || kecepatan_baru[j].getApi_gravity() > 2.67){
                            if(kecepatan_baru[j].getApi_gravity() < 1){
                                kecepatan_baru[j].setApi_gravity(1);
                            }
                            
                            if(kecepatan_baru[j].getApi_gravity() > 2.67){
                                kecepatan_baru[j].setApi_gravity(2.67);
                            }
                        }
                        
                        if(kecepatan_baru[j].getSalinity() < 1.3 || kecepatan_baru[j].getSalinity() > 3){
                            if(kecepatan_baru[j].getSalinity() < 1.3){
                                kecepatan_baru[j].setSalinity(1.3);
                            }
                            
                            if(kecepatan_baru[j].getSalinity() > 3){
                                kecepatan_baru[j].setSalinity(3);
                            }
                        }
                        
                        if(kecepatan_baru[j].getPorosity() < 1 || kecepatan_baru[j].getPorosity() > 2.83){
                            if(kecepatan_baru[j].getPorosity() < 1){
                                kecepatan_baru[j].setPorosity(1);
                            }
                            
                            if(kecepatan_baru[j].getPorosity() > 2.83){
                                kecepatan_baru[j].setPorosity(2.83);
                            }
                        }
                        
                        if(kecepatan_baru[j].getPermeability() <= 2.23 || kecepatan_baru[j].getPermeability() > 3){
                            if(kecepatan_baru[j].getPermeability() < 2.23){
                                kecepatan_baru[j].setPermeability(2.23);
                            }
                            
                            if(kecepatan_baru[j].getPermeability() > 3){
                                kecepatan_baru[j].setPermeability(3);
                            }
                        }
                        
                        break;
                    default:
                        if(kecepatan_baru[j].getViscosity() < 1.2 || kecepatan_baru[j].getViscosity() >= 1.32){
                            if(kecepatan_baru[j].getViscosity() < 1.2){
                                kecepatan_baru[j].setViscosity(1.2);
                            }
                            
                            if(kecepatan_baru[j].getViscosity() >= 1.32){
                                kecepatan_baru[j].setViscosity(1.32);
                            }
                        }
                        
                        if(kecepatan_baru[j].getApi_gravity() <= 1.83 || kecepatan_baru[j].getApi_gravity() > 3){
                            if(kecepatan_baru[j].getApi_gravity() <= 1.83){
                                kecepatan_baru[j].setApi_gravity(1.83);
                            }
                            
                            if(kecepatan_baru[j].getApi_gravity() > 3){
                                kecepatan_baru[j].setApi_gravity(3);
                            }
                        }
                        
                        if(kecepatan_baru[j].getSalinity() < 1.37 || kecepatan_baru[j].getSalinity() > 2.11){
                            if(kecepatan_baru[j].getSalinity() < 1.37){
                                kecepatan_baru[j].setSalinity(1.37);
                            }
                            
                            if(kecepatan_baru[j].getSalinity() > 2.11){
                                kecepatan_baru[j].setSalinity(2.11);
                            }
                        }
                        
                        if(kecepatan_baru[j].getPorosity() < 1.33 || kecepatan_baru[j].getPorosity() > 2.5){
                            if(kecepatan_baru[j].getPorosity() < 1.33){
                                kecepatan_baru[j].setPorosity(1.33);
                            }
                            
                            if(kecepatan_baru[j].getPorosity() > 2.5){
                                kecepatan_baru[j].setPorosity(2.5);
                            }
                        }
                        
                        if(kecepatan_baru[j].getPermeability() < 1 || kecepatan_baru[j].getPermeability() > 2.61){
                            if(kecepatan_baru[j].getPermeability() < 1){
                                kecepatan_baru[j].setPermeability(1);
                            }
                            
                            if(kecepatan_baru[j].getPermeability() > 2.61){
                                kecepatan_baru[j].setPermeability(2.61);
                            }
                        }
                        
                        break;
                }
            }
            
            partikels[i].setKecepatan(kecepatan_baru);
        }
        
        posisi_baru = new FluidaMinyakBumiModel[3];
        
        for(i=0; i<nPartikel; i++){
            for(j=0; j<3; j++){
                posisi_baru[j] = new FluidaMinyakBumiModel();
                
                posisi_baru[j].setViscosity(partikels[i].getPosisi()[j].getViscosity()+partikels[i].getKecepatan()[j].getViscosity());
                posisi_baru[j].setApi_gravity(partikels[i].getPosisi()[j].getApi_gravity()+partikels[i].getKecepatan()[j].getApi_gravity());
                posisi_baru[j].setSalinity(partikels[i].getPosisi()[j].getSalinity()+partikels[i].getKecepatan()[j].getSalinity());
                posisi_baru[j].setPorosity(partikels[i].getPosisi()[j].getPorosity()+partikels[i].getKecepatan()[j].getPorosity());
                posisi_baru[j].setPermeability(partikels[i].getPosisi()[j].getPermeability()+partikels[i].getKecepatan()[j].getPermeability());
                
                switch (j) {
                    case 0:
                        if(posisi_baru[j].getViscosity() < 2.1 || posisi_baru[j].getViscosity() > 3){
                            if(posisi_baru[j].getViscosity() < 2.1){
                                posisi_baru[j].setViscosity(2.1);
                            }
                            
                            if(posisi_baru[j].getViscosity() > 3){
                                posisi_baru[j].setViscosity(3);
                            }
                        }
                        
                        if(posisi_baru[j].getApi_gravity() < 1.17 || posisi_baru[j].getApi_gravity() > 2.92){
                            if(posisi_baru[j].getApi_gravity() < 1.17){
                                posisi_baru[j].setApi_gravity(1.17);
                            }
                            
                            if(posisi_baru[j].getApi_gravity() > 2.92){
                                posisi_baru[j].setApi_gravity(2.92);
                            }
                        }
                        
                        if(posisi_baru[j].getSalinity() < 1 || posisi_baru[j].getSalinity() > 2.97){
                            if(posisi_baru[j].getSalinity() < 1){
                                posisi_baru[j].setSalinity(1);
                            }
                            
                            if(posisi_baru[j].getSalinity() > 2.97){
                                posisi_baru[j].setSalinity(2.97);
                            }
                        }
                        
                        if(posisi_baru[j].getPorosity() < 1.83 || posisi_baru[j].getPorosity() > 3){
                            if(posisi_baru[j].getPorosity() < 1.83){
                                posisi_baru[j].setPorosity(1.83);
                            }
                            
                            if(posisi_baru[j].getPorosity() > 3){
                                posisi_baru[j].setPorosity(3);
                            }
                        }
                        
                        if(posisi_baru[j].getPermeability() < 1.04 || posisi_baru[j].getPermeability() > 2.31){
                            if(posisi_baru[j].getPermeability() < 1.04){
                                posisi_baru[j].setPermeability(1.04);
                            }
                            
                            if(posisi_baru[j].getPermeability() > 2.31){
                                posisi_baru[j].setPermeability(2.31);
                            }
                        }
                        
                        break;
                    case 1:
                        if(posisi_baru[j].getViscosity() < 1 || posisi_baru[j].getViscosity() > 1.14){
                            if(posisi_baru[j].getViscosity() < 1){
                                posisi_baru[j].setViscosity(1);
                            }
                            
                            if(posisi_baru[j].getViscosity() > 1.14){
                                posisi_baru[j].setViscosity(1.14);
                            }
                        }
                        
                        if(posisi_baru[j].getApi_gravity() < 1 || posisi_baru[j].getApi_gravity() > 2.67){
                            if(posisi_baru[j].getApi_gravity() < 1){
                                posisi_baru[j].setApi_gravity(1);
                            }
                            
                            if(posisi_baru[j].getApi_gravity() > 2.67){
                                posisi_baru[j].setApi_gravity(2.67);
                            }
                        }
                        
                        if(posisi_baru[j].getSalinity() < 1.3 || posisi_baru[j].getSalinity() > 3){
                            if(posisi_baru[j].getSalinity() < 1.3){
                                posisi_baru[j].setSalinity(1.3);
                            }
                            
                            if(posisi_baru[j].getSalinity() > 3){
                                posisi_baru[j].setSalinity(3);
                            }
                        }
                        
                        if(posisi_baru[j].getPorosity() < 1 || posisi_baru[j].getPorosity() > 2.83){
                            if(posisi_baru[j].getPorosity() < 1){
                                posisi_baru[j].setPorosity(1);
                            }
                            
                            if(posisi_baru[j].getPorosity() > 2.83){
                                posisi_baru[j].setPorosity(2.83);
                            }
                        }
                        
                        if(posisi_baru[j].getPermeability() <= 2.23 || posisi_baru[j].getPermeability() > 3){
                            if(posisi_baru[j].getPermeability() < 2.23){
                                posisi_baru[j].setPermeability(2.23);
                            }
                            
                            if(posisi_baru[j].getPermeability() > 3){
                                posisi_baru[j].setPermeability(3);
                            }
                        }
                        
                        break;
                    default:
                        if(posisi_baru[j].getViscosity() < 1.2 || posisi_baru[j].getViscosity() >= 1.32){
                            if(posisi_baru[j].getViscosity() < 1.2){
                                posisi_baru[j].setViscosity(1.2);
                            }
                            
                            if(posisi_baru[j].getViscosity() >= 1.32){
                                posisi_baru[j].setViscosity(1.32);
                            }
                        }
                        
                        if(posisi_baru[j].getApi_gravity() <= 1.83 || posisi_baru[j].getApi_gravity() > 3){
                            if(posisi_baru[j].getApi_gravity() <= 1.83){
                                posisi_baru[j].setApi_gravity(1.83);
                            }
                            
                            if(posisi_baru[j].getApi_gravity() > 3){
                                posisi_baru[j].setApi_gravity(3);
                            }
                        }
                        
                        if(posisi_baru[j].getSalinity() < 1.37 || posisi_baru[j].getSalinity() > 2.11){
                            if(posisi_baru[j].getSalinity() < 1.37){
                                posisi_baru[j].setSalinity(1.37);
                            }
                            
                            if(posisi_baru[j].getSalinity() > 2.11){
                                posisi_baru[j].setSalinity(2.11);
                            }
                        }
                        
                        if(posisi_baru[j].getPorosity() < 1.33 || posisi_baru[j].getPorosity() > 2.5){
                            if(posisi_baru[j].getPorosity() < 1.33){
                                posisi_baru[j].setPorosity(1.33);
                            }
                            
                            if(posisi_baru[j].getPorosity() > 2.5){
                                posisi_baru[j].setPorosity(2.5);
                            }
                        }
                        
                        if(posisi_baru[j].getPermeability() < 1 || posisi_baru[j].getPermeability() > 2.61){
                            if(posisi_baru[j].getPermeability() < 1){
                                posisi_baru[j].setPermeability(1);
                            }
                            
                            if(posisi_baru[j].getPermeability() > 2.61){
                                posisi_baru[j].setPermeability(2.61);
                            }
                        }
                        
                        break;
                }
            }
            
            partikels[i].setPosisi(posisi_baru);
        }
    }
     
    public boolean isKondisiTerpenuhi(){
        return flag_kondisi;
    }
    
    public FluidaMinyakBumiModel[] getCentroidGbest(){
        return Gbest;
    }
}
