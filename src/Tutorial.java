import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by dawid on 12.06.2017.
 */
public class Tutorial {
    public static void showTutorial(EarTrainer m) {
        FlowPane newRoot = new FlowPane();

        VBox intervalsBox = new VBox();
        intervalsBox.setPadding(new Insets(20));
        intervalsBox.setSpacing(6);
        VBox chordsBox = new VBox();
        chordsBox.setPadding(new Insets(20));
        chordsBox.setSpacing(6);

        Label titleIntervals= new Label();
        titleIntervals.setText("Listen to intervals:");

        Label titleChords= new Label();
        titleChords.setText("Listen to chords:");
        intervalsBox.getChildren().addAll(titleIntervals);
        chordsBox.getChildren().add(titleChords);

        for(Interval i: m.intervals) {
            Button b = new Button(i.getFullName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String notes = 55 + " " + (55+i.getDistance());
                    Runnable r = new Runnable() {
                        public void run() {
                            Play.playNotes(notes);
                        }
                    };

                    new Thread(r).start();
                };
            });

            intervalsBox.getChildren().add(b);
        }

        for(Chord c: m.chords) {
            Button b = new Button(c.getFullName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String notes = 55 + " " + (55+c.intervals.get(0).getDistance()) + " " + (55+c.intervals.get(0).getDistance()+c.intervals.get(1).getDistance());
                    Runnable r = new Runnable() {
                        public void run() {
                            Play.playNotes(notes);
                        }
                    };

                    new Thread(r).start();
                };
            });

            chordsBox.getChildren().add(b);
        }

        newRoot.getChildren().addAll(intervalsBox, chordsBox);
        newRoot.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(newRoot, 400, 600);
        Stage stage = new Stage();
        stage.setTitle("Tutorial");
        stage.setScene(scene);
        stage.show();
    }

}
