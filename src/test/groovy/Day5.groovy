import org.junit.Test

import java.util.stream.Collectors

class Day5 {

    @Test
    void part1() {
        react(getClass().getResource('day5.txt').getText())
    }

    @Test
    void part2() {
        String input = getClass().getResource('day5.txt').getText()
        def result = ('a'..'d').stream().map {
            react(input.replaceAll(/(?i)[$it]/, ""))
        }.collect(Collectors.toList())
        println result.min()
    }

    private static int react(String input) {
        int oldSize = 0
        while (oldSize != input.size()) {
            oldSize = input.size()
            input = input.replaceAll(/(?=(?i)(.)\1)(([a-z][A-Z])|([A-Z][a-z]))/, "")
        }
        return input.size()
    }

}
