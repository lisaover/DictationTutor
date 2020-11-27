package dictationtutor;

import java.util.*;
class Question {
  private String questionText;
  private String answerText;
  private String questionSoundToken;
  private String answerSoundToken;
  
  public Question(String qt, String at, String qst, String ast){
    questionText = qt;
    answerText = at;
    questionSoundToken = qst;
    answerSoundToken = ast;  
  }
  
  public String getQuestionText(){
    return questionText;
  }
  
  public String getAnswerText(){
    return answerText;
  }
  
  public String getQuestionSoundToken(){
    return questionSoundToken;
  }
  
  public String getAnswerSoundToken(){
    return answerSoundToken;
  }
  
  public String getScrambledAnswerText(){
    String toReturn = "";
    List<String> items = Arrays.asList(answerText.split("\\s+"));
    
    Collections.shuffle(items);
    for (String s : items)
      toReturn = toReturn  + s + " ";
    return toReturn.substring(0, toReturn.length() - 1);
  }
  
}