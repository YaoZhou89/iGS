/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pigcomput.tj;

import com.Polygenic.main.Polygenic_model;
import com.Polygenic.operate.FileCV;
import com.Polygenic.operate.FileTraits;
import com.pigcomput.exception.ArithmeticExceptionImp;
import com.pigcomput.main.Desk2;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import zb.archer.Demo.domain.CluButton;
import zb.archer.Demo.domain.CluFinButton;
import zb.archer.Demo.operate.Write;

/**
 * FileDialog loadDiaA 育种分析文件 FileDialog loadDiaPhe 表性信息文件 FileDialog writeDia
 * 导出BLUP结果文件 double[][] pedigree 育种分析值 String[] name 育种分析个体名 String[] ColName
 * 表型列名 String[][] resultstr BLUP结果 SelectBxsz sb 表型选择 dialog String[] tableHead
 * 表 Object[][] tableModel
 *
 * @author Asus
 */
public class Polygenic extends javax.swing.JInternalFrame {

    //文件
    private FileDialog loadDiaCV;
    private FileDialog loadDiaY;
    private FileDialog loadDiaKa;
    private FileDialog loadDiaKd;
    //写文件
    private FileDialog writeDia;
    private String[] ColName;
    private String[][] resultstr;
//    private double[][] forecas;
    private SelectBxsz sb;
    private String[] tableHead = {"Colnumn", "Traits"};
    private Object[][] tableModel;
    private String[] tableHead1 = {"Kinship list"};
    private Map<String, String> Kfile = new HashMap<String, String>();
    private Object[][] tableModel1;

    private boolean standard_id = false;
    private double[][] forecas;

    private Desk2 d;
    private CluButton cb1;

    /**
     * Creates new form Blup
     */
    public Polygenic(Desk2 d) {
        initComponents();
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.d = d;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();

        setBackground(new java.awt.Color(233, 242, 252));
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("BayesA计算");
        setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(133, 34));
        setPreferredSize(new java.awt.Dimension(650, 515));

        jPanel1.setBackground(new java.awt.Color(233, 242, 252));

        jButton2.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 102));
        jButton2.setText("Run");
        jButton2.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 102));
        jButton4.setText("Export .txt");
        jButton4.setEnabled(false);
        jButton4.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 102));
        jButton3.setText("Export .csv");
        jButton3.setEnabled(false);
        jButton3.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 0, 102));
        jButton6.setText("Select Traits");
        jButton6.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("楷体", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Colnumn", "Traits"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 102));
        jButton1.setText("myCV File");
        jButton1.setPreferredSize(new java.awt.Dimension(147, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText(" ");

        jButton5.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 102));
        jButton5.setText("myY  File");
        jButton5.setPreferredSize(new java.awt.Dimension(147, 32));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setText(" ");

        jButton9.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 0, 102));
        jButton9.setText("Import kinship ");
        jButton9.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("楷体", 0, 16)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kinship list "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setMaximumSize(null);
        jScrollPane2.setViewportView(jTable2);

        jButton7.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 0, 102));
        jButton7.setText("Remove Kinship");
        jButton7.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REML algorithm", "fs", "nr", "ai" }));
        jComboBox1.setMaximumSize(null);
        jComboBox1.setPreferredSize(new java.awt.Dimension(110, 32));

        jButton8.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 0, 102));
        jButton8.setText("Reset");
        jButton8.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton24.setFont(new java.awt.Font("微软雅黑", 1, 14)); // NOI18N
        jButton24.setForeground(new java.awt.Color(0, 0, 102));
        jButton24.setText("Return");
        jButton24.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //开始计算
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        //判断是否导入两个文件
        if ((loadDiaY == null || loadDiaY.getDirectory() == null)
                || (Kfile.size() <= 0)) {
            CluFinButton.CluFinButton("Please import the files first!", JOptionPane.ERROR_MESSAGE, "Error");
            return;
        }
        FileTraits fb = new FileTraits(loadDiaY.getDirectory() + loadDiaY.getFile());
        if (tableModel.length == ColName.length) {
            boolean[] bool1 = new boolean[fb.getColName().length - 1];
            for (int i = 0; i < bool1.length; i++) {
                bool1[i] = true;
            }
            if (closeList(bool1, fb, 2) == 1) {
                return;
            }
        }

        //表型数组
        ArrayList<Integer> indexlist = new ArrayList<Integer>();
        for (int i = 0; i < sb.bool1.length; i++) {
            if (sb.bool1[i]) {
                indexlist.add(i);
            }
        }
        Object[] temp1 = indexlist.toArray();
        int[] index1 = new int[temp1.length];
        for (int i = 0; i < temp1.length; i++) {
            index1[i] = (int) temp1[i];
        }

        if ((String) jComboBox1.getSelectedItem() == "REML algorithm") {
            CluFinButton.CluFinButton("Sorry! The files import error or the parameters set error. Please load new files or reset!", JOptionPane.ERROR_MESSAGE, "Error");
            return;
        }

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    FileCV myCV = null;
                    FileTraits myY = null;
                    FileCV myKa = null;
                    ArrayList<double[][]> K = new ArrayList<double[][]>();
                    try {
                        if (loadDiaCV != null && loadDiaCV.getDirectory() != null) {
                            myCV = new FileCV(loadDiaCV.getDirectory() + loadDiaCV.getFile(), true);
                        } else {
                            myCV = new FileCV();
                        }
                        myY = new FileTraits(loadDiaY.getDirectory() + loadDiaY.getFile());

                        Iterator<Map.Entry<String, String>> it = Kfile.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, String> entry = it.next();
                            entry.getKey();
                            K.add(new FileCV(entry.getValue(), false).getDoublePhe1("K"));
                        }

                    } catch (Exception e) {
                        new ArithmeticExceptionImp().getStackTrace();
                        cb1.shutdown();
                        return;
                    }
                    double[] Var = null;

                    Map<String, Object> pol = Polygenic_model.runiGS(myCV, myY, Var, (String) jComboBox1.getSelectedItem(), index1, K);
                    resultstr = (String[][]) pol.get("BLUP");
                    forecas = (double[][]) pol.get("phe");
                } catch (Exception e) {
                    cb1.shutdown();
                    CluFinButton.CluFinButton("Sorry! The files import error or the parameters set error. Please load new files or reset! ", JOptionPane.ERROR_MESSAGE, "Error");
                    return;
                }
                cb1.shutdown();
                jButton3.setEnabled(true);
                jButton4.setEnabled(true);
                CluFinButton.CluFinButton("Finished successfully!", JOptionPane.INFORMATION_MESSAGE, "Hint");
            }
        });
        cb1 = new CluButton(t3);
        cb1.start();
    }//GEN-LAST:event_jButton2ActionPerformed

    //导出txt文件
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        writeDia = new FileDialog(new Frame(), "SAVE", FileDialog.SAVE);
        writeDia.setVisible(true);// TODO add your handling code here:
        if (writeDia.getDirectory() != null && writeDia.getFile() != null) {
            Write.write(writeDia.getDirectory() + writeDia.getFile(), resultstr, forecas, 2);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    //导出csv文件
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        writeDia = new FileDialog(new Frame(), "SAVE", FileDialog.SAVE);
        writeDia.setVisible(true);// TODO add your handling code here:
        if (writeDia.getDirectory() != null && writeDia.getFile() != null) {
            Write.write(writeDia.getDirectory() + writeDia.getFile(), resultstr, forecas, 1);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    //选择表型
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        if (loadDiaY == null || loadDiaY.getDirectory() == null) {
            CluFinButton.CluFinButton("Please import the files first!", JOptionPane.ERROR_MESSAGE, "Error");
            return;
        }

        FileTraits fb = new FileTraits(loadDiaY.getDirectory() + loadDiaY.getFile());
        ColName = new String[fb.getColName().length - 1];
        System.arraycopy(fb.getColName(), 1, ColName, 0, fb.getColName().length - 1);
        sb = new SelectBxsz(ColName, null, true);
        sb.setLocationRelativeTo(this);
        sb.setVisible(true);
        if (sb.stat == 0) {
            closeList(sb.bool1, fb, 2);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadDiaCV = new FileDialog(new Frame(), "LOAD", FileDialog.LOAD);
        loadDiaCV.setVisible(true);
        String directory = loadDiaCV.getDirectory();
        String file = loadDiaCV.getFile();
        if (directory != null && file != null) {
            if ((directory + file).length() < 40) {
                jLabel5.setText("" + directory + file);
                jLabel5.setToolTipText(null);
            } else {
                jLabel5.setText(directory.substring(0, directory.indexOf(File.separator)) + File.separator + "..." + File.separator + file);
                jLabel5.setToolTipText(directory + file);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadDiaY = new FileDialog(new Frame(), "LOAD", FileDialog.LOAD);
        loadDiaY.setVisible(true);// TODO add your handling code here:
        String directory = loadDiaY.getDirectory();
        String file = loadDiaY.getFile();
        if (directory != null && file != null) {
            if ((directory + file).length() < 40) {
                jLabel6.setText("" + directory + file);
                jLabel6.setToolTipText(null);
            } else {
                jLabel6.setText(directory.substring(0, directory.indexOf(File.separator)) + File.separator + "..." + File.separator + file);
                jLabel6.setToolTipText(directory + file);
            }
        } else {
            return;
        }
        //初始默认选择全部表型
        FileTraits fb = new FileTraits(loadDiaY.getDirectory() + loadDiaY.getFile());
        boolean[] bool1 = new boolean[fb.getColName().length - 1];
        for (int i = 0; i < bool1.length; i++) {
            bool1[i] = true;
        }
        ColName = new String[fb.getColName().length - 1];
        System.arraycopy(fb.getColName(), 1, ColName, 0, fb.getColName().length - 1);
        sb = new SelectBxsz(ColName, null, true);
        try {
            sb.setAllBool1True();
        } catch (NullPointerException e) {
            return;
        }
        closeList(bool1, fb, 1);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        loadDiaKa = new FileDialog(new Frame(), "LOAD", FileDialog.LOAD);
        loadDiaKa.setVisible(true);
        String directory = loadDiaKa.getDirectory();
        String file = loadDiaKa.getFile();
        if (directory != null && file != null) {
            Kfile.put(file, directory + file);
            closeListK(file);
        }
//        if (directory != null && file != null) {
//            jLabel9.setText("文件目录：" + directory + file);
//        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        Kfile.remove((String) tableModel1[jTable2.getSelectedRow()][0]);
        tableModel1 = new Object[Kfile.size()][1];
        int i = 0;
        Iterator<Map.Entry<String, String>> it = Kfile.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            tableModel1[i++][0] = entry.getKey();
        }
        jTable2.setModel(new javax.swing.table.DefaultTableModel(tableModel1, tableHead1));
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        setData();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed

        setData();
        d.jPanel1.removeAll();
        d.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton24ActionPerformed

    //更新表型表格
    private int closeList(boolean[] bool1, FileTraits fb, int stat) {

        ArrayList<Integer> index = new ArrayList<Integer>();
        int ide = -1;
        for (int i = 0; i < bool1.length; i++) {
            if (bool1[i]) {
                index.add(i + 1);
            }
        }
        if (stat == 1) {
            tableModel = new Object[index.size()][2];
            for (int i = 0; i < tableModel.length; i++) {
                tableModel[i][0] = i + 1;
                tableModel[i][1] = ColName[i];
            }
            jTable1.setModel(new javax.swing.table.DefaultTableModel(tableModel, tableHead));
            return 0;
        }

        try {
            String[][] temp = fb.getPhe1();
            double t1;
            for (int k = 0; k < index.size(); k++) {
                ide = k;
                //System.out.println(temp[0][index.get(k)-1]);
                if (!temp[0][index.get(k) - 1].equals("NA")) {
                    t1 = Double.parseDouble(temp[0][index.get(k) - 1]);
                }
            }
        } catch (Exception e) {

            CluFinButton.CluFinButton("Values of " + ColName[ide] + " isn't numeric.", JOptionPane.ERROR_MESSAGE, "Error");
            return 1;
        }

        tableModel = new Object[index.size()][2];
        for (int i = 0; i < tableModel.length; i++) {
            tableModel[i][0] = index.get(i);
            tableModel[i][1] = ColName[index.get(i) - 1];
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(tableModel, tableHead));
        //setSelected(true);
        //System.gc();
        return 0;
    }

    //更新表型表格
    private int closeListK(String fileName) {

        int i = 0;
        tableModel1 = new Object[Kfile.size()][1];

        Iterator<Map.Entry<String, String>> it = Kfile.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            tableModel1[i++][0] = entry.getKey();
        }
        jTable2.setModel(new javax.swing.table.DefaultTableModel(tableModel1, tableHead1));
        return 0;
    }

    /**
     * 从num[]中获取index[]序列的值
     *
     * @param num
     * @param index
     * @return
     */
    private static double[] getArrayByIndex(double[][] num, int index) {

        double[] douArr = new double[num.length];
        for (int i = 0; i < num.length; i++) {
            douArr[i] = num[i][index];
        }

        return douArr;
    }

    private static byte[][] getArrayByIndex(byte[][] num, int index[]) {

        byte[][] bytArr = new byte[num.length][index.length];
        for (int j = 0; j < index.length; j++) {
            for (int i = 0; i < num.length; i++) {
                bytArr[i][j] = Byte.valueOf(num[i][index[j]]);
            }
        }
        return bytArr;
    }

    /**
     * toDouble
     *
     * @param CV
     * @return
     */
    private static double[][] toDouble(String[][] CV) {
        if (CV == null) {
            return null;
        }

        double[][] doubleCV = new double[CV.length][CV[0].length];
        for (int i = 0; i < CV.length; i++) {
            for (int j = 0; j < CV[0].length; j++) {
                doubleCV[i][j] = Double.valueOf(CV[i][j]);
            }
        }
        return doubleCV;
    }

    /**
     * subDoubleArray
     *
     * @param beta
     * @param start
     * @param end
     * @return
     */
    private static double[] subDoubleArray(double[] beta, int start, int end) {

        double[] arraycopy = new double[end - start + 1];
        System.arraycopy(beta, start, arraycopy, 0, end - start + 1);
        return arraycopy;
    }

    /**
     * @param GAWS
     * @param GM
     * @return
     */
    private static int[] match(String[][] GAWS, String[] GM) {

        List<Integer> index1 = new ArrayList<Integer>();
        for (int i = 0; i < GAWS.length; i++) {
            for (int j = 0; j < GM.length; j++) {
                if (GAWS[i][0].equals(GM[j])) {
                    index1.add(j);
                    continue;
                }
            }
        }

        int[] index = new int[index1.size()];
        for (int i = 0; i < index1.size(); i++) {
            index[i] = index1.get(i);
        }
        return index;
    }

    /**
     * @param GD
     * @return
     */
    private static double min(double[][] GD) {
        double max = -1;
        for (int i = 0; i < GD.length; i++) {
            max = GD[i][0] < GD[i][1] ? GD[i][0] : GD[i][1];
            max = max < GD[i][2] ? max : GD[i][2];
            if (max == 0) {
                break;
            }
        }

        return max;
    }

    //初始化
    public void setData() {
        jLabel5.setText("");
        jLabel6.setText("");
        loadDiaCV = null;
        loadDiaY = null;
        loadDiaKa = null;
        loadDiaKd = null;
        writeDia = null;
        Kfile = new HashMap<String, String>();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[0][2], tableHead)
        );
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[0][1], tableHead1)
        );
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
    }

    public JTable getjTable1() {
        return jTable1;
    }

    public void setjTable1(JTable jTable1) {
        this.jTable1 = jTable1;
    }

    public void setTableModel(Object[][] tableModel) {
        this.tableModel = tableModel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
