/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author joaop
 */
public abstract class Camada {
    protected Neuronio[] neuronios;
    protected Transferencia strategy;
    protected Camada proximaCamada;
    protected Camada camadaAnterior;

    protected Camada(int numNeuronios, String strategy) {
        this.neuronios = new Neuronio[numNeuronios];
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

    public int getNumNeuronios(){
        return neuronios.length;
    }
    
    public Neuronio[] getNeuronios() {
        return neuronios;
    }

    public void setNeuronios(Neuronio[] neuronios) {
        this.neuronios = neuronios;
    }

    public Camada getProximaCamada() {
        return proximaCamada;
    }

    public void setProximaCamada(Camada proximaCamada) {
        this.proximaCamada = proximaCamada;
    }

    public Camada getCamadaAnterior() {
        return camadaAnterior;
    }

    public void setCamadaAnterior(Camada camadaAnterior) {
        this.camadaAnterior = camadaAnterior;
    }

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
            }
        }
        proximaCamada.setStrategy(strategy);
    }
    
    public void propagation(){
        Neuronio[] nerPC = proximaCamada.getNeuronios();
        for(int i=0; i<nerPC.length; i++){
            nerPC[i].zerarNet();
            for(int k=0; k<neuronios.length; k++){
                double saida = strategy.funcaoTransferencia(neuronios[k].getNet());
                nerPC[i].somarNet(saida, k);
            }
        }
        proximaCamada.propagation();
    }
    
    public void backpropagation(){
        if(camadaAnterior == null){
            return;
        }
        Neuronio[] nerCA = camadaAnterior.getNeuronios();
        for(int i=0; i<nerCA.length; i++){
            double soma = 0;
            for (Neuronio neuronio : neuronios) {
                soma = soma + neuronio.calcularErroPeso(i);
                this.atualizarPeso(neuronio, i, nerCA[i].getNet());
            }
            double erro = strategy.derivadaFuncaoTransferencia(nerCA[i].getNet()) * soma;
            nerCA[i].setErro(erro);
        }
        camadaAnterior.backpropagation();
    }
    
    public void atualizarPeso(Neuronio neuronio, int k, double net){
        neuronio.atualizarPeso(k, strategy.funcaoTransferencia(net));
    }
    
    public void gerarNeuronios(int numPesos, double taxaAprendizado){
        int numNeuronios = neuronios.length;
        for(int i = 0; i<numNeuronios; i++){
            neuronios[i] = new Neuronio(numPesos, taxaAprendizado);
        }
    }
    
    public void gerarNeuronios(double taxaAprendizado){
        if(camadaAnterior == null){
            gerarNeuronios(0, taxaAprendizado);
        }
        else{
            gerarNeuronios(camadaAnterior.getNumNeuronios(), taxaAprendizado);
        }
        if(proximaCamada != null){
            proximaCamada.gerarNeuronios(taxaAprendizado);
        }
    }
    
}