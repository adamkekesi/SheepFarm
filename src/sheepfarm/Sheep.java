package sheepfarm;

import java.awt.*;
import java.util.Random;

public class Sheep extends Animal {
    public Sheep(Farm farm, Point location, Random r) {
        super(farm, location, r);

    }

    @Override
    protected void Move() {
        int deltaX = 0;
        int deltaY = 0;

        if (checkForDogHorizontal(1)) {

        }
        //farm.getFieldAt();
    }

    @Override
    public String toString() {
        return "S";
    }

    private boolean checkForDogHorizontal(int dir) {
        return farm.getFieldAt(dir, 1) instanceof Dog ||
                farm.getFieldAt(dir, 0) instanceof Dog ||
                farm.getFieldAt(dir, -1) instanceof Dog;
    }

    private boolean checkForDogVertical(int dir) {
        return farm.getFieldAt( 1, dir) instanceof Dog ||
                farm.getFieldAt( 0, dir) instanceof Dog ||
                farm.getFieldAt( -1, dir) instanceof Dog;
    }
}
