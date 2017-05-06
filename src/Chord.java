import java.util.ArrayList;

public class Chord extends NamedSequence{
    ArrayList<Interval> intervals;

    Chord(String f, String s, ArrayList<Interval> i){
        super(f,s);
        intervals = i;
    }

}
