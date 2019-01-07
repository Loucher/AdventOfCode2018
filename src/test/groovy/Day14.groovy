import groovy.transform.CompileStatic
import org.junit.Test

import static junit.framework.Assert.assertEquals

@CompileStatic
class Day14 {

    static String part1Score(int requiredNumberOfRecipes) {
        StringBuilder buffer = new StringBuilder("37")
        int indexA = 0
        int indexB = 1

        while (buffer.size() < requiredNumberOfRecipes + 10) {
            int recipeA = Character.getNumericValue(buffer.charAt(indexA))
            int recipeB = Character.getNumericValue(buffer.charAt(indexB))
            buffer.append(recipeA + recipeB)
            indexA = (indexA + recipeA + 1) % buffer.size()
            indexB = (indexB + recipeB + 1) % buffer.size()
        }
        return buffer.toString().substring(requiredNumberOfRecipes, requiredNumberOfRecipes + 10)
    }


    static int part2Score(String input) {
        def inputArray = input.collect { it as short }
        List recipes = new ArrayList<Short>()
        recipes.addAll([3 as short, 7 as short])
        short indexA = 0
        short indexB = 1
        while (true) {
            short recipeA = recipes.get(indexA)
            short recipeB = recipes.get(indexB)
            short newRecipe = recipeA + recipeB as short
            short tens = newRecipe / 10 as short
            if (tens != 0 as short) {
                recipes.add(tens)
            }
            recipes.add(newRecipe % 10 as short)
            indexA = (indexA + recipeA + 1) % recipes.size() as short
            indexB = (indexB + recipeB + 1) % recipes.size() as short
            if (recipes.size() >= inputArray.size()) {
                def sublist = recipes[recipes.size() - inputArray.size()..recipes.size() - 1]
                if (sublist == inputArray) {
                    return recipes.size() - inputArray.size()
                }
                sublist = recipes[recipes.size() - inputArray.size() - 1..recipes.size() - 2]
                if (sublist == inputArray) {
                    return recipes.size() - inputArray.size() - 1
                }
            }
        }
    }

    @Test
    void examples() {
        assertEquals("5158916779", part1Score(9))
        assertEquals("0124515891", part1Score(5))
        assertEquals("9251071085", part1Score(18))
        assertEquals("5941429882", part1Score(2018))
    }


    @Test
    void examples2() {
        assertEquals(9, part2Score("51589"))
        assertEquals(5, part2Score("01245"))
        assertEquals(18, part2Score("92510"))
        assertEquals(2018, part2Score("59414"))
    }


    @Test
    void solutions() {
//        println "Part1: ${part1Score(939601)}"
        println "Part2: ${part2Score("939601")}"
    }
}
