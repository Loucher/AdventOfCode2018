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
        Point collisionPosition = null
        while (collisionPosition == null) {
            carts.forEach { it.advance(map) }
            collisionPosition = detectCollision(carts)
        }
        println "$collisionPosition.x, $collisionPosition.y"
    }

    static void printMap(List<List<Character>> map, List<Cart> carts) {
        for (int y = 0; y < map.size(); y++) {
            List<Character> row = map[y]
            for (int x = 0; x < row.size(); x++) {
                Cart cart = getCartByPosition(new Point(x, y), carts)
                if (cart == null) {
                    print(row[x] as char)
                } else {
                    switch (cart.direction) {
                        case UP:
                            print '^'
                            break
                        case DOWN:
                            print 'v'
                            break
                        case LEFT:
                            print '<'
                            break
                        case RIGHT:
                            print '>'
                            break
                    }
                }

            }
            println ""
        }
    }


    private static Cart getCartByPosition(Point position, List<Cart> carts) {
        for (Cart cart : carts) {
            if (cart.position == position) {
                return cart
            }
        }
        return null
    }

    private static Point detectCollision(List<Cart> carts) {
        Set<Point> existingPositions = new HashSet<>()
        for (Cart cart : carts) {
            if (existingPositions.contains(cart.position)) {
                return cart.position
            }
            existingPositions.add(cart.position)
        }
        return null
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

        boolean advance(List<List<Character>> map) {
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
            return false
        }

    }

    private static class Point {
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
    }

}
