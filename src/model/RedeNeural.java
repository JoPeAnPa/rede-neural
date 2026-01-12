/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Map;

/**
 *
 * @author joaop
 */
public class RedeNeural {
    private CamadaEntrada entrada;
    private CamadaSaida saida;
    private int numClasses;

    public RedeNeural(int numNeurEntrada, int numNeurSaida, int numNeurOculto, int numCamadaOculta, String strategy, double taxaAprendizado) {
        CamadaOculta oculto = new CamadaOculta(numNeurOculto, strategy);
        entrada = new CamadaEntrada(numNeurEntrada, strategy, oculto);
        oculto.setCamadaAnterior(entrada);
        for(int i=1; i<numCamadaOculta; i++){
            CamadaOculta aux = new CamadaOculta(numNeurOculto, strategy);
            oculto.setProximaCamada(aux);
            aux.setCamadaAnterior(oculto);
            oculto = aux;
        }
        saida = new CamadaSaida(numNeurSaida, strategy, oculto);
        oculto.setProximaCamada(saida);
        entrada.gerarNeuronios(taxaAprendizado);
        
        numClasses = numNeurSaida;
    }
    
    private void carregarValoresEntrada(CSV dados, int linha){
        int numEntradas = entrada.getNumNeuronios();
        Map<String, Double> valorMaximo = dados.getMaiorValor();
        for(int n = 0 ; n<numEntradas; n++){
            String coluna = "X" + (n+1);
            double valorMaximoAtributo = valorMaximo.get(coluna);
            entrada.setValorEntrada(n, dados.getValor(coluna, linha)/valorMaximoAtributo);
        }
    }
    
    public void treinarErro(CSV dados, double erroMinimo){
        boolean erroNaoAceitavel = true;
        Map<Integer, Integer> indiceLimiteClasses = dados.getIndiceLimiteClasse();
        int numLinhas = dados.getNumLinhas();
        while(erroNaoAceitavel){
            erroNaoAceitavel = false;
            int k = 0;
            Map<Integer, Integer> indiceClasses = dados.getIndiceClasse();
            while(k<numLinhas){
                for(int i = 1; i<=numClasses; i++){
                    if(indiceClasses.get(i)<indiceLimiteClasses.get(i)){
                        carregarValoresEntrada(dados, indiceClasses.get(i));
                        entrada.propagation();
                        double erro = saida.erroRede(i, numClasses);
                        if(erro>=erroMinimo){
                            erroNaoAceitavel = true;
                        }
                        saida.backpropagation();
                        k++;
                        indiceClasses.put(i, indiceClasses.get(i)+1);
                    }
                }
            }
        }
    }
    
    public void treinarErro(String endereco, double erroMinimo){
        CSV dados = new CSV(endereco);
        treinarErro(dados, erroMinimo);
    }
    
    public void treinarRepeticao(CSV dados, int numRepeticao){
        Map<Integer, Integer> indiceLimiteClasses = dados.getIndiceLimiteClasse();
        int numLinhas = dados.getNumLinhas();
        for(int i = 0; i<numRepeticao; i++){
            int k = 0;
            Map<Integer, Integer> indiceClasses = dados.getIndiceClasse();
            while(k<numLinhas){
                for(int classe = 1; classe<=numClasses; classe++){
                    if(indiceClasses.get(classe)<indiceLimiteClasses.get(classe)){
                        carregarValoresEntrada(dados, indiceClasses.get(classe));
                        entrada.propagation();
                        saida.erroRede(classe, numClasses);
                        saida.backpropagation();
                        k++;
                        indiceClasses.put(classe, indiceClasses.get(classe)+1);
                    }
                }
            }
        }
    }
    
    public void treinarRepeticao(String endereco, int numRepeticao){
        CSV dados = new CSV(endereco);
        treinarRepeticao(dados, numRepeticao);
    }
    
    public int[][] teste(String endereco){
        CSV dados = new CSV();
        dados.carregar(endereco);
        int numLinhas = dados.getNumLinhas();
        int[][] matrizConfusao = new int[numClasses][numClasses];
        for(int i = 0; i<numClasses; i++){
            for(int k = 0; k<numClasses; k++){
                matrizConfusao[i][k] = 0;
            }
        }
        for(int i=0; i<numLinhas; i++){
            carregarValoresEntrada(dados, i);
            entrada.propagation();
            int classeObtida = saida.getSaida();
            int classeDesejada = (int) Math.round(dados.getValor("classe", i));
            matrizConfusao[classeObtida-1][classeDesejada-1]++; 
        }
        return matrizConfusao;
    }
    
    public int classificar(double[] valoresEntrada, Map<String, Double> valorMaximo){
        int numEntradas = entrada.getNumNeuronios();
        if(numEntradas!=valoresEntrada.length){
            return -1;
        }
        for(int i=0; i<numEntradas; i++){
            String chave = "X" + (i+1);
            entrada.setValorEntrada(i, valoresEntrada[i]/valorMaximo.get(chave));
        }
        entrada.propagation();
        return saida.getSaida();
    }

    @Override
    public String toString() {
        return "RedeNeural{" + "entrada=" + entrada + ", saida=" + saida + ", numClasses=" + numClasses + '}';
    }
    
    
}
