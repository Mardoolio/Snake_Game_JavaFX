import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Body extends Rectangle {

    public Body(int x, int y, int width, int height, Color color) {
        super(width, height, color);

        setTranslateX(x);
        setTranslateY(y);
    }
}
