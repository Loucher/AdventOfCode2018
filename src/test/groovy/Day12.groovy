import org.junit.Test

class Day12 {

    @Test
    void part1() {
        String initialState = '..##.#######...##.###...#..#.#.#..#.##.#.##....####..........#..#.######..####.#.#..###.##..##..#..#'
        String generation = '.'.multiply(10) + initialState + '.'.multiply(10)
        List<String> rules = new ArrayList<>()
        getClass().getResource('day12.txt').readLines().each {
            String[] items = it.split(" => ")
            if (items[1] == '#') {
                String pattern = items[0].replaceAll(/\./, "\\\\.")
                rules.add(pattern)
            }
        }
        for (int i = 0; i < 100; i++) {
            if (i == 20) {
                long sum = 0
                for (int j = 0; j < generation.length(); j++) {
                    if (generation.charAt(j) == '#' as char) {
                        sum += j - 10
                    }
                }
                println "part1 $sum"
            }
            generation = advance(generation, rules)
            generation += '.'
        }
        long sum = 0
        for (int i = 0; i < generation.length(); i++) {
            if (generation.charAt(i) == '#' as char) {
                sum += i - 10 + 50000000000 - 100
            }
        }
        println "part2 $sum"
    }


    String advance(String originalState, List rules) {
        StringBuilder newState = new StringBuilder('.' * originalState.size())
        rules.each {
            def finder = (originalState =~ /$it/)
            int index = 0
            while (finder.find(index)) {
                index = finder.start() + 1
                newState.setCharAt(index + 1, '#' as char)
            }
        }
        return newState.toString()
    }


}
