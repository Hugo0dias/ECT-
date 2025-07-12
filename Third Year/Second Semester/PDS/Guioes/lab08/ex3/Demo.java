import java.awt.Color;

import startypes.*;

public class Demo {
    static int CANVAS_SIZE = 1200;
    static int STARS_TO_DRAW = 1000000;
    
    public static void main(String[] args) {
        Sky sky = new Sky();

        // https://astrobackyard.com/wp-content/uploads/2020/03/types-of-stars.jpg
        char[] starTypes = {'O', 'B', 'A', 'F', 'G', 'K', 'M'};
        char type;

		Runtime runtime = Runtime.getRuntime();
		long before = runtime.totalMemory() - runtime.freeMemory();

        for (int i = 0; i < STARS_TO_DRAW; i++) {
            type = starTypes[random(0, starTypes.length-1)];
            sky.placeStar(createStar(type));
        }
        sky.setSize(CANVAS_SIZE, CANVAS_SIZE);
        sky.setBackground(Color.BLACK);
        sky.setVisible(true);

        long after = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory: " + (after - before) / 1024 / 1024 + " MB");

    }

    private static Star createStar(char typeChar) {
        int x = random(0, CANVAS_SIZE);
        int y = random(0, CANVAS_SIZE);
        StarType type=null;
        switch (typeChar) {
            case 'O' : type = OStar.getInstance(); break;
            case 'A' : type = AStar.getInstance(); break;
            case 'B' : type = BStar.getInstance(); break;
            case 'F' : type = FStar.getInstance(); break;
            case 'G' : type = GStar.getInstance(); break;
            case 'K' : type = KStar.getInstance(); break;
            case 'M' : type = MStar.getInstance(); break;
        }
        Star star = new Star(x, y, type);
        return star;
    }

	private static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}