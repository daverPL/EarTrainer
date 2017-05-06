import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class EarTrainer {
    Interval [] intervals ;
    Chord [] chords ;
    PlayerPreferences pref;
    int intervalsCount, chordsCount;

    EarTrainer(){
        intervalsCount = 2;
        intervals = new Interval[intervalsCount];
        chordsCount = 10;
        chords = new Chord[chordsCount];
        pref = new PlayerPreferences();

        intervals[0] = new Interval("Pryma", "pry", 1);
        intervals[1] = new Interval("Sekunda", "sek", 2);



    }

    boolean question() throws IOException{
        //na razie losuje z pelnej listy interwalow trzba przerobic na loswanie zgodne z wyborem
        int elemntsCount = intervalsCount;//<--

        Random rn = new Random();
        int rand = rn.nextInt(elemntsCount);

        Play.toPlay(pref, intervals[rand]);

        System.out.println("podaj odpowiedź");

        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        String odp = buffer.readLine();

        System.out.println(odp.equals(intervals[rand].getShortName()));

        return false;
    }

    public static void main(String[] args) throws IOException{
        EarTrainer M = new EarTrainer();

        while(true){
            M.question();

        }
    }

}
