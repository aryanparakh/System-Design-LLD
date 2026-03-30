public class Floor {

    int floorNumber;
    Button upButton;
    Button downButton;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.upButton = new Button(ButtonType.UP);
        this.downButton = new Button(ButtonType.DOWN);
    }
}
