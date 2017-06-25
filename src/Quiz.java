import com.sun.tools.javac.comp.Flow;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;



public class Quiz {

    public static NamedSequence ans;
    public static int correctAnswers = 0;
    public static int allAnswers = 0;
    public static String lastNotes;
    public static int [] intervalsCounter;
    public static int [] chordsCounter;
    public static int expectedValueOfCounter;

    public static void getOne(EarTrainer m){

        while(true) {
            // Choosing interval vs chord:
            Random rand = new Random();
            int random = rand.nextInt(m.UserIntervals.size() + m.UserChords.size());

            if (m.UserChords.isEmpty() && !m.UserIntervals.isEmpty() || random < m.UserIntervals.size()) {
                //rand from intervals
                int randomInterval = rand.nextInt(m.UserIntervals.size());
                if(intervalsCounter[randomInterval] <= expectedValueOfCounter) {
                    intervalsCounter[randomInterval]++;
                    lastNotes = Play.toPlay(m.userPreferences, m.UserIntervals.get(randomInterval));

                    Runnable r = new Runnable() {
                        public void run() {
                            Play.playNotes(lastNotes);
                        }
                    };

                    new Thread(r).start();
                    ans = m.UserIntervals.get(randomInterval);
                    break;
                }

            } else {
                //rand from chords
                int randomChord = rand.nextInt(m.UserChords.size());
                if(chordsCounter[randomChord] <= expectedValueOfCounter) {
                    chordsCounter[randomChord]++;
                    lastNotes = Play.toPlay(m.userPreferences, m.UserChords.get(randomChord));
                    Runnable r = new Runnable() {
                        public void run() {
                            Play.playNotes(lastNotes);
                        }
                    };
                    new Thread(r).start();
                    ans = m.UserChords.get(randomChord);
                    break;
                }
            }
        }
    }

    public static void question(EarTrainer m, Stage stag2e) {

        intervalsCounter = new int[m.UserIntervals.size()];
        chordsCounter = new int[m.UserChords.size()];
        expectedValueOfCounter = m.numberOfQuestions / (m.UserChords.size() + m.UserIntervals.size());

        Stage stage = new Stage();
        VBox root = new VBox();
        root.setSpacing(20);

        Scene scene = new Scene(root, 700, 300, Color.WHITE);
        stage.setScene(scene);
        FlowPane buttons;

        getOne(m);

        Text answer = new Text();
        answer.setFont(Font.font ("Verdana", 20));
        Text score = new Text();
        score.setText("0/0");
        score.setFont(Font.font ("Verdana", 20));


        buttons = new FlowPane();
        buttons.setPrefWrapLength(680);
        buttons.setVgap(5);
        buttons.setHgap(10);

        ArrayList<NamedSequence> l = new ArrayList<>();

        l.addAll(m.UserIntervals);
        l.addAll(m.UserChords);

        for (NamedSequence k : l) {
            Button b = new Button(k.getFullName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (ans.getFullName().equals(b.getText())) {
                        correctAnswers++;
                        answer.setText("True! " + ans.getFullName());
                        answer.setFill(Color.GREEN);
                        Stats.correctAnswerToday(m);

                        if (m.CorrectAnswers.containsKey(ans.getFullName())) {
                            Integer n = m.CorrectAnswers.get(ans.getFullName());
                            m.CorrectAnswers.remove(ans.getFullName());
                            m.CorrectAnswers.put(ans.getFullName(), n+1);
                        } else {
                            m.CorrectAnswers.put(ans.getFullName(), 1);
                        }
                    } else {

                        answer.setText("False! Correct: " + ans.getFullName());
                        answer.setFill(Color.RED);
                        Stats.wrongAnswerToday(m);

                        if (m.WrongAnswers.containsKey(ans.getFullName())) {
                            Integer n = m.WrongAnswers.get(ans.getFullName());
                            m.WrongAnswers.remove(ans.getFullName());
                            m.WrongAnswers.put(ans.getFullName(), n+1);
                        } else {
                            m.WrongAnswers.put(ans.getFullName(), 1);
                        }
                    }
                    allAnswers++;

                    FadeTransition ft = new FadeTransition(Duration.millis(2000), answer);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.0);
                    ft.play();
                    score.setText(correctAnswers + "/" + allAnswers);

                    if(allAnswers == m.numberOfQuestions) {
                        String info = "End of Quiz! Your score: " + correctAnswers + "/" + allAnswers;
                        allAnswers = 0;
                        correctAnswers = 0;
                        Alert tooFew = new Alert(Alert.AlertType.INFORMATION, info);
                        tooFew.showAndWait();
                        stage.close();
                    } else {
                        getOne(m);
                    }
                }
            });

            buttons.getChildren().add(b);
        }

        Button rep = new Button("Replay");
        rep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Runnable r = new Runnable() {
                    public void run() {
                        Play.playNotes(lastNotes);
                    }
                };

                new Thread(r).start();
            }
        });

        root.getChildren().add(rep);
        buttons.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(buttons);
        root.getChildren().addAll(answer, score);

        root.setAlignment(Pos.CENTER);
        stage.show();
    }
}
