import com.sun.tools.javac.comp.Flow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;



public class Quiz {

    public static NamedSequence getOne(EarTrainer m){

        Random rand = new Random();
        int random = rand.nextInt(m.userPreferences.types.size());

        if (m.userPreferences.types.get(random) == 0 || m.UserChords.isEmpty()) {
            //rand from intervals
            final int random1 = rand.nextInt(m.UserIntervals.size());
            System.out.println("Wylosowany:" + m.UserIntervals.get(random1).getFullName());
            Runnable r = new Runnable() {
                public void run() {
                    Play.toPlay(m.userPreferences, m.UserIntervals.get(random1));
                }
            };

            new Thread(r).start();
            return  m.UserIntervals.get(random1);
        } else {
            //rand from chords
            final int random2 = rand.nextInt(m.UserChords.size());
            Runnable r = new Runnable() {
                public void run() {
                    Play.toPlay(m.userPreferences, m.UserChords.get(random2));
                }
            };

            new Thread(r).start();
            return m.UserChords.get(random2);
        }

    }

    public static void question(EarTrainer m, Stage stag2e) {
        Stage stage = new Stage();
        Group root = new Group();

        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        stage.setScene(scene);
        FlowPane butony;

        NamedSequence ans = getOne(m);

        butony = new FlowPane();
        butony.setPrefWrapLength(460);
        butony.setVgap(5);
        butony.setHgap(10);
        //butony.setSpacing(10);
        //butony.setPadding(new Insets(20));
        System.out.println("elo");
        ArrayList<NamedSequence> l = new ArrayList<>();

        l.addAll(m.UserIntervals);
        l.addAll(m.UserChords);

        for (NamedSequence k : l) {
            Button b = new Button(k.getFullName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (ans.getFullName().equals(b.getText())) {
                        System.out.println("yay");
                        ans.addScore(true);
                        Alert tooFew = new Alert(Alert.AlertType.CONFIRMATION, "OK!");
                        tooFew.showAndWait();
                        stage.close();
                    } else {
                        System.out.println("nay");
                        ans.addScore(false);
                        Alert tooFew = new Alert(Alert.AlertType.ERROR, "Wrong!");
                        tooFew.showAndWait();
                        stage.close();
                    }
                }
            });

            butony.getChildren().add(b);
        }

        Button rep = new Button("Replay");
        rep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Play.toPlay(m.userPreferences, ans);
            }
        });


        root.getChildren().add(butony);
        stage.show();


    }
}
