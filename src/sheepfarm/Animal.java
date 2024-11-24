package sheepfarm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Animal extends Thread {
    protected Farm farm;

    protected Point location;

    protected Random r;

    private final AtomicBoolean running = new AtomicBoolean(true);

    public Animal(Farm farm, Point location, Random r) {
        this.farm = farm;
        this.location = location;
        this.r = r;
        this.start();
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isRunning() {
        synchronized (running) {
            return running.get();
        }
    }

    @Override
    public void run() {
        while (isRunning()) {
            move();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public void dispose() {
        synchronized (running){
            running.set(false);
        }
    }

    protected abstract void move();

    protected void moveToRandomAvailablePosition(ArrayList<Point> validPositions){
        while (!validPositions.isEmpty()) {
            int index = r.nextInt(0, validPositions.size());
            Point nextPos = validPositions.get(index);
            validPositions.remove(index);
            if (farm.tryMove(location, nextPos)) {
                break;
            }
        }
    }
}
