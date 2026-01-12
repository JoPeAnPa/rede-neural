/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaop
 */
public class CSV {
    private HashMap<String, ArrayList<Double>> tabela;
    private HashMap<Integer, Integer> indiceClasse;
    private HashMap<Integer, Integer> indiceLimiteClasse;
    private HashMap<String, Double> maiorValor;
    private int numLinhas;

    public CSV() {
        indiceClasse = new HashMap<>();
        indiceLimiteClasse = new HashMap<>();
        maiorValor = new HashMap<>();
    }
    
    public CSV(String endereco) {
        indiceClasse = new HashMap<>();
        indiceLimiteClasse = new HashMap<>();
        maiorValor = new HashMap<>();
        carregar(endereco);
    }
    
    public boolean carregar(String endereco){
        HashMap<String, ArrayList<Double>> csv = new HashMap<>();
        numLinhas = 0;
        try (RandomAccessFile arquivoCSV = new RandomAccessFile(new File(endereco), "r")){
            String[] cabeca = arquivoCSV.readLine().split(",");
            int colunas = cabeca.length;
            for(int i=0; i<colunas; i++){
                csv.put(cabeca[i], new ArrayList<>());
                maiorValor.put(cabeca[i], Double.MIN_VALUE);
            }
            String[] linha = arquivoCSV.readLine().split(",");
            while(linha != null){
                numLinhas++;
                for(int i=0; i<colunas; i++){
                    double valor = Double.parseDouble(linha[i]);
                    csv.get(cabeca[i]).add(valor);
                    if(maiorValor.get(cabeca[i])<valor){
                        maiorValor.put(cabeca[i], valor);
                    }
                }
                int classe = (int) Math.round(csv.get("classe").get(numLinhas-1));
                if(!indiceClasse.containsKey(classe)){
                    indiceClasse.put(classe, numLinhas-1);
                    if(classe>1){
                        indiceLimiteClasse.put(classe-1, numLinhas-1);
                    }
                }
                if(arquivoCSV.getFilePointer()<arquivoCSV.length()){
                    linha = arquivoCSV.readLine().split(",");
                }
                else{
                    linha = null;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        indiceLimiteClasse.put(indiceClasse.size(), numLinhas);
        tabela = csv;
        return true;
    }
    
    public int getNumLinhas(){
        return numLinhas;
    }
    
    public Double getValor(String coluna, int linha){
        return tabela.get(coluna).get(linha);
    }
    
    public int getNumAtributos(){
        return tabela.size()-1;
    }

    public int getNumClasses() {
        return indiceClasse.size();
    }

    public Map<String, Double> getMaiorValor() {
        return maiorValor;
    }

    public Map<Integer, Integer> getIndiceLimiteClasse() {
        return indiceLimiteClasse;
    }

    public Map<Integer, Integer> getIndiceClasse() {
        return (Map<Integer, Integer>) indiceClasse.clone();
    }
}
