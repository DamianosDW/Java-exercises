package lab4;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class SeaBattleController
{
    // Injected FXML objects
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private GridPane playerGridPane;
    @FXML
    private GridPane computerGridPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private VBox shipPreviewMainContainer;
    @FXML
    private FlowPane shipPreviewWorkingArea;
    @FXML
    private VBox playerShips;
    @FXML
    private VBox computerShips;

    // App stage
    private Stage mainStage;

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

    // Player ship rotation
    private static boolean rotatedShip = false;
    // Selected ship length, ship number and shipOrientation
    private static int playerShipLength = 4;
    private static int shipNumber = 0;
    private static String shipOrientation = "vertical";

    @FXML
    void initialize()
    {
        // Pass this controller to Ship class
        Ship.setSeaBattleController(this);

        setShipPreviewWorkingArea(shipOrientation);

        prepareComputerShips();

        playerGridPane.setOnMousePressed(event -> {
            // Place ship when player press left mouse button while cursor is on player GridPane
            if(event.isPrimaryButtonDown())
            {
                System.out.println("Ship placed!");
                // Change player ship length after number of ships (with this length) is correct
                switch(playerShipLength)
                {
                    case 4:
                        // Place player ship
                        playerShipWithFourFields[shipNumber] = placePlayerShip(playerShipLength, shipOrientation);
                        // Set proper values
                        if(shipNumber == 0)
                        {
                            playerShipLength = 3;
                            shipNumber = 0;
                        }
                        break;
                    case 3:
                        // Place player ship
                        playerShipsWithThreeFields[shipNumber] = placePlayerShip(playerShipLength, shipOrientation);
                        // Set proper values
                        if(shipNumber == 2)
                        {
                            playerShipLength = 2;
                            shipNumber = 0;
                        }
                        break;
                    case 2:
                        // Place player ship
                        playerShipsWithTwoFields[shipNumber] = placePlayerShip(playerShipLength, shipOrientation);
                        // Set proper values
                        if(shipNumber == 3)
                        {
                            playerShipLength = 1;
                            shipNumber = 0;
                        }
                        break;
                    case 1:
                        // Place player ship
                        playerShipsWithOneField[shipNumber] = placePlayerShip(playerShipLength, shipOrientation);
                        // Set proper values
                        if(shipNumber == 4)
                        {
                            playerShipLength = 0;
                            shipNumber = 0;
                        }
                        break;
                }
                shipNumber++;

                setShipPreviewWorkingArea(shipOrientation);
                // Add placed ships to proper array
//                switch(playerShipLength)
//                {
//                    case 4:
//                        playerShipWithFourFields[shipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
//                        // Set default ship number for other ship types
//                        shipNumber = 0;
//                        break;
//                    case 3:
//                        playerShipsWithThreeFields[shipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
//                        // Set default ship number for other ship types
//                        if(shipNumber == 1)
//                            shipNumber = 0;
//                        break;
//                    case 2:
//                        playerShipsWithTwoFields[shipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
//                        // Set default ship number for other ship types
//                        if(shipNumber == 2)
//                            shipNumber = 0;
//                        break;
//                    case 1:
//                        playerShipsWithOneField[shipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
//                        // Set default ship number for other ship types
//                        if(shipNumber == 3)
//                            shipNumber = 0;
//                        break;
//                }
            }
            // Rotate ship when player press left mouse button while cursor is on player GridPane
            if(event.isSecondaryButtonDown())
            {
                rotateShip();
            }
        });

//        for(FieldsOccupied fieldsOccupied : computerFieldsOccupied)
//            System.out.println(fieldsOccupied);
    }

    // Getters and setters
    public VBox getPlayerShips() {
        return playerShips;
    }

    public VBox getComputerShips() {
        return computerShips;
    }

    public void setMainStage(Stage mainStage)
    {
        this.mainStage = mainStage;
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

        placeComputerShips();

    }

    // This method creates ship with defined length
    private Ship createShip(int length)
    {
        String orientation;
        int columnNumber = 0;
        int endPosition = 0;
        int rowNumber = 0;
        Random random = new Random();
        // Get random shipOrientation
        orientation = (random.nextInt(2) == 1) ? "horizontal" : "vertical";
        // Create temp array with ship coordinates
        ShipCoordinates[] shipCoordinates = new ShipCoordinates[length];

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
                    // Add first field to array
                    shipCoordinates[0] = new ShipCoordinates(rowNumber, columnNumber);
                    // Add another fields to array
                    for(int i = 1; i < length; i++)
                    {
                        shipCoordinates[i] = new ShipCoordinates(rowNumber, ++columnNumber);
                    }
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
                    // Add first field to array
                    shipCoordinates[0] = new ShipCoordinates(rowNumber, columnNumber);
                    // Add another fields to array
                    for(int i = 1; i < length; i++)
                    {
                        shipCoordinates[i] = new ShipCoordinates(++rowNumber, columnNumber);
                    }
                    break;
            }
            System.out.println("Generated coordinates - rowNumber = " + rowNumber + ", column = " + columnNumber + ", endPosition = " + endPosition + ". Orientation: " + orientation);
        } while(!checkIfShipPositionIsCorrect(shipCoordinates, true));

        // Insert information about occupied fields to list
        if(orientation.equals("vertical"))
        {
            for(ShipCoordinates ship : shipCoordinates)
            {
                // Add fields occupied by ship to list
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn()));
                // Add fields next to the ship to list
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn()));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn() - 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn()));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn() + 1));

                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn() - 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn() + 1));
            }
        }
        else
        {
            for(ShipCoordinates ship : shipCoordinates)
            {
                // Add fields occupied by ship to list
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn()));
                // Add fields next to the ship to list
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn() - 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn()));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn() + 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn()));

                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn() - 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn() + 1));
            }
        }
        // Create ship object
        return new Ship(length, orientation, shipCoordinates, true);
    }
    // This method places computer ships into GridPane container fields
    private void placeComputerShips()
    {
        System.out.println("-------------------------------------------------------------");
        for(Ship ship : computerShipWithFourFields)
        {
            System.out.println(ship);
            // Add ToggleButtons to GridPane in proper fields
            if(ship.getOrientation().equals("horizontal"))
            {
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: green; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
                }
            }
            else
            {
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: green; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
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
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: blue; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
                }
            }
            else
            {
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: blue; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
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
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: red; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
                }
            }
            else
            {
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: red; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
                }
            }
        }
        System.out.println("-------------------------------------------------------------");
        for(Ship ship : computerShipsWithOneField)
        {
            System.out.println(ship);
            // Add ToggleButtons to GridPane in proper fields
            if(ship.getOrientation().equals("horizontal"))
            {
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: orange; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
                }
            }
            else
            {
                for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
                {
                    ToggleButton toggleButton = new ToggleButton("S");
                    toggleButton.setStyle("-fx-background-color: orange; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                    computerGridPane.add(toggleButton, shipCoordinates.getColumn() - 1, shipCoordinates.getRow() - 1);
                }
            }
        }
    }

    // This method checks if ship position is correct
    private boolean checkIfShipPositionIsCorrect(ShipCoordinates[] shipCoordinates, boolean checkComputerShips)
    {
        if(checkComputerShips)
        {
            // Check if fields are free
            if(!computerFieldsOccupied.isEmpty())
            {
                for(FieldsOccupied field : computerFieldsOccupied)
                {
                    for(int i = 0; i < shipCoordinates.length; i++)
                    {
                        if(field.getRowNumber() == shipCoordinates[i].getRow() && field.getColumnNumber() == shipCoordinates[i].getColumn())
                            return false;
                    }
                }
            }
            return true;
        }
        else
        {
            // Check if fields are free
            if(!playerFieldsOccupied.isEmpty())
            {
                for(FieldsOccupied field : playerFieldsOccupied)
                {
                    for(int i = 0; i < shipCoordinates.length; i++)
                    {
                        if(field.getRowNumber() == shipCoordinates[i].getRow() && field.getColumnNumber() == shipCoordinates[i].getColumn())
                            return false;
                    }
                }
            }
            return true;
        }
    }
    // This method places player ship in game area
    private Ship placePlayerShip(int length, String orientation)
    {
        // Create temp array with ship coordinates

//        ShipCoordinates[] shipCoordinates = new ShipCoordinates[length];


        return null;
        // Create ship object
//        return new Ship(length, shipOrientation, shipCoordinates, false);
    }
    // This method rotates player ship
    private void rotateShip() //TODO NAPISAC CALA FUNKCJONALNOSC
    {
        if(shipOrientation.equals("vertical"))
        {
            // Change ship preview
            setShipPreviewWorkingArea("horizontal");
            System.out.println("Ship orientation: horizontal!");
            shipOrientation = "horizontal";
        }
        else
        {
            // Change ship preview
            setShipPreviewWorkingArea("vertical");
            System.out.println("Ship orientation: vertical!");
            shipOrientation = "vertical";
        }
//        if(!rotatedShip)
//        {
//            // Change ship preview
//            setShipPreviewWorkingArea("horizontal");
//            System.out.println("Ship shipOrientation: horizontal!");
//
//            rotatedShip = true;
//        }
//        else
//        {
//            // Change ship preview
//            setShipPreviewWorkingArea("vertical");
//            System.out.println("Ship shipOrientation: vertical!");
//            // Set default value
//            rotatedShip = false;
//        }

    }
    // This method changes ship preview
    private void setShipPreviewWorkingArea(String orientation)
    {
        // Show start game dialog when player placed all ships
        if(playerShipLength == 0)
            showStartGameDialogAndHideShipPreview();

        // Clear ship preview container
        shipPreviewWorkingArea.getChildren().clear();

        // Create proper container for buttons
        switch(orientation)
        {
            case "vertical":
                // Create working area
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                // Prepare ship view (use ship length to show ship which can be placed in game area)
                for(int i = 0; i < playerShipLength; i++)
                {
                    // Create button that will be used to show ship view
                    Button button = new Button();
                    button.setStyle("-fx-background-color: black; -fx-background-radius: 0; -fx-border-color: silver; -fx-border-width: 1; -fx-pref-width: 15; -fx-pref-height: 15;");
                    button.setDisable(true);
                    // Add button to temp container
                    vBox.getChildren().add(button);
                }
                // Add temp container to ship preview container
                shipPreviewWorkingArea.getChildren().add(vBox);
                break;
            case "horizontal":
                // Create working area
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER);
                // Prepare ship view (use ship length to show ship which can be placed in game area)
                for(int i = 0; i < playerShipLength; i++)
                {
                    // Create button that will be used to show ship view
                    Button button = new Button();
                    button.setStyle("-fx-background-color: black; -fx-background-radius: 0; -fx-border-color: silver; -fx-border-width: 1; -fx-pref-width: 15; -fx-pref-height: 15;");
                    button.setDisable(true);
                    // Add button to temp container
                    hBox.getChildren().add(button);
                }
                // Add temp container to ship preview container
                shipPreviewWorkingArea.getChildren().add(hBox);
                break;
        }
    }

    private void showStartGameDialogAndHideShipPreview()
    {
        // Show information about game start
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText("You've placed all ships on the game area! Now you can start shooting computer ships. Good luck!");
        info.showAndWait();
        // Hide ship preview container
        shipPreviewMainContainer.setVisible(false);
    }
}
