/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author joaop
 */
public class CamadaEntrada extends Camada {
    
    public CamadaEntrada(int numNeuronios, String strategy, Camada proximaCamada) {
        super(numNeuronios, strategy);
        this.proximaCamada = proximaCamada;
        this.camadaAnterior = null;
    }   
    
    public void setValorEntrada(int neuronio, double valor){
        neuronios[neuronio].setNet(valor);
    }
}
