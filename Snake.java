import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public class Snake{

    private double speed = 3;
    private int maxLength = 30;
    private final int width = 30;
    private final int height = 30;

    //testing an idea for snake head-body collision
    private boolean isAlive = true;

    //List containing body elements to paint to screen
    LinkedList<Body> snakeBody = new LinkedList<>();
    //Garbage collector for board to remove in its update method.
    ArrayList<Body> snakeBodyGarbageCollector = new ArrayList<>();

    //Transparent "paint brush" controlled by player, which draws the snake body to the screen
    Body snakeHead = new Body(50, 50, width, height, Color.TRANSPARENT);

    enum Direction{
        UP, DOWN, LEFT, RIGHT
    }
    Direction movingDirection = Direction.RIGHT; //Default starting direction movement

    //Constructor
    public Snake() {
        snakeHead.setTranslateX(120);
        snakeHead.setTranslateY(120);
    }

    public void move(String direction){
        slither();
            switch (direction) {
                case "left" -> {
                    if(movingDirection==Direction.RIGHT) {
                        move("right");
                        break;
                    }
                    if(isAlive) {
                        snakeHead.setTranslateX(snakeHead.getTranslateX() - speed);
                    }
                    movingDirection=Direction.LEFT;
                }
                case "right" -> {
                    if(movingDirection==Direction.LEFT) {
                        move("left");
                        break;
                    }
                    if(isAlive) {
                        snakeHead.setTranslateX(snakeHead.getTranslateX() + speed);
                    }
                    movingDirection=Direction.RIGHT;
                }
                case "up" -> {
                    if(movingDirection==Direction.DOWN) {
                        move("down");
                        break;
                    }
                    if(isAlive) {
                        snakeHead.setTranslateY(snakeHead.getTranslateY() - speed);
                    }
                    movingDirection=Direction.UP;
                }
                case "down" -> {
                    if(movingDirection==Direction.UP) {
                        move("up");
                        break;
                    }
                    if(isAlive) {
                        snakeHead.setTranslateY(snakeHead.getTranslateY() + speed);
                    }
                    movingDirection=Direction.DOWN;
                }
            }
        checkCollision();
    }

    private void slither(){
        snakeBody.add(new Body((int)snakeHead.getTranslateX(), (int)snakeHead.getTranslateY(), width, height, Color.GREEN));

        if (snakeBody.size() > maxLength){
            snakeBodyGarbageCollector.add(snakeBody.getFirst());
            snakeBody.removeFirst();
        }
    }

    private void checkCollision() {
        if (snakeHead.getTranslateX() <= 5
            || snakeHead.getTranslateX() > 665
            || snakeHead.getTranslateY() <= 3
            || snakeHead.getTranslateY() > 644)
            isAlive=false;

        //Collision with body
        if (snakeBody.size()>=30) {
            for (int i = 0; i < snakeBody.size()-25; i++) {
                if (snakeBody.getLast().getBoundsInParent().intersects(snakeBody.get(i).getBoundsInParent())){
                    isAlive = false;
                }
            }
        }
    }
    public void increaseSpeed(double speedIncrease) {
        this.speed += speedIncrease;
    }
    public void grow() {
        this.maxLength += 1;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void reset(){
        snakeHead.setTranslateX(120);
        snakeHead.setTranslateY(120);
        snakeBody.clear();
        movingDirection=Direction.RIGHT;
        maxLength = 30;
        speed = 3;
        isAlive=true;
    }
}
