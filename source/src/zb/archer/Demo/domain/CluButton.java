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
public class CluButton {

    public JButton j24;
    public JOptionPane op;
    public JDialog dialog;
    public Thread t3;
    
    public CluButton(Thread t3) {
        j24 = new JButton(" Stop ");
        op = new JOptionPane("Calculating......", JOptionPane.INFORMATION_MESSAGE);
        dialog = op.createDialog("Hint");
        j24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if ( t3 != null )
                    t3.stop();
                shutdown();
                return;
            }
        }                
        );
        if ( t3 != null )
            this.t3 = t3; 
        dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        op.setOptions(new JButton[]{j24});
    }

    public void shutdown() {
        dialog.dispose();
    }
    
    public void start(){
        if ( t3 != null)
            t3.start();
        dialog.show();
    }

}
