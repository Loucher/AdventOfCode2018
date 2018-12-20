import org.junit.Test

import java.util.stream.Collectors

class Day6 {

    @Test
    void part1() {
        int maxX = 0
        int maxY = 0
        def coordinates = getClass().getResource('day6.txt').readLines().stream().map {
            int[] pos = it.split(',')*.toInteger()
            if (maxX < pos[0]) {
                maxX = pos[0]
            }
            if (maxY < pos[1]) {
                maxY = pos[1]
            }
            return pos
        }.collect(Collectors.toList())
        int[] coordinateAreas = new int[coordinates.size()]
        boolean[] areaIsInfinite = new boolean[coordinates.size()]
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                int minDistance = Integer.MAX_VALUE
                int locationIndex = -1
                for (int i = 0; i < coordinates.size(); i++) {
                    def pos = coordinates[i]
                    int distance = getDistance(pos[0], pos[1], x, y)
                    if (distance < minDistance) {
                        minDistance = distance
                        locationIndex = i
                    } else if (distance == minDistance) {
                        locationIndex = -1 //location index -1 means there are multiple closest locations
                    }
                }
                if (locationIndex != -1) {
                    coordinateAreas[locationIndex]++
                    if (x == 0 || y == 0 || x == maxX || y == maxY) {
                        areaIsInfinite[locationIndex] = true
                    }
                }
            }
        }
        int area = 0
        for (int i = 0; i < coordinates.size(); i++) {
            if (!areaIsInfinite[i] && area < coordinateAreas[i]) {
                area = coordinateAreas[i]
            }
        }
        println area
    }

    @Test
    void part2() {
        int maxX = 0
        int maxY = 0
        def coordinates = getClass().getResource('day6.txt').readLines().stream().map {
            int[] pos = it.split(',')*.toInteger()
            if (maxX < pos[0]) {
                maxX = pos[0]
            }
            if (maxY < pos[1]) {
                maxY = pos[1]
            }
            return pos
        }.collect(Collectors.toList())
        int areaSize = 0
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                int totalDistance = 0
                for (int i = 0; i < coordinates.size(); i++) {
                    def pos = coordinates[i]
                    totalDistance += getDistance(pos[0], pos[1], x, y)
                }
                if (totalDistance < 10000) {
                    areaSize++
                }
            }
        }
        println areaSize
    }


    private static int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2)
    }

}
