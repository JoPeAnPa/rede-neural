/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author joaop
 */
public class Logistica extends Transferencia {

    @Override
    public double funcaoTransferencia(double net) {
        return 1/(1 + Math.exp(-net));
    }

    @Override
    public double derivadaFuncaoTransferencia(double net) {
        return Math.exp(-net)/Math.pow(1 + Math.exp(-net), 2);
    }
    
    @Override
    public double valorLigado() {
        return 1;
    }

    @Override
    public double valorDesligado() {
        return 0;
    }
}
