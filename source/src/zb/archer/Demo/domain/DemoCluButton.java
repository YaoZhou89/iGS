/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zb.archer.Demo.domain;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class DemoCluButton extends Thread implements Runnable {

    JButton j24;
    
        JOptionPane op;
       
        JDialog dialog ;
        public DemoCluButton(){    
            j24 = new JButton(" Ok ");
            j24.setEnabled(false);
        op = new JOptionPane("Calculating......", JOptionPane.INFORMATION_MESSAGE);
        
             op.setOptions(new JButton[] { j24 });
              dialog = op.createDialog("Hint");
        }
        
    @Override
    public void run() {
        dialog.show();
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void dispose () {
         dialog.dispose();
         stop();
    }        
    
}
