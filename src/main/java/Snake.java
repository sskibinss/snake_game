import java.util.ArrayList;

/**
 * Snake class
 */
public class Snake {
    // Direction of the snake
    private SnakeDirection direction;
    // Whether the snake is alive or not.
    private boolean isAlive;
    // A list of snake pieces.
    private ArrayList<SnakeSection> sections;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    /**
     * The method moves the snake one move at a time.
     * The direction of movement is specified by the direction variable.
     */
    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**
     * The method moves the snake to the next cage
     * Cell coordinates are set relative to the current head using variables (dx, dy).
     */
    private void move(int dx, int dy) {
        // We're creating a new head - a new "piece of snake".
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        // Checking to see if the head's out of the room
        checkBorders(head);
        if (!isAlive) return;

        // Checking to see if the snake crosses itself
        checkBody(head);
        if (!isAlive) return;

        // Let's see if the mouse was eaten by a snake.
        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) // eaten
        {
            sections.add(0, head);                  // Add new head
            Room.game.eatMouse();                   // We don't remove the tail, but we're creating a new mouse.
        } else // Just moving
        {
            sections.add(0, head);                  // Add new head
            sections.remove(sections.size() - 1);   // Removed the last element from the tail
        }
    }

    /**
     * The method checks to see if the new head is within the room
     */
    private void checkBorders(SnakeSection head) {
        if ((head.getX() < 0 || head.getX() >= Room.game.getWidth()) || head.getY() < 0 || head.getY() >= Room.game.getHeight()) {
            isAlive = false;
        }
    }

    /**
     * The method checks to see if the head matches any part of the snake's body.
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
