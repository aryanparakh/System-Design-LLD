public class Button {

    ButtonType type;

    public Button(ButtonType type) {
        this.type = type;
    }

    public void press() {
        System.out.println(type + " button pressed");
    }
}
