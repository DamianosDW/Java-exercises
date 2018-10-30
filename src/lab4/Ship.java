package lab4;

import javafx.scene.control.Label;

public class Ship
{
    private Label shipInfo;
    private String orientation;
    private int columnNumber;
    private int endPosition;
    private int rowNumber;
    // Controller
    private static SeaBattleController seaBattleController;

    // Getters and setters
    public static void setSeaBattleController(SeaBattleController seaBattleController) {
        Ship.seaBattleController = seaBattleController;
    }
    public Label getShipInfo() {
        return shipInfo;
    }
    public String getOrientation() {
        return orientation;
    }
    public int getRowNumber() {
        return rowNumber;
    }
    public int getColumnNumber() {
        return columnNumber;
    }
    public int getEndPosition() {
        return endPosition;
    }

    public Ship(int length, String orientation, int rowNumber, int columnNumber, int endPosition)
    {
        // Create new label and add it to proper container
        switch(length)
        {
            case 1:
                shipInfo = new Label("-");
                // Add ship info to VBox container
                seaBattleController.getPlayerShips().getChildren().add(shipInfo);
                break;
            case 2:
                shipInfo = new Label("--");
                // Add ship info to VBox container
                seaBattleController.getPlayerShips().getChildren().add(shipInfo);
                break;
            case 3:
                shipInfo = new Label("---");
                // Add ship info to VBox container
                seaBattleController.getPlayerShips().getChildren().add(shipInfo);
                break;
            case 4:
                shipInfo = new Label("----");
                // Add ship info to VBox container
                seaBattleController.getPlayerShips().getChildren().add(shipInfo);
                break;
        }

        // Set values to variables
        this.orientation = orientation;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.endPosition = endPosition;
    }
    @Override
    public String toString() {
        return  "Ship info: " + this.shipInfo.getText() + ", orientation: " + orientation + ", row number: " + rowNumber + ", column: " + columnNumber + ", end position: " + endPosition;
    }
}
