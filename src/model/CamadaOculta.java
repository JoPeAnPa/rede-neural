/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author joaop
 */
public class CamadaOculta extends Camada {
    
    public CamadaOculta(int numNeuronios, String strategy) {
        super(numNeuronios, strategy);
        this.proximaCamada = null;
        this.camadaAnterior = null;
    }
    
}
