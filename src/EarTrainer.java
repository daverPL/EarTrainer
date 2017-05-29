import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



public class EarTrainer {
    public Interval [] intervals ;
    public Chord [] chords ;
    public PlayerPreferences userPreferences;
    public int intervalsCount, chordsCount;
    public ArrayList <Interval> UserIntervals;
    public ArrayList <Chord> UserChords;
    public int numberOfQuestions;
    public ArrayList <Integer> Directions = new ArrayList<>();
    public HashMap <String, Integer> CorrectAnswers = new HashMap<>();
    public HashMap <String, Integer> WrongAnswers = new HashMap<>();

    EarTrainer(){
        intervalsCount = 14;
        intervals = new Interval[intervalsCount];
        chordsCount = 8;
        chords = new Chord[chordsCount];

        // Default preferences in future set after picking in UI
        UserIntervals = new ArrayList<>();
        UserChords = new ArrayList<>();
        ArrayList <Integer> Speeds = new ArrayList<>();
        ArrayList <Integer> Types = new ArrayList<>();


        Speeds.add(0);
        Types.add(0);
        Types.add(1);

        userPreferences = new PlayerPreferences(Speeds, Directions, Types);

        // Setting set of all chords and intervals:
        setIntervals();
        setChords();
    }


    boolean question() throws IOException{
        Random rand = new Random();
        int random = rand.nextInt(userPreferences.types.size());
        String correctAnswer;

        if(userPreferences.types.get(random) == 0 || UserChords.isEmpty()) {
            //rand from intervals
            random = rand.nextInt(UserIntervals.size());

            Play.toPlay(userPreferences, UserIntervals.get(random));
            correctAnswer = UserIntervals.get(random).getShortName();
        }else {
            //rand from chords
            random = rand.nextInt(UserChords.size());

            Play.toPlay(userPreferences, UserChords.get(random));
            correctAnswer = UserChords.get(random).getShortName();
        }

        System.out.println("Write answer:");

        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        String odp = buffer.readLine();

        if(odp.equals(correctAnswer)) {
            System.out.println("OK!");
            return true;
        }
        else {
            System.out.println("Fail! Correct answer: " + correctAnswer);
            return false;
        }
    }

    private void setIntervals() {
        intervals[0] = new Interval("Perfect unison", "P1", 0);
        intervals[1] = new Interval("Minor second", "m2", 1);
        intervals[2] = new Interval("Major second", "M2", 2);
        intervals[3] = new Interval("Minor third", "m3", 3);
        intervals[4] = new Interval("Major third", "M3", 4);
        intervals[5] = new Interval("Perfect fourth", "P4", 5);
        intervals[6] = new Interval("Augmented fourth", "A4", 6);
        intervals[7] = new Interval("Diminished fifth", "d5", 6);
        intervals[8] = new Interval("Perfect fifth", "P5", 7);
        intervals[9] = new Interval("Minor sixth", "m6", 8);
        intervals[10] = new Interval("Major sixth", "M6", 9);
        intervals[11] = new Interval("Minor seventh", "m7", 10);
        intervals[12] = new Interval("Major seventh", "M7", 11);
        intervals[13] = new Interval("Perfect octave", "P8", 12);
    }

    private void setChords() {
        ArrayList <Interval> ChordIntervals = new ArrayList<>();
        ChordIntervals.add(intervals[4]);
        ChordIntervals.add(intervals[3]);
        chords[0] = new Chord("Major Tonica", "MT", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[3]);
        ChordIntervals.add(intervals[5]);
        chords[1] = new Chord("Major First", "M1", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[5]);
        ChordIntervals.add(intervals[4]);
        chords[2] = new Chord("Major Second", "M2", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[3]);
        ChordIntervals.add(intervals[4]);
        chords[3] = new Chord("Minor Tonica", "mT", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[4]);
        ChordIntervals.add(intervals[5]);
        chords[4] = new Chord("Minor First", "m1", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[5]);
        ChordIntervals.add(intervals[3]);
        chords[5] = new Chord("Minor Second", "m2", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[3]);
        ChordIntervals.add(intervals[3]);
        chords[6] = new Chord("Diminished", "d", ChordIntervals);
        ChordIntervals.clear();
        ChordIntervals.add(intervals[4]);
        ChordIntervals.add(intervals[4]);
        chords[7] = new Chord("Augmented", "A", ChordIntervals);
        //TODO: Add Dominantas
    }

    public static void main(String[] args) throws IOException{
        EarTrainer M = new EarTrainer();

        while(true){
            M.question();

        }
    }

}
