/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictationtutor;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.text.*;
import java.util.*;

/**
 *
 * @author Anthony
 */
public class CSVSaver {

    public static File chooseFile() {
        File currentDirectory = null;
        int result;
        result = 0;

        //get file from user
        JFileChooser fileChooser = new JFileChooser(currentDirectory);

        result = fileChooser.showSaveDialog(fileChooser);

        if (result == JFileChooser.CANCEL_OPTION) {
            fileChooser.setVisible(false);
        }
        //If user selected a file, return the
        //directory location for future reference
        return fileChooser.getSelectedFile();
    }

    /**
     * This writeFile method receives the file path and file contents as
     * parameters. It tries to open the file and writes the contents to the file
     * if successful. The method catches a FileNotFoundException and exits the
     * program if it cannot write to the file.
     *
     * @param filePath (File)
     * @param fileContents (String)
     */
    public static void writeFile(File filePath, String fileContents) {

        PrintWriter outputStream;
        outputStream = null;

        try {
            outputStream = new PrintWriter(new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("The program is ending.");
            System.exit(0);
        }

        outputStream.println(fileContents);
        outputStream.close();
    }

    /**
     * This writeFile method receives the file path and file contents as
     * parameters. It tries to open the file and appends the contents to the
     * file if successful. The method catches a FileNotFoundException and exits
     * the program if it cannot write to the file.
     *
     * @param filePath (File)
     * @param fileContents (String)
     */
    public static void appendFile(File filePath, String fileContents) {

        PrintWriter outputStream;
        outputStream = null;

        try {
            outputStream = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            outputStream.println(fileContents);
            outputStream.close();
        } catch (IOException e) {
            System.out.println("The program is ending.");
            System.exit(0);
        }

        outputStream.println(fileContents);
        outputStream.close();
    }

    public static int writeCSV(File filePath, MainJFrame ourJFrame) {
        
        boolean file_is_not_empty = false;
        boolean file_has_right_first_line = false;
// Construct BufferedReader from FileReader
        
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line = null;
                line = br.readLine();
                if(line != null) {
                    file_is_not_empty = true;
                    
                    if (line.equals("Name, Date, Section, Correct, Almost Correct, Incorrect, Student Response, Correct Response"))
                        file_has_right_first_line = true;
                }
            }
        
        catch(Exception e){

        }
        
        if (file_is_not_empty && !file_has_right_first_line) {
            JOptionPane.showMessageDialog(null, "The file you chose does not have the right format. Choose another file or type in a new name to create a new file.");
            return 1;
        }
        
        
        /* Get current date and time */
        String timeStamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(new Date());
        int correct, almost_Correct, incorrect;
        
        /* Put column names in file if its empty*/
        if (!file_is_not_empty) 
            appendFile(filePath, "Name, Date, Section, Correct, Almost Correct, Incorrect, Student Response, Correct Response");

        for (int i = 0; i < ourJFrame.vocabResponse.length; i++) {

            /* Set flags to 0 or 1 for correct / incorrect / almost correct */
            correct = 0;
            if (ourJFrame.vocabFeedback[i].equals("Correct!")) {
                correct = 1;
            }

            almost_Correct = 0;
            if (ourJFrame.vocabFeedback[i].equals("Almost correct. Try again.")) {
                almost_Correct = 1;
            }

            incorrect = 0;
            if (ourJFrame.vocabFeedback[i].equals("Incorrect, please try again.")) {
                incorrect = 1;
            }

            /* write record to CSV */
            appendFile(filePath, "\"" + ourJFrame.getUsername() + "\","
                    + "\"" + timeStamp + "\","
                    + "\"" + "Vocabulary" + "\","
                    + "\"" + correct + "\","
                    + "\"" + almost_Correct + "\","
                    + "\"" + incorrect + "\","
                    + "\"" + ourJFrame.vocabResponse[i] + "\","
                    + "\"" + ourJFrame.vocabCorrect[i] + "\""
            );
        }

        for (int i = 0; i < ourJFrame.scramResponse.length; i++) {

            /* Set flags to 0 or 1 for correct / incorrect / almost correct */
            correct = 0;
            if (ourJFrame.scramFeedback[i].equals("Correct!")) {
                correct = 1;
            }

            almost_Correct = 0;
            if (ourJFrame.scramFeedback[i].equals("Almost correct. Try again.")) {
                almost_Correct = 1;
            }

            incorrect = 0;
            if (ourJFrame.scramFeedback[i].equals("Incorrect, please try again.")) {
                incorrect = 1;
            }

            /* write record to CSV */
            appendFile(filePath, "\"" + ourJFrame.getUsername() + "\","
                    + "\"" + timeStamp + "\","
                    + "\"" + "Sentence Scramble" + "\","
                    + "\"" + correct + "\","
                    + "\"" + almost_Correct + "\","
                    + "\"" + incorrect + "\","
                    + "\"" + ourJFrame.scramResponse[i] + "\","
                    + "\"" + ourJFrame.scramCorrect[i] + "\""
            );
        }

        for (int i = 0; i < ourJFrame.qaResponse.length; i++) {

            /* Set flags to 0 or 1 for correct / incorrect / almost correct */
            correct = 0;
            if (ourJFrame.qaFeedback[i].equals("Correct!")) {
                correct = 1;
            }

            almost_Correct = 0;
            if (ourJFrame.qaFeedback[i].equals("Almost correct. Try again.")) {
                almost_Correct = 1;
            }

            incorrect = 0;
            if (ourJFrame.qaFeedback[i].equals("Incorrect, please try again.")) {
                incorrect = 1;
            }

            /* write record to CSV */
            appendFile(filePath, "\"" + ourJFrame.getUsername() + "\","
                    + "\"" + timeStamp + "\","
                    + "\"" + "Question and Answer" + "\","
                    + "\"" + correct + "\","
                    + "\"" + almost_Correct + "\","
                    + "\"" + incorrect + "\","
                    + "\"" + ourJFrame.qaResponse[i] + "\","
                    + "\"" + ourJFrame.qaCorrect[i] + "\""
            );
        }

        return 0;
    }
}
