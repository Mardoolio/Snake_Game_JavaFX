import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Apple extends Rectangle {

    public Apple(){
        this(30, 30, Color.RED);
    }

    private Apple(double width, double height, Color color) {
        super(width, height, color);
        int initPositionX = (int) ((Math.random() * (500 - 50)) + 50);
        int initPositionY = (int) ((Math.random() * (500 - 50)) + 50);
        setTranslateX(initPositionX);
        setTranslateY(initPositionY);
    }

    public void setEaten() {
        setTranslateX((int) ((Math.random() * (650 - 10)) + 10));
        setTranslateY((int) ((Math.random() * (650 - 10)) + 10));
    }
}
