/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JOptionPane;
import model.CSV;
import model.RedeNeural;

/**
 *
 * @author joaop
 */
public class Controlador {
    private static RedeNeural rede;
    private static CSV dados = new CSV();

    private Controlador() {
        
    }

    public static void carregarDados(String endereco) {
        boolean sucesso = dados.carregar(endereco);
        if(sucesso){
            JOptionPane.showMessageDialog(null, "Leitura do arquivo realizada com sucesso", "Leitura", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Erro na leitura do arquivo", "Leitura", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static int getNumAtributos() {
        return dados.getNumAtributos();
    }

    public static int getNumClasses() {
        return dados.getNumClasses();
    }
    
    public static void criarRede(int numNeurEntrada, int numNeurSaida, int numNeurOculto, int numCamadaOculta, String strategy, double taxaAprendizado){
        rede = new RedeNeural(numNeurEntrada, numNeurSaida, numNeurOculto, numCamadaOculta, strategy, taxaAprendizado);
    }
    
    public static void criarRede(int numNeurOculto, int numCamadaOculta, String strategy, double taxaAprendizado){
        rede = new RedeNeural(dados.getNumAtributos(), dados.getNumClasses(), numNeurOculto, numCamadaOculta, strategy, taxaAprendizado);
    }
    
    public static void treinarErro(double erroMinimo) {
        rede.treinarErro(dados, erroMinimo);
    }

    public static void treinarRepeticao(int numRepeticao) {
        rede.treinarRepeticao(dados, numRepeticao);
    }

    public static Object[][] teste(String endereco) {
        int[][] resultado =  rede.teste(endereco);
        Object[][] obj = new Object[resultado.length][resultado[0].length+1];
        for (int i = 0; i<resultado.length; i++){
            obj[i][0] = i+1;
            for(int k = 0; k<resultado[0].length; k++){
                obj[i][k+1] = resultado[i][k];
            }
        }
        return obj;
    }

    public static int classificar(double[] valoresEntrada) {
        return rede.classificar(valoresEntrada, dados.getMaiorValor());
    }
}
