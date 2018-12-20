import org.junit.Test

import static junit.framework.Assert.assertEquals

class Day11 {

    @Test
    void part1() {
        println findLargest(300, 9435, 3)
    }

    @Test
    void part2() {
        def tmp = (1..300).parallelStream().map {
            [squareSize: it,
             powerLevel: findLargest(300, 9435, it)
            ]
        }.max(Comparator.comparing { it.powerLevel.powerLevel }).get()
        println tmp
    }


    @Test
    void tesPowerLevels() {
        assertEquals(4, getPowerLevel(3, 5, 8))
        assertEquals(-5, getPowerLevel(122, 79, 57))
        assertEquals(0, getPowerLevel(217, 196, 39))
        assertEquals(4, getPowerLevel(101, 153, 71))
    }

    @Test
    void testGrid() {
        assertEquals([x: 33, y: 45], findLargest(300, 18, 3))
        assertEquals([x: 21, y: 61], findLargest(300, 42, 3))
    }


    static def findLargest(int size, int serialNumber, int squareSize) {
        int[][] totalPowerGrid = getTotalPowerGrid(generateGrid(size, serialNumber), squareSize)
        def length = totalPowerGrid.length
        int largest = Integer.MIN_VALUE
        Integer posX = null
        Integer posY = null
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < length; y++) {
                if (totalPowerGrid[x][y] > largest) {
                    largest = totalPowerGrid[x][y]
                    posX = x
                    posY = y
                }
            }
        }
        return [
                x         : posX,
                y         : posY,
                powerLevel: largest
        ]
    }

    static int[][] generateGrid(int size, int serialNumber) {
        int[][] grid = new int[size][size]
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = getPowerLevel(x, y, serialNumber)
            }
        }
        return grid
    }

    static int[][] getTotalPowerGrid(int[][] grid, int squareSize) {
        int size = grid.length - squareSize
        int[][] totalPower = new int[size][size]
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                totalPower[x][y] = getTotalPower(x, y, grid, squareSize)

            }
        }
        return totalPower
    }

    static int getTotalPower(int x, int y, int[][] grid, int squareSize) {
        int power = 0
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                power += grid[x + i][y + j]
            }
        }
        return power
    }


    private static int getPowerLevel(int x, int y, int serialNumber) {
        int rackId = x + 10
        int powerLevel = rackId * y
        powerLevel += serialNumber
        powerLevel *= rackId
        String digits = String.format("%03d", powerLevel)
        return String.valueOf(digits.charAt(digits.length() - 3)).toInteger() - 5
    }

}
