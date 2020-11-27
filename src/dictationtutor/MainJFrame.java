/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictationtutor;

import java.awt.CardLayout;
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
public class MainJFrame extends javax.swing.JFrame implements ListSelectionListener, ChangeListener {
    
    /* Create our wordbank 
        * You will need to edit these paths so they have the right directory
        */
    
        public final String relativePath = "./src/dictationtutor/";
    
        public final QuestionBank ourWordBank = new QuestionBank('w');
    
        public final QuestionBank ourQABank = new QuestionBank('q');
        
        public final String vocabTitle = "Vocabulary";
        public final String scramTitle = "Sentence Scramble";
        public final String qaTitle = "Question & Answer";
        public final String vocabInstructions = "Type the word or phrase you hear. To play the selection, click the sound button below.";
        public final String scramInstructions = "Unscramble and type the sentence on the right based on what you hear. To play the selection, click the sound button below.";
        public final String qaInstructions = "Type the sentence you hear which is the answer to the question on the right. To play the selection, click the sound button below.";
        //default test settings
        public final int defaultVSet = 10;
        public final int defaultSSet = 10;
        public final int defaultQSet = 10;
        //for associative index of testFlag and tabbed pane indices
        public final int vocab = 0;
        public final int scramble = 1;
        public final int qa = 2;
        public final int question = 1;
        public final int answer = 2;
    
        
        //Create an instance of our MP3 player       
        public final Mp3Player ourPlayer = new Mp3Player();
        
        //Declare class instance variables
        private Question current;
        
        //practice test flag
        private int testFlag;
        
        //simulated test variables
        //keep track of the number of times a student selects the sound button during a simulated test question
        private int soundTracker;  
        //keep track of student's name for feedback on simulated test
        private String username;
        //test setting indicating the number of vocabulary, scramble, and qa questions on simulated test
        private int vocabSetting;
        private int scramSetting;
        private int qaSetting;
        //total number of test questions - sum of all three of the aforementioned settings
        private int testTotal;
        //keeps track of the number of vocabulary, scramble, qa, and total questions completed on simulated test
        private int vocabCount;
        private int scramCount;
        private int qaCount;
        private int testCount;
        //holds the student's responses, feedback, questions, and correct answers during the simulated test
        public String[] vocabResponse; 
        public String[] vocabFeedback; 
        public String[] vocabCorrect; 
        public String[] vocabQuestion;
        public String[] scramResponse; 
        public String[] scramFeedback; 
        public String[] scramCorrect;
        public String[] scramQuestion;
        public String[] qaResponse; 
        public String[] qaFeedback; 
        public String[] qaCorrect;
        public String[] qaQuestion;
        //holds the number of correct responses for vocabulary, scramble, and qa
        private int numVocabCorrect;
        private int numScramCorrect;
        private int numQaCorrect;
        //holds the number of almost correct responses for vocabulary, scramble, and qa
        private int numVocabAlmost;
        private int numScramAlmost;
        private int numQaAlmost;

    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
        
        //simulated test settings and tracking
        setSoundTracker(0);
        setVocabSetting(defaultVSet);
        setScramSetting(defaultSSet);
        setQaSetting(defaultQSet);
        setTestTotal(this.vocabSetting + this.scramSetting + this.qaSetting);
        //The following are initialized at start of each test - may not need them initialized
        //here initialize test count, section count, and response, correct, and feedback arrays 
        setVocabCount(0);
        setScramCount(0);
        setQaCount(0);
        setTestCount(0);
        setVocabResponse();
        setVocabCorrect();
        setVocabFeedback();
        setVocabQuestion();
        setScramResponse();
        setScramCorrect();
        setScramFeedback();
        setScramQuestion();
        setQaResponse();
        setQaCorrect();
        setQaFeedback();
        setQaQuestion();
        
        //In guided practice, when the user changes tabs, the first item in the 
        //list will be selected and the Question for that item will be retrieved
        //and loaded into current - code for this is in stateChanged method
        //at end of program
        tabbedpane.addChangeListener(this);
        
        //populate the lists
        String temp = ""; //for finding and eliminating repeat questions/answers
        DefaultListModel vocabModel = new DefaultListModel();
            for(int vM = 0; vM < ourWordBank.getAllQuestions().length; vM++) {
                vocabModel.addElement(ourWordBank.getAllQuestions()[vM]);
            }
        
            vocablist.setModel(vocabModel);
                    
            DefaultListModel questModel = new DefaultListModel();
            for(int qM = 0; qM < ourQABank.getAllQuestions().length; qM++) {
                if(!ourQABank.getAllQuestions()[qM].equals(temp))
                    questModel.addElement(ourQABank.getAllQuestions()[qM]);
                temp = ourQABank.getAllQuestions()[qM];
            }
            
            questlist.setModel(questModel);
                        
            DefaultListModel answerModel = new DefaultListModel();
            for(int aM = 0; aM < ourQABank.getAllAnswers().length; aM++) {
                if(!ourQABank.getAllAnswers()[aM].equals(temp))
                    answerModel.addElement(ourQABank.getAllAnswers()[aM]);
                temp = ourQABank.getAllAnswers()[aM];
            }
            
            answerlist.setModel(answerModel);
        
        //list selection listener sets new question when user selects an item
        //and write the item in the area below the sound button
        ListSelectionListener lsl = new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //get the newly selected list item
                JList source = (JList)e.getSource();
                //get the value of the selected list item
                Object o;
                o = source.getSelectedValue();
                
                //write the selected item to the selectedItem box located 
                //under the sound button
                selectedItem.setText((String)o);
                
                //get and set current question based on selection value
                if(source == vocablist) {
                    setCurrent(ourWordBank.getQuestionByQuestionText((String)o));
                } else if (source == questlist) {
                    setCurrent(ourQABank.getQuestionByQuestionText((String)o));
                } else if (source == answerlist) {
                    setCurrent(ourQABank.getQuestionByAnswerText((String)o));
                }
                
            }
        };
        
        vocablist.addListSelectionListener(lsl);
        questlist.addListSelectionListener(lsl);
        answerlist.addListSelectionListener(lsl);
    }
    /**
     * getCurrent returns the current Question
     * @return 
     */
    public Question getCurrent() {
        return current;
    }
    /**
     * setCurrent sets the current Question
     * @param current 
     */
    public void setCurrent(Question current) {
        this.current = current;
    }
    /**
     * setSoundTracker sets the sound tracker counter,
     * which is used to control the number of times a student listens to a 
     * selection during simulated test
     * @param s 
     */
    public void setSoundTracker( int s){
        this.soundTracker = s;
    }
    /**
     * getSoundtracker returns the current value of the sound tracker counter,
     * which is used to control the number of times a student listens to a 
     * selection during simulated test
     * @return 
     */
    public int getSoundTracker(){
    return soundTracker;
    }
    /**
     * setTestFlag sets the test flag to 0 for vocab, 1 for scramble, or 2
     * for qa for keeping track of which section a student is on during 
     * the simulated test
     * @param t 
     */
    public void setTestFlag(int t) {
        this.testFlag = t;
    }
    /**
     * getTestFlag returns the current value of the test flag 0 for vocab, 1 
     * for scramble, or 2 for qa for keeping track of which section a student 
     * is on during the simulated test
     * @return 
     */
    public int getTestFlag() {
        return testFlag;
    }
    /**
     * getUsername returns the current username, 
     * which a student enters at the start of the simulated test
     * @return 
     */
    public String getUsername() {
        return username;
    }
    /**
     * setUsername sets the username, which is used to provide feedback 
     * for the simulated test
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * getTestTotal returns the total number of questions that will be asked
     * during a simulated test
     * @return 
     */
    public int getTestTotal() {
        return testTotal;
    }
    /**
     * setTestTotal sets the total number of questions based on the test
     * settings for each section of the simulated test
     * @param c 
     */
    public void setTestTotal(int c) {
        this.testTotal = c;
    }
    
    public int getVocabCount() {
        return vocabCount;
    }

    public void setVocabCount(int vocabCount) {
        this.vocabCount = vocabCount;
    }

    public int getScramCount() {
        return scramCount;
    }

    public void setScramCount(int scramCount) {
        this.scramCount = scramCount;
    }

    public int getQaCount() {
        return qaCount;
    }

    public void setQaCount(int qaCount) {
        this.qaCount = qaCount;
    }
    
    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }
    
    public int getVocabSetting() {
        return vocabSetting;
    }

    public void setVocabSetting(int v) {
        this.vocabSetting = v;
    }

    public int getScramSetting() {
        return scramSetting;
    }

    public void setScramSetting(int s) {
        this.scramSetting = s;
    }

    public int getQaSetting() {
        return qaSetting;
    }

    public void setQaSetting(int q) {
        this.qaSetting = q;
    }
    
    public String[] getVocabResponse() {
        return vocabResponse;
    }
    public void setVocabResponse() {
        this.vocabResponse = new String[this.vocabSetting];
    }
    public void addVocabResponse(int i, String s) {
        this.vocabResponse[i] = s;
    }
    
    public String[] getVocabCorrect() {
        return vocabCorrect;
    }
    public void setVocabCorrect() {
        this.vocabCorrect = new String[this.vocabSetting];
    }
    public void addVocabCorrect(int i, String s) {
        this.vocabCorrect[i] = s;
    }
    
    public String[] getVocabQuestion() {
        return vocabQuestion;
    }
    public void setVocabQuestion() {
        this.vocabQuestion = new String[this.vocabSetting];
    }
    public void addVocabQuestion(int i, String s) {
        this.vocabQuestion[i] = s;
    }
    
    public String[] getVocabFeedback() {
        return vocabFeedback;
    }    
    public void setVocabFeedback() {
        this.vocabFeedback = new String[this.vocabSetting];
    }
    public void addVocabFeedback(int i, String s) {
        this.vocabFeedback[i] = s;
    }
    
    public String[] getScramResponse() {
        return scramResponse;
    }
    public void setScramResponse() {
        this.scramResponse = new String[this.scramSetting];
    }
    public void addScramResponse(int i, String s) {
        this.scramResponse[i] = s;
    }
    
    public String[] getScramCorrect() {
        return scramCorrect;
    }
    public void setScramCorrect() {
        this.scramCorrect = new String[this.scramSetting];
    }
    public void addScramCorrect(int i, String s) {
        this.scramCorrect[i] = s;
    }
    
    public String[] getScramQuestion() {
        return scramQuestion;
    }
    public void setScramQuestion() {
        this.scramQuestion = new String[this.scramSetting];
    }
    public void addScramQuestion(int i, String s) {
        this.scramQuestion[i] = s;
    }
    
    public String[] getScramFeedback() {
        return scramFeedback;
    }    

    public void setScramFeedback() {
        this.scramFeedback = new String[this.scramSetting];
    }
    public void addScramFeedback(int i, String s) {
        this.scramFeedback[i] = s;
    }
    
    public String[] getQaResponse() {
        return qaResponse;
    }
    public void setQaResponse() {
        this.qaResponse = new String[this.qaSetting];
    }
    public void addQaResponse(int i, String s) {
        this.qaResponse[i] = s;
    }
    
    public String[] getQaCorrect() {
        return qaCorrect;
    }
    public void setQaCorrect() {
        this.qaCorrect = new String[this.qaSetting];
    }
    public void addQaCorrect(int i, String s) {
        this.qaCorrect[i] = s;
    }
    
    public String[] getQaQuestion() {
        return qaQuestion;
    }
    public void setQaQuestion() {
        this.qaQuestion = new String[this.qaSetting];
    }
    public void addQaQuestion(int i, String s) {
        this.qaQuestion[i] = s;
    }
    
    public String[] getQaFeedback() {
        return qaFeedback;
    }    

    public void setQaFeedback() {
        this.qaFeedback = new String[this.qaSetting];
    }
    public void addQaFeedback(int i, String s) {
        this.qaFeedback[i] = s;
    }
        
    public int getNumVocabCorrect() {
        return numVocabCorrect;
    }

    public void setNumVocabCorrect(int c) {
        this.numVocabCorrect = c;
    }
    
    public int getNumVocabAlmost() {
        return numVocabAlmost;
    }

    public void setNumVocabAlmost(int a) {
        this.numVocabAlmost = a;
    }

    public int getNumScramCorrect() {
        return numScramCorrect;
    }

    public void setNumScramCorrect(int c) {
        this.numScramCorrect = c;
    }
    
    public int getNumScramAlmost() {
        return numScramAlmost;
    }

    public void setNumScramAlmost(int a) {
        this.numScramAlmost = a;
    }

    public int getNumQaCorrect() {
        return numQaCorrect;
    }

    public void setNumQaCorrect(int c) {
        this.numQaCorrect = c;
    }
    
    public int getNumQaAlmost() {
        return numQaAlmost;
    }

    public void setNumQaAlmost(int a) {
        this.numQaAlmost = a;
    }
    
    public void startTest() {
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"simtest");
        //clear test question area
        simtestQuestArea.setText("");
        
        //set total number of questions
        setTestTotal(this.vocabSetting + this.scramSetting + this.qaSetting);
        
        //reset section counters to zero
        setVocabCount(0);
        setScramCount(0);
        setQaCount(0);
        
        //start test at 1
        this.setTestCount(1);
        
        //initialize feedback, correct, and response arrays
        setVocabResponse();
        setVocabCorrect();
        setVocabFeedback();
        setScramResponse();
        setScramCorrect();
        setScramFeedback();
        setQaResponse();
        setQaCorrect();
        setQaFeedback();
        
        //set up first section based on test settings
        //if vocabulary settings are not zero, start here
        if(this.vocabSetting != 0) {
            
            //set current to vocab question
            setCurrent(ourWordBank.getNewQuestionNoReplacement());
            this.setVocabCount(1);
            
            //set test flag to vocab
            setTestFlag(vocab);
            
            sectionTitle.setText(vocabTitle);
            sectionInstruct.setText(vocabInstructions);
            
            secTrack.setText("Vocabulary " + this.vocabCount + " of " + this.vocabSetting);
            testTrack.setText("Total " + this.getTestCount() + " of " + this.testTotal);
        
        //if vocabulary settings are zero but scramble settings are not zero, start here
        } else if(this.scramSetting != 0) {
            
            //set current to question question
            setCurrent(ourQABank.getNewQuestionNoReplacement());
            this.setScramCount(1);
            
            //set test flag to scramble
            setTestFlag(scramble);
            
            simtestQuestArea.setText(current.getScrambledAnswerText());
            sectionTitle.setText(scramTitle);
            sectionInstruct.setText(scramInstructions);
            
            secTrack.setText("Sentence Scramble " + this.scramCount + " of " + this.scramSetting);
            testTrack.setText("Total " + this.getTestCount() + " of " + this.testTotal);
        
        //if vocabulary and scramble settings are zero but qa settings are not zero, start here
        } else if(this.qaSetting != 0) {
            
            //set current to question question
            setCurrent(ourQABank.getNewQuestionNoReplacement());
            this.setQaCount(1);
            
            //set test flag to qa
            setTestFlag(qa);
            
            simtestQuestArea.setText(current.getQuestionText());    
            sectionTitle.setText(qaTitle);
            sectionInstruct.setText(qaInstructions);
            
            secTrack.setText("Question & Answer " + this.qaCount + " of " + this.qaSetting);
            testTrack.setText("Total " + this.getTestCount() + " of " + this.testTotal);
            
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

        mainpanel = new javax.swing.JPanel();
        mainmenu = new javax.swing.JPanel();
        maincontainer = new javax.swing.JPanel();
        menuarea2 = new javax.swing.JPanel();
        vocabulary = new javax.swing.JButton();
        sentencescramble = new javax.swing.JButton();
        questionanswer = new javax.swing.JButton();
        menuarea1 = new javax.swing.JPanel();
        guidedpracticebtn = new javax.swing.JButton();
        menuarea3 = new javax.swing.JPanel();
        simtestbtn = new javax.swing.JButton();
        settings = new javax.swing.JButton();
        mainhead = new javax.swing.JLabel();
        menuarea5 = new javax.swing.JPanel();
        exitbtn1 = new javax.swing.JButton();
        simtest = new javax.swing.JPanel();
        simtestContainer = new javax.swing.JPanel();
        simtestDashboard = new javax.swing.JPanel();
        simtestInstruct = new javax.swing.JPanel();
        sectionTitle = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane7 = new javax.swing.JScrollPane();
        sectionInstruct = new javax.swing.JTextArea();
        simtestDashBtns = new javax.swing.JPanel();
        btncontainer2 = new javax.swing.JPanel();
        simtestSound = new javax.swing.JButton();
        simtestSubmit = new javax.swing.JButton();
        simtestMainBtn = new javax.swing.JButton();
        soundLimitMsg = new javax.swing.JTextField();
        simtestWorkspace = new javax.swing.JPanel();
        simtestWorkspace2 = new javax.swing.JPanel();
        workAreaInstruct = new javax.swing.JLabel();
        simtestResponse = new javax.swing.JTextField();
        simtestClear = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        simtestQuestArea = new javax.swing.JTextArea();
        testTrack = new javax.swing.JTextField();
        secTrack = new javax.swing.JTextField();
        guidedpractice = new javax.swing.JPanel();
        mainmenubtn1 = new javax.swing.JButton();
        guidedpracticehead = new javax.swing.JLabel();
        tabbedpane = new javax.swing.JTabbedPane();
        vocabpane = new javax.swing.JScrollPane();
        vocablist = new javax.swing.JList();
        questpane = new javax.swing.JScrollPane();
        questlist = new javax.swing.JList();
        answerpane = new javax.swing.JScrollPane();
        answerlist = new javax.swing.JList();
        txtcontainer1 = new javax.swing.JPanel();
        txtinstruct1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectedItem = new javax.swing.JTextArea();
        gp_textarea = new javax.swing.JScrollPane();
        gp_usertext = new javax.swing.JTextArea();
        gp_clear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        soundbtn1 = new javax.swing.JButton();
        practest = new javax.swing.JPanel();
        practestContainer = new javax.swing.JPanel();
        practestDashboard = new javax.swing.JPanel();
        practestInstruct = new javax.swing.JPanel();
        sectionTitle1 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane8 = new javax.swing.JScrollPane();
        sectionInstruct1 = new javax.swing.JTextArea();
        practestDashBtns = new javax.swing.JPanel();
        btncontainer3 = new javax.swing.JPanel();
        practestMainBtn = new javax.swing.JButton();
        practestNext = new javax.swing.JButton();
        practestSound1 = new javax.swing.JButton();
        practestWorkspace = new javax.swing.JPanel();
        practestWorkspace2 = new javax.swing.JPanel();
        practestResponse = new javax.swing.JTextField();
        practestClear = new javax.swing.JButton();
        practestCheck = new javax.swing.JButton();
        practestShow = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        practestFeedback = new javax.swing.JTextArea();
        testTrack1 = new javax.swing.JTextField();
        secTrack1 = new javax.swing.JTextField();
        jScrollPane21 = new javax.swing.JScrollPane();
        practestQuestArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        mainpanel.setName("mainpanel"); // NOI18N
        mainpanel.setPreferredSize(new java.awt.Dimension(900, 700));
        mainpanel.setLayout(new java.awt.CardLayout());

        mainmenu.setBackground(new java.awt.Color(0, 102, 153));

        maincontainer.setBackground(new java.awt.Color(0, 102, 153));
        maincontainer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        maincontainer.setPreferredSize(new java.awt.Dimension(900, 700));

        menuarea2.setBackground(new java.awt.Color(0, 102, 153));
        menuarea2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Practice Test", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N

        vocabulary.setBackground(new java.awt.Color(204, 204, 255));
        vocabulary.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        vocabulary.setForeground(new java.awt.Color(0, 51, 51));
        vocabulary.setText("Vocabulary");
        vocabulary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vocabularyActionPerformed(evt);
            }
        });

        sentencescramble.setBackground(new java.awt.Color(204, 204, 255));
        sentencescramble.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        sentencescramble.setForeground(new java.awt.Color(0, 51, 51));
        sentencescramble.setText("Sentence Scramble");
        sentencescramble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentencescrambleActionPerformed(evt);
            }
        });

        questionanswer.setBackground(new java.awt.Color(204, 204, 255));
        questionanswer.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        questionanswer.setForeground(new java.awt.Color(0, 51, 51));
        questionanswer.setText("Questions & Answers");
        questionanswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                questionanswerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuarea2Layout = new javax.swing.GroupLayout(menuarea2);
        menuarea2.setLayout(menuarea2Layout);
        menuarea2Layout.setHorizontalGroup(
            menuarea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuarea2Layout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addGroup(menuarea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(questionanswer, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sentencescramble, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vocabulary, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(226, 226, 226))
        );
        menuarea2Layout.setVerticalGroup(
            menuarea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuarea2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(vocabulary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sentencescramble)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(questionanswer)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        menuarea1.setBackground(new java.awt.Color(0, 102, 153));
        menuarea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Guided Practice", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        menuarea1.setName(""); // NOI18N

        guidedpracticebtn.setBackground(new java.awt.Color(204, 204, 255));
        guidedpracticebtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        guidedpracticebtn.setForeground(new java.awt.Color(0, 51, 51));
        guidedpracticebtn.setText("Dictation Practice");
        guidedpracticebtn.setAutoscrolls(true);
        guidedpracticebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guidedpracticebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuarea1Layout = new javax.swing.GroupLayout(menuarea1);
        menuarea1.setLayout(menuarea1Layout);
        menuarea1Layout.setHorizontalGroup(
            menuarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuarea1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(guidedpracticebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(226, 226, 226))
        );
        menuarea1Layout.setVerticalGroup(
            menuarea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuarea1Layout.createSequentialGroup()
                .addComponent(guidedpracticebtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuarea3.setBackground(new java.awt.Color(0, 102, 153));
        menuarea3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Simulated Test", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N

        simtestbtn.setBackground(new java.awt.Color(204, 204, 255));
        simtestbtn.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        simtestbtn.setForeground(new java.awt.Color(0, 51, 51));
        simtestbtn.setText("Start Test");
        simtestbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simtestbtnActionPerformed(evt);
            }
        });

        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dictationtutor/images/settings.jpg"))); // NOI18N
        settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuarea3Layout = new javax.swing.GroupLayout(menuarea3);
        menuarea3.setLayout(menuarea3Layout);
        menuarea3Layout.setHorizontalGroup(
            menuarea3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuarea3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(simtestbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settings, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
        );
        menuarea3Layout.setVerticalGroup(
            menuarea3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuarea3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuarea3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(settings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simtestbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        mainhead.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        mainhead.setForeground(new java.awt.Color(255, 255, 255));
        mainhead.setText("Main Menu");

        menuarea5.setBackground(new java.awt.Color(0, 102, 153));
        menuarea5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Sylfaen", 0, 24), new java.awt.Color(0, 102, 153))); // NOI18N

        exitbtn1.setBackground(new java.awt.Color(204, 204, 255));
        exitbtn1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        exitbtn1.setForeground(new java.awt.Color(0, 51, 51));
        exitbtn1.setText("Exit");
        exitbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuarea5Layout = new javax.swing.GroupLayout(menuarea5);
        menuarea5.setLayout(menuarea5Layout);
        menuarea5Layout.setHorizontalGroup(
            menuarea5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuarea5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231))
        );
        menuarea5Layout.setVerticalGroup(
            menuarea5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuarea5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitbtn1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout maincontainerLayout = new javax.swing.GroupLayout(maincontainer);
        maincontainer.setLayout(maincontainerLayout);
        maincontainerLayout.setHorizontalGroup(
            maincontainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, maincontainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainhead)
                .addGap(335, 335, 335))
            .addGroup(maincontainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(maincontainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuarea2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuarea1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuarea3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuarea5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        maincontainerLayout.setVerticalGroup(
            maincontainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(maincontainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainhead)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuarea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuarea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuarea3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuarea5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
        );

        javax.swing.GroupLayout mainmenuLayout = new javax.swing.GroupLayout(mainmenu);
        mainmenu.setLayout(mainmenuLayout);
        mainmenuLayout.setHorizontalGroup(
            mainmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(maincontainer, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainmenuLayout.setVerticalGroup(
            mainmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainmenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(maincontainer, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainpanel.add(mainmenu, "mainMenu");

        simtestContainer.setBackground(new java.awt.Color(0, 102, 153));

        simtestDashboard.setBackground(new java.awt.Color(255, 255, 255));

        simtestInstruct.setBackground(new java.awt.Color(255, 255, 255));
        simtestInstruct.setName("instructions"); // NOI18N
        simtestInstruct.setPreferredSize(new java.awt.Dimension(300, 240));

        sectionTitle.setEditable(false);
        sectionTitle.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        sectionTitle.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sectionTitle.setBorder(null);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane7.setFocusable(false);

        sectionInstruct.setEditable(false);
        sectionInstruct.setColumns(20);
        sectionInstruct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        sectionInstruct.setForeground(new java.awt.Color(0, 51, 51));
        sectionInstruct.setLineWrap(true);
        sectionInstruct.setRows(5);
        sectionInstruct.setWrapStyleWord(true);
        jScrollPane7.setViewportView(sectionInstruct);

        javax.swing.GroupLayout simtestInstructLayout = new javax.swing.GroupLayout(simtestInstruct);
        simtestInstruct.setLayout(simtestInstructLayout);
        simtestInstructLayout.setHorizontalGroup(
            simtestInstructLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestInstructLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
            .addGroup(simtestInstructLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
            .addComponent(sectionTitle)
        );
        simtestInstructLayout.setVerticalGroup(
            simtestInstructLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestInstructLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(sectionTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        simtestDashBtns.setBackground(new java.awt.Color(255, 255, 255));

        btncontainer2.setBackground(new java.awt.Color(255, 255, 255));

        simtestSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dictationtutor/images/play2-sm.jpg"))); // NOI18N
        simtestSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simtestSoundActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btncontainer2Layout = new javax.swing.GroupLayout(btncontainer2);
        btncontainer2.setLayout(btncontainer2Layout);
        btncontainer2Layout.setHorizontalGroup(
            btncontainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btncontainer2Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(simtestSound, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btncontainer2Layout.setVerticalGroup(
            btncontainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simtestSound, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        simtestSubmit.setBackground(new java.awt.Color(0, 102, 153));
        simtestSubmit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        simtestSubmit.setForeground(new java.awt.Color(255, 255, 255));
        simtestSubmit.setText("Submit");
        simtestSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simtestSubmitActionPerformed(evt);
            }
        });

        simtestMainBtn.setBackground(new java.awt.Color(0, 102, 153));
        simtestMainBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        simtestMainBtn.setForeground(new java.awt.Color(255, 255, 255));
        simtestMainBtn.setText("Main Menu");
        simtestMainBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simtestMainBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout simtestDashBtnsLayout = new javax.swing.GroupLayout(simtestDashBtns);
        simtestDashBtns.setLayout(simtestDashBtnsLayout);
        simtestDashBtnsLayout.setHorizontalGroup(
            simtestDashBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btncontainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(simtestDashBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simtestDashBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(simtestMainBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simtestSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        simtestDashBtnsLayout.setVerticalGroup(
            simtestDashBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestDashBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btncontainer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(simtestSubmit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(simtestMainBtn)
                .addContainerGap())
        );

        soundLimitMsg.setEditable(false);
        soundLimitMsg.setForeground(new java.awt.Color(204, 0, 0));
        soundLimitMsg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        soundLimitMsg.setBorder(null);

        javax.swing.GroupLayout simtestDashboardLayout = new javax.swing.GroupLayout(simtestDashboard);
        simtestDashboard.setLayout(simtestDashboardLayout);
        simtestDashboardLayout.setHorizontalGroup(
            simtestDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simtestDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(simtestDashBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simtestInstruct, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(simtestDashboardLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(soundLimitMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        simtestDashboardLayout.setVerticalGroup(
            simtestDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(simtestInstruct, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soundLimitMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simtestDashBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        simtestWorkspace.setBackground(new java.awt.Color(255, 255, 255));

        simtestWorkspace2.setBackground(new java.awt.Color(255, 255, 255));

        workAreaInstruct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        workAreaInstruct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workAreaInstruct.setText("Type what you hear and press Submit or Enter.");

        simtestResponse.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        simtestResponse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simtestResponseActionPerformed(evt);
            }
        });
        simtestResponse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                simtestResponseKeyPressed(evt);
            }
        });

        simtestClear.setBackground(new java.awt.Color(0, 102, 153));
        simtestClear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        simtestClear.setForeground(new java.awt.Color(255, 255, 255));
        simtestClear.setText("Clear");
        simtestClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simtestClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout simtestWorkspace2Layout = new javax.swing.GroupLayout(simtestWorkspace2);
        simtestWorkspace2.setLayout(simtestWorkspace2Layout);
        simtestWorkspace2Layout.setHorizontalGroup(
            simtestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestWorkspace2Layout.createSequentialGroup()
                .addGroup(simtestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simtestWorkspace2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(simtestResponse))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simtestWorkspace2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(simtestClear, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(workAreaInstruct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        simtestWorkspace2Layout.setVerticalGroup(
            simtestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simtestWorkspace2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(workAreaInstruct)
                .addGap(18, 18, 18)
                .addComponent(simtestResponse, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simtestClear)
                .addGap(107, 107, 107))
        );

        jScrollPane19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        simtestQuestArea.setEditable(false);
        simtestQuestArea.setColumns(20);
        simtestQuestArea.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        simtestQuestArea.setLineWrap(true);
        simtestQuestArea.setRows(5);
        simtestQuestArea.setWrapStyleWord(true);
        jScrollPane19.setViewportView(simtestQuestArea);

        testTrack.setEditable(false);
        testTrack.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        testTrack.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        testTrack.setBorder(null);

        secTrack.setEditable(false);
        secTrack.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        secTrack.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        secTrack.setBorder(null);

        javax.swing.GroupLayout simtestWorkspaceLayout = new javax.swing.GroupLayout(simtestWorkspace);
        simtestWorkspace.setLayout(simtestWorkspaceLayout);
        simtestWorkspaceLayout.setHorizontalGroup(
            simtestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestWorkspaceLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(simtestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(simtestWorkspace2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simtestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(secTrack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                        .addComponent(testTrack, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(simtestWorkspaceLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        simtestWorkspaceLayout.setVerticalGroup(
            simtestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestWorkspaceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testTrack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(secTrack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(simtestWorkspace2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout simtestContainerLayout = new javax.swing.GroupLayout(simtestContainer);
        simtestContainer.setLayout(simtestContainerLayout);
        simtestContainerLayout.setHorizontalGroup(
            simtestContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simtestContainerLayout.createSequentialGroup()
                .addComponent(simtestDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simtestWorkspace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        simtestContainerLayout.setVerticalGroup(
            simtestContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simtestDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(simtestWorkspace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout simtestLayout = new javax.swing.GroupLayout(simtest);
        simtest.setLayout(simtestLayout);
        simtestLayout.setHorizontalGroup(
            simtestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simtestContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        simtestLayout.setVerticalGroup(
            simtestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simtestContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainpanel.add(simtest, "simtest");

        guidedpractice.setBackground(new java.awt.Color(255, 255, 255));
        guidedpractice.setForeground(new java.awt.Color(0, 102, 153));
        guidedpractice.setPreferredSize(new java.awt.Dimension(900, 700));

        mainmenubtn1.setBackground(new java.awt.Color(0, 102, 153));
        mainmenubtn1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        mainmenubtn1.setForeground(new java.awt.Color(255, 255, 255));
        mainmenubtn1.setText("Main Menu");
        mainmenubtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainmenubtn1ActionPerformed(evt);
            }
        });

        guidedpracticehead.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        guidedpracticehead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        guidedpracticehead.setText("Guided Practice");
        guidedpracticehead.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tabbedpane.setBackground(new java.awt.Color(0, 102, 153));
        tabbedpane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        vocablist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        vocablist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "", "", "", "", "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        vocabpane.setViewportView(vocablist);

        tabbedpane.addTab("Vocabulary", vocabpane);

        questlist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        questlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "", "", "", "", "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        questpane.setViewportView(questlist);

        tabbedpane.addTab("Questions", questpane);

        answerlist.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        answerlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "", "", "", "", "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        answerpane.setViewportView(answerlist);

        tabbedpane.addTab("Answers", answerpane);

        txtcontainer1.setBackground(new java.awt.Color(255, 255, 255));

        txtinstruct1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtinstruct1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtinstruct1.setText("Type what you hear and press enter.");

        jScrollPane2.setBorder(null);

        selectedItem.setEditable(false);
        selectedItem.setColumns(20);
        selectedItem.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        selectedItem.setForeground(new java.awt.Color(0, 102, 153));
        selectedItem.setLineWrap(true);
        selectedItem.setRows(3);
        selectedItem.setWrapStyleWord(true);
        selectedItem.setMargin(new java.awt.Insets(2, 10, 2, 2));
        jScrollPane2.setViewportView(selectedItem);

        gp_usertext.setColumns(20);
        gp_usertext.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gp_usertext.setLineWrap(true);
        gp_usertext.setRows(5);
        gp_usertext.setWrapStyleWord(true);
        gp_textarea.setViewportView(gp_usertext);

        gp_clear.setBackground(new java.awt.Color(0, 102, 153));
        gp_clear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gp_clear.setForeground(new java.awt.Color(255, 255, 255));
        gp_clear.setText("Clear");
        gp_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gp_clearActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setFocusable(false);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("This text area is for practice only. \nTo check your work, refer to the \nselected element displayed above.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout txtcontainer1Layout = new javax.swing.GroupLayout(txtcontainer1);
        txtcontainer1.setLayout(txtcontainer1Layout);
        txtcontainer1Layout.setHorizontalGroup(
            txtcontainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtcontainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtcontainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtcontainer1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(txtcontainer1Layout.createSequentialGroup()
                        .addGroup(txtcontainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(txtcontainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(gp_clear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(gp_textarea, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(txtcontainer1Layout.createSequentialGroup()
                .addComponent(txtinstruct1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        txtcontainer1Layout.setVerticalGroup(
            txtcontainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtcontainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtinstruct1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gp_textarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gp_clear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtcontainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtcontainer1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap())
                    .addComponent(jScrollPane1)))
        );

        soundbtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dictationtutor/images/play2-sm.jpg"))); // NOI18N
        soundbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soundbtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout guidedpracticeLayout = new javax.swing.GroupLayout(guidedpractice);
        guidedpractice.setLayout(guidedpracticeLayout);
        guidedpracticeLayout.setHorizontalGroup(
            guidedpracticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guidedpracticeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(guidedpracticehead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(guidedpracticeLayout.createSequentialGroup()
                .addGroup(guidedpracticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(guidedpracticeLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(tabbedpane, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guidedpracticeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mainmenubtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)))
                .addGroup(guidedpracticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(guidedpracticeLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(soundbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(guidedpracticeLayout.createSequentialGroup()
                        .addComponent(txtcontainer1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        guidedpracticeLayout.setVerticalGroup(
            guidedpracticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guidedpracticeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(guidedpracticehead)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(guidedpracticeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(guidedpracticeLayout.createSequentialGroup()
                        .addComponent(soundbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtcontainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(guidedpracticeLayout.createSequentialGroup()
                        .addComponent(tabbedpane, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainmenubtn1)
                        .addContainerGap(76, Short.MAX_VALUE))))
        );

        mainpanel.add(guidedpractice, "guidedPractice");

        practestContainer.setBackground(new java.awt.Color(0, 102, 153));

        practestDashboard.setBackground(new java.awt.Color(255, 255, 255));

        practestInstruct.setBackground(new java.awt.Color(255, 255, 255));
        practestInstruct.setName("instructions"); // NOI18N
        practestInstruct.setPreferredSize(new java.awt.Dimension(300, 240));

        sectionTitle1.setEditable(false);
        sectionTitle1.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        sectionTitle1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sectionTitle1.setBorder(null);

        jScrollPane8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane8.setFocusable(false);

        sectionInstruct1.setEditable(false);
        sectionInstruct1.setColumns(20);
        sectionInstruct1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        sectionInstruct1.setForeground(new java.awt.Color(0, 51, 51));
        sectionInstruct1.setLineWrap(true);
        sectionInstruct1.setRows(5);
        sectionInstruct1.setWrapStyleWord(true);
        jScrollPane8.setViewportView(sectionInstruct1);

        javax.swing.GroupLayout practestInstructLayout = new javax.swing.GroupLayout(practestInstruct);
        practestInstruct.setLayout(practestInstructLayout);
        practestInstructLayout.setHorizontalGroup(
            practestInstructLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestInstructLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(practestInstructLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sectionTitle1)
                    .addComponent(jSeparator5)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap())
        );
        practestInstructLayout.setVerticalGroup(
            practestInstructLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestInstructLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(sectionTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        practestDashBtns.setBackground(new java.awt.Color(255, 255, 255));

        btncontainer3.setBackground(new java.awt.Color(255, 255, 255));

        practestMainBtn.setBackground(new java.awt.Color(0, 102, 153));
        practestMainBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        practestMainBtn.setForeground(new java.awt.Color(255, 255, 255));
        practestMainBtn.setText("Main Menu");
        practestMainBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practestMainBtnActionPerformed(evt);
            }
        });

        practestNext.setBackground(new java.awt.Color(0, 102, 153));
        practestNext.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        practestNext.setForeground(new java.awt.Color(255, 255, 255));
        practestNext.setText("Next");
        practestNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practestNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btncontainer3Layout = new javax.swing.GroupLayout(btncontainer3);
        btncontainer3.setLayout(btncontainer3Layout);
        btncontainer3Layout.setHorizontalGroup(
            btncontainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btncontainer3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btncontainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(practestMainBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(practestNext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        btncontainer3Layout.setVerticalGroup(
            btncontainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btncontainer3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(practestNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(practestMainBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout practestDashBtnsLayout = new javax.swing.GroupLayout(practestDashBtns);
        practestDashBtns.setLayout(practestDashBtnsLayout);
        practestDashBtnsLayout.setHorizontalGroup(
            practestDashBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btncontainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        practestDashBtnsLayout.setVerticalGroup(
            practestDashBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestDashBtnsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btncontainer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        practestSound1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dictationtutor/images/play2-sm.jpg"))); // NOI18N
        practestSound1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practestSound1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout practestDashboardLayout = new javax.swing.GroupLayout(practestDashboard);
        practestDashboard.setLayout(practestDashboardLayout);
        practestDashboardLayout.setHorizontalGroup(
            practestDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(practestDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(practestDashboardLayout.createSequentialGroup()
                        .addComponent(practestInstruct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(practestDashBtns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, practestDashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(practestSound1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        practestDashboardLayout.setVerticalGroup(
            practestDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(practestInstruct, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(practestSound1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(practestDashBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        practestWorkspace.setBackground(new java.awt.Color(255, 255, 255));

        practestWorkspace2.setBackground(new java.awt.Color(255, 255, 255));

        practestResponse.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        practestResponse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                practestResponseKeyPressed(evt);
            }
        });

        practestClear.setBackground(new java.awt.Color(0, 102, 153));
        practestClear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        practestClear.setForeground(new java.awt.Color(255, 255, 255));
        practestClear.setText("Clear");
        practestClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practestClearActionPerformed(evt);
            }
        });

        practestCheck.setBackground(new java.awt.Color(0, 102, 153));
        practestCheck.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        practestCheck.setForeground(new java.awt.Color(255, 255, 255));
        practestCheck.setText("Check");
        practestCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practestCheckActionPerformed(evt);
            }
        });

        practestShow.setBackground(new java.awt.Color(0, 102, 153));
        practestShow.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        practestShow.setForeground(new java.awt.Color(255, 255, 255));
        practestShow.setText("Show Correct Response");
        practestShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practestShowActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Press Check/Enter to check; Next to continue.");

        javax.swing.GroupLayout practestWorkspace2Layout = new javax.swing.GroupLayout(practestWorkspace2);
        practestWorkspace2.setLayout(practestWorkspace2Layout);
        practestWorkspace2Layout.setHorizontalGroup(
            practestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(practestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(practestResponse, javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, practestWorkspace2Layout.createSequentialGroup()
                    .addComponent(practestCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(practestShow, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(practestClear, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        practestWorkspace2Layout.setVerticalGroup(
            practestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestWorkspace2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(practestResponse, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(practestWorkspace2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(practestCheck)
                    .addComponent(practestShow)
                    .addComponent(practestClear))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jScrollPane20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        practestFeedback.setColumns(20);
        practestFeedback.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        practestFeedback.setLineWrap(true);
        practestFeedback.setRows(5);
        practestFeedback.setWrapStyleWord(true);
        jScrollPane20.setViewportView(practestFeedback);

        testTrack1.setEditable(false);
        testTrack1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        testTrack1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        testTrack1.setBorder(null);

        secTrack1.setEditable(false);
        secTrack1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        secTrack1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        secTrack1.setBorder(null);

        jScrollPane21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        practestQuestArea.setEditable(false);
        practestQuestArea.setColumns(20);
        practestQuestArea.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        practestQuestArea.setLineWrap(true);
        practestQuestArea.setRows(5);
        practestQuestArea.setWrapStyleWord(true);
        jScrollPane21.setViewportView(practestQuestArea);

        javax.swing.GroupLayout practestWorkspaceLayout = new javax.swing.GroupLayout(practestWorkspace);
        practestWorkspace.setLayout(practestWorkspaceLayout);
        practestWorkspaceLayout.setHorizontalGroup(
            practestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestWorkspaceLayout.createSequentialGroup()
                .addGroup(practestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, practestWorkspaceLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(practestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(testTrack1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                            .addComponent(secTrack1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, practestWorkspaceLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(practestWorkspaceLayout.createSequentialGroup()
                        .addGroup(practestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(practestWorkspaceLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(practestWorkspaceLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(practestWorkspace2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        practestWorkspaceLayout.setVerticalGroup(
            practestWorkspaceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestWorkspaceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testTrack1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(secTrack1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(practestWorkspace2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout practestContainerLayout = new javax.swing.GroupLayout(practestContainer);
        practestContainer.setLayout(practestContainerLayout);
        practestContainerLayout.setHorizontalGroup(
            practestContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(practestContainerLayout.createSequentialGroup()
                .addComponent(practestDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(practestWorkspace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        practestContainerLayout.setVerticalGroup(
            practestContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(practestDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(practestWorkspace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout practestLayout = new javax.swing.GroupLayout(practest);
        practest.setLayout(practestLayout);
        practestLayout.setHorizontalGroup(
            practestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(practestContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        practestLayout.setVerticalGroup(
            practestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(practestContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainpanel.add(practest, "practest");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void vocabularyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vocabularyActionPerformed
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"practest");
        sectionTitle1.setText(vocabTitle);
        sectionInstruct1.setText(vocabInstructions);
        
        //set focus to user response text area
        practestResponse.requestFocusInWindow();
        
        setCurrent(ourWordBank.getNewQuestionNoReplacement());
        setTestFlag(vocab);
    }//GEN-LAST:event_vocabularyActionPerformed

    private void guidedpracticebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guidedpracticebtnActionPerformed
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"guidedPractice");
        
        //onload the first index to be "selected" is vocabulary
        vocablist.setSelectedIndex(0);
        
        //get the value of the first item in the list and retrieve and set
        //current
        String selected = (String)vocablist.getSelectedValue();
        setCurrent(ourWordBank.getQuestionByQuestionText(selected));
        
        //write the selected item to the selectedItem box located 
        //under the sound button
        selectedItem.setText(selected);
                
        //put focus on the user response area
        gp_usertext.requestFocusInWindow();
    }//GEN-LAST:event_guidedpracticebtnActionPerformed

    private void exitbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtn1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitbtn1ActionPerformed

    private void simtestbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simtestbtnActionPerformed
        new Username(this).setVisible(true);
        simtestResponse.requestFocusInWindow();
    }//GEN-LAST:event_simtestbtnActionPerformed

    private void soundbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soundbtn1ActionPerformed
           
           //get the index of the selected tab
           int selectedIndex = tabbedpane.getSelectedIndex();
           //play appropriate item - question or answer - based on tab
           if(selectedIndex == 0) {
               //if vocabulary is selected, get question
               ourPlayer.playAnswer(current);
           } else if(selectedIndex == 1) {
               ourPlayer.playQuestion(current);
           } else {
               ourPlayer.playAnswer(current);
           }
           
           //set focus to user response text area
           gp_usertext.requestFocusInWindow();
              
    }//GEN-LAST:event_soundbtn1ActionPerformed

    private void mainmenubtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainmenubtn1ActionPerformed
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"mainMenu");
        //clear the text area
        gp_usertext.setText("");
    }//GEN-LAST:event_mainmenubtn1ActionPerformed

    private void sentencescrambleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sentencescrambleActionPerformed
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"practest");
        setCurrent(ourQABank.getNewQuestionNoReplacement());
        practestQuestArea.setText(current.getScrambledAnswerText()); 
        sectionTitle1.setText(scramTitle);
        sectionInstruct1.setText(scramInstructions);
        practestResponse.requestFocusInWindow();
        setTestFlag(scramble);
    }//GEN-LAST:event_sentencescrambleActionPerformed

    private void questionanswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_questionanswerActionPerformed
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"practest");
        setCurrent(ourQABank.getNewQuestionNoReplacement());
        practestQuestArea.setText(current.getQuestionText());
        sectionTitle1.setText(qaTitle);
        sectionInstruct1.setText(qaInstructions);
        practestResponse.requestFocusInWindow();
        setTestFlag(qa);
    }//GEN-LAST:event_questionanswerActionPerformed

    private void simtestMainBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simtestMainBtnActionPerformed
        new ExitPopup(this).setVisible(true);
    }//GEN-LAST:event_simtestMainBtnActionPerformed

    private void simtestSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simtestSoundActionPerformed
        if(getSoundTracker() < 2) //this only allows the play button to be activated twice
        {
        ourPlayer.playAnswer(current);
        int track = getSoundTracker() + 1;
        setSoundTracker(track);
        } else {
            soundLimitMsg.setText("You can only hear the selection twice.");
        }
        
        //set focus to user response text area
        simtestResponse.requestFocusInWindow();
        
    }//GEN-LAST:event_simtestSoundActionPerformed

    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsActionPerformed
        new TestSettings(this).setVisible(true);
    }//GEN-LAST:event_settingsActionPerformed

    private void simtestSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simtestSubmitActionPerformed
        
        submitResponse();
        
    }//GEN-LAST:event_simtestSubmitActionPerformed

    private void practestMainBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practestMainBtnActionPerformed
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"mainMenu");
        //clear the text area
        practestResponse.setText("");
        //clear the feedback area
        practestFeedback.setText("");
        //clear question display area
        practestQuestArea.setText("");
    }//GEN-LAST:event_practestMainBtnActionPerformed

    private void practestNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practestNextActionPerformed
        nextQuestion();
    }//GEN-LAST:event_practestNextActionPerformed

    private void practestSound1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practestSound1ActionPerformed
        ourPlayer.playAnswer(current);
        practestResponse.requestFocusInWindow();
    }//GEN-LAST:event_practestSound1ActionPerformed

    private void practestCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practestCheckActionPerformed
        checkSingle();
    }//GEN-LAST:event_practestCheckActionPerformed

    private void practestShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practestShowActionPerformed
        String receivedFromQuestion = current.getAnswerText();
        practestFeedback.setText(receivedFromQuestion);
    }//GEN-LAST:event_practestShowActionPerformed

    private void practestClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practestClearActionPerformed
        practestResponse.setText("");
        practestResponse.requestFocusInWindow();
    }//GEN-LAST:event_practestClearActionPerformed

    private void gp_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gp_clearActionPerformed
        gp_usertext.setText("");
        gp_usertext.requestFocusInWindow();
    }//GEN-LAST:event_gp_clearActionPerformed

    private void simtestClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simtestClearActionPerformed
        simtestResponse.setText("");
        simtestResponse.requestFocusInWindow();
    }//GEN-LAST:event_simtestClearActionPerformed

    private void simtestResponseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simtestResponseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simtestResponseActionPerformed

    private void simtestResponseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_simtestResponseKeyPressed
          
        if(evt.getKeyCode() == 10) {
          submitResponse();
        }
    }//GEN-LAST:event_simtestResponseKeyPressed

    private void practestResponseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_practestResponseKeyPressed
        if(evt.getKeyCode() == 10) {
          checkSingle();
        }
    }//GEN-LAST:event_practestResponseKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        MainJFrame junk = new MainJFrame();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        
        /* This is just to test to make sure the player is working */
        //ourPlayer.playQuestion(ourQABank.getNewQuestionNoReplacement());
        
        /* I DONT KNOW WHY THIS DOESNT WORK AND ITS REALLY PISSING ME OFF
        junk.vocablist.setListData(ourWordBank.getAllQuestions().toArray());
        */
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new MainJFrame().setVisible(true); 
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList answerlist;
    private javax.swing.JScrollPane answerpane;
    private javax.swing.JPanel btncontainer2;
    private javax.swing.JPanel btncontainer3;
    private javax.swing.JButton exitbtn1;
    private javax.swing.JButton gp_clear;
    private javax.swing.JScrollPane gp_textarea;
    private javax.swing.JTextArea gp_usertext;
    private javax.swing.JPanel guidedpractice;
    private javax.swing.JButton guidedpracticebtn;
    private javax.swing.JLabel guidedpracticehead;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel maincontainer;
    private javax.swing.JLabel mainhead;
    private javax.swing.JPanel mainmenu;
    private javax.swing.JButton mainmenubtn1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel menuarea1;
    private javax.swing.JPanel menuarea2;
    private javax.swing.JPanel menuarea3;
    private javax.swing.JPanel menuarea5;
    private javax.swing.JPanel practest;
    private javax.swing.JButton practestCheck;
    private javax.swing.JButton practestClear;
    private javax.swing.JPanel practestContainer;
    private javax.swing.JPanel practestDashBtns;
    private javax.swing.JPanel practestDashboard;
    private javax.swing.JTextArea practestFeedback;
    private javax.swing.JPanel practestInstruct;
    private javax.swing.JButton practestMainBtn;
    private javax.swing.JButton practestNext;
    private javax.swing.JTextArea practestQuestArea;
    private javax.swing.JTextField practestResponse;
    private javax.swing.JButton practestShow;
    private javax.swing.JButton practestSound1;
    private javax.swing.JPanel practestWorkspace;
    private javax.swing.JPanel practestWorkspace2;
    private javax.swing.JButton questionanswer;
    private javax.swing.JList questlist;
    private javax.swing.JScrollPane questpane;
    private javax.swing.JTextField secTrack;
    private javax.swing.JTextField secTrack1;
    private javax.swing.JTextArea sectionInstruct;
    private javax.swing.JTextArea sectionInstruct1;
    private javax.swing.JTextField sectionTitle;
    private javax.swing.JTextField sectionTitle1;
    private javax.swing.JTextArea selectedItem;
    private javax.swing.JButton sentencescramble;
    private javax.swing.JButton settings;
    private javax.swing.JPanel simtest;
    private javax.swing.JButton simtestClear;
    private javax.swing.JPanel simtestContainer;
    private javax.swing.JPanel simtestDashBtns;
    private javax.swing.JPanel simtestDashboard;
    private javax.swing.JPanel simtestInstruct;
    private javax.swing.JButton simtestMainBtn;
    private javax.swing.JTextArea simtestQuestArea;
    private javax.swing.JTextField simtestResponse;
    private javax.swing.JButton simtestSound;
    private javax.swing.JButton simtestSubmit;
    private javax.swing.JPanel simtestWorkspace;
    private javax.swing.JPanel simtestWorkspace2;
    private javax.swing.JButton simtestbtn;
    private javax.swing.JTextField soundLimitMsg;
    private javax.swing.JButton soundbtn1;
    private javax.swing.JTabbedPane tabbedpane;
    private javax.swing.JTextField testTrack;
    private javax.swing.JTextField testTrack1;
    private javax.swing.JPanel txtcontainer1;
    private javax.swing.JLabel txtinstruct1;
    private javax.swing.JList vocablist;
    private javax.swing.JScrollPane vocabpane;
    private javax.swing.JButton vocabulary;
    private javax.swing.JLabel workAreaInstruct;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * On list change, the first item in the list will be selected and the 
     * Question for that will be retrieved and loaded into current.
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane pane = (JTabbedPane) e.getSource();
           int selectedIndex = pane.getSelectedIndex();
           String selected;
           
           if(selectedIndex == vocab) {
               vocablist.setSelectedIndex(0);
               selected = (String)vocablist.getSelectedValue();
               setCurrent(ourWordBank.getQuestionByQuestionText(selected));
           } else if(selectedIndex == question) {
               questlist.setSelectedIndex(0);
               selected = (String)questlist.getSelectedValue();
               setCurrent(ourQABank.getQuestionByQuestionText(selected));
           } else {
               answerlist.setSelectedIndex(0);
               selected = (String)answerlist.getSelectedValue();
               setCurrent(ourQABank.getQuestionByAnswerText(selected));
           }
           
           //write the selected item to the selectedItem box located 
           //under the sound button
           selectedItem.setText(selected);
    }
    
    private void checkSingle() {
        //get the user's response and if null ask them to enter some text
        //otherwise send to checker and report feedback
        String sendToChecker = practestResponse.getText();
        if(sendToChecker.equals("")) {
            practestFeedback.setText("The text area is empty. \nPlease enter some text and select Check again.");
        }
        else {
            String receivedFromChecker = Checker.evaluate(current,sendToChecker);
            practestFeedback.setText("Your response: \"" + sendToChecker + "\" " + receivedFromChecker);
        }
        
        //set focus to user response text area
        practestResponse.requestFocusInWindow();
    }
    
    private void recordResponse(int index, int code) {
        if(code == vocab) {
            addVocabResponse(index, simtestResponse.getText());
            addVocabCorrect(index, current.getAnswerText());
            addVocabQuestion(index, current.getQuestionText());
        }
        if(code == scramble) {
            addScramResponse(index, simtestResponse.getText());
            addScramCorrect(index, current.getAnswerText());
            addScramQuestion(index, current.getQuestionText());
        }
        if(code == qa) {
            addQaResponse(index, simtestResponse.getText());
            addQaCorrect(index, current.getAnswerText());
            addQaQuestion(index, current.getQuestionText());
        }
        
    }
    
    private void setUpTestArea(int code) {
        if(code == vocab) {
            sectionTitle.setText(vocabTitle);
            sectionInstruct.setText(vocabInstructions);
            setCurrent(ourWordBank.getNewQuestionNoReplacement());
            //display the number of questions done with respect to vocab section and whole test
            secTrack.setText("Vocabulary " + this.vocabCount + " of " + this.vocabSetting);
            testTrack.setText("Total " + this.getTestCount() + " of " + this.testTotal);
        } else if(code == scramble) {
            sectionTitle.setText(scramTitle);
            sectionInstruct.setText(scramInstructions);
            setCurrent(ourQABank.getNewQuestionNoReplacement());
            simtestQuestArea.setText(current.getScrambledAnswerText());
            //display the number of questions done with respect to vocab section and whole test
            secTrack.setText("Sentence Scramble " + this.scramCount + " of " + this.scramSetting);
            testTrack.setText("Total " + this.getTestCount() + " of " + this.testTotal);
        } else if(code == qa) {
            sectionTitle.setText(qaTitle);
            sectionInstruct.setText(qaInstructions);
            setCurrent(ourQABank.getNewQuestionNoReplacement());
            simtestQuestArea.setText(current.getQuestionText());
            //display the number of questions done with respect to vocab section and whole test
            secTrack.setText("Question & Answer " + this.qaCount + " of " + this.qaSetting);
            testTrack.setText("Total " + this.getTestCount() + " of " + this.testTotal);
        }
    }
    
    public void nextQuestion() {
        
        //check to see which section the student is in and get a word/question
       //and display accordingly
        if(getTestFlag() == vocab){
            setCurrent(ourWordBank.getNewQuestionNoReplacement());
        }
        else if(getTestFlag() == scramble) {
            setCurrent(ourQABank.getNewQuestionNoReplacement());
            practestQuestArea.setText(current.getScrambledAnswerText());
        } 
        else if(getTestFlag() == qa) {
            setCurrent(ourQABank.getNewQuestionNoReplacement());
            practestQuestArea.setText(current.getQuestionText());
        }
        
        //clear the feedback text area
        practestFeedback.setText("");
        
        //clear the user response text area in preparation for typing in next response
        practestResponse.setText("");
        practestResponse.requestFocusInWindow();
        
    }
    
    public void submitResponse() {
        
        //reset sound tracker to zero
        setSoundTracker(0);
        
        //increment the test counter
        int counter;
        counter = this.getTestCount();
        counter++;
        this.setTestCount(counter);
        
        //if the current section is vocab and student is on the last question
        //for the section, determine which test to set up next
        if(getTestFlag() == vocab && this.vocabCount == this.vocabSetting) {
            //record last response
            recordResponse((this.vocabSetting-1), vocab);
            //if scramble settings are not zero, set the flag to scramble
            //set up the scramble test area for first scramble question
            if(this.scramSetting != 0) {
                setTestFlag(scramble);
                setScramCount(1);
                setUpTestArea(scramble);
            //otherwise set the flag to qa and set up the qa test area for first qa
            } else  if(this.qaSetting != 0) {
                setTestFlag(qa);
                setQaCount(1);
                setUpTestArea(qa);
            } else {
                callFeedback();
            }
        //else if the current section is scramble and student is on the last question
        //for the section, determine if can move on to the last section
        } else if(getTestFlag() == scramble && this.scramCount == this.scramSetting) {
            //record last response
            recordResponse((this.scramSetting-1), scramble);
              //if qa settings are not zero, set the flag to qa 
              //and set up the qa test area for first qa
              if(this.qaSetting != 0) {
                  setTestFlag(qa);
                  setQaCount(1);
                  setUpTestArea(qa);
              //otherwise call feedback form
              } else {
                callFeedback();
              }
        //if the current section is qa and student is on the last question
        //for the section, call feedback form
        } else if(getTestFlag() == qa && this.qaCount == this.qaSetting) {
            //record last response
            recordResponse((this.qaSetting-1), qa);
            //process results
            callFeedback();
            
        } else if(getTestFlag() == vocab) {
            //record response
            recordResponse((this.vocabCount-1), vocab);
            
            //keep track of what question student is on for display - increment counter
            //the value of this counter is used for the section number display
            counter = this.vocabCount;
            counter++;
            this.setVocabCount(counter);
            
            //set up the vocabulary test area for next question
            setUpTestArea(vocab);
 
        } else if(getTestFlag() == scramble) {
            //record response
            recordResponse((this.scramCount-1), scramble);
            
            //keep track of what question student is on for display - increment counter
            //the value of this counter is used for the section number display
            counter = this.scramCount;
            counter++;
            this.setScramCount(counter);
            
            //set up the scramble test area for next question
            setUpTestArea(scramble);
 
        } else if(getTestFlag() == qa) {
            //record response
            recordResponse((this.qaCount-1), qa);
            
            //keep track of what question student is on for display - increment counter
            //the value of this counter is used for the section number display
            counter = this.qaCount;
            counter++;
            this.setQaCount(counter);
            
            //set up the qa test area for next question
            setUpTestArea(qa);
        }
        
        //reset soundLimitMsg area - remove sound limit message
        soundLimitMsg.setText("");
        
        //clear test area in preparation of next entry 
        simtestResponse.setText("");
        simtestResponse.requestFocusInWindow();
        
    }
    
    public void callFeedback() {        
        Checker.evaluate(this.vocabCorrect, this.vocabResponse, this.vocab, this);
        Checker.evaluate(this.scramCorrect, this.scramResponse, this.scramble, this);
        Checker.evaluate(this.qaCorrect, this.qaResponse, this.qa, this);
            
        new FeedbackForm(this).setVisible(true);
            
        //display the main menu for when the user is finished reviewing the scores
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"mainMenu");
    }
    
    public void goToMain() {
        
        CardLayout cl = (CardLayout)(mainpanel.getLayout());
        cl.show(mainpanel, (String)"mainMenu");
        //clear the text area
        simtestResponse.setText("");
        //clear question display area
        simtestQuestArea.setText("");
        
    }

}
