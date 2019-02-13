/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pigcomput.exception;

import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class ArithmeticExceptionImp {
    
    ArithmeticException ae = new ArithmeticException();
    
    public void getStackTrace(){
        ae.getStackTrace();
        JOptionPane.showMessageDialog(null, "wrong file!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
