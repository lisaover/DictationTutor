package dictationtutor;

import java.util.*;
class QuestionUsageWrapper{
  private Question aQuestion;
  private boolean used;
  
  public QuestionUsageWrapper(Question q){
    aQuestion = q;
    used = false;
  }
  
  public Question getQuestion(){
    return aQuestion;
  }
  public boolean getUsed(){
    return used;
  } 
  public void setUsed(){
    used = true;
  }   
  public void resetUsed(){
    used = false;
  }    
  
}
