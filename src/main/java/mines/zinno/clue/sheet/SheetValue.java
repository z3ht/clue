package mines.zinno.clue.sheet;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SheetValue extends GridPane {

    private final static double FONT_MULTIPLIER = 0.7;
    private final static int[] COLUMN_DIVISION = new int [] {3, 5, 60, 5};

    private CheckBox knownCheckBox;
    private Label label;
    private List<CheckBox> predictedCheckBoxes = new ArrayList<CheckBox>();

    public SheetValue(String value, int numCheckMarks, int rowSize) {
        addConstraints(numCheckMarks);

        addValue(value, rowSize);
        addCheckMarks(numCheckMarks);
    }

    public void crossOut() {
        this.knownCheckBox.setSelected(true);
    }

    public String getValue() {
        return label.getText();
    }

    private void addConstraints(int numCheckMarks) {
        for(int i = 0; i < 3+numCheckMarks; i++) {
            ColumnConstraints temp = new ColumnConstraints();
            temp.setPercentWidth(COLUMN_DIVISION[Math.min(i, 3)]);
            this.getColumnConstraints().add(temp);
        }
    }

    private void addValue(String value, int rowSize) {
        this.label = generateLabel(value, (int) (rowSize*FONT_MULTIPLIER));
        this.add(label, 2, 0, 1, 1);
    }

    private void addCheckMarks(int numCheckMarks) {
        this.knownCheckBox = generateKnownCheckBox();
        this.add(knownCheckBox, 1, 0);
        for(int x = 0; x < numCheckMarks; x++) {
            CheckBox checkBox = generatePredictCheckBox();
            this.predictedCheckBoxes.add(checkBox);
            this.add(checkBox, x+3, 0);
        }
    }

    private Label generateLabel(String text, int size) {
        Label label = new Label(text);
        label.setStyle(String.format("-fx-font-size: %dpt", size));
        label.getStyleClass().add("value");
        return label;
    }

    private CheckBox generatePredictCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setAlignment(Pos.CENTER);
        return checkBox;
    }

    private CheckBox generateKnownCheckBox() {
        final CheckBox checkBox = new CheckBox();
        checkBox.setAlignment(Pos.CENTER);
        checkBox.setOnAction(event -> checkBox.setSelected(!checkBox.isSelected()));
        return checkBox;
    }
}
