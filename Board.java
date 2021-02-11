import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends Application implements KeyListener {

    private final Canvas board = new Canvas();
    private final GraphicsContext gc = board.getGraphicsContext2D();
    Pane root = new Pane();

    private int score = 0;

    private final Snake playerSnake = new Snake();
    private final Apple apple = new Apple();

    private final Text gameOverText = new Text();

    private String moveDirection = "right"; // starting moving direction

    private void setMoveDirection(String moveDirection) {
        this.moveDirection = moveDirection;
    }

    private final AnimationTimer timer = new AnimationTimer() {

        private long lastUpdate = 0 ;
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= 28_000_000) {
                lastUpdate = now ;
            }
            update();
        }
    };

    public void init() {
        //Loading before the scenes.
    }

    public void update(){
        if (playerSnake.isAlive()) {
            playerSnake.move(moveDirection);
        }
        checkForApple();
        //remove drawn squares moved to the garbage collector
        root.getChildren().removeAll(playerSnake.snakeBodyGarbageCollector);

        //clean up garbage-arrayList
        if (playerSnake.snakeBodyGarbageCollector.size()>=1){
            playerSnake.snakeBodyGarbageCollector.clear();
        }

        root.getChildren().removeAll(playerSnake.snakeBody);
        root.getChildren().addAll(playerSnake.snakeBody);

        root.getChildren().remove(apple);
        root.getChildren().add(apple);

        if (!playerSnake.isAlive()){
            root.getChildren().removeAll(playerSnake.snakeBody);
            apple.setTranslateX(1000); //move apple off-screen instead of removing from canvas
            gameOverText.setText("GAME OVER \n Score: " + score + "\n\n press Space to play again");
        }
    }

    public void start(Stage stage) {

        //region Window Properties
        stage.setTitle("Snake");
        stage.setWidth(700);
        stage.setHeight(700);
        stage.setResizable(false);
        //endregion

        board.setHeight(700);
        board.setWidth(700);

        //region draw background and border

        //Colour background
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, 800, 800);

        //Colour in Border
        gc.setFill(Color.RED);
        gc.fillRect(0,0,700, 700);
        gc.setFill(Color.BLACK);
        gc.fillRect(2,1,696,675);

        //endregion

        root.getChildren().add(board);
        root.getChildren().add(playerSnake.snakeHead);
        root.getChildren().add(apple);
        root.getChildren().add(gameOverText);

        Scene scene = new Scene(root);

        //Game Over Text:
        gameOverText.setFill(Color.WHITE);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        gameOverText.setX(55);
        gameOverText.setY(300);

        //Key Pressed for movement
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case UP -> { if(!moveDirection.equals("down"))setMoveDirection("up"); }
                case DOWN -> { if(!moveDirection.equals("up"))setMoveDirection("down"); }
                case LEFT -> { if(!moveDirection.equals("right"))setMoveDirection("left"); }
                case RIGHT -> { if(!moveDirection.equals("left"))setMoveDirection("right"); }
                case SPACE -> {
                    if (!playerSnake.isAlive()) {
                        resetGame();
                    }
                }
            }
        });

        timer.start();

        stage.setScene(scene);
        stage.show();
    }

    private void resetGame() {
        gameOverText.setText("");
        apple.setEaten();
        score=0;
        setMoveDirection("right");
        playerSnake.reset();
    }

    public void stop() {
        //Runs after the window is closed
        System.out.println("Goodbye");

        //for debugging to make sure that every deleted body element has been cleared from the garbage collector
        for (Body body : playerSnake.snakeBodyGarbageCollector){
            System.out.println(body);
        }
    }

    private void checkForApple(){
        if (playerSnake.snakeBody.get(playerSnake.snakeBody.size()-1).getBoundsInParent().intersects(apple.getBoundsInParent())){
            apple.setEaten();
            playerSnake.increaseSpeed(0.1);
            playerSnake.grow();
            score++;
        }
    }

    //region KeyListener Unused methods
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    //endregion
}
