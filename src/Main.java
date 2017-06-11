import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Map;

public class Main extends Application {
    String[] intervalsLabels = {"Perfect unison", "Minor second", "Major second", "Minor third", "Major third", "Perfect fourth",
            "Augmented fourth", "Diminished fifth", "Perfect fifth", "Minor sixth", "Major sixth", "Minor seventh", "Major seventh", "Perfect octave"};
    String[] chordsLabels = {"Major Tonica", "Major First", "Major Second", "Minor Tonica", "Minor First", "Minor Second", "Diminished", "Augmented", };

    ArrayList<CheckBox> intervalsCheckboxes = new ArrayList<>();
    ArrayList<CheckBox> chordsCheckboxes = new ArrayList<>();
    Label titleIntervals, titleChords, titlePreferences;
    VBox intervals, chords, preferences;
    Button startQuiz, select, sessionStats;
    EarTrainer m = new EarTrainer();

    @Override
    public void stop() throws Exception {
        m.saveStats();
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        intervals = new VBox();
        intervals.setSpacing(10);
        intervals.setPadding(new Insets(20));

        chords = new VBox();
        chords.setSpacing(10);
        chords.setPadding(new Insets(20));

        preferences = new VBox();
        preferences.setSpacing(10);
        preferences.setPadding(new Insets(20));

        for(int i = 0; i < intervalsLabels.length; i++) {
            CheckBox chinButton = new CheckBox(intervalsLabels[i]);
            intervalsCheckboxes.add(chinButton);
        }

        for(int i = 0; i < chordsLabels.length; i++) {
            CheckBox chinButton = new CheckBox(chordsLabels[i]);
            chordsCheckboxes.add(chinButton);
        }

        // Preferences:
        CheckBox directionUp = new CheckBox("Direction: up");
        CheckBox directionDown = new CheckBox("Direction: down");
        Spinner spinner = new Spinner<Integer>(1, 20, 10);
        Label label = new Label("Number of questions:");
        Label blank = new Label(" ");

        ListView<String> instruments = new ListView<String>();


        instruments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<String> insts =FXCollections.observableArrayList ("PIANO", "ELECTRIC_JAZZ_GUITAR", "FLUTE", "CELESTA", "CHURCH_ORGAN", "VIOLIN", "HARMONICA", "CELLO", "ROCK_ORGAN", "ACOUSTIC_BASS", "TROMBONE",  "TUBA", "CONTRABASS");
        instruments.setPrefHeight(insts.size() * 24 + 2);

        instruments.setItems(insts);

        titleIntervals = new Label("Select intervals to play: ");
        titleChords = new Label("Select chords to play: ");
        titlePreferences = new Label("Select playing preferences: ");
        intervals.getChildren().addAll(titleIntervals);
        intervals.getChildren().addAll(intervalsCheckboxes);
        chords.getChildren().addAll(titleChords);
        chords.getChildren().addAll(chordsCheckboxes);
        preferences.getChildren().addAll(titlePreferences);
        preferences.getChildren().addAll(directionUp, directionDown, blank, label, spinner, instruments);


        //create main container and add 2 vboxes to it
        FlowPane root = new FlowPane();
        root.setHgap(20);
        root.getChildren().addAll(intervals, chords, preferences);

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
                m.UserIntervals.clear();
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
                m.UserChords.clear();
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

                m.numberOfQuestions = ((int) spinner.getValue());
                System.out.println(m.numberOfQuestions);

                m.Directions.clear();
                if(!directionDown.isSelected() && !directionUp.isSelected()) {
                    Alert tooFew = new Alert(Alert.AlertType.ERROR, "Select at least one direction!");
                    tooFew.showAndWait();
                    return;
                } else {
                    if(directionUp.isSelected()) m.Directions.add(0);
                    if(directionDown.isSelected()) m.Directions.add(1);
                }

                if(c<2){
                    Alert tooFew = new Alert(Alert.AlertType.ERROR, "Select at least two items!");
                    tooFew.showAndWait();
                    return;
                }

                m.userPreferences.instrument = instruments.getSelectionModel().getSelectedItem();
                if(m.userPreferences.instrument == null)m.userPreferences.instrument = "PIANO";

                Quiz.question(m, primaryStage);
                primaryStage.show();
            }
        });

        sessionStats = new Button("Session Stats");
        sessionStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stats.showStats(m);////////////////
                FlowPane newRoot = new FlowPane();
                root.setHgap(20);
                VBox correctBox = new VBox();
                correctBox.setPadding(new Insets(20));
                VBox wrongBox = new VBox();
                wrongBox.setPadding(new Insets(20));

                Label titleCorrect= new Label();
                titleCorrect.setText("Correct answers stats:");
                Label subtitleCorrect= new Label();
                subtitleCorrect.setText("Interval: Number of answers");
                correctBox.getChildren().addAll(titleCorrect, subtitleCorrect);

                for (Map.Entry<String, Integer> entry : m.CorrectAnswers.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    Label stat = new Label();
                    stat.setText(key + ": " + value);
                    correctBox.getChildren().add(stat);
                }

                Label titleWrong= new Label();
                titleWrong.setText("Wrong answers stats:");
                Label subtitleWrong= new Label();
                subtitleWrong.setText("Interval / Number of answers");
                wrongBox.getChildren().addAll(titleWrong, subtitleWrong);


                for (Map.Entry<String, Integer> entry : m.WrongAnswers.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    Label stat = new Label();
                    stat.setText(key + ": " + value);
                    wrongBox.getChildren().add(stat);
                }

                newRoot.getChildren().addAll(correctBox, wrongBox);
                newRoot.setAlignment(Pos.TOP_CENTER);
                Scene scene2 = new Scene(newRoot, 800, 550);
                Stage stage2 = new Stage();
                stage2.setTitle("Stats");
                stage2.setScene(scene2);
                stage2.show();

            }
        });

        preferences.getChildren().addAll(startQuiz, select, sessionStats);

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("Ear Trainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
