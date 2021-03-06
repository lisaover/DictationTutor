/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictationtutor;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Lisa
 */
public class TestSettings extends javax.swing.JFrame {
    
    public final int vShort = 10;
    public final int sShort = 10;
    public final int qShort = 10;
    public final int vMed = 20;
    public final int sMed = 20;
    public final int qMed = 20;
    public final int vAll = 55;
    public final int sAll = 103;
    public final int qAll = 103;
    
    //Declare class instance variables
    MainJFrame main;

    /**
     * Creates new form testSettings
     * @param m
     */
    public TestSettings(MainJFrame m) {
        setMain(m);
        initComponents();
        
        initSelections();
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(getMostRecentFocusOwner());
    }

    public MainJFrame getMain() {
        return main;
    }

    public final void setMain(MainJFrame m) {
        this.main = m;
    }
    
    /**
     * This method gets the current test settings from main and 
     * initializes the radio buttons' focus
     */
    public void initSelections() {
        int v, s, q;
        v = main.getVocabSetting();
        s = main.getScramSetting();
        q = main.getQaSetting();
        
        if (v == 0) {
            vocab0.setSelected(true);
        } else if(v == vShort) {
            vocabShort.setSelected(true);
        } else if(v == vMed) {
            vocabMed.setSelected(true);
        } else {
            vocabAll.setSelected(true);
        }
        
        if (s == 0) {
            scram0.setSelected(true);
        } else if(s == sShort) {
            scramShort.setSelected(true);
        } else if(s == sMed) {
            scramMed.setSelected(true);
        } else {
            scramAll.setSelected(true);
        }
        
        if (q == 0) {
            qa0.setSelected(true);
        } else if(q == qShort) {
            qaShort.setSelected(true);
        } else if(q == qMed) {
            qaMed.setSelected(true);
        } else {
            qaAll.setSelected(true);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vocabGrp = new javax.swing.ButtonGroup();
        scramGrp = new javax.swing.ButtonGroup();
        qaGrp = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        mainhead = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        vocab0 = new javax.swing.JRadioButton();
        vocabShort = new javax.swing.JRadioButton();
        vocabMed = new javax.swing.JRadioButton();
        vocabAll = new javax.swing.JRadioButton();
        scramShort = new javax.swing.JRadioButton();
        scramMed = new javax.swing.JRadioButton();
        scramAll = new javax.swing.JRadioButton();
        qaShort = new javax.swing.JRadioButton();
        qaMed = new javax.swing.JRadioButton();
        qaAll = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        saveset = new javax.swing.JButton();
        cancelset = new javax.swing.JButton();
        scram0 = new javax.swing.JRadioButton();
        qa0 = new javax.swing.JRadioButton();
        testSetMsg = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        mainhead.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        mainhead.setForeground(new java.awt.Color(0, 51, 51));
        mainhead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainhead.setText("Test Settings");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Vocabulary");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Sentence Scramble");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Question & Answer");

        vocab0.setBackground(new java.awt.Color(255, 255, 255));
        vocabGrp.add(vocab0);
        vocab0.setText("0");
        vocab0.setActionCommand("vocab0");

        vocabShort.setBackground(new java.awt.Color(255, 255, 255));
        vocabGrp.add(vocabShort);
        vocabShort.setText("10");
        vocabShort.setActionCommand("vocabShort");

        vocabMed.setBackground(new java.awt.Color(255, 255, 255));
        vocabGrp.add(vocabMed);
        vocabMed.setText("20");
        vocabMed.setActionCommand("vocabMed");

        vocabAll.setBackground(new java.awt.Color(255, 255, 255));
        vocabGrp.add(vocabAll);
        vocabAll.setText("All");
        vocabAll.setActionCommand("vocabAll");
        vocabAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vocabAllActionPerformed(evt);
            }
        });

        scramShort.setBackground(new java.awt.Color(255, 255, 255));
        scramGrp.add(scramShort);
        scramShort.setText("10");
        scramShort.setActionCommand("scramShort");

        scramMed.setBackground(new java.awt.Color(255, 255, 255));
        scramGrp.add(scramMed);
        scramMed.setText("20");
        scramMed.setActionCommand("scramMed");

        scramAll.setBackground(new java.awt.Color(255, 255, 255));
        scramGrp.add(scramAll);
        scramAll.setText("All");
        scramAll.setActionCommand("scramAll");

        qaShort.setBackground(new java.awt.Color(255, 255, 255));
        qaGrp.add(qaShort);
        qaShort.setText("10");
        qaShort.setActionCommand("qaShort");

        qaMed.setBackground(new java.awt.Color(255, 255, 255));
        qaGrp.add(qaMed);
        qaMed.setText("20");
        qaMed.setActionCommand("qaMed");

        qaAll.setBackground(new java.awt.Color(255, 255, 255));
        qaGrp.add(qaAll);
        qaAll.setText("All");
        qaAll.setActionCommand("qaAll");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        saveset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saveset.setText("Save");
        saveset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savesetActionPerformed(evt);
            }
        });

        cancelset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cancelset.setText("Cancel");
        cancelset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelsetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveset)
                    .addComponent(cancelset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scram0.setBackground(new java.awt.Color(255, 255, 255));
        scramGrp.add(scram0);
        scram0.setText("0");
        scram0.setActionCommand("scram0");
        scram0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scram0ActionPerformed(evt);
            }
        });

        qa0.setBackground(new java.awt.Color(255, 255, 255));
        qaGrp.add(qa0);
        qa0.setText("0");
        qa0.setActionCommand("qa0");

        testSetMsg.setEditable(false);
        testSetMsg.setBackground(new java.awt.Color(255, 255, 255));
        testSetMsg.setForeground(new java.awt.Color(204, 0, 0));
        testSetMsg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        testSetMsg.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testSetMsg)
                    .addComponent(mainhead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vocab0)
                            .addComponent(scram0)
                            .addComponent(qa0))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vocabShort)
                            .addComponent(scramShort)
                            .addComponent(qaShort))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vocabMed)
                            .addComponent(scramMed)
                            .addComponent(qaMed))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vocabAll)
                            .addComponent(scramAll)
                            .addComponent(qaAll))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainhead)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testSetMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(vocab0)
                    .addComponent(vocabShort)
                    .addComponent(vocabMed)
                    .addComponent(vocabAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(scram0)
                    .addComponent(scramShort)
                    .addComponent(scramMed)
                    .addComponent(scramAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(qa0)
                    .addComponent(qaShort)
                    .addComponent(qaMed)
                    .addComponent(qaAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void savesetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savesetActionPerformed
        
    if(vocabGrp.getSelection().getActionCommand().equals("vocab0") && 
        scramGrp.getSelection().getActionCommand().equals("scram0") &&
        qaGrp.getSelection().getActionCommand().equals("qa0")) {
            
        testSetMsg.setText("PLease select a non-zero value for at least one test.");
            
    } else {
        
        if(vocabGrp.getSelection().getActionCommand().equals("vocab0")) {
            main.setVocabSetting(0);
        } else if(vocabGrp.getSelection().getActionCommand().equals("vocabShort")) {
            main.setVocabSetting(vShort);
        }  else if(vocabGrp.getSelection().getActionCommand().equals("vocabMed")) {
            main.setVocabSetting(vMed);
        } else if(vocabGrp.getSelection().getActionCommand().equals("vocabAll")) {
            main.setVocabSetting(vAll);
        }
        
        if(scramGrp.getSelection().getActionCommand().equals("scram0")) {
            main.setScramSetting(0);
        } else if(scramGrp.getSelection().getActionCommand().equals("scramShort")) {
            main.setScramSetting(sShort);
        } else if(scramGrp.getSelection().getActionCommand().equals("scramMed")) {
            main.setScramSetting(sMed);
        } else if(scramGrp.getSelection().getActionCommand().equals("scramAll")) {
            main.setScramSetting(sAll);
        }
        
        if(qaGrp.getSelection().getActionCommand().equals("qa0")) {
            main.setQaSetting(0);
        } else if(qaGrp.getSelection().getActionCommand().equals("qaShort")) {
            main.setQaSetting(qShort);
        }else if(qaGrp.getSelection().getActionCommand().equals("qaMed")) {
            main.setQaSetting(qMed);
        } else if(qaGrp.getSelection().getActionCommand().equals("qaAll")) {
            main.setQaSetting(qAll);
        }
        
        main.setTestTotal(main.getVocabSetting() + main.getScramSetting() + 
                main.getQaSetting());
        
        this.setVisible(false);
        
    } //end out else
        
    }//GEN-LAST:event_savesetActionPerformed

    private void cancelsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelsetActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelsetActionPerformed

    private void vocabAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vocabAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vocabAllActionPerformed

    private void scram0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scram0ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scram0ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel mainhead;
    private javax.swing.JRadioButton qa0;
    private javax.swing.JRadioButton qaAll;
    private javax.swing.ButtonGroup qaGrp;
    private javax.swing.JRadioButton qaMed;
    private javax.swing.JRadioButton qaShort;
    private javax.swing.JButton saveset;
    private javax.swing.JRadioButton scram0;
    private javax.swing.JRadioButton scramAll;
    private javax.swing.ButtonGroup scramGrp;
    private javax.swing.JRadioButton scramMed;
    private javax.swing.JRadioButton scramShort;
    private javax.swing.JTextField testSetMsg;
    private javax.swing.JRadioButton vocab0;
    private javax.swing.JRadioButton vocabAll;
    private javax.swing.ButtonGroup vocabGrp;
    private javax.swing.JRadioButton vocabMed;
    private javax.swing.JRadioButton vocabShort;
    // End of variables declaration//GEN-END:variables
}
