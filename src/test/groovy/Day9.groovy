import org.junit.Test

import static junit.framework.Assert.assertEquals

class Day9 {


    @Test
    void part1() {
        println getHighScore(427, 70723)
        println getHighScore(427, 7072300)
    }


    @Test
    void example() {
        assertEquals(32, getHighScore(9, 25))
        assertEquals(8317, getHighScore(10, 1618))
        assertEquals(146373, getHighScore(13, 7999))
        assertEquals(2764, getHighScore(17, 1104))
        assertEquals(54718, getHighScore(21, 6111))
        assertEquals(37305, getHighScore(30, 5807))
    }


    private static long getHighScore(int players, int lastMarble) {
        Marble current = Marble.createRoot(0)
        Marble root = current
        Map<Integer, Long> score = new HashMap<>()
        for (int i = 1; i <= lastMarble; i++) {
            int activePlayer = (i - 1) % players
            if (i % 23 == 0) {
                current = current.getAt(-7)
                score.put(activePlayer, score.getOrDefault(activePlayer, 0) + i + current.value)
                current = current.remove()
            } else {
                current = current.next.insertAfter(i)
            }
        }
        return score.values().max()
    }


    private static void printMarbles(Marble root, Marble current, int activePlayer) {
        print "[$activePlayer] "
        Marble marble = root
        while (true) {
            if (marble == current) {
                print "($marble.value)"
            } else {
                print " $marble.value "
            }
            marble = marble.next
            if (marble == root) {
                break
            }
        }
        println ""
    }

    private static class Marble {
        long value
        Marble next
        Marble previous

        Marble(long value) {
            this.value = value
            next = null
        }

        Marble insertAfter(long value) {
            Marble newMarble = new Marble(value)
            newMarble.next = next
            next.previous = newMarble
            next = newMarble
            newMarble.previous = this
            return newMarble
        }

        Marble getAt(int index) {
            if (index == 0) {
                return this
            }
            Marble current = this
            for (int i = 0; i < Math.abs(index); i++) {
                if (index > 0) {
                    current = current.next
                } else {
                    current = current.previous
                }
            }
            return current
        }

        Marble remove() {
            previous.next = next
            next.previous = previous
            return next
        }

        static Marble createRoot(long value) {
            Marble root = new Marble(0)
            root.previous = root
            root.next = root
            return root
        }
    }


}
