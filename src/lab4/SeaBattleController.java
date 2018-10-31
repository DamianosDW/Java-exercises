package lab4;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    // SeaBattleCells
    private static SeaBattleCell[][] playerSeaBattleCells = new SeaBattleCell[10][10];
    private static SeaBattleCell[][] computerSeaBattleCells = new SeaBattleCell[10][10];

    // Fields occupied by player ships
    private static List<FieldsOccupied> playerFieldsOccupied = new ArrayList<>();
    // Fields occupied by computer ships
    private static List<FieldsOccupied> computerFieldsOccupied = new ArrayList<>();

    // Player ship rotation
    private static boolean rotatedShip = false;
    // Selected ship length, ship number and shipOrientation
    private static int playerShipLength = 4;
    private static int playerShipNumber = 0;
    private static String shipOrientation = "vertical";

    @FXML
    void initialize()
    {
        // Pass this controller to Ship and SeaBattleCell classes
        Ship.setSeaBattleController(this);
        SeaBattleCell.setSeaBattleController(this);

        setShipPreviewWorkingArea(shipOrientation);

        // Prepare game areas
        preparePlayerGridPane();
        prepareComputerGridPane();

//        playerGridPane.setOnMousePressed(event -> {
//            // Place ship when player press left mouse button while cursor is on player GridPane
//            if(event.isPrimaryButtonDown())
//            {
//                System.out.println("Ship placed!");
//                // Change player ship length after number of ships (with this length) is correct
//                switch(playerShipLength)
//                {
//                    case 4:
//                        // Place player ship
//                        playerShipWithFourFields[playerShipNumber] = placePlayerShip(playerShipLength, shipOrientation);
//                        // Set proper values
//                        if(playerShipNumber == 0)
//                        {
//                            playerShipLength = 3;
//                            playerShipNumber = 0;
//                        }
//                        break;
//                    case 3:
//                        // Place player ship
//                        playerShipsWithThreeFields[playerShipNumber] = placePlayerShip(playerShipLength, shipOrientation);
//                        // Set proper values
//                        if(playerShipNumber == 2)
//                        {
//                            playerShipLength = 2;
//                            playerShipNumber = 0;
//                        }
//                        break;
//                    case 2:
//                        // Place player ship
//                        playerShipsWithTwoFields[playerShipNumber] = placePlayerShip(playerShipLength, shipOrientation);
//                        // Set proper values
//                        if(playerShipNumber == 3)
//                        {
//                            playerShipLength = 1;
//                            playerShipNumber = 0;
//                        }
//                        break;
//                    case 1:
//                        // Place player ship
//                        playerShipsWithOneField[playerShipNumber] = placePlayerShip(playerShipLength, shipOrientation);
//                        // Set proper values
//                        if(playerShipNumber == 4)
//                        {
//                            playerShipLength = 0;
//                            playerShipNumber = 0;
//                        }
//                        break;
//                }
//                playerShipNumber++;
//
//                setShipPreviewWorkingArea(shipOrientation);
//                // Add placed ships to proper array
////                switch(playerShipLength)
////                {
////                    case 4:
////                        playerShipWithFourFields[playerShipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
////                        // Set default ship number for other ship types
////                        playerShipNumber = 0;
////                        break;
////                    case 3:
////                        playerShipsWithThreeFields[playerShipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
////                        // Set default ship number for other ship types
////                        if(playerShipNumber == 1)
////                            playerShipNumber = 0;
////                        break;
////                    case 2:
////                        playerShipsWithTwoFields[playerShipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
////                        // Set default ship number for other ship types
////                        if(playerShipNumber == 2)
////                            playerShipNumber = 0;
////                        break;
////                    case 1:
////                        playerShipsWithOneField[playerShipNumber] = placePlayerShip(playerShipLength, (rotatedShip) ? "horizontal" : "vertical");
////                        // Set default ship number for other ship types
////                        if(playerShipNumber == 3)
////                            playerShipNumber = 0;
////                        break;
////                }
//            }
//            // Rotate ship when player press right mouse button while cursor is on player GridPane
//            if(event.isSecondaryButtonDown())
//            {
//                rotateShip();
//            }
//        });

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

    public static int getPlayerShipLength() {
        return playerShipLength;
    }

    public static void setPlayerShipLength(int playerShipLength) {
        SeaBattleController.playerShipLength = playerShipLength;
    }

    public static int getPlayerShipNumber() {
        return playerShipNumber;
    }

    public static void setPlayerShipNumber(int playerShipNumber) {
        SeaBattleController.playerShipNumber = playerShipNumber;
    }

    public static String getShipOrientation() {
        return shipOrientation;
    }

    public static void setShipOrientation(String shipOrientation) {
        SeaBattleController.shipOrientation = shipOrientation;
    }

    // Prepare game area for player
    private void preparePlayerGridPane()
    {
        // Add buttons to all gridpane fields
        for(int row = 0; row < 10; row++)
        {
            for(int column = 0; column < 10; column++)
            {
                // Create button
                SeaBattleCell seaBattleCell = new SeaBattleCell(row, column, true);
                // Add button to array
                playerSeaBattleCells[row][column] = seaBattleCell;
                // Add button to GridPane
                playerGridPane.add(seaBattleCell, column, row);
            }
        }
    }
    // This method creates ship for player with defined length
    private Ship createPlayerShip(int rowNumber, int columnNumber)
    {
        // Create temp array with ship coordinates
        ShipCoordinates[] shipCoordinates = new ShipCoordinates[playerShipLength];

        // Put ship into fields
        switch(shipOrientation)
        {
            case "horizontal":
                // Add first field to array
                shipCoordinates[0] = new ShipCoordinates(rowNumber, columnNumber);
                // Add another fields to array
                for(int i = 1; i < playerShipLength; i++)
                {
                    shipCoordinates[i] = new ShipCoordinates(rowNumber, ++columnNumber);
                }
                break;
            case "vertical":
                // Add first field to array
                shipCoordinates[0] = new ShipCoordinates(rowNumber, columnNumber);
                // Add another fields to array
                for(int i = 1; i < playerShipLength; i++)
                {
                    shipCoordinates[i] = new ShipCoordinates(++rowNumber, columnNumber);
                }
                break;
        }

        // Check if ship coordinates are free
        if(!checkIfShipPositionIsCorrect(shipCoordinates, false))
        {
            // Show error dialog
            showErrorDialog("You can't place ship there, because it's next to other ship or on other ship!");
            return null;
        }
        else
        {
            System.out.println("Generated coordinates - rowNumber = " + rowNumber + ", column = " + columnNumber + ". Orientation: " + shipOrientation);

            // Insert information about occupied fields to list
            if(shipOrientation.equals("vertical"))
            {
                for(ShipCoordinates ship : shipCoordinates)
                {
                    // Add fields occupied by ship to list
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow(), ship.getColumn()));
                    // Add fields next to the ship to list
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() - 1, ship.getColumn()));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow(), ship.getColumn() - 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow(), ship.getColumn() + 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() + 1, ship.getColumn()));

                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() - 1, ship.getColumn() - 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() - 1, ship.getColumn() + 1));

                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() + 1, ship.getColumn() - 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() + 1, ship.getColumn() + 1));
                }
            }
            else
            {
                for(ShipCoordinates ship : shipCoordinates)
                {
                    // Add fields occupied by ship to list
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow(), ship.getColumn()));
                    // Add fields next to the ship to list
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow(), ship.getColumn() - 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() - 1, ship.getColumn()));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() + 1, ship.getColumn()));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow(), ship.getColumn() + 1));

                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() - 1, ship.getColumn() - 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() + 1, ship.getColumn() - 1));

                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() - 1, ship.getColumn() + 1));
                    playerFieldsOccupied.add(new FieldsOccupied(shipOrientation, ship.getRow() + 1, ship.getColumn() + 1));
                }
            }

            return new Ship(playerShipLength, shipOrientation, shipCoordinates, false);
        }
    }
    // This method places player ship in game area
    public void placePlayerShip(int rowNumber, int columnNumber)
    {
        // Change player ship length after number of ships (with this length) is correct
        switch(playerShipLength)
        {
            case 4:
                // Show error dialog when ship is out of game area boundaries
                if((shipOrientation.equals("horizontal") && columnNumber > 6) || (shipOrientation.equals("vertical") && rowNumber > 6))
                {
                    showErrorDialog("You can't place your ship there, because it's out of game area boundaries!");
                }
                else
                {
                    playerShipWithFourFields[playerShipNumber] = createPlayerShip(rowNumber, columnNumber);

                    if(playerShipWithFourFields[playerShipNumber] != null)
                    {
                        // Place player ship (change proper buttons background and check if ship can be placed there)
                        changeSeaBattleCellBackgroundColor(playerShipLength, playerShipNumber, false);

                        // Set proper values
                        playerShipNumber++;
                        if(playerShipNumber == 1)
                        {
                            playerShipLength = 3;
                            playerShipNumber = 0;
                        }
                        // Set default ship orientation
                        shipOrientation = "vertical";
                    }
                }
                break;
            case 3:
                // Show error dialog when ship is out of game area boundaries
                if((shipOrientation.equals("horizontal") && columnNumber > 7) || (shipOrientation.equals("vertical") && rowNumber > 7))
                {
                    showErrorDialog("You can't place your ship there, because it's out of game area boundaries!");
                }
                else
                {
                    playerShipsWithThreeFields[playerShipNumber] = createPlayerShip(rowNumber, columnNumber);

                    if(playerShipsWithThreeFields[playerShipNumber] != null)
                    {
                        // Place player ship (change proper buttons background and check if ship can be placed there)
                        changeSeaBattleCellBackgroundColor(playerShipLength, playerShipNumber, false);

                        // Set proper values
                        playerShipNumber++;
                        if(playerShipNumber == 2)
                        {
                            playerShipLength = 2;
                            playerShipNumber = 0;
                        }
                        // Set default ship orientation
                        shipOrientation = "vertical";
                    }
                }
                break;
            case 2:
                // Show error dialog when ship is out of game area boundaries
                if((shipOrientation.equals("horizontal") && columnNumber > 8) || (shipOrientation.equals("vertical") && rowNumber > 8))
                {
                    showErrorDialog("You can't place your ship there, because it's out of game area boundaries!");
                }
                else
                {
                    playerShipsWithTwoFields[playerShipNumber] = createPlayerShip(rowNumber, columnNumber);

                    if(playerShipsWithTwoFields[playerShipNumber] != null)
                    {
                        // Place player ship (change proper buttons background and check if ship can be placed there)
                        changeSeaBattleCellBackgroundColor(playerShipLength, playerShipNumber, false);

                        // Set proper values
                        playerShipNumber++;
                        if(playerShipNumber == 3)
                        {
                            playerShipLength = 1;
                            playerShipNumber = 0;
                        }
                        // Set default ship orientation
                        shipOrientation = "vertical";
                    }
                }
                break;
            case 1:
                // Show error dialog when ship is out of game area boundaries
                if((shipOrientation.equals("horizontal") && columnNumber > 9) || (shipOrientation.equals("vertical") && rowNumber > 9))
                {
                    showErrorDialog("You can't place your ship there, because it's out of game area boundaries!");
                }
                else
                {
                    // Create 1-field ship
                    playerShipsWithOneField[playerShipNumber] = createPlayerShip(rowNumber, columnNumber);

                    if(playerShipsWithOneField[playerShipNumber] != null)
                    {
                        // Place player ship (change proper buttons background and check if ship can be placed there)
                        changeSeaBattleCellBackgroundColor(playerShipLength, playerShipNumber, false);

                        // Set proper values
                        playerShipNumber++;
                        if(playerShipNumber == 4)
                        {
                            playerShipLength = 0;
                            playerShipNumber = 0;
                        }
                        // Set default ship orientation
                        shipOrientation = "vertical";
                    }
                }
                break;
        }
        // Show new ship preview
        setShipPreviewWorkingArea(shipOrientation);
    }

    // Prepare game area for computer
    private void prepareComputerGridPane()
    {
        // Add buttons to all gridpane fields
        for(int row = 0; row < 10; row++)
        {
            for(int column = 0; column < 10; column++)
            {
                // Create button
                SeaBattleCell seaBattleCell = new SeaBattleCell(row, column, false);
                // Add button to array
                computerSeaBattleCells[row][column] = seaBattleCell;
                // Add button to GridPane
                computerGridPane.add(seaBattleCell, column, row);
            }
        }
        // Prepare computer ships
        prepareComputerShips();
    }
    // Prepare computer ships
    private void prepareComputerShips()
    {
        // Create 4-fields ship
        computerShipWithFourFields[0] = createComputerShip(4);
        System.out.println("Umieszczono statek złożony z 4 pól!");
        // Create 3-fields ships
        computerShipsWithThreeFields[0] = createComputerShip(3);
        System.out.println("Umieszczono statek złożony z 3 pól!");
        computerShipsWithThreeFields[1] = createComputerShip(3);
        System.out.println("Umieszczono statek złożony z 3 pól!");
        // Create 2-fields ships
        computerShipsWithTwoFields[0] = createComputerShip(2);
        System.out.println("Umieszczono statek złożony z 2 pól!");
        computerShipsWithTwoFields[1] = createComputerShip(2);
        System.out.println("Umieszczono statek złożony z 2 pól!");
        computerShipsWithTwoFields[2] = createComputerShip(2);
        System.out.println("Umieszczono statek złożony z 2 pól!");
        // Create 1-field ships
        computerShipsWithOneField[0] = createComputerShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");
        computerShipsWithOneField[1] = createComputerShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");
        computerShipsWithOneField[2] = createComputerShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");
        computerShipsWithOneField[3] = createComputerShip(1);
        System.out.println("Umieszczono statek złożony z 1 pola!");

        placeComputerShips();

    }
    // This method creates ship for computer with defined length
    private Ship createComputerShip(int length)
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
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn() + 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn()));

                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn() - 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn() + 1));

                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn() - 1));
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
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn()));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow(), ship.getColumn() + 1));

                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn() - 1));
                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() + 1, ship.getColumn() - 1));

                computerFieldsOccupied.add(new FieldsOccupied(orientation, ship.getRow() - 1, ship.getColumn() + 1));
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
        // Set proper number of ships
        int numberOfShips = 1;
        // Place computer ship (change proper buttons background and check if ship can be placed there)
        changeSeaBattleCellBackgroundColor(4, numberOfShips - 1, true);

        for(Ship ship : computerShipWithFourFields)
        {
            System.out.println(ship);
        }
        System.out.println("-------------------------------------------------------------");
        // Increase number of ships
        numberOfShips++;
        // Place computer ship (change proper buttons background and check if ship can be placed there)
        for(int shipNumber = 0; shipNumber < numberOfShips; shipNumber++)
            changeSeaBattleCellBackgroundColor(3, shipNumber, true);

        for(Ship ship : computerShipsWithThreeFields)
        {
            System.out.println(ship);
        }
        System.out.println("-------------------------------------------------------------");
        // Increase number of ships
        numberOfShips++;
        // Place computer ship (change proper buttons background and check if ship can be placed there)
        for(int shipNumber = 0; shipNumber < numberOfShips; shipNumber++)
            changeSeaBattleCellBackgroundColor(2, shipNumber, true);

        for(Ship ship : computerShipsWithTwoFields)
        {
            System.out.println(ship);
        }
        System.out.println("-------------------------------------------------------------");
        // Increase number of ships
        numberOfShips++;
        // Place computer ship (change proper buttons background and check if ship can be placed there)
        for(int shipNumber = 0; shipNumber < numberOfShips; shipNumber++)
            changeSeaBattleCellBackgroundColor(1, shipNumber, true);

        for(Ship ship : computerShipsWithOneField)
        {
            System.out.println(ship);
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
    // This method changes buttons background color
    private void changeSeaBattleCellBackgroundColor(int shipLength, int shipNumber, boolean changeComputerSeaBattleCells)
    {
        // Temporary array
        ShipCoordinates[] tempShipCoordinates = null;

        // Use proper array
        switch(shipLength)
        {
            case 1:
                if(changeComputerSeaBattleCells)
                    tempShipCoordinates = computerShipsWithOneField[shipNumber].getShipCoordinates();
                else
                    tempShipCoordinates = playerShipsWithOneField[shipNumber].getShipCoordinates();
                break;
            case 2:
                if(changeComputerSeaBattleCells)
                    tempShipCoordinates = computerShipsWithTwoFields[shipNumber].getShipCoordinates();
                else
                    tempShipCoordinates = playerShipsWithTwoFields[shipNumber].getShipCoordinates();
                break;
            case 3:
                if(changeComputerSeaBattleCells)
                    tempShipCoordinates = computerShipsWithThreeFields[shipNumber].getShipCoordinates();
                else
                    tempShipCoordinates = playerShipsWithThreeFields[shipNumber].getShipCoordinates();
                break;
            case 4:
                if(changeComputerSeaBattleCells)
                    tempShipCoordinates = computerShipWithFourFields[shipNumber].getShipCoordinates();
                else
                    tempShipCoordinates = playerShipWithFourFields[shipNumber].getShipCoordinates();
                break;
        }

        if(changeComputerSeaBattleCells)
        {
            // Search for proper buttons and change their background color
            if(tempShipCoordinates != null)
            {
                for(ShipCoordinates shipCoordinates : tempShipCoordinates)
                {
                    for(int i = 0; i < computerSeaBattleCells.length; i++)
                    {
                        for(int j = 0; j < computerSeaBattleCells.length; j++)
                        {
                            if(i == shipCoordinates.getRow() && j == shipCoordinates.getColumn())
                            {
                                // Change button background color
                                computerSeaBattleCells[i][j].setStyle("-fx-background-color: green; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: silver; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                            }
                        }
                    }
                }
            }
        }
        else
        {
            // Search for proper buttons and change their background color
            if(tempShipCoordinates != null)
            {
                for(ShipCoordinates shipCoordinates : tempShipCoordinates)
                {
                    for(int i = 0; i < playerSeaBattleCells.length; i++)
                    {
                        for(int j = 0; j < playerSeaBattleCells.length; j++)
                        {
                            if(i == shipCoordinates.getRow() && j == shipCoordinates.getColumn())
                            {
                                // Change button background color
                                playerSeaBattleCells[i][j].setStyle("-fx-background-color: blue; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: silver; -fx-pref-width: 42px; -fx-pref-height: 40px;");
                            }
                        }
                    }
                }
            }
        }
    }
    // This method rotates player ship
    public void rotateShip()
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
        {
            showStartGameDialogAndHideShipPreview();
            // Prevent from showing dialog after game was started
            playerShipLength = -1;
        }


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
                    Button button;
                    if(i == 0)
                    {
                        button = new Button("X");
                        button.setStyle("-fx-text-fill: white; -fx-padding: 4; -fx-background-color: black; -fx-background-radius: 0; -fx-border-color: silver; -fx-border-width: 1; -fx-pref-width: 15; -fx-pref-height: 15;");
                    }
                    else
                    {
                        button = new Button();
                        button.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-background-radius: 0; -fx-border-color: silver; -fx-border-width: 1; -fx-pref-width: 15; -fx-pref-height: 15;");
                    }
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
                    Button button;
                    if(i == 0)
                    {
                        button = new Button("X");
                        button.setStyle("-fx-text-fill: white; -fx-padding: 4; -fx-background-color: black; -fx-background-radius: 0; -fx-border-color: silver; -fx-border-width: 1; -fx-pref-width: 15; -fx-pref-height: 15;");
                    }
                    else
                    {
                        button = new Button();
                        button.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-background-radius: 0; -fx-border-color: silver; -fx-border-width: 1; -fx-pref-width: 15; -fx-pref-height: 15;");
                    }
                    button.setDisable(true);
                    // Add button to temp container
                    hBox.getChildren().add(button);
                }
                // Add temp container to ship preview container
                shipPreviewWorkingArea.getChildren().add(hBox);
                break;
        }
    }

    // Alerts
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
    private void showErrorDialog(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
