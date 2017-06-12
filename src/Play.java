import org.jfugue.player.Player;

import java.util.Random;

public class Play {
    public static String toPlay(PlayerPreferences pref, Interval i){
        //TODO: Implement different speeds and unisono option

        Random randomize = new Random();
        Integer rand = randomize.nextInt(20);
        rand += 50;
        Integer direction = randomize.nextInt(pref.directions.size());
        String notes, instrument = "I["+ pref.instrument +"] ";

        if(pref.directions.get(direction) == 0) {  // play up

            notes = instrument + rand.toString() + " " + (rand+i.getDistance());

        } else { //play down
            notes = instrument + (rand+i.getDistance()) + " " + rand.toString();

        }

        return notes;
    }

    public static String toPlay(PlayerPreferences pref, Chord c){
        //TODO: Implement different speeds and unisono option

        Random randomize = new Random();
        Integer rand = randomize.nextInt(20);
        rand += 45;
        Integer direction = randomize.nextInt(pref.directions.size());
        String instrument = "I["+ pref.instrument +"] ";
        StringBuilder notes = new StringBuilder();
        notes.append(instrument);
        notes.append(rand.toString() + " ");

        for(int i = 0; i < c.intervals.size(); i++) {
            rand = rand + c.intervals.get(i).getDistance();
            notes.append(rand.toString() + " ");
        }

        return String.valueOf(notes);
    }


    public static void playNotes(String notes) {
        Player player = new Player();
        player.play(notes);
    }

}
