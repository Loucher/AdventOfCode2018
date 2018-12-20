import org.junit.Test

class Day1 {

    @Test
    void part1() {
        println getClass().getResource('day1.txt').readLines()*.toInteger().sum()
    }

    @Test
    void part2() {
        int[] changes = getClass().getResource('day1.txt').readLines()*.toInteger()
        List<Integer> frequencies = []
        int frequency = 0
        while (true) {
            for (int change : changes) {
                frequency += change
                if (frequencies.contains(frequency)) {
                    println frequency
                    return
                } else {
                    frequencies.add frequency
                }
            }
        }
    }

}
