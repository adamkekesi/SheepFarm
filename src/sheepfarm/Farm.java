package sheepfarm;

import utils.Combinations;
import utils.Sector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Farm {

    private Object[][] fields;

    private Random r;

    private Sheep escapedSheep;

    public Farm(int rows, int cols, int numberOfDogs, int numberOfSheep) {
        if (rows % 3 != 2) {
            throw new IllegalArgumentException("The number of rows must be a number which is greater than a multiple of three by two.");
        }
        if (rows < 5) {
            throw new IllegalArgumentException("The number of rows must be greater than or equal to 5");
        }
        if (cols % 3 != 2) {
            throw new IllegalArgumentException("The number of columns must be a number which is greater than a multiple of three by two.");
        }
        if (cols < 5) {
            throw new IllegalArgumentException("The number of columns must be greater than or equal to 5");
        }
        if (numberOfDogs < 1) {
            throw new IllegalArgumentException("At least one dog should be spawned");
        }
        if (numberOfSheep < 1) {
            throw new IllegalArgumentException("At least one sheep should be spawned");
        }
        int areaOfSectors = ((rows - 2) / 3) * ((cols - 2) / 3);
        if (numberOfDogs > 9 * areaOfSectors) {
            throw new IllegalArgumentException("A maximum of " + 9 * areaOfSectors + " dogs can fit on the map");
        }
        if (numberOfSheep > areaOfSectors) {
            throw new IllegalArgumentException("A maximum of " + areaOfSectors + " sheep can fit on the map");
        }
        this.r = new Random();

        initMap(rows, cols);
        initWalls();
        initSheep(numberOfSheep);
        initDogs(numberOfDogs);
    }

    public Farm() {
        this(14, 14, 5, 10);
    }

    public void printGame() {
        ArrayList<String> lines = new ArrayList<>();
        synchronized (this) {
            for (int y = 0; y < getLengthY(); y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < getLengthX(); x++) {
                    sb.append(getFieldAt(x, y).toString());
                }
                lines.add(sb.toString());
            }
        }

        System.out.println("\\u001B[0;0H");
        for (String line :
                lines) {
            System.out.println(line);
        }
    }

    public void move(Point source, Point dest) {

        Object sourceField = getFieldAt(source.x, source.y);
        Object destField = getFieldAt(dest.x, dest.y);
        if (!(sourceField instanceof Animal)) {
            return;
        }
        if (!(destField instanceof Empty)) {
            return;
        }
        setFieldAt(source.x, source.y, new Empty());
        setFieldAt(dest.x, dest.y, sourceField);
        ((Animal) sourceField).setLocation(dest);
        if (sourceField instanceof Sheep && isEdge(dest)) {
            gameOver((Sheep) sourceField);
        }

    }

    public boolean isGameOver() {
        return escapedSheep != null;
    }

    private void gameOver(Sheep escapedSheep) {
        this.escapedSheep = escapedSheep;
        for (int x = 0; x < getLengthX(); x++) {
            for (int y = 0; y < getLengthY(); y++) {
                Object field = getFieldAt(x, y);
                if (field instanceof Animal) {
                    ((Animal) field).Dispose();
                }
            }
        }
    }

    private boolean isEdge(Point p) {
        return p.x == 0 || p.x == getLengthX() - 1 || p.y == 0 || p.y == getLengthY() - 1;
    }

    public Object getFieldAt(int x, int y) {

        return fields[y][x];

    }

    public boolean isOutOfMap(Point p) {
        return p.x < 0 || p.x >= getLengthX() || p.y < 0 || p.y >= getLengthY();
    }

    private void setFieldAt(int x, int y, Object value) {
        fields[y][x] = value;
    }

    public int getLengthX() {
        return fields[0].length;
    }

    public int getLengthY() {
        return fields.length;
    }

    private void initMap(int rows, int cols) {
        fields = new Object[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                setFieldAt(x, y, new Empty());
            }
        }
    }

    private void initWalls() {
        for (int x = 0; x < getLengthX(); x++) {
            setFieldAt(x, 0, new Wall());
        }
        setFieldAt(r.nextInt(1, getLengthX() - 1), 0, new Empty());

        for (int x = 0; x < getLengthX(); x++) {
            setFieldAt(x, getLengthY() - 1, new Wall());
        }
        setFieldAt(r.nextInt(1, getLengthX() - 1), getLengthY() - 1, new Empty());

        for (int y = 0; y < getLengthY(); y++) {
            setFieldAt(0, y, new Wall());
        }
        setFieldAt(0, r.nextInt(1, getLengthY() - 1), new Empty());

        for (int y = 0; y < getLengthY(); y++) {
            setFieldAt(getLengthX() - 1, y, new Wall());
        }
        setFieldAt(getLengthX() - 1, r.nextInt(1, getLengthY() - 1), new Empty());

    }

    private void initDogs(int numberOfDogs) {
        int areaOfSectors = ((fields.length - 2) / 3) * ((fields[0].length - 2) / 3);
        int numberOfDogSlots = 8 * areaOfSectors;
        ArrayList<Integer> dogLocations = Combinations.withoutRepetition(r, numberOfDogSlots, numberOfDogs);
        for (int i : dogLocations) {
            int sector = i / areaOfSectors;
            if (sector >= 4) {
                sector++;
            }
            Point p = Sector.mapSectorIndexToCoord(getLengthX(), getLengthY(), sector, i % areaOfSectors);
            setFieldAt(p.x, p.y, new Dog(this, p, r));
        }
    }

    private void initSheep(int numberOfSheep) {
        int areaOfSectors = ((fields.length - 2) / 3) * ((fields[0].length - 2) / 3);
        ArrayList<Integer> dogLocations = Combinations.withoutRepetition(r, areaOfSectors, numberOfSheep);
        for (int i : dogLocations) {
            Point p = Sector.mapSectorIndexToCoord(getLengthX(), getLengthY(), 4, i);
            setFieldAt(p.x, p.y, new Sheep(this, p, r));
        }
    }

    public Sheep getEscapedSheep() {
        return escapedSheep;
    }
}
