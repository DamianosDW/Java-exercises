package lab4;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    private Label playerScoreLabel;
    @FXML
    private Label computerScoreLabel;
    @FXML
    private VBox shipPreviewMainContainer;
    @FXML
    private FlowPane shipPreviewWorkingArea;
    @FXML
    private VBox playerShips;
    @FXML
    private VBox computerShips;

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

    // Selected ship length, ship number and shipOrientation
    private static int playerShipLength = 4;
    private static int playerShipNumber = 0;
    private static String shipOrientation = "vertical";

    // Scores
    private static int playerScore = 0;
    private static int computerScore = 0;

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
        // Create temp array with ship coordinates
        ShipCoordinates[] shipCoordinates = new ShipCoordinates[length];

        // Put ship into free fields
        do
        {
            // Get random shipOrientation
            orientation = (random.nextInt(2) == 1) ? "horizontal" : "vertical";
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
                        columnNumber = random.nextInt(9);
                    } while(columnNumber < length);
                    // Set proper start ship position (prevent from getting position > 10)
                    switch(length)
                    {
                        case 1:
                            // Set ship start position
                            rowNumber = random.nextInt(9) + 1;
                            break;
                        case 2:
                            // Set ship start position
                            rowNumber = random.nextInt(7) + 1;
                            break;
                        case 3:
                            // Set ship start position
                            rowNumber = random.nextInt(6) + 1;
                            break;
                        case 4:
                            // Set ship start position
                            rowNumber = random.nextInt(5) + 1;
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
//            if(tempShipCoordinates != null)
//            {
//                for(ShipCoordinates shipCoordinates : tempShipCoordinates)
//                {
//                    for(int i = 0; i < computerSeaBattleCells.length; i++)
//                    {
//                        for(int j = 0; j < computerSeaBattleCells.length; j++)
//                        {
//                            if(i == shipCoordinates.getRow() && j == shipCoordinates.getColumn())
//                            {
//                                // Change button background color
//                                computerSeaBattleCells[i][j].setStyle("-fx-background-color: green; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: silver; -fx-pref-width: 42px; -fx-pref-height: 40px;");
//                            }
//                        }
//                    }
//                }
//            }
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
        // Start game
        startGame();
    }
    private void showErrorDialog(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
    private void showEndGameDialog(int playerScore, int computerScore)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game over");

        if(playerScore > computerScore)
            alert.setHeaderText("Game over! You won! Take a look at the game statistics.");
        else
            alert.setHeaderText("Game over! You lost! Take a look at the game statistics.");

        alert.setContentText("You destroyed " + playerScore + " ship parts!\nComputer destroyed " + computerScore + " ship parts!\n\nDo you want to play again?");
        alert.showAndWait().ifPresent(result -> {
            // Restart game
            if(result == ButtonType.OK)
            {
                // Set default variables values
                playerShipLength = 4;
                playerShipNumber = 0;
                shipOrientation = "vertical";
                this.playerScore = 0;
                this.computerScore = 0;
                // Clear lists
                playerFieldsOccupied.clear();
                computerFieldsOccupied.clear();

                // Reload app window
                SeaBattleGame.loadAppWindow();
            }
        });
    }
    // This method configures buttons and labels to start sea battle game
    private void startGame()
    {
        // Change all buttons action
        for(int i = 0; i < computerSeaBattleCells.length; i++)
        {
            for(int j = 0; j < computerSeaBattleCells.length; j++)
            {
                SeaBattleCell computerSeaBattleCell = computerSeaBattleCells[i][j];
                SeaBattleCell playerSeaBattleCell = playerSeaBattleCells[i][j];
                // Clear buttons action
                computerSeaBattleCell.setOnMouseDragged(event -> {});
                playerSeaBattleCell.setOnMouseDragged(event -> {});
                // Set new action
                computerSeaBattleCell.setOnAction(event ->
                {
                    System.out.println("Computer SeaBattle cell coordinates: rowNumber = " + computerSeaBattleCell.getRowNumber() + ", columnNumber = " + computerSeaBattleCell.getColumnNumber());

                    markSelectedField(computerSeaBattleCell.getRowNumber(), computerSeaBattleCell.getColumnNumber());
                });
            }
        }
        // Set default score
        playerScoreLabel.setText(playerScore + "");
        computerScoreLabel.setText(computerScore + "");
    }
    // This method disabled all containers and shows end game information
    private void endGame()
    {
        // Disable containers
        playerGridPane.setDisable(true);
        computerGridPane.setDisable(true);
        // Show end game dialog
        showEndGameDialog(playerScore, computerScore);
    }

    // This method reveals selected field and updates score label
    private void markSelectedField(int rowNumber, int columnNumber)
    {
        boolean shipIsDamagedByPlayer;
        boolean shipIsDamagedByComputer;

        // Player's turn
        shipIsDamagedByPlayer = checkIfPlayerDamagedShip(rowNumber, columnNumber);

        if(shipIsDamagedByPlayer)
        {
            // Mark damaged ship part
            computerSeaBattleCells[rowNumber][columnNumber].setStyle("-fx-background-color: red; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: black; -fx-pref-width: 42px; -fx-pref-height: 40px;");
            // Disable button
            computerSeaBattleCells[rowNumber][columnNumber].setDisable(true);
            System.out.println("Computer SeaBattle cell coordinates: rowNumber = " + rowNumber + ", columnNumber = " + columnNumber);

            updateShipInfo(true, rowNumber, columnNumber);

            // Update score info when ship is hit by player
            playerScore++;
            playerScoreLabel.setText(playerScore + "");
            System.out.println("Marked damaged ship part (player)!");
            // End game if all ships were destroyed
            if(playerScore == 20)
                endGame();
        }
        else
        {
            // Mark empty field
            // Add 'X' to button
            computerSeaBattleCells[rowNumber][columnNumber].setText("X");
            // Disable button
            computerSeaBattleCells[rowNumber][columnNumber].setDisable(true);
            System.out.println("Empty computer SeaBattle cell coordinates: rowNumber = " + rowNumber + ", columnNumber = " + columnNumber);
            System.out.println("Marked empty field (player)!");
        }

        // Computer's turn
        // Generate random row and column
        Random random = new Random();
        int computerRowNumber = random.nextInt(9) + 1;
        int computerColumnNumber = random.nextInt(9) + 1;

        if(!playerGridPane.isDisabled())
        {
            if(playerSeaBattleCells[computerRowNumber][computerColumnNumber].isDisabled())
            {
                // Get first enabled SeaBattle cell coordinates
                for(int i = 0; i < playerSeaBattleCells.length; i++)
                {
                    for(int j = 0; j < playerSeaBattleCells.length; j++)
                    {
                        if(!playerSeaBattleCells[i][j].isDisabled())
                        {
                            computerRowNumber = i;
                            computerColumnNumber = j;
                        }
                    }
                }
            }
        }

        shipIsDamagedByComputer = checkIfComputerDamagedShip(computerRowNumber, computerColumnNumber);

        if(shipIsDamagedByComputer)
        {
            // Mark damaged ship part
            playerSeaBattleCells[computerRowNumber][computerColumnNumber].setStyle("-fx-background-color: red; -fx-background-radius: 0; -fx-border-width: 1; -fx-border-color: black; -fx-pref-width: 42px; -fx-pref-height: 40px;");
            // Disable button
            playerSeaBattleCells[computerRowNumber][computerColumnNumber].setDisable(true);
            System.out.println("Player SeaBattle cell coordinates: rowNumber = " + computerRowNumber + ", columnNumber = " + computerColumnNumber);

            updateShipInfo(false, computerRowNumber, computerColumnNumber);

            // Update score info when ship is hit by computer
            computerScore++;
            computerScoreLabel.setText(computerScore + "");
            System.out.println("Marked damaged ship part (computer)!");
            // End game if all ships were destroyed
            if(computerScore == 20)
                endGame();
        }
        else
        {
            // Mark empty field
            // Add 'X' to button
            playerSeaBattleCells[computerRowNumber][computerColumnNumber].setText("X");
            // Disable button
            playerSeaBattleCells[computerRowNumber][computerColumnNumber].setDisable(true);
            System.out.println("Empty player SeaBattle cell coordinates: rowNumber = " + computerRowNumber + ", columnNumber = " + computerColumnNumber);
            System.out.println("Marked empty field (computer)!");
        }
    }
    // This method checks if player damaged ship
    private boolean checkIfPlayerDamagedShip(int rowNumber, int columnNumber)
    {
        // Check if 4-fields ship has been damaged
        for(Ship ship : computerShipWithFourFields)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                    return true;
            }
        }
        // Check if 3-fields ship has been damaged
        for(Ship ship : computerShipsWithThreeFields)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                    return true;
            }
        }
        // Check if 2-fields ship has been damaged
        for(Ship ship : computerShipsWithTwoFields)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                    return true;
            }
        }
        // Check if 1-field ship has been damaged
        for(Ship ship : computerShipsWithOneField)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                    return true;
            }
        }

        return false;
    }
    // This method checks if computer damaged ship
    private boolean checkIfComputerDamagedShip(int rowNumber, int columnNumber)
    {
        // Check if 4-fields ship has been damaged
        for(Ship ship : playerShipWithFourFields)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                {
                    System.out.println("4-fields ship coordinates: rowNumber = " + shipCoordinates.getRow() + ", columnNumber = " + shipCoordinates.getColumn());
                    return true;
                }
            }
        }
        // Check if 3-fields ship has been damaged
        for(Ship ship : playerShipsWithThreeFields)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                {
                    System.out.println("3-fields ship coordinates: rowNumber = " + shipCoordinates.getRow() + ", columnNumber = " + shipCoordinates.getColumn());
                    return true;
                }
            }
        }
        // Check if 2-fields ship has been damaged
        for(Ship ship : playerShipsWithTwoFields)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                {
                    System.out.println("2-fields ship coordinates: rowNumber = " + shipCoordinates.getRow() + ", columnNumber = " + shipCoordinates.getColumn());
                    return true;
                }
            }
        }
        // Check if 1-field ship has been damaged
        for(Ship ship : playerShipsWithOneField)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                {
                    System.out.println("1-field ship coordinates: rowNumber = " + shipCoordinates.getRow() + ", columnNumber = " + shipCoordinates.getColumn());
                    return true;
                }
            }
        }
        return false;
    }
    // This method updates labels that contain ship info
    private void updateShipInfo(boolean updatePlayerShipInfo, int rowNumber, int columnNumber)
    {
        // Add all ships to list
        List<Ship> allShips = new ArrayList<>();

        if(updatePlayerShipInfo)
        {
            allShips.addAll(Arrays.asList(computerShipWithFourFields));
            allShips.addAll(Arrays.asList(computerShipsWithThreeFields));
            allShips.addAll(Arrays.asList(computerShipsWithTwoFields));
            allShips.addAll(Arrays.asList(computerShipsWithOneField));
        }
        else
        {
            allShips.addAll(Arrays.asList(playerShipWithFourFields));
            allShips.addAll(Arrays.asList(playerShipsWithThreeFields));
            allShips.addAll(Arrays.asList(playerShipsWithTwoFields));
            allShips.addAll(Arrays.asList(playerShipsWithOneField));
        }

        // Update proper ship info label
        for(Ship ship : allShips)
        {
            for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
            {
                if(shipCoordinates.getRow() == rowNumber && shipCoordinates.getColumn() == columnNumber)
                {
                    // Get number of destroyed ship parts
                    int numberOfDestroyedShipParts = 1;
                    for(int i = ship.getShipLength(); i >= 0; i--)
                    {
                        System.out.println(ship.getShipInfo().getText().substring(i));
                        if(ship.getShipInfo().getText().substring(i).contains("X"))
                            numberOfDestroyedShipParts++;
                    }
                    System.out.println("Number of destroyed ship parts = " + numberOfDestroyedShipParts);
                    // Prepare ship info
                    StringBuilder shipInfo = new StringBuilder();
                    for(int i = 0; i < ship.getShipLength(); i++)
                    {
                        // Add destroyed ship parts to ship info
                        if(i < numberOfDestroyedShipParts)
                            shipInfo.append("X");
                        else
                            shipInfo.append("-");
                    }

                    // Update label with generated ship info
                    if(!updatePlayerShipInfo)
                    {
                        ship.getShipInfo().setText(shipInfo.toString());
                        // Disable fields next to destroyed ship
                        if(numberOfDestroyedShipParts == ship.getShipLength())
                            markFieldsNextToDestroyedShip(true, ship);
                    }
                    else
                    {
                        // Update label
                        ship.getShipInfo().setText(shipInfo.toString());
                        // Show computer ship info when ship is destroyed
                        if(numberOfDestroyedShipParts == ship.getShipLength())
                        {
                            ship.getShipInfo().setVisible(true);
                            // Disable fields next to destroyed ship
                            markFieldsNextToDestroyedShip(false, ship);
                        }
                    }
                }
            }
        }
    }
    // This method marks fields next to destroyed ship
    private void markFieldsNextToDestroyedShip(boolean updatePlayerSeaBattleCells, Ship ship)
    {
        // Use proper array
        SeaBattleCell[][] seaBattleCells;
        if(updatePlayerSeaBattleCells)
            seaBattleCells = playerSeaBattleCells;
        else
            seaBattleCells = computerSeaBattleCells;

        for(ShipCoordinates shipCoordinates : ship.getShipCoordinates())
        {
            for(int i = 0; i < seaBattleCells.length; i++)
            {
                for(int j = 0; j < seaBattleCells.length; j++)
                {
                    switch(ship.getOrientation())
                    {
                        case "horizontal":
                            // Mark proper fields as empty
                            if(i == shipCoordinates.getRow() && j == shipCoordinates.getColumn())
                            {

                                // Fields above ship
                                if((shipCoordinates.getRow() > 0 && shipCoordinates.getRow() < 10) && (shipCoordinates.getColumn() > 0 && shipCoordinates.getColumn() < 10))
                                {
                                    seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn()].setText("X");
                                    seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn()].setDisable(true);
                                }
                                // Fields below ship
                                if((shipCoordinates.getRow() > 0 && shipCoordinates.getRow() < 9) && (shipCoordinates.getColumn() > 0 && shipCoordinates.getColumn() < 10))
                                {
                                    seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn()].setText("X");
                                    seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn()].setDisable(true);
                                }

                                // Fields on the left side of ship
                                if(shipCoordinates.getRow() > 0)
                                {
                                    int tempColumnNumber = (shipCoordinates.getColumn() - 1 > 0) ? shipCoordinates.getColumn() - 1 : 0;
                                    int tempRowNumber = (shipCoordinates.getRow() + 1 < 10) ? shipCoordinates.getRow() + 1 : 9;

                                    if(shipCoordinates.getColumn() - 1 >= 0)
                                    {
                                        // Upper part
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() - 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() - 1].setDisable(true);

                                        // Middle part
                                        seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() - 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() - 1].setDisable(true);
                                    }
                                    if(shipCoordinates.getColumn() + 1 < 10)
                                    {
                                        // Upper part
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() + 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() + 1].setDisable(true);

                                        // Middle part
                                        seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() + 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() + 1].setDisable(true);
                                    }

                                    // Bottom part
                                    seaBattleCells[tempRowNumber][tempColumnNumber].setText("X");
                                    seaBattleCells[tempRowNumber][tempColumnNumber].setDisable(true);
                                }
                                // Fields on the right of ship
                                if(shipCoordinates.getRow() < 9)
                                {
                                    int tempColumnNumber = (shipCoordinates.getColumn() + 1 < 10) ? shipCoordinates.getColumn() + 1 : shipCoordinates.getColumn();
                                    int tempRowNumber = (shipCoordinates.getRow() - 1 > 0) ? shipCoordinates.getRow() - 1 : 0;

                                    // Upper part
                                    seaBattleCells[tempRowNumber][tempColumnNumber].setText("X");
                                    seaBattleCells[tempRowNumber][tempColumnNumber].setDisable(true);
                                    // Middle part
                                    seaBattleCells[shipCoordinates.getRow()][tempColumnNumber].setText("X");
                                    seaBattleCells[shipCoordinates.getRow()][tempColumnNumber].setDisable(true);
                                    // Bottom part
                                    seaBattleCells[shipCoordinates.getRow() + 1][tempColumnNumber].setText("X");
                                    seaBattleCells[shipCoordinates.getRow() + 1][tempColumnNumber].setDisable(true);
                                }
                            }
                            break;
                        case "vertical":
                            // Mark proper fields as empty
                            if(i == shipCoordinates.getRow() && j == shipCoordinates.getColumn())
                            {
                                // Fields above ship
                                if(shipCoordinates.getRow() > 0)
                                {
                                    // Left side
                                    if(shipCoordinates.getColumn() - 1 >= 0)
                                    {
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() - 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() - 1].setDisable(true);

                                        seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() - 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() - 1].setDisable(true);
                                    }

                                    seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn()].setText("X");
                                    seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn()].setDisable(true);

                                    // Right side
                                    if(shipCoordinates.getColumn() + 1 < 10)
                                    {
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() + 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow() - 1][shipCoordinates.getColumn() + 1].setDisable(true);
                                    }
                                }
                                // Fields below ship
                                if(shipCoordinates.getRow() < 9)
                                {
                                    // Left side
                                    if(shipCoordinates.getColumn() - 1 >= 0)
                                    {
                                        seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn() - 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn() - 1].setDisable(true);
                                    }

                                    seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn()].setText("X");
                                    seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn()].setDisable(true);

                                    // Right side
                                    if(shipCoordinates.getColumn() + 1 < 10)
                                    {
                                        seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn() + 1].setText("X");
                                        seaBattleCells[shipCoordinates.getRow() + 1][shipCoordinates.getColumn() + 1].setDisable(true);
                                    }
                                }
                                // Fields on the left side of ship
                                if((shipCoordinates.getRow() > 0 && shipCoordinates.getRow() < 10) && (shipCoordinates.getColumn() > 1 && shipCoordinates.getColumn() < 10))
                                {
                                    seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() - 1].setText("X");
                                    seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() - 1].setDisable(true);
                                }
                                // Fields on the right side of ship
                                if((shipCoordinates.getRow() > 0 && shipCoordinates.getRow() < 10) && (shipCoordinates.getColumn() > 0 && shipCoordinates.getColumn() < 9))
                                {
                                    seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() + 1].setText("X");
                                    seaBattleCells[shipCoordinates.getRow()][shipCoordinates.getColumn() + 1].setDisable(true);
                                }
                            }
                            break;
                    }
                }
            }
        }
    }
}
