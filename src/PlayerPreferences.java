import java.util.ArrayList;

public class PlayerPreferences {
    ArrayList<Integer> directions; //0-up 1-down
    ArrayList<Integer> types; // 0-intervals 1-chords
    String instrument;
    PlayerPreferences(ArrayList<Integer> d, ArrayList<Integer> t) {
        directions = d;
        types = t;
    }
}
