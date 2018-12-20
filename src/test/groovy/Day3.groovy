import org.junit.Test

import java.util.stream.Collectors

class Day3 {


    @Test
    void part1() {
        int[][] fabric = new int[1000][1000]
        List<int[]> cuts = getClass().getResource('day3.txt').readLines().stream().map {
            it.findAll(/\d+/)*.toInteger()
        }.collect(Collectors.toList())
        cuts.forEach {
            for (int x = it[1]; x < it[1] + it[3]; x++) {
                for (int y = it[2]; y < it[2] + it[4]; y++) {
                    fabric[x][y]++
                }
            }
        }
        int size = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (fabric[x][y] > 1) {
                    size++
                }
            }
        }
        println size
    }

    @Test
    void part2() {
        Set<Integer> cleanCuts = new HashSet<>();
        int[][] fabric = new int[1000][1000]
        List<int[]> cuts = getClass().getResource('day3.txt').readLines().stream().map {
            it.findAll(/\d+/)*.toInteger()
        }.collect(Collectors.toList())
        cuts.forEach {
            int id = it[0]
            boolean isCleanCut = true
            for (int x = it[1]; x < it[1] + it[3]; x++) {
                for (int y = it[2]; y < it[2] + it[4]; y++) {
                    if (fabric[x][y] != 0) {
                        isCleanCut = false
                        cleanCuts.remove(new Integer(fabric[x][y]))
                    } else {
                        fabric[x][y] = id
                    }
                }
            }
            if (isCleanCut) {
                cleanCuts.add(id)
            }
        }
        println cleanCuts
    }


}
