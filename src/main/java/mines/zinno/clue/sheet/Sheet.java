package mines.zinno.clue.sheet;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class Sheet extends GridPane {

    private final static double FONT_MULTIPLIER = 0.75;

    private final List<SheetValue> sheetValues = new ArrayList<SheetValue>();

    public Sheet(String title, List<String> values, int rowSize) {
        this(title, values, rowSize, 6);
    }

    public Sheet(String title, List<String> values, int rowSize, int numCheckMarks) {
        numCheckMarks = Math.min(6, numCheckMarks);

        this.getStylesheets().add(this.getClass().getResource("/css/sheet-style.css").toExternalForm());
        this.setId("mines/zinno/clue/sheet");

        addConstraints(values.size(), rowSize);

        addTitleLabel(title, rowSize);
        addSheetValues(values, numCheckMarks, rowSize);

    }

    public void crossOut(String value) {
        for(SheetValue sheetValue : sheetValues) {
            if(sheetValue.getValue().equalsIgnoreCase(value))
                sheetValue.crossOut();
        }
    }

    private void addConstraints(int numRows, int rowSize) {
        for(int i = 0; i <= numRows; i++)
            this.getRowConstraints().add(new RowConstraints(rowSize));

    }

    private void addTitleLabel(String title, int rowSize) {
        this.add(generateLabel(title, (int) (rowSize*FONT_MULTIPLIER)), 0, 0);
    }

    private void addSheetValues(List<String> values, int numCheckMarks, int rowSize) {
        for(int i = 0; i < values.size(); i++) {
            SheetValue sheetValue = new SheetValue(values.get(i), numCheckMarks, rowSize);
            this.add(sheetValue, 0, i+1);
            sheetValues.add(sheetValue);
        }
    }

    private Label generateLabel(String text, int size) {
        Label label = new Label(text);
        label.setStyle(String.format("-fx-font-size: %dpt", size));
        label.getStyleClass().add("title");
        return label;
    }

}
