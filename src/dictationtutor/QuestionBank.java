
package dictationtutor;

import java.util.*;
import java.io.*;

public class QuestionBank {
  
  private ArrayList<QuestionUsageWrapper> ourQuestions;
  
  public QuestionBank(char qw){
    
    ourQuestions = new ArrayList<QuestionUsageWrapper>();
    if(qw == 'q'){
      ourQuestions.add(new QuestionUsageWrapper( new Question("What country is north of the United States?","Canada is north of the United States","Q1.MP3","A1.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is the capital of the United States?","The capital of the United States is Washington D.C.","Q2.MP3","A2.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What country is south of the United States?","Mexico is south of the United States","Q3.MP3","A3.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is the largest state in the United States?","Alaska is the largest state","Q4.MP3","A4.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What city in the United States has the most people?","New York city has the most people","Q5.MP3","A5.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What are the colors of the American flag?","The flag is red, white, and blue","Q6.MP3","A6.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("How many states does the United States have?","The United States has fifty states","Q7.MP3","A7.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What state has the most people in the United States?","The state of California has the most people","Q8.MP3","A8.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is Washington D.C.?","Washington D.C. is the capital of the United States","Q9.MP3","A9.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is the largest state in the country?","Alaska is the largest state","Q10.MP3","A10.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Where does the Congress of the United States meet?","The Congress meets in Washington D.C.","Q11.MP3","A11.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who lives in the White House?","The president of the United States lives in the White House","Q12.MP3","A12.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Where is the White House?","The White House is in Washington D.C.","Q13.MP3","A13.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Where do the United States senators meet?","United states senators meet in Washington D.C.","Q14.MP3","A14.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Where does the president live?","The president lives in the White House in Washington D.C.","Q15.MP3","A15.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who can vote for president of the United States?","Citizens of the United States can vote for the president","Q16.MP3","A16.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When do people in the United States vote for president?","People vote for the president in November","Q17.MP3","A17.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("How many United States senators does Congress have?","The Congress of the United States has one hundred senators","Q18.MP3","A18.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who elects the United States senators?","Citizens in the fifty states elect the senators","Q19.MP3","A19.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When do citizens vote for the government of the United States?","Citizens of the United States vote in November","Q20.MP3","A20.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who elects the president of the United States?","The people of the United States elect the president","Q21.MP3","A21.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who votes for Congress of the United States?","Citizens of the United States vote for Congress","Q22.MP3","A22.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When do the people of the United States vote for the Congress?","We vote for Congress in November","Q23.MP3","A23.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is one right in the bill of rights?","People in the United States have freedom of speech","Q24.MP3","A24.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Where does the president of the United States live?","The president lives in the White House","Q25.MP3","A25.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Independence Day?","Independence day is in July","Q26.MP3","A26.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who was the first president of the United States?","The first president of the United States was Washington","Q27.MP3","A27.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is the capital of the United States?","Washington D.C. is the capital of the United States","Q28.MP3","A28.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who was George Washington?","Washington was the first president of the United States","Q29.MP3","A29.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What right do people in the United States have?","People have the right to be free","Q30.MP3","A30.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who was George Washington?","Washington was the father of our country","Q31.MP3","A31.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who is on the dollar bill?","President Washington is on the dollar bill","Q32.MP3","A32.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Why is George Washington the father of our country?","Washington was the first president of the United States","Q33.MP3","A33.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What was the first state in the United States?","Delaware was the first state","Q34.MP3","A34.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What city was the first capital of the United States?","New York City was the first capital of the United States","Q35.MP3","A35.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Presidents' Day?","Presidents' Day is in February","Q36.MP3","A36.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When was Abraham Lincoln the president?","Abraham Lincoln was the president during the civil war","Q37.MP3","A37.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is one right in the bill of rights?","One right people have is freedom of speech","Q38.MP3","A38.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who can vote for the president?","Citizens of the United States can vote for the president","Q39.MP3","A39.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is the largest state in the United States?","Alaska is the largest state in the United States","Q40.MP3","A40.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Labor Day?","Labor Day is in September","Q41.MP3","A41.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Why do people come to the United States?","People come to the United States to be free","Q42.MP3","A42.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is one right people in the United States have?","People in the United States have freedom of speech","Q43.MP3","A43.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What can citizens of the United States do?","Citizens can vote for the president of the United States","Q44.MP3","A44.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who can citizens of the United States vote for?","Citizens can vote for senators and the president","Q45.MP3","A45.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Memorial Day?","Memorial Day is in may","Q46.MP3","A46.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Independence Day?","Independence Day is in July","Q47.MP3","A47.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Thanksgiving?","Thanksgiving is in November","Q48.MP3","A48.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Columbus Day?","Columbus Day is in October","Q49.MP3","A49.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("When is Flag Day?","Flag Day is in June","Q50.MP3","A50.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What do people in the United States pay to the government?","People in the United States pay taxes","Q51.MP3","A51.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What is one right citizens of the United States have?","Citizens have the right to vote","Q52.MP3","A52.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who can citizens vote for?","Citizens can vote for the president and Congress","Q53.MP3","A53.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Who elects the president?","Citizens of the United States elect the president","Q54.MP3","A54.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("What do people in the United States have to pay?","People in the United States have to pay taxes","Q55.MP3","A55.MP3")));
      
    }
    
    if(qw == 'w'){
      ourQuestions.add(new QuestionUsageWrapper( new Question("a","a","a.MP3","a.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Abraham Lincoln","Abraham Lincoln","Abraham Lincoln.MP3","Abraham Lincoln.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Adams","Adams","Adams.MP3","Adams.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Alaska","Alaska","Alaska.MP3","Alaska.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("America","America","America.MP3","America.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("American flag","American flag","American flag.MP3","American flag.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("American Indians","American Indians","American Indians.MP3","American Indians.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("and","and","and.MP3","and.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Bill of Rights","Bill of Rights","Bill of Rights.MP3","Bill of Rights.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("blue","blue","blue.MP3","blue.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("California","California","California.MP3","California.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("can","can","can.MP3","can.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Canada","Canada","Canada.MP3","Canada.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("capital","capital","capital.MP3","capital.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("citizen","citizen","citizen.MP3","citizen.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("citizens","citizens","citizens.MP3","citizens.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("city","city","city.MP3","city.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Civil War","Civil War","Civil War.MP3","Civil War.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("colors","colors","colors.MP3","colors.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Columbus Day","Columbus Day","Columbus Day.MP3","Columbus Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("come","come","come.MP3","come.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Congress","Congress","Congress.MP3","Congress.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("country","country","country.MP3","country.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Delaware","Delaware","Delaware.MP3","Delaware.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("do","do","do.MP3","do.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("does","does","does.MP3","does.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("dollar bill","dollar bill","dollar bill.MP3","dollar bill.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("during","during","during.MP3","during.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("elect","elect","elect.MP3","elect.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("elects","elects","elects.MP3","elects.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("father of our country","father of our country","father of our country.MP3","father of our country.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("February","February","February.MP3","February.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("fifty","fifty","fifty.MP3","fifty.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("first","first","first.MP3","first.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("flag","flag","flag.MP3","flag.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Flag Day","Flag Day","Flag Day.MP3","Flag Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("for","for","for.MP3","for.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("free","free","free.MP3","free.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("freedom of speech","freedom of speech","freedom of speech.MP3","freedom of speech.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("George Washington","George Washington","George Washington.MP3","George Washington.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("government","government","government.MP3","government.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("have","have","have.MP3","have.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("has","has","has.MP3","has.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("here","here","here.MP3","here.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("how","how","how.MP3","how.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("in","in","in.MP3","in.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Independence Day","Independence Day","Independence Day.MP3","Independence Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("is","is","is.MP3","is.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("are","are","are.MP3","are.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("was","was","was.MP3","was.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("be","be","be.MP3","be.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("July","July","July.MP3","July.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("June","June","June.MP3","June.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Labor Day","Labor Day","Labor Day.MP3","Labor Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("largest","largest","largest.MP3","largest.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Lincoln","Lincoln","Lincoln.MP3","Lincoln.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("lives","lives","lives.MP3","lives.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("lived","lived","lived.MP3","lived.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("many","many","many.MP3","many.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("May","May","May.MP3","May.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("meet","meet","meet.MP3","meet.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("meets","meets","meets.MP3","meets.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Memorial Day","Memorial Day","Memorial Day.MP3","Memorial Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Mexico","Mexico","Mexico.MP3","Mexico.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("most","most","most.MP3","most.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("name","name","name.MP3","name.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("New York City","New York City","New York City.MP3","New York City.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("north","north","north.MP3","north.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("November","November","November.MP3","November.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("October","October","October.MP3","October.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("of","of","of.MP3","of.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("on","on","on.MP3","on.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("one","one","one.MP3","one.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("one hundred","one hundred","one hundred.MP3","one hundred.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("pay","pay","pay.MP3","pay.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("people","people","people.MP3","people.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("President","President","President.MP3","President.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Presidents' Day","Presidents' Day","Presidents Day.MP3","Presidents Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("red","red","red.MP3","red.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("right","right","right.MP3","right.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("second","second","second.MP3","second.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("senators","senators","senators.MP3","senators.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("September","September","September.MP3","September.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("south","south","south.MP3","south.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("state","state","state.MP3","state.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("states","states","states.MP3","states.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("taxes","taxes","taxes.MP3","taxes.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Thanksgiving","Thanksgiving","Thanksgiving.MP3","Thanksgiving.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("the","the","the.MP3","the.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("to","to","to.MP3","to.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("U.S. Flag Day","U.S. Flag Day","US Flag Day.MP3","US Flag Day.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("United States","United States","United States.MP3","United States.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("vote","vote","vote.MP3","vote.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("want","want","want.MP3","want.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Washington","Washington","Washington.MP3","Washington.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("Washington D.C.","Washington D.C.","Washington DC.MP3","Washington DC.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("we","we","we.MP3","we.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("what","what","what.MP3","what.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("when","when","when.MP3","when.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("where","where","where.MP3","where.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("white","white","white.MP3","white.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("White House","White House","White House.MP3","White House.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("who","who","who.MP3","who.MP3")));
      ourQuestions.add(new QuestionUsageWrapper( new Question("why","why","why.MP3","why.MP3")));
      
      
    }
  }
  
  public Question getQuestionByQuestionText(String qtext){
    for (QuestionUsageWrapper s : ourQuestions)
      if (s.getQuestion().getQuestionText().equals(qtext))
      return s.getQuestion(); 
    
    return null;
  }
  
  
  public Question getQuestionByAnswerText(String atext){
    for (QuestionUsageWrapper s : ourQuestions)
      if (s.getQuestion().getAnswerText().equals(atext))
      return s.getQuestion();  
    
    return null;
  }
  
  public Question getNewQuestionNoReplacement(){
    
    
    ArrayList<Integer> randomArray = new ArrayList<Integer>();
    for (int i = 0; i < ourQuestions.size(); i++) {
      randomArray.add(i);
    }
    Collections.shuffle(randomArray);
    
    for (Integer j : randomArray)
      if (ourQuestions.get(j).getUsed() == false){
      ourQuestions.get(j).setUsed();
      return ourQuestions.get(j).getQuestion();
    }
    
    /* If it gets to this part of the code that means all the elements have been used
     * and therefore we need to reset all the usage flags and start over
     */
    
    resetReplacement();
    
    return getNewQuestionNoReplacement();
  }
  
  public void resetReplacement(){
    for (QuestionUsageWrapper s : ourQuestions)
      s.resetUsed();        
  }
  /* 
   public ArrayList<String> getAllQuestions(){
   ArrayList<String> toReturn = new ArrayList<String>();
   
   for (QuestionUsageWrapper s : ourQuestions)
   toReturn.add(s.getQuestion().getQuestionText());
   
   Collections.sort(toReturn);
   return toReturn;
   
   }
   */
  public String[] getAllAnswers(){
    ArrayList<String> toReturn = new ArrayList<String>();
    for (QuestionUsageWrapper s : ourQuestions)
      toReturn.add(s.getQuestion().getAnswerText());
    String[] a = new String[toReturn.size()];
    
    Collections.sort(toReturn);
    toReturn.toArray(a);
    
    return a;
  } 
  public String[] getAllQuestions(){
    ArrayList<String> toReturn = new ArrayList<String>();
    
    for (QuestionUsageWrapper s : ourQuestions)
      toReturn.add(s.getQuestion().getQuestionText());
    String[] a = new String[toReturn.size()];
    Collections.sort(toReturn);
    toReturn.toArray(a);
    
    return a;
  }  
}
