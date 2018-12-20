import org.junit.Test

import static org.testng.Assert.assertFalse
import static org.testng.Assert.assertTrue

class Day2 {

    @Test
    void part1() {
        int two = 0;
        int three = 0;
        getClass().getResource('day2.txt').readLines().each {
            boolean hasTwo = false
            boolean hasThree = false
            for (char c : it.toCharArray() as Set) {
                int count = it.count(c as String)
                if (count == 2) {
                    hasTwo = true
                } else if (count == 3) {
                    hasThree = true
                }
            }
            if (hasTwo) {
                two++
            }
            if (hasThree) {
                three++
            }
        }
        println two * three
    }

    @Test
    void part2() {
        List<String> lines = getClass().getResource('day2.txt').readLines();
        for (int i = 0; i < lines.size() - 1; i++) {
            String a = lines.get(i)
            for (int j = i + 1; j < lines.size(); j++) {
                String b = lines.get(j)
                if (correct(a, b)) {
                    printCommonChars(a, b)
                }
            }
        }

    }

    private static void printCommonChars(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                print a.charAt(i)
            }
        }
        println ""
    }

    private static boolean correct(String a, String b) {
        boolean matching = true;
        for (int k = 0; k < a.size(); k++) {
            if (a.charAt(k) != b.charAt(k)) {
                if (matching) {
                    matching = false;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    void testCorrect() {
        assertTrue correct("testing", "testing")
        assertTrue correct("testing", "teating")
        assertFalse correct("testing", "teatinf")
    }


}
