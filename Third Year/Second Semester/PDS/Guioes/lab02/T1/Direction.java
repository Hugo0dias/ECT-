public enum Direction {
    UP("up"), 
    DOWN("down"), 
    LEFT("left"), 
    RIGHT("right"), 
    UPLEFT("upleft"), 
    UPRIGHT("upright"), 
    DOWNLEFT("downleft"), 
    DOWNRIGHT("downright");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public static int[] getCord(Direction direction) {
        int[] cords = {0, 0};
        int deltaCol = 0;
        int deltaRow = 0;

        switch (direction) {
            case RIGHT:
                deltaCol = 1;
                break;
            case LEFT:
                deltaCol = -1;
                break;
            case DOWN:
                deltaRow = 1;
                break;
            case UP:
                deltaRow = -1;
                break;
            case UPRIGHT:
                deltaRow = -1;
                deltaCol = 1;
                break;
            case UPLEFT:
                deltaRow = -1;
                deltaCol = -1;
                break;
            case DOWNRIGHT:
                deltaRow = 1;
                deltaCol = 1;
                break;
            case DOWNLEFT:
                deltaRow = 1;
                deltaCol = -1;
                break;
        }
        cords[0] = deltaRow;
        cords[1] = deltaCol;

        return cords;
    }

    public static Direction getDirection(int deltaLine, int deltaCol) {
        if (deltaLine == 0 && deltaCol == 0) {
            return null;
        }

        // Atribui a direção
        switch(deltaCol) {
            case -1: {
                switch(deltaLine) {
                    case -1: return Direction.UPLEFT;
                    case 0: return Direction.LEFT;
                    case 1: return Direction.DOWNLEFT;
                }
                break;
            }
            case 0: {
                switch(deltaLine) {
                    case -1: return Direction.UP;
                    case 1: return Direction.DOWN;
                }
                break;
            }
            case 1: {
                switch(deltaLine) {
                    case -1: return Direction.UPRIGHT;
                    case 0: return Direction.RIGHT;
                    case 1: return Direction.DOWNRIGHT;
                }
                break;
            }
        }
        return null;
    }
}