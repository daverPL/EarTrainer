import org.jfugue.player.Player;

import java.util.Random;

public class Play {
    public static void toPlay(PlayerPreferences pref, Interval i){
        //TODO: Implement different speeds and unisono option

        Player player = new Player();
        Random randomize = new Random();
        Integer rand = randomize.nextInt(20);
        rand += 50;
        Integer direction = randomize.nextInt(pref.directions.size());
        String notes;

        if(pref.directions.get(direction) == 0) {  // play up

            notes = rand.toString() + " " + (rand+i.getDistance());

        } else { //play down

            notes = (rand+i.getDistance()) + " " + rand.toString();
        }

        player.play(notes);
    }

    public static void toPlay(PlayerPreferences pref, Chord c){
        //TODO: Implement different speeds and unisono option
        //TODO: Think about playing ind different directions?

        Player player = new Player();
        Random randomize = new Random();
        Integer rand = randomize.nextInt(20);
        rand += 45;
        Integer direction = randomize.nextInt(pref.directions.size());
        StringBuilder notes = new StringBuilder();
        notes.append(rand.toString() + " ");

        for(int i = 0; i < c.intervals.size(); i++) {
            rand = rand + c.intervals.get(i).getDistance();
            notes.append(rand.toString() + " ");
        }

        player.play(String.valueOf(notes));
    }

}
