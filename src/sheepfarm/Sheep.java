package sheepfarm;

import utils.Sector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Sheep extends Animal {
    public Sheep(Farm farm, Point location, Random r) {
        super(farm, location, r);

    }

    @Override
    protected void Move() {
        synchronized (farm) {
            // Based on dog positions, the sheep might want to move left (-1), right (1), or random (0)
            int preferenceX = 0;
            // Based on dog positions, the sheep might want to move down (-1), up (1), or random (0)
            int preferenceY = 0;

            boolean dogLeft = checkForDogHorizontal(-1);
            boolean dogRight = checkForDogHorizontal(1);
            boolean dogUp = checkForDogVertical(1);
            boolean dogDown = checkForDogVertical(-1);

            if (dogLeft && !dogRight) {
                // If there are dogs on the left, and there's no dog on the right, the sheep wants to go right
                preferenceX = 1;
            }

            if (dogRight && !dogLeft) {
                // If there are dogs on the right, and there's no dog on the left, the sheep wants to go left
                preferenceX = -1;
            }

            if (dogUp && !dogDown) {
                // If there are dogs up, and there's no dog down, the sheep wants to go down
                preferenceY = -1;
            }

            if (dogDown && !dogUp) {
                // If there are dogs down, and there's no dog up, the sheep wants to go up
                preferenceY = 1;
            }

            // Get available positions satisfying preferences
            ArrayList<Point> validPositions = getAvailablePositions(preferenceX, preferenceY);

            if (!validPositions.isEmpty()) {
                Point nextPos = validPositions.get(r.nextInt(0, validPositions.size()));
                farm.move(location, nextPos);
            }
        }
    }

    @Override
    public String toString() {
        return "S";
    }

    private boolean checkForDogHorizontal(int dir) {
        return checkIfDog(new Point(location.x + dir, location.y + 1)) ||
                checkIfDog(new Point(location.x + dir, location.y + 0)) ||
                checkIfDog(new Point(location.x + dir, location.y + -1));
    }

    private boolean checkForDogVertical(int dir) {
        return checkIfDog(new Point(location.x + 1, location.y + dir)) ||
                checkIfDog(new Point(location.x + 0, location.y + dir)) ||
                checkIfDog(new Point(location.x + -1, location.y + dir));
    }

    private boolean checkIfDog(Point p) {
        return !farm.isOutOfMap(p) && farm.getFieldAt(p.x, p.y) instanceof Dog;
    }

    private ArrayList<Point> getAvailablePositions(int preferenceX, int preferenceY) {
        ArrayList<Point> validPositions = new ArrayList<>();
        for (int x = location.x + (preferenceX == 1 ? 1 : -1); x <= location.x + (preferenceX == -1 ? -1 : 1); x++) {
            for (int y = location.y + (preferenceY == 1 ? 1 : -1); y <= location.y + (preferenceY == -1 ? -1 : 1); y++) {
                Point p = new Point(x, y);
                if (!(x == location.x && y == location.y) && checkNextPos(p)) {
                    validPositions.add(p);
                }

            }
        }
        return validPositions;
    }

    private boolean checkNextPos(Point p) {
        if (farm.isOutOfMap(p)) {
            return false;
        }

        if (!(farm.getFieldAt(p.x, p.y) instanceof Empty)) {
            return false;
        }

        return true;
    }
}
