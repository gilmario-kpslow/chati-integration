/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author gilmario
 */
public class MainTeste {

    // -3.7476938, -38.5322678
    public static void main(String[] args) {
        BigDecimal XA = new BigDecimal("-4.145648047403586");
        BigDecimal YA = new BigDecimal("-38.85791301727295");
        BigDecimal XB = new BigDecimal("-4.144746881428757");
        BigDecimal YB = new BigDecimal("-38.85668453102206");

        BigDecimal resultado = XB.subtract(XA).pow(2).add(YB.subtract(YA).pow(2)).sqrt(MathContext.DECIMAL32).multiply(new BigDecimal("100000"));

        System.out.println(resultado);
        //0.0004657311 =
        //0.0004407321
        // 0.0004603194
        // 0.001341062
    }
}
