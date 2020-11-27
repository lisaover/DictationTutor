//Computing Checker's distance
// By Sam Kelly
package dictationtutor;

import java.util.*;
public class Checker
{
  public static int calcEditDistance(String correctAnswer, String response)//accepts question and string
                {
    String stra=correctAnswer.toUpperCase(); //changes string to all caps
    String strb=response.toUpperCase();//changes string to all caps
    int[][] distance = new int[stra.length() + 1][strb.length() + 1];
    for (int i = 0; i <= stra.length(); i++)
      distance[i][0] = i;
    for (int j = 1; j <= strb.length(); j++)
      distance[0][j] = j;
    for (int i = 1; i <= stra.length(); i++)
      for (int j = 1; j <= strb.length(); j++)
      distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, 
                                         distance[i][j - 1] + 1),
                                distance[i - 1][j - 1] + ((stra.charAt(i - 1) == strb.charAt(j - 1)) ? 0 : 1));
    int lsd = distance[stra.length()][strb.length()];
    
    return lsd;
  }
  
  public static String evaluate(Question question, String response)//accepts question and string
        {    
    
    int lsd = calcEditDistance(question.getAnswerText(), response);

    double rat = ((float)lsd/(float)question.getAnswerText().length());
    
    String toReturn;
    if ( rat == 0.0)
      toReturn = "is correct!";
    else if (rat < .15) //85% warrants some praise
      toReturn = "is almost correct. Try again."; 
    else
      toReturn = "is incorrect, please try again.";
    
    return toReturn;
  }
  
  public static void evaluate(String[] correctAnswers, String[] responses, int code, MainJFrame main)//accepts string arrays, code, and main
                {  
      
    int correct = 0;
    int almost = 0;
    for(int i=0; i<responses.length; i++) {
        int lsd = calcEditDistance(correctAnswers[i], responses[i]);
        double rat = ((float)lsd/(float)correctAnswers[i].length());
        
        //add feedback to main instance array based on code
        if (code == main.vocab) {
            if ( rat == 0.0) {
                main.addVocabFeedback(i, "Correct!");
                correct++;
            }
            else if (rat < .15) { //85% warrants some praise
                main.addVocabFeedback(i, "Almost correct. Try again.");
                almost++;
            }
            else
                main.addVocabFeedback(i, "Incorrect, please try again.");
        }
        if (code == main.scramble) {
            if ( rat == 0.0) {
                main.addScramFeedback(i, "Correct!");
                correct++;
            }
            else if (rat < .15) { //85% warrants some praise
                main.addScramFeedback(i, "Almost correct. Try again.");
                almost++;
            }
            else
                main.addScramFeedback(i, "Incorrect, please try again.");
        }
        if (code == main.qa) {
            if ( rat == 0.0) {
                main.addQaFeedback(i, "Correct!");
                correct++;
            }
            else if (rat < .15) { //85% warrants some praise
                main.addQaFeedback(i, "Almost correct. Try again.");
                almost++;
            }
            else
                main.addQaFeedback(i, "Incorrect, please try again.");
        }
    }
    
    if (code == main.vocab) {
       main.setNumVocabCorrect(correct);
       main.setNumVocabAlmost(almost);
    } else if (code == main.scramble) {
       main.setNumScramCorrect(correct);
       main.setNumScramAlmost(almost);
    } else if (code == main.qa) {
       main.setNumQaCorrect(correct);
       main.setNumQaAlmost(almost);     
    }
            
  }
  
}