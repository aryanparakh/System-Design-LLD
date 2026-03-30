import java.util.List;

public class Theatre {
    int id;
    String name;
    List<Screen> screens;

    public Theatre(int id, String name, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.screens = screens;
    }
}
