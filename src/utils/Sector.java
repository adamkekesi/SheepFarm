package utils;

import java.awt.*;

public class Sector {
    public static Point mapSectorIndexToCoord(int lengthX, int lengthY, int sector, int index) {
        int sectorLengthX = (lengthX - 2) / 3;

        Point sectorStart = determineSectorStart(lengthX, lengthY, sector);
        int deltaX = index % sectorLengthX;
        int deltaY = index / sectorLengthX;
        return new Point(sectorStart.x + deltaX, sectorStart.y + deltaY);
    }

    public static boolean isInsideSector(int lengthX, int lengthY, int sector, Point p) {
        int sectorLengthX = (lengthX - 2) / 3;
        int sectorLengthY = (lengthY - 2) / 3;
        Point sectorStart = determineSectorStart(lengthX, lengthY, sector);
        int deltaX = p.x - sectorStart.x;
        int deltaY = p.y - sectorStart.y;
        return deltaX >= 0 && deltaX < sectorLengthX && deltaY >= 0 && deltaY < sectorLengthY;
    }

    public static Point determineSectorStart(int lengthX, int lengthY, int sector) {
        int sectorLengthX = (lengthX - 2) / 3;
        int sectorLengthY = (lengthY - 2) / 3;
        int x = (sector % 3) * sectorLengthX + 1;
        int y = (sector / 3) * sectorLengthY + 1;
        return new Point(x, y);
    }
}

