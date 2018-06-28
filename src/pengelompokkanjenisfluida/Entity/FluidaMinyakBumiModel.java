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
public class FluidaMinyakBumiModel {
    private double viscosity;
    private double api_gravity;
    private double salinity;
    private double porosity;
    private double permeability;
    private String eor_fluid;
    
    public FluidaMinyakBumiModel() {
    }

    public FluidaMinyakBumiModel(double viscosity, double api_gravity, double salinity, double porosity, double permeability, String eor_fluid) {
        this.viscosity = viscosity;
        this.api_gravity = api_gravity;
        this.salinity = salinity;
        this.porosity = porosity;
        this.permeability = permeability;
        this.eor_fluid = eor_fluid;
    }

    public double getViscosity() {
        return viscosity;
    }

    public void setViscosity(double viscosity) {
        this.viscosity = viscosity;
    }

    public double getApi_gravity() {
        return api_gravity;
    }

    public void setApi_gravity(double api_gravity) {
        this.api_gravity = api_gravity;
    }

    public double getSalinity() {
        return salinity;
    }

    public void setSalinity(double salinity) {
        this.salinity = salinity;
    }

    public double getPorosity() {
        return porosity;
    }

    public void setPorosity(double porosity) {
        this.porosity = porosity;
    }

    public double getPermeability() {
        return permeability;
    }

    public void setPermeability(double permeability) {
        this.permeability = permeability;
    }

    public String getEor_fluid() {
        return eor_fluid;
    }

    public void setEor_fluid(String eor_fluid) {
        this.eor_fluid = eor_fluid;
    }
    
    
}
