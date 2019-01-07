import org.testng.annotations.Test

class Day15 {


    @Test
    void targetSelection() {
        Battlefield map = new Battlefield(getClass().getResource('day15.txt').readLines())
        map.printMap()
    }

    private static class Battlefield {
        private static final Entity WALL = new Entity(null)
        private final Entity[][] map
        int elfs
        int goblins
        private final sizeX
        private final sizeY

        Battlefield(List<String> input) {
            sizeY = input.size()
            sizeX = input.first().size()
            elfs = 0
            goblins = 0
            map = new Entity[this.sizeY][this.sizeX]
            for (int y = 0; y < this.sizeY; y++) {
                char[] lineChars = input.get(y).chars
                for (int x = 0; x < sizeX; x++) {
                    switch (lineChars[x]) {
                        case '#':
                            map[y][x] = WALL
                            break
                        case 'E':
                            map[y][x] = new Entity(Affinity.ELF)
                            elfs++
                            break
                        case 'G':
                            map[y][x] = new Entity(Affinity.GOBLIN)
                            goblins++
                            break
                    }
                }
            }
        }


        void move(int x, int y){
            
        }



        void printMap() {
            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    Entity entity = map[y][x]
                    if (entity == null) {
                        print '.'
                    } else if (entity.affinity == null) {
                        print '#'
                    } else if (entity.affinity == Affinity.ELF) {
                        print 'E'
                    } else {
                        print 'G'
                    }
                }
                println ''
            }
        }

    }

    private static class Entity {
        int hitPoints
        Affinity affinity

        Entity(Affinity affinity) {
            hitPoints = 200
            this.affinity = affinity
        }
    }

    private enum Affinity {
        ELF, GOBLIN
    }

    private static class Position implements Comparable<Position> {
        int x
        int y

        @Override
        int compareTo(Position o) {
            y <=> o.y ?: x <=> o.x
        }
    }

}







