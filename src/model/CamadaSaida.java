/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author joaop
 */
public class CamadaSaida extends Camada {
    private double[] saida;
    
    public CamadaSaida(int numNeuronios, String strategy, Camada camadaAnterior) {
        super(numNeuronios, strategy);
        this.proximaCamada = null;
        this.camadaAnterior = camadaAnterior;
        saida = new double[numNeuronios];
    }

    @Override
    public void setStrategy(String strategy) {
        switch(strategy){
            case "Logistica": {
                this.strategy = new Logistica();
                break;
            }
            case "Tangente hiperbolica": {
                this.strategy = new TangenteHiperbolica();
                break;
            }
            default: {
                this.strategy = new Logistica();
                break;
            }
        }
    }
    
    @Override
    public void propagation() {
        for(int i = 0; i<neuronios.length; i++){
            saida[i] = strategy.funcaoTransferencia(neuronios[i].getNet());
        }
    }
    
    public double erroRede(int classeDesejada, int numClasses){
        double desejado;
        double soma = 0;
        for(int i = 0; i<numClasses; i++){
            if(i+1 == classeDesejada){
                desejado = strategy.valorLigado();
            }
            else{
                desejado = strategy.valorDesligado();
            }
            double erro = (desejado-saida[i])*strategy.derivadaFuncaoTransferencia(neuronios[i].getNet());
            soma = soma + Math.pow(erro, 2);
            neuronios[i].setErro(erro);
        }
        return (soma/2)*10;
    }
    
    public int getSaida(){
        int numMaior = 0;
        double valorMaior = saida[0];
        for(int i = 1; i<saida.length; i++){
            if(saida[i] > valorMaior){
                valorMaior = saida[i];
                numMaior = i;
            }
        }
        return numMaior+1;
    }
}