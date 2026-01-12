/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Random;

/**
 *
 * @author joaop
 */
public class Neuronio {
    private double net;
    private double[] pesos; //peso para o valor da camada anterior
    private double taxaAprendizado;
    private double erro;

    public Neuronio(int numPesos, double taxaAprendizado) {
        net = 0;
        pesos = new double[numPesos];
        for(int i = 0; i<numPesos; i++){
            Random peso = new Random();
            pesos[i] = peso.nextDouble(-0.5, 0.5);
        }
        this.taxaAprendizado = taxaAprendizado;
        erro = 0;
    }
    
    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double[] getPesos() {
        return pesos;
    }

    public void setPesos(double[] pesos) {
        this.pesos = pesos;
    }

    public double getTaxaAprendizado() {
        return taxaAprendizado;
    }

    public void setTaxaAprendizado(double taxaAprendizado) {
        this.taxaAprendizado = taxaAprendizado;
    }

    public double getErro() {
        return erro;
    }

    public void setErro(double erro) {
        this.erro = erro;
    }
    
    public void zerarNet(){
        net = 0;
    }
    
    public void somarNet(double saida, int peso){
        net = net + (saida*pesos[peso]);
    }
    
    public double calcularErroPeso(int peso){
        return erro * pesos[peso];
    }
    
    public void atualizarPeso(int peso, double x){
        pesos[peso] = pesos[peso] + (taxaAprendizado * erro * x);
    }
}
