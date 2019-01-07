import org.junit.Test

import java.util.stream.Collectors

class Day13 {

    public static final Point UP = new Point(0, -1)
    public static final Point DOWN = new Point(0, 1)
    public static final Point LEFT = new Point(-1, 0)
    public static final Point RIGHT = new Point(1, 0)

    @Test
    void part1() {
        List<List<Character>> map = getClass().getResource('day13.txt').readLines().stream().map {
            it.getChars() as List
        }.collect(Collectors.toList())
        List<Cart> carts = extractCarts(map)
        boolean firstCrashOccured = false
        while (carts.size() > 1) {
            def sortedCarts = carts.sort { a, b -> a.position <=> b.position }
            sortedCarts.any {
                Point collisionPosition = it.advance(map, carts)
                if (collisionPosition != null) {
                    if (!firstCrashOccured) {
                        println "Part1: $collisionPosition.x,$collisionPosition.y"
                        firstCrashOccured = true
                    }
                    carts.removeAll(getCartsByPosition(collisionPosition, carts))
                }
            }
        }
        println "Part2: ${carts.get(0).position.x},${carts.get(0).position.y}"
    }

    private static List<Cart> getCartsByPosition(Point position, List<Cart> carts) {
        return carts.findAll { it.position == position }
    }

    private static List<Cart> extractCarts(List<List<Character>> map) {
        List<Cart> carts = new ArrayList<>()
        for (int y = 0; y < map.size(); y++) {
            List<Character> row = map[y]
            for (int x = 0; x < row.size(); x++) {
                Character c = row[x]
                switch (c) {
                    case '>':
                        carts.add(new Cart(
                                position: new Point(x, y),
                                direction: RIGHT
                        ))
                        row[x] = '-' as char
                        break
                    case 'v':
                        carts.add(new Cart(
                                position: new Point(x, y),
                                direction: DOWN
                        ))
                        row[x] = '|' as char
                        break
                    case '^':
                        carts.add(new Cart(
                                position: new Point(x, y),
                                direction: UP
                        ))
                        row[x] = '|' as char
                        break
                    case '<':
                        carts.add(new Cart(
                                position: new Point(x, y),
                                direction: LEFT
                        ))
                        row[x] = '-' as char
                        break
                }
            }
        }
        return carts
    }

    private static class Cart {
        Point position
        Point direction
        long turns = 0

        Point advance(List<List<Character>> map, List<Cart> carts) {
            position += direction
            switch (map[position.y][position.x]) {
                case '\\':
                    direction = new Point(direction.y, direction.x)
                    break
                case '/':
                    direction = new Point(-direction.y, -direction.x)
                    break
                case '+':
                    if (turns % 3 == 0) {
                        //turn left
                        direction = new Point(direction.y, -direction.x)
                    } else if (turns % 3 == 2) {
                        //turn right
                        direction = new Point(-direction.y, direction.x)
                    }
                    turns++
                    break
                case '-':
                case '|':
                    break
            }
            for (Cart cart : carts) {
                if (cart == this) {
                    continue
                }
                if (cart.position == this.position) {
                    return position
                }
            }
            return null
        }
    }

    private static class Point implements Comparable<Point> {
        int x
        int y

        Point(int x, int y) {
            this.x = x
            this.y = y
        }

        Point plus(Point b) {
            return new Point(x + b.x, y + b.y)
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Point point = (Point) o

            if (x != point.x) return false
            if (y != point.y) return false

            return true
        }

        int hashCode() {
            int result
            result = x
            result = 31 * result + y
            return result
        }

        @Override
        int compareTo(Point o) {
            y <=> o.y ?: x <=> o.x
        }
    }

}
