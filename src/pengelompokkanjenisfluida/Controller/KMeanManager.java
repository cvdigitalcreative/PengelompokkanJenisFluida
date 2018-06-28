/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pengelompokkanjenisfluida.Controller;

import java.util.ArrayList;
import pengelompokkanjenisfluida.Entity.FluidaMinyakBumiModel;

/**
 *
 * @author shaff
 */
public class KMeanManager {
    private FluidaMinyakBumiModel[] centroid;
    private FluidaMinyakBumiModel[] dataModel;
    
    public KMeanManager() {
    }

    public KMeanManager(FluidaMinyakBumiModel[] centroid, FluidaMinyakBumiModel[] dataModel) {
        this.centroid = centroid;
        this.dataModel = dataModel;
    }

    public FluidaMinyakBumiModel[] getCentroid() {
        return centroid;
    }

    public void setCentroid(FluidaMinyakBumiModel[] centroid) {
        this.centroid = centroid;
    }

    public FluidaMinyakBumiModel[] getDataModel() {
        return dataModel;
    }

    public void setDataModel(FluidaMinyakBumiModel[] dataModel) {
        this.dataModel = dataModel;
    }
    
    public double hitungEuclideanDistance(FluidaMinyakBumiModel model1, FluidaMinyakBumiModel model2){
        double euc = Math.sqrt(Math.pow(model1.getViscosity()-model2.getViscosity(), 2)+Math.pow(model1.getApi_gravity()-model2.getApi_gravity(), 2)
                +Math.pow(model1.getSalinity()-model2.getSalinity(), 2)+Math.pow(model1.getPorosity()-model2.getPorosity(), 2)
                +Math.pow(model1.getPermeability()-model2.getPermeability(), 2));
        
        return euc;
    }
    
    public double[] hitungJarakDataKeCentroid(){
        int i, j;
        double[] minJarak, indexMinJarak;
        double[][] dataJarakDataCentroid;
        
        dataJarakDataCentroid = new double[dataModel.length][centroid.length];
        minJarak = new double[dataModel.length];
        indexMinJarak = new double[dataModel.length];
        
        for(i=0; i<dataModel.length; i++){
            minJarak[i] = 10000;
            indexMinJarak[i] = 0;
            for(j=0; j<centroid.length; j++){
                dataJarakDataCentroid[i][j] = hitungEuclideanDistance(dataModel[i], centroid[j]);
                
                if(minJarak[i] > dataJarakDataCentroid[i][j]){
                    minJarak[i] = dataJarakDataCentroid[i][j];
                    indexMinJarak[i] = j;
                }
            }
        }
        
        return indexMinJarak;
    }
    
//    public void hitungCentroidBaru(double[] indexMinJarak){
//        int i, j;
//        ArrayList<FluidaMinyakBumiModel> centroid_1, centroid_2, centroid_3;
//        FluidaMinyakBumiModel centroid_cl1, centroid_cl2, centroid_cl3; 
//        
//        centroid_1 = new ArrayList<>();
//        centroid_2 = new ArrayList<>();
//        centroid_3 = new ArrayList<>();
//        
//        for(i=0; i<centroid.length; i++){
//            for(j=0; j<dataModel.length; j++){
//                switch ((int)indexMinJarak[j]) {
//                    case 0:
//                        centroid_1.add(dataModel[j]);
//                        break;
//                    case 1:
//                        centroid_2.add(dataModel[j]);
//                        break;
//                    default:
//                        centroid_3.add(dataModel[j]);
//                        break;
//                }
//            }
//        }
//        
//        centroid_cl1 = new FluidaMinyakBumiModel();
//        centroid_cl1.setViscosity(0);
//        centroid_cl1.setApi_gravity(0);
//        centroid_cl1.setSalinity(0);
//        centroid_cl1.setPorosity(0);
//        centroid_cl1.setPermeability(0);
//        
//        centroid_cl2 = new FluidaMinyakBumiModel();
//        centroid_cl2.setViscosity(0);
//        centroid_cl2.setApi_gravity(0);
//        centroid_cl2.setSalinity(0);
//        centroid_cl2.setPorosity(0);
//        centroid_cl2.setPermeability(0);
//        
//        centroid_cl3 = new FluidaMinyakBumiModel();
//        centroid_cl3.setViscosity(0);
//        centroid_cl3.setApi_gravity(0);
//        centroid_cl3.setSalinity(0);
//        centroid_cl3.setPorosity(0);
//        centroid_cl3.setPermeability(0);
//        
//        for(i=0; i<centroid_1.size(); i++){
//            centroid_cl1.setViscosity(centroid_cl1.getViscosity()+centroid_1.get(i).getViscosity());
//            centroid_cl1.setApi_gravity(centroid_cl1.getApi_gravity()+centroid_1.get(i).getApi_gravity());
//            centroid_cl1.setSalinity(centroid_cl1.getSalinity()+centroid_1.get(i).getSalinity());
//            centroid_cl1.setPorosity(centroid_cl1.getPorosity()+centroid_1.get(i).getPorosity());
//            centroid_cl1.setPermeability(centroid_cl1.getPermeability()+centroid_1.get(i).getPermeability());    
//        }
//        
//        centroid_cl1.setViscosity(centroid_cl1.getViscosity()/centroid_1.size());
//        centroid_cl1.setApi_gravity(centroid_cl1.getApi_gravity()/centroid_1.size());
//        centroid_cl1.setSalinity(centroid_cl1.getSalinity()/centroid_1.size());
//        centroid_cl1.setPorosity(centroid_cl1.getPorosity()/centroid_1.size());
//        centroid_cl1.setPermeability(centroid_cl1.getPermeability()/centroid_1.size());    
//        
//        for(i=0; i<centroid_2.size(); i++){
//            centroid_cl2.setViscosity(centroid_cl2.getViscosity()+centroid_2.get(i).getViscosity());
//            centroid_cl2.setApi_gravity(centroid_cl2.getApi_gravity()+centroid_2.get(i).getApi_gravity());
//            centroid_cl2.setSalinity(centroid_cl2.getSalinity()+centroid_2.get(i).getSalinity());
//            centroid_cl2.setPorosity(centroid_cl2.getPorosity()+centroid_2.get(i).getPorosity());
//            centroid_cl2.setPermeability(centroid_cl2.getPermeability()+centroid_2.get(i).getPermeability());    
//        }
//        
//        centroid_cl2.setViscosity(centroid_cl2.getViscosity()/centroid_2.size());
//        centroid_cl2.setApi_gravity(centroid_cl2.getApi_gravity()/centroid_2.size());
//        centroid_cl2.setSalinity(centroid_cl2.getSalinity()/centroid_2.size());
//        centroid_cl2.setPorosity(centroid_cl2.getPorosity()/centroid_2.size());
//        centroid_cl2.setPermeability(centroid_cl2.getPermeability()/centroid_2.size());    
//        
//        for(i=0; i<centroid_3.size(); i++){
//            centroid_cl3.setViscosity(centroid_cl3.getViscosity()+centroid_3.get(i).getViscosity());
//            centroid_cl3.setApi_gravity(centroid_cl3.getApi_gravity()+centroid_3.get(i).getApi_gravity());
//            centroid_cl3.setSalinity(centroid_cl3.getSalinity()+centroid_3.get(i).getSalinity());
//            centroid_cl3.setPorosity(centroid_cl3.getPorosity()+centroid_3.get(i).getPorosity());
//            centroid_cl3.setPermeability(centroid_cl3.getPermeability()+centroid_3.get(i).getPermeability());    
//        }
//        
//        centroid_cl3.setViscosity(centroid_cl3.getViscosity()/centroid_3.size());
//        centroid_cl3.setApi_gravity(centroid_cl3.getApi_gravity()/centroid_3.size());
//        centroid_cl3.setSalinity(centroid_cl3.getSalinity()/centroid_3.size());
//        centroid_cl3.setPorosity(centroid_cl3.getPorosity()/centroid_3.size());
//        centroid_cl3.setPermeability(centroid_cl3.getPermeability()/centroid_3.size());
//        
//        centroid[0] = centroid_cl1;
//        centroid[1] = centroid_cl2;
//        centroid[2] = centroid_cl3;
//    }
}
