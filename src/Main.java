import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    String[] intervalsLabels = {"Perfect unison", "Minor second", "Major second", "Minor third", "Major third", "Perfect fourth",
            "Augmented fourth", "Diminished fifth", "Perfect fifth", "Minor sixth", "Major sixth", "Minor seventh", "Major seventh", "Perfect octave"};
    String[] chordsLabels = {"Major Tonica", "Major First", "Major Second", "Minor Tonica", "Minor First", "Minor Second", "Diminished", "Augmented", };

    ArrayList<CheckBox> intervalsCheckboxes = new ArrayList<>();
    ArrayList<CheckBox> chordsCheckboxes = new ArrayList<>();
    Label titleIntervals, titleChords;
    VBox intervals, chords;
    Button startQuiz, select;

    @Override
    public void start(Stage primaryStage) throws Exception {
        EarTrainer m = new EarTrainer();

        intervals = new VBox();
        intervals.setSpacing(10);
        intervals.setPadding(new Insets(20));

        //vbox for labels
        chords = new VBox();
        chords.setSpacing(10);
        chords.setPadding(new Insets(20));

        for(int i = 0; i < intervalsLabels.length; i++) {
            CheckBox chinButton = new CheckBox(intervalsLabels[i]);
            intervalsCheckboxes.add(chinButton);
        }

        for(int i = 0; i < chordsLabels.length; i++) {
            CheckBox chinButton = new CheckBox(chordsLabels[i]);
            chordsCheckboxes.add(chinButton);
        }

        titleIntervals = new Label("Select intervals to play: ");
        titleChords = new Label("Select chords to play: ");

        intervals.getChildren().addAll(titleIntervals);
        intervals.getChildren().addAll(intervalsCheckboxes);
        chords.getChildren().addAll(titleChords);
        chords.getChildren().addAll(chordsCheckboxes);

        //create main container and add 2 vboxes to it
        FlowPane root = new FlowPane();
        root.setHgap(20);
        root.getChildren().addAll(intervals, chords);

        select = new Button("Invert choice");
        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (CheckBox c : intervalsCheckboxes)c.setSelected(!c.isSelected());
                for (CheckBox c : chordsCheckboxes)c.setSelected(!c.isSelected());
            }
        });

        startQuiz = new Button("Start");
        startQuiz.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                int c=0;
                for (CheckBox k : intervalsCheckboxes) {
                    if (k.isSelected()) {
                        for (Interval j : m.intervals) {
                            if (j.getFullName().equals(k.getText())) {
                                m.UserIntervals.add(j);
                                c++;
                            }
                        }
                    }
                }
                for (CheckBox k : chordsCheckboxes) {
                    if (k.isSelected()) {
                        for (Chord j : m.chords) {
                            if (j.getFullName().equals(k.getText())) {
                                m.UserChords.add(j);
                                c++;
                            }
                        }
                    }
                }
                //System.out.print(c);
                if(c<2){
                    Alert tooFew = new Alert(Alert.AlertType.ERROR, "Select at least two items!");
                    tooFew.showAndWait();
                    return;
                }
                Quiz.question(m, primaryStage);
                primaryStage.show();
            }
        });

        root.getChildren().add(startQuiz);
        root.getChildren().add(select);


        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Ear Trainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
