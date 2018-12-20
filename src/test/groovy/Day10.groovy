import javax.swing.*
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.List
import java.util.stream.Collectors

class Day10 {

    static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing")
        Canvas canvas = new Drawing()
        canvas.setSize(900, 900)
        frame.add(canvas)
        frame.addKeyListener(canvas)
        frame.pack()
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.setVisible(true)
    }


    private static class Drawing extends Canvas implements KeyListener {

        List<List<Long>> input
        double xOffset
        double xScale


        double yOffset
        double yScale

        int seconds = 0

        Drawing() {
            input = getClass().getResource('day10.txt').readLines().stream().map {
                it.findAll(/-?\d+/)*.toLong()
            }.collect(Collectors.toList())

            recalculate()
            addKeyListener(this)
        }

        void recalculate() {
            long maxX = input*.getAt(0).max()
            long minX = input*.getAt(0).min()
            xOffset = -minX
            xScale = 900 / (Math.abs(minX) + maxX)

            long maxY = input*.getAt(0).max()
            long minY = input*.getAt(0).min()
            yOffset = -minY
            yScale = 900 / (Math.abs(minY) + maxY)
        }

        void paint(Graphics g) {
            input.each {
                g.fillRect(
                        Math.round((it[0] + xOffset) * xScale) as int,
                        Math.round((it[1] + yOffset) * yScale) as int,
                        4,
                        4)

            }
        }

        @Override
        void keyTyped(KeyEvent e) {
        }

        @Override
        void keyPressed(KeyEvent e) {
            if (e.keyChar == 'm' as char) {
                long scale = Math.max((1 / xScale), 1)
                seconds += scale
                println seconds
                input.each {
                    it[0] += (it[2] * scale)
                    it[1] += (it[3] * scale)
                }
                recalculate()
                repaint()
            }
        }

        @Override
        void keyReleased(KeyEvent e) {
        }
    }

}
