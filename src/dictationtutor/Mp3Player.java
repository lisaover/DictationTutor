package dictationtutor;

import dictationtutor.Question;
import javazoom.jl.player.Player;
import java.io.InputStream;
import java.io.File;



public class Mp3Player
{
    String path = "/dictationtutor/audio/";
   
        public void playQuestion(Question myQuestion){
        try
        {
            System.out.println("i got here and am trying to play" + myQuestion.getQuestionSoundToken() + "from" + path);
            System.out.println(new File(".").getAbsolutePath());
            InputStream fin = getClass().getResourceAsStream(path + myQuestion.getQuestionSoundToken());
            Player playMp3 = new Player(fin);

            playMp3.play();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        }

        public void playAnswer(Question myAnswer){
        try
        {
                InputStream fin = getClass().getResourceAsStream(path + myAnswer.getAnswerSoundToken());
                Player playMp3 = new Player(fin);

                playMp3.play();
        }
        catch(Exception e)
        {
                System.out.println(e);
        }
        }
}