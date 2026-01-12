/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author joaop
 */
public abstract class Transferencia {
    
    public abstract double funcaoTransferencia(double net);
    
    public abstract double derivadaFuncaoTransferencia(double net);
    
    public abstract double valorLigado();
    
    public abstract double valorDesligado();
}
