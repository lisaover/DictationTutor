/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictationtutor;

import java.io.File;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Lisa
 */
public class FeedbackForm extends javax.swing.JFrame implements ListSelectionListener, ChangeListener {
    
    public final String yourResponse = "Your Response...";
    public final String correctResponse = "Correct Response...";
    
    //declare instance variables
    public MainJFrame main;
    public String vocabSummary;
    public String scramSummary;
    public String qaSummary;    

    /**
     * Creates new form FeedbackForm
     */
    public FeedbackForm(MainJFrame m) {
        initComponents();
        
        setMain(m);
        
        //retrieve date and use to set the sections summaries       
        //set the summary information for each section of the test - includes
        //name, date, number correct out of section total, number almost correct 
        //out of section total, and overall correct and almost correct out of section total
        Date dt = new Date();
        setVocabSummary(dt.toString());
        setScramSummary(dt.toString());
        setQaSummary(dt.toString());
        
        //Check each section's response array to see if it is empty and if so, 
        //set respective tab visibility to false. 
        //Otherwise popuate the lists with the correct responses.
        if(main.getVocabSetting() == 0) {
            vocablist.setVisible(false);
            vocabPane.setVisible(false);
        } else {
            DefaultListModel vocabModel = new DefaultListModel();
            for(int v = 0; v < main.vocabQuestion.length; v++) {
                vocabModel.addElement(main.vocabQuestion[v]);
            }
            //populate the list with test data
            vocablist.setModel(vocabModel);
        }
        
        if(main.getScramSetting() == 0) {
            scramlist.setVisible(false);
            scramPane.setVisible(false);
        } else {
            DefaultListModel scramModel = new DefaultListModel();
            for(int s = 0; s < main.scramQuestion.length; s++) {
                scramModel.addElement(main.scramQuestion[s]);
            }
            //populate the list with test data
            scramlist.setModel(scramModel);
        }
        
        if(main.getQaSetting() == 0) {
            qalist.setVisible(false);
            qaPane.setVisible(false);
        } else {
            DefaultListModel qaModel = new DefaultListModel();
            for(int q = 0; q < main.qaQuestion.length; q++) {
                qaModel.addElement(main.qaQuestion[q]);
            }
            //populate the list with test data
            qalist.setModel(qaModel);
        }
        
        //Set selected index to be Vocabulary (Tab 0) if that test was taken,
        //Scramble (Tab 1) if Vocabulary was not taken but Scramble was, or
        //QA (Tab 2) if Vocabulary and Scramble were not taken but QA was.
        //This makes sure a blank tab is not displayed to user onload of the feedback screen.
        if(main.getVocabSetting() != 0) {
            TabbedPane.setSelectedIndex(main.vocab);
            //write section summary data
            sectionSummary.setText(getVocabSummary());
            //set the first item to be selected on load
            vocablist.setSelectedIndex(0);
            //write the item summary
            writeItemSummary(0, main.vocab);
        } else if(main.getScramSetting() != 0) {
            TabbedPane.setSelectedIndex(main.scramble);
            //write section summary data
            sectionSummary.setText(getScramSummary());
            //set the first item to be selected on load
            scramlist.setSelectedIndex(0);
            //write the item summary
            writeItemSummary(0, main.scramble);
        } else if(main.getQaSetting() != 0) {
            TabbedPane.setSelectedIndex(main.qa);
            //write section summary data
            sectionSummary.setText(getQaSummary());
            //set the first item to be selected on load
            qalist.setSelectedIndex(0);
            //write the item summary
            writeItemSummary(0, main.qa);
        }
        
        //When the user changes tabs, the section summary appears based on the selected tab
        //method for action on change is at end and displays the correct section summary 
        //based on the current selected tab
        TabbedPane.addChangeListener(this);
        
        //list listener to detect when user selects an item from the list
        //for use in displaying response specific feedback 
        ListSelectionListener lsl = new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList source = (JList)e.getSource();
                int i;
                
                i = source.getSelectedIndex();
                
                //display correct response, user response, feedback for selected item
                if(source == vocablist) {
                    writeItemSummary(i, main.vocab);
                } else if (source == scramlist) {
                    writeItemSummary(i, main.scramble);
                } else if (source == qalist) {
                    writeItemSummary(i, main.qa);
                }
            }
        };
        
        //add listener to each list
        vocablist.addListSelectionListener(lsl);
        scramlist.addListSelectionListener(lsl);
        qalist.addListSelectionListener(lsl);
        
    }
    
    public MainJFrame getMain() {
        return main;
    }

    public void setMain(MainJFrame main) {
        this.main = main;
    }

    public JList getQalist() {
        return qalist;
    }

    public void setQalist(JList qalist) {
        this.qalist = qalist;
    }

    public JList getScramlist() {
        return scramlist;
    }

    public void setScramlist(JList scramlist) {
        this.scramlist = scramlist;
    }
    
    public String getVocabSummary() {
        return vocabSummary;
    }

    public void setVocabSummary(String date) { 
        this.vocabSummary = main.getUsername() + "\n\n" + date + "\n\n" + "Vocabulary Correct: " + 
                       main.getNumVocabCorrect() + "/" + 
                       main.getVocabSetting() + "   " +
                       calcPercent(main.getNumVocabCorrect(), main.getVocabSetting()) +
                       "\n\n" + "Vocabulary Almost Correct: " + 
                       main.getNumVocabAlmost() + "/" + 
                       main.getVocabSetting() +
                       "\n\n" + "Vocabulary Incorrect: " + 
                       (main.getVocabSetting() - (main.getNumVocabCorrect() + main.getNumVocabAlmost())) +
                       "/" + main.getVocabSetting() + "   " +
                       calcPercent((main.getNumVocabCorrect() + main.getNumVocabAlmost()), main.getVocabSetting());
    }

    public String getScramSummary() {
        return scramSummary;
    }

    public void setScramSummary(String date) {
        this.scramSummary = main.getUsername() + "\n\n" + date + "\n\n" + "Sentence Scramble Correct: "  + 
                       main.getNumScramCorrect() + "/" + 
                       main.getScramSetting() + "   " +
                       calcPercent(main.getNumScramCorrect(), main.getScramSetting()) +
                       "\n\n" + "Sentence Scramble Almost Correct: " + 
                       main.getNumScramAlmost() + "/" + 
                       main.getScramSetting() +
                       "\n\n" + "Sentence Scramble Incorrect: "  + 
                       (main.getScramSetting() - (main.getNumScramCorrect() + main.getNumScramAlmost())) +
                       "/" + main.getScramSetting() + "   " +
                       calcPercent((main.getNumScramCorrect() + main.getNumScramAlmost()), main.getScramSetting());
    }

    public String getQaSummary() {
        return qaSummary;
    }

    public void setQaSummary(String date) {
        this.qaSummary = main.getUsername() + "\n\n" + date + "\n\n" + "Question & Answer Correct: " + 
                       main.getNumQaCorrect() + "/" + 
                       main.getQaSetting() + "   " +
                       calcPercent(main.getNumQaCorrect(), main.getQaSetting()) +
                       "\n\n" + "Question & Answer Almost Correct: " + 
                       main.getNumQaAlmost() + "/" + 
                       main.getQaSetting() +
                       "\n\n" + "Question & Answer Incorrect: " + 
                       (main.getQaSetting() - (main.getNumQaCorrect() + main.getNumQaAlmost())) +
                       "/" + main.getQaSetting() + "   " +
                       calcPercent((main.getNumQaCorrect() + main.getNumQaAlmost()), main.getQaSetting());
    }
    
    public void writeItemSummary(int i, int code) {
        if(code == main.vocab) {
           itemSummary.setText(getSummaryElement(i, main.vocabFeedback) + "\n\n" +
                            correctResponse +  "\n" + 
                            getSummaryElement(i, main.vocabCorrect)  + 
                            "\n\n" + yourResponse + "\n" + 
                            getSummaryElement(i, main.vocabResponse));
        } else if (code == main.scramble) {
            itemSummary.setText(getSummaryElement(i, main.scramFeedback) + "\n\n" +
                            correctResponse +  "\n" + 
                            getSummaryElement(i, main.scramCorrect)  + 
                            "\n\n" + yourResponse + "\n" + 
                            getSummaryElement(i, main.scramResponse));
        } else if (code == main.qa) {
            itemSummary.setText(getSummaryElement(i, main.qaFeedback) + "\n\n" +
                            correctResponse +  "\n" + 
                            getSummaryElement(i, main.qaCorrect)  + 
                            "\n\n" + yourResponse + "\n" + 
                            getSummaryElement(i, main.qaResponse));
        }
    }
    
    /**
     * The getSummaryElement receives an index and a String array
     * and returns the array value at the index.
     * @param i
     * @param c
     * @return 
     */
    public String getSummaryElement(int i, String[] c) {
        return c[i];
    }
    
    /**
     * The getSummaryElement receives an index and an int array
     * and returns the array value at the index.
     * @param i
     * @param c
     * @return 
     */
    public int getSummaryElement(int i, int[] c) {
        return c[i];
    }
    
    /**
     * The calcPercent method receives the number correct and total, calculates
     * the percent, and returns it as a String.
     * @param numerator
     * @param denominator
     * @return 
     */
    public String calcPercent(int numerator, int denominator) {
        double percent;
        int asInt;
        
        if(denominator == 0 || numerator == 0) {
            return "";
        }
        
        //Calculate the percent with type casting and rounding (+ .05).
        percent = (((double)numerator / (double)denominator) + .05)*100;
        asInt = (int)percent;

        return asInt + "%";
    }
    
    /**
     * The stateChanged method detects a change in the active tab and 
     * retrieves and displays the appropriate summary information.
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e) {
           JTabbedPane pane = (JTabbedPane) e.getSource();
           int selectedIndex = pane.getSelectedIndex();
           
           if(selectedIndex == main.vocab && main.getVocabSetting() != 0) {
               //add section summary
               sectionSummary.setText(getVocabSummary());
               //set the first item in the list to be selected
               vocablist.setSelectedIndex(0);
               //write the item summary
               writeItemSummary(0, main.vocab);
           } else if(selectedIndex == main.scramble && main.getScramSetting() != 0) {
               //add section summary
               sectionSummary.setText(getScramSummary());
               //set the first item in the list to be selected
               scramlist.setSelectedIndex(0);
               //write the item summary
               writeItemSummary(0, main.scramble);
           } else if(selectedIndex == main.qa && main.getQaSetting() != 0) {
               //add section summary
               sectionSummary.setText(getQaSummary());
               //set the first item in the list to be selected
               qalist.setSelectedIndex(0);
               //write the item summary
               writeItemSummary(0, main.qa);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TabbedPane = new javax.swing.JTabbedPane();
        vocabPane = new javax.swing.JScrollPane();
        vocablist = new javax.swing.JList();
        scramPane = new javax.swing.JScrollPane();
        scramlist = new javax.swing.JList();
        qaPane = new javax.swing.JScrollPane();
        qalist = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemSummary = new javax.swing.JTextArea();
        saveFeedback = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        sectionSummary = new javax.swing.JTextArea();
        mainMenuBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 700));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Feedback");

        TabbedPane.setBackground(new java.awt.Color(255, 255, 255));
        TabbedPane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        vocablist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        vocablist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "", "", "", "", "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        vocabPane.setViewportView(vocablist);

        TabbedPane.addTab("Vocabulary", vocabPane);

        scramlist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        scramlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "", "", "", "", "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scramPane.setViewportView(scramlist);

        TabbedPane.addTab("Sentence Scramble", scramPane);

        qalist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        qalist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "", "", "", "", "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        qaPane.setViewportView(qalist);

        TabbedPane.addTab("Question & Answer", qaPane);

        itemSummary.setEditable(false);
        itemSummary.setColumns(20);
        itemSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        itemSummary.setLineWrap(true);
        itemSummary.setRows(5);
        itemSummary.setWrapStyleWord(true);
        jScrollPane1.setViewportView(itemSummary);

        saveFeedback.setBackground(new java.awt.Color(0, 102, 153));
        saveFeedback.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saveFeedback.setForeground(new java.awt.Color(255, 255, 255));
        saveFeedback.setText("Save");
        saveFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFeedbackActionPerformed(evt);
            }
        });

        sectionSummary.setEditable(false);
        sectionSummary.setColumns(20);
        sectionSummary.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sectionSummary.setRows(5);
        jScrollPane5.setViewportView(sectionSummary);

        mainMenuBtn.setBackground(new java.awt.Color(0, 102, 153));
        mainMenuBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mainMenuBtn.setForeground(new java.awt.Color(255, 255, 255));
        mainMenuBtn.setText("Main Menu");
        mainMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Section Summary");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Selected Question Summary");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(TabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(mainMenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mainMenuBtn)
                            .addComponent(saveFeedback)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(TabbedPane)))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFeedbackActionPerformed
        //file writing capabilities
         //add file writing capabilities
        //clear summary fields
        itemSummary.setText("");
        sectionSummary.setText("");
        File toSave;
        
        int returnCode = 1;
        
        while(returnCode == 1){
            toSave = CSVSaver.chooseFile();
            returnCode = CSVSaver.writeCSV(toSave, main);
        }
        //close window
        this.setVisible(false);
        
        //clear summary fields
        itemSummary.setText("");
        sectionSummary.setText("");
        //close window
        this.setVisible(false);
    }//GEN-LAST:event_saveFeedbackActionPerformed

    private void mainMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuBtnActionPerformed
        new ExitPopup(main, this).setVisible(true);
    }//GEN-LAST:event_mainMenuBtnActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedPane;
    private javax.swing.JTextArea itemSummary;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton mainMenuBtn;
    private javax.swing.JScrollPane qaPane;
    private javax.swing.JList qalist;
    private javax.swing.JButton saveFeedback;
    private javax.swing.JScrollPane scramPane;
    private javax.swing.JList scramlist;
    private javax.swing.JTextArea sectionSummary;
    private javax.swing.JScrollPane vocabPane;
    private javax.swing.JList vocablist;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
