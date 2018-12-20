import org.junit.Test

class Day8 {

    @Test
    void part1And2() {
        String input = getClass().getResource('day8.txt').getText()
        Iterator<Integer> iterator = input.findAll(/\d+/)*.toInteger().iterator()
        Node rootNode = parseNode(iterator)
        println sumPart1(rootNode)
        println sumPart2(rootNode)
    }

    static Node parseNode(Iterator<Integer> iterator) {
        int childrenCount = iterator.next()
        int metadataCount = iterator.next()
        Node node = new Node()
        childrenCount.times {
            node.addChild(parseNode(iterator))
        }
        metadataCount.times {
            node.addMetadata(iterator.next())
        }
        return node
    }

    static int sumPart2(Node node) {
        int childCount = node.children.size()
        if (childCount == 0) {
            return node.metadata.sum() as int
        }
        int sum = 0
        node.metadata.each { index ->
            if (index <= childCount) {
                sum += sumPart2(node.children.get(index - 1))
            }
        }
        return sum
    }

    static int sumPart1(Node node) {
        int sum = node.metadata.sum() as int
        node.children.each {
            sum += sumPart1(it)
        }
        return sum
    }

    private static class Node {
        private List<Node> children = new ArrayList<>()
        private List<Integer> metadata = new ArrayList<>()

        void addMetadata(Integer value) {
            metadata.add(value)
        }

        void addChild(Node child) {
            children.add(child)
        }

    }

}