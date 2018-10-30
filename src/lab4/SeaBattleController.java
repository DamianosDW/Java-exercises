package lab4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.*;

public class SeaBattleController
{
    // Injected FXML objects
    @FXML
    private GridPane playerGridPane;
    @FXML
    private GridPane computerGridPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private FlowPane shipPreview;
    @FXML
    private VBox playerShips;

    // Player ships
    private static Ship[] playerShipWithFourFields = new Ship[1];
    private static Ship[] playerShipsWithThreeFields = new Ship[2];
    private static Ship[] playerShipsWithTwoFields = new Ship[3];
    private static Ship[] playerShipsWithOneField = new Ship[4];

    // Computer ships
    private static Ship[] computerShipWithFourFields = new Ship[1];
    private static Ship[] computerShipsWithThreeFields = new Ship[2];
    private static Ship[] computerShipsWithTwoFields = new Ship[3];
    private static Ship[] computerShipsWithOneField = new Ship[4];

    // Fields occupied by player ships
    private static List<FieldsOccupied> playerFieldsOccupied = new ArrayList<>();
    // Fields occupied by computer ships
    private static List<FieldsOccupied> computerFieldsOccupied = new ArrayList<>();

    @FXML
    void initialize()
    {
        // Pass this controller to Ship class
        Ship.setSeaBattleController(this);

        prepareComputerShips();

        for(FieldsOccupied fieldsOccupied : computerFieldsOccupied)
            System.out.println(fieldsOccupied);
    }

    // Getters and setters
    public VBox getPlayerShips() {
        return playerShips;
    }

    // Prepare computer ships
    private void prepareComputerShips()
    {
        // Create 4-fields ship
        computerShipWithFourFields[0] = createShip(4);
        System.out.println("Umieszczono statek złożony z 4 pól!");
        // Create 3-fields ships
        computerShipsWithThreeFields[0] = createShip(3);
        System.out.println("Umieszczono statek złożony z 3 pól!");
        computerShipsWithThreeFields[1] = createShip(3);
        System.out.println("Umieszczono statek złożony z 3 pól!");
        // Create 2-fields ships
        computerShipsWithTwoFields[0] = createShip(2);
        System.out.println("Umieszczono statek złożony z 2 pól!");
        computerShipsWithTwoFields[1] = createShip(2);
        System.out.println("Umieszczono statek złożony z 2 pól!");
        computerShipsWithTwoFields[2] = createShip(2);
        System.out.println("Umieszczono statek złożony z 2 pól!");
        // Create 1-field ships
        computerShipsWithOneField[0] = createShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");
        computerShipsWithOneField[1] = createShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");
        computerShipsWithOneField[2] = createShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");
        computerShipsWithOneField[3] = createShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");

        for(Ship ship : computerShipWithFourFields)
        {
            System.out.println(ship);
            // Add ToggleButtons to GridPane in proper fields
            if(ship.getOrientation().equals("horizontal"))
            {
                for(int i = ship.getColumnNumber(); i <= ship.getEndPosition(); i++)
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: green;");
                    computerGridPane.add(toggleButton, (i == 0) ? 0 : i-1, (ship.getRowNumber() == 0) ? 0 : ship.getRowNumber() - 1);
                }
            }
            else
            {
                for(int i = ship.getRowNumber(); i < ship.getEndPosition(); i++)
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: green;");
                    computerGridPane.add(toggleButton, (ship.getColumnNumber() == 0) ? 0 : ship.getColumnNumber() - 1, (i == 0) ? 0 : i-1);
                }
            }
        }

        System.out.println("-------------------------------------------------------------");
        for(Ship ship : computerShipsWithThreeFields)
        {
            System.out.println(ship);
            // Add ToggleButtons to GridPane in proper fields
            if(ship.getOrientation().equals("horizontal"))
            {
                for(int i = ship.getColumnNumber(); i <= ship.getEndPosition(); i++)
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: blue;");
                    computerGridPane.add(toggleButton, (i == 0) ? 0 : i-1, (ship.getRowNumber() == 0) ? 0 : ship.getRowNumber()-1);
                }
            }
            else
            {
                for(int i = ship.getRowNumber(); i < ship.getEndPosition(); i++)
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: blue;");
                    computerGridPane.add(toggleButton, (ship.getColumnNumber() == 0) ? 0 : ship.getColumnNumber() - 1, (i == 0) ? 0 : i-1);
                }
            }
        }
        System.out.println("-------------------------------------------------------------");
        for(Ship ship : computerShipsWithTwoFields)
        {
            System.out.println(ship);
            // Add ToggleButtons to GridPane in proper fields
            if(ship.getOrientation().equals("horizontal"))
            {
                for(int i = ship.getColumnNumber(); i <= ship.getEndPosition(); i++)
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: red;");
                    computerGridPane.add(toggleButton, (i == 0) ? 0 : i-1, (ship.getRowNumber() == 0) ? 0 : ship.getRowNumber()-1);
                }
            }
            else
            {
                for(int i = ship.getRowNumber(); i < ship.getEndPosition(); i++)
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: red;");
                    computerGridPane.add(toggleButton, (ship.getColumnNumber() == 0) ? 0 : ship.getColumnNumber() - 1, (i == 0) ? 0 : i-1);
                }
            }
        }
        System.out.println("-------------------------------------------------------------");
        for(Ship ship : computerShipsWithOneField)
        {
            System.out.println(ship);
            // Add ToggleButtons to GridPane in proper fields
//            if(ship.getOrientation().equals("horizontal"))
//            {
//                for(int i = ship.getColumnNumber(); i <= ship.getEndPosition(); i++)
//                {
//                    ToggleButton toggleButton = new ToggleButton("S");
//                    toggleButton.setStyle("-fx-background-color: orange;");
//                    computerGridPane.add(toggleButton, (i == 0) ? 0 : i-1, (ship.getRowNumber() == 0) ? 0 : ship.getRowNumber()-1);
//                }
//            }
//            else
//            {
//                for(int i = ship.getRowNumber(); i < ship.getEndPosition(); i++)
//                {
//                    ToggleButton toggleButton = new ToggleButton("S");
//                    toggleButton.setStyle("-fx-background-color: orange;");
//                    computerGridPane.add(toggleButton, (ship.getColumnNumber() == 0) ? 0 : ship.getColumnNumber() - 1, (i == 0) ? 0 : i-1);
//                }
//            }
        }
    }

    // This method creates ship with defined length
    private Ship createShip(int length)
    {
        String orientation;
        int columnNumber = 0;
        int endPosition = 0;
        int rowNumber = 0;
        Random random = new Random();
        // Get random orientation
        orientation = (random.nextInt(2) == 1) ? "horizontal" : "vertical";

        // Put ship into free fields
        do
        {
            // Set proper ship end position
            switch(orientation)
            {
                case "horizontal":
                    // Get random row number
                    rowNumber = random.nextInt(9) + 1;
                    // Set proper start ship position (prevent from getting position > 10)
                    switch(length)
                    {
                        case 1:
                            // Set ship start position
                            columnNumber = random.nextInt(9) + 1;
                            break;
                        case 2:
                            // Set ship start position
                            columnNumber = random.nextInt(7) + 1;
                            break;
                        case 3:
                            // Set ship start position
                            columnNumber = random.nextInt(6) + 1;
                            break;
                        case 4:
                            // Set ship start position
                            columnNumber = random.nextInt(5) + 1;
                            break;
                    }
                    if(columnNumber == 10)
                        endPosition = columnNumber - length;
                    else
                        endPosition = columnNumber + (length-1);
                    break;
                case "vertical":
                    // Get random row number
                    do {
                        rowNumber = random.nextInt(9);
                    } while(rowNumber < length);
                    // Set proper start ship position (prevent from getting position > 10)
                    switch(length)
                    {
                        case 1:
                            // Set ship start position
                            columnNumber = random.nextInt(9) + 1;
                            break;
                        case 2:
                            // Set ship start position
                            columnNumber = random.nextInt(7) + 1;
                            break;
                        case 3:
                            // Set ship start position
                            columnNumber = random.nextInt(6) + 1;
                            break;
                        case 4:
                            // Set ship start position
                            columnNumber = random.nextInt(5) + 1;
                            break;
                    }
                    // Set proper ship end position
                    if(length != 1)
                        endPosition = rowNumber + length;
                    else
                        endPosition = rowNumber;
                    break;
            }
            System.out.println("Generated coordinates - rowNumber = " + rowNumber + ", column = " + columnNumber + ", endPosition = " + endPosition + ". Orientation: " + orientation);
        } while(!checkIfShipPositionIsCorrect(orientation, rowNumber, columnNumber, endPosition, true));

        // Insert information about occupied fields to list
        if(orientation.equals("vertical"))
        {
            computerFieldsOccupied.add(new FieldsOccupied(orientation, rowNumber, (rowNumber == 0) ? 0 : rowNumber - 1, (endPosition == 10) ? 10 : endPosition + 1));
            // Add fields next to the ship to list
            computerFieldsOccupied.add(new FieldsOccupied(orientation, (rowNumber == 0) ? 0 : rowNumber - 1, (rowNumber == 0) ? 0 : rowNumber - 1, (endPosition == 10) ? 10 : endPosition + 1));
            computerFieldsOccupied.add(new FieldsOccupied(orientation, rowNumber, (rowNumber == 10) ? 10 : rowNumber + 1, (endPosition == 10) ? 10 : endPosition + 1));
        }
        else
        {
            computerFieldsOccupied.add(new FieldsOccupied(orientation, rowNumber, (columnNumber == 10) ? 10 : columnNumber + 1, (endPosition == 10) ? 10 : endPosition + 1));
            // Add fields above the ship to list
            computerFieldsOccupied.add(new FieldsOccupied(orientation, (rowNumber == 0) ? 0 : rowNumber - 1, (columnNumber == 10) ? 10 : columnNumber + 1, (endPosition == 10) ? 10 : endPosition + 1));
            // Add fields below the ship to list
            computerFieldsOccupied.add(new FieldsOccupied(orientation, (rowNumber == 10) ? 10 : rowNumber + 1, (columnNumber == 10) ? 10 : columnNumber + 1, (endPosition == 10) ? 10 : endPosition + 1));
        }
        // Create ship object
        return new Ship(length, orientation, rowNumber, columnNumber, endPosition);
    }
    private static Ship createShip(int length, String orientation, int rowNumber, int startPosition, int endPosition)
    {
        // Create ship object
        return new Ship(length, orientation, rowNumber, startPosition, endPosition);
    }
    // This method checks if ship position is correct
    private boolean checkIfShipPositionIsCorrect(String orientation, int rowNumber, int columnNumber, int endPosition, boolean checkComputerShips)
    {
        if(checkComputerShips)
        {
            // Check horizontally oriented ship
            switch(orientation)
            {
                case "horizontal":
                    // Check if fields are free
                    if(!computerFieldsOccupied.isEmpty())
                    {
                        for(FieldsOccupied field : computerFieldsOccupied)
                        {
                            if(rowNumber == field.getRowNumber() && (columnNumber == field.getColumnNumber() && endPosition == field.getLastPosition()))
                                return false;
                        }
                    }
                    break;
                case "vertical":
                    // Check if fields are free
                    if(!computerFieldsOccupied.isEmpty())
                    {
                        for(FieldsOccupied field : computerFieldsOccupied)
                        {
                            if(rowNumber == field.getRowNumber() && (columnNumber >= field.getColumnNumber() && columnNumber <= field.getLastPosition()) && (endPosition >= field.getColumnNumber() && endPosition >= field.getLastPosition()))
                                return false;
                        }
                    }
                    break;
            }

        }
        return true;
    }
}
