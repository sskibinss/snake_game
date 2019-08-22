import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Main class of program.
 */
public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
        game = this;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     * Main cycle of program.
     */
    public void run() {
        //Creating and starting keyboardObserver.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //While snake is alive
        while (snake.isAlive()) {
            //"Observer" contains events ?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //If equal 'q' - close the game.
                if (event.getKeyChar() == 'q') return;

                //If "arrow to the left" - move the figure to the left
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //If "arrow to the right" - move the figure to the right
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //If "arrow to the up" - move the figure to the up
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //If "arrow to the down" - move the figure to the down
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   //Moving snake
            print();        //Current game status
            sleep();        //Pause between moves
        }

        //Print "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * Display the current state of the game
     */
    public void print() {
        //Create an array where we will "draw" the current state of the game
        int[][] matrix = new int[height][width];

        //Drawing all the pieces of snake
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }

        //Draw the head of the snake (4 - if the snake is dead)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;

        //Drawing a mouse
        matrix[mouse.getY()][mouse.getX()] = 3;

        //Let's get all this on the screen.
        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * The method is called when the mouse is eaten
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Creates a new mouse.
     */
    public void createMouse() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        mouse = new Mouse(x, y);
    }


    public static Room game;

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }

    private int initialDelay = 520;
    private int delayStep = 20;

    /**
     * The program pauses, the length of which depends on the length of the snake.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
