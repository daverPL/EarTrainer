import java.util.ArrayList;

public class PlayerPreferences {
    ArrayList<Integer> speeds; //0-slow
    ArrayList<Integer> directions; //0-up 1-down 2-uniosno
    ArrayList<Integer> types; // 0-intervals 1-chords
    String instrument;
    PlayerPreferences(ArrayList<Integer> s, ArrayList<Integer> d, ArrayList<Integer> t) {
        speeds = s;
        directions = d;
        types = t;
    }
}
