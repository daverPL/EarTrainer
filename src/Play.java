import org.jfugue.player.Player;

import java.util.Random;

public class Play {
    public static void toPlay(PlayerPreferences pref, Interval i){
        Player player = new Player();

        Random rn = new Random();
        Integer rand = rn.nextInt(10);
        rand += 60;
        String p = rand.toString() + " " + (rand+i.getDistance());

        player.play(p);
    }

    public static void toPlay(PlayerPreferences pref, Chord c){
        Player player = new Player();
        player.play("36");
    }

}
