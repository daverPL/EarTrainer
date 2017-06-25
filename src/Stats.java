import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by me on 04.06.17.
 */
public class Stats {
    public static void showStats(EarTrainer m) {
        FlowPane newRoot = new FlowPane();
        VBox calendarBox = new VBox();
        calendarBox.setPadding(new Insets(20));
        VBox correctBox = new VBox();
        correctBox.setPadding(new Insets(20));
        VBox wrongBox = new VBox();
        wrongBox.setPadding(new Insets(20));

        Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    LocalDate localDate = LocalDate.now();
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if(m.statsByDay.containsKey(item)){
                                    setStyle("-fx-background-color: #24e514;");
                                    setTooltip(new Tooltip("Total answers: " + m.statsByDay.get(item).getKey() + " correct: " + m.statsByDay.get(item).getValue()));
                                }
                                else if(item.equals(localDate) || item.isBefore(localDate)){
                                    setStyle("-fx-background-color: #ffc0cb;");
                                    setTooltip(new Tooltip("No answers that day."));
                                }

                            }
                        };
                    }
                };

        DatePicker d = new DatePicker();
        d.setDayCellFactory(dayCellFactory);
        d.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                correctBox.getChildren().clear();
                wrongBox.getChildren().clear();
                if(m.statsByDay.containsKey(d.getValue())){

                    Label titleCorrect= new Label();
                    titleCorrect.setText("Correct answers stats:");
                    Label subtitleCorrect= new Label();
                    subtitleCorrect.setText("Interval / Number of answers");
                    correctBox.getChildren().addAll(titleCorrect, subtitleCorrect);

                    for (Map.Entry<String, Integer> entry : m.correctByDay.get(d.getValue()).entrySet()) {
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


                    for (Map.Entry<String, Integer> entry : m.wrongByDay.get(d.getValue()).entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        Label stat = new Label();
                        stat.setText(key + ": " + value);
                        wrongBox.getChildren().add(stat);
                    }
                }
            }
        });
        DatePickerSkin calendar = new DatePickerSkin(d);
        calendarBox.getChildren().addAll(calendar.getPopupContent());

        newRoot.getChildren().addAll(calendarBox, correctBox, wrongBox);
        newRoot.setAlignment(Pos.TOP_CENTER);
        Scene scene2 = new Scene(newRoot, 800, 400);
        Stage stage2 = new Stage();
        stage2.setTitle("Stats");
        stage2.setScene(scene2);
        stage2.show();
    }
    static void correctAnswerToday(EarTrainer m){
        if(m.statsByDay.containsKey(LocalDate.now())){
            Pair<Integer, Integer> p = new Pair<>(new Integer(m.statsByDay.get(LocalDate.now()).getKey() + 1), new Integer(m.statsByDay.get(LocalDate.now()).getValue() + 1));
            m.statsByDay.remove(LocalDate.now());
            m.statsByDay.put(LocalDate.now(), p);

        }
        else{
            Pair<Integer, Integer> p = new Pair<>(new Integer(1), new Integer(1));
            m.statsByDay.put(LocalDate.now(), p);
        }

    }
    static void wrongAnswerToday(EarTrainer m){
        if(m.statsByDay.containsKey(LocalDate.now())){
            Pair<Integer, Integer> p = new Pair<>(new Integer(m.statsByDay.get(LocalDate.now()).getKey() + 1), new Integer(m.statsByDay.get(LocalDate.now()).getValue() ));
            m.statsByDay.remove(LocalDate.now());
            m.statsByDay.put(LocalDate.now(), p);
        }
        else{
            Pair<Integer, Integer> p = new Pair<>(new Integer(1), new Integer(0));
            m.statsByDay.put(LocalDate.now(), p);
        }
    }
}
