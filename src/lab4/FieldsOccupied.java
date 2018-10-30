package lab4;

public class FieldsOccupied
{
    private String orientation;
    private int rowNumber;
    private int columnNumber;
    private int lastPosition;

    public FieldsOccupied(String orientation, int rowNumber, int columnNumber, int lastPosition)
    {
        this.orientation = orientation;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.lastPosition = lastPosition;
    }
    // Getters
    public String getOrientation() {
        return orientation;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    @Override
    public String toString() {
        return "Fields occupied - orientation: " + orientation + ", rowNumber: " + rowNumber + ", columnNumber: " + columnNumber + ", lastPosition: " + lastPosition;
    }
}
