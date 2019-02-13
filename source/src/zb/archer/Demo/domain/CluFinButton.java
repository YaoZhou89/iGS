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
public class CluFinButton {

    public static void CluFinButton(String value, int type, String Hint) {
        JOptionPane fin = new JOptionPane(value, type);
        JDialog diafin = fin.createDialog(Hint);
        JButton jb2 = new JButton(" Ok ");
        jb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diafin.dispose();
            }
        });
        fin.setOptions(new JButton[]{jb2});

        diafin.show();
    }
/*
    public static int yesNoButton(String value, int type, String Hint) {
        JOptionPane fin = new JOptionPane(value, type);
        JDialog diafin = fin.createDialog(Hint);
        JButton jb2 = new JButton(" Yes ");
        jb2.addActionListener(new java.awt.event.ActionListener() {
            public int actionPerformed(java.awt.event.ActionEvent evt) {
                diafin.dispose();
                return 1;
            }
        });
        JButton jb2 = new JButton(" Yes ");
        jb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diafin.dispose();
            }
        });
        fin.setOptions(new JButton[]{jb2});

        diafin.show();
    }
*/
}
