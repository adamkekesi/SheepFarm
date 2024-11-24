package sheepfarm;

import utils.Sector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Dog extends Animal {
    public Dog(Farm farm, Point location, Random r) {
        super(farm, location, r);
    }


    @Override
    public String toString() {
        return "1";
    }

    @Override
    protected void move() {
        farm.lockAreaForRead(location);
        ArrayList<Point> validPositions = new ArrayList<>();
        for (int x = location.x - 1; x <= location.x + 1; x++) {
            for (int y = location.y - 1; y <= location.y + 1; y++) {
                Point p = new Point(x, y);
                if (!(x == location.x && y == location.y) && checkNextPos(p)) {
                    validPositions.add(p);
                }
            }
        }
        farm.unlockAreaForRead(location);

        if (validPositions.isEmpty()) {
            return;
        }

        moveToRandomAvailablePosition(validPositions);
    }

    private boolean checkNextPos(Point p) {
        if (p.x == 0 || p.y == 0 || p.x == farm.getLengthX() - 1 || p.y == farm.getLengthY() - 1) {
            return false;
        }

        if (farm.isOutOfMap(p)) {
            return false;
        }

        if (Sector.isInsideSector(farm.getLengthX(), farm.getLengthY(), 4, p)) {
            return false;
        }

        if (!(farm.getFieldAt(p.x, p.y) instanceof Empty)) {
            return false;
        }

        return true;
    }


}
