import org.jsoup.Jsoup

import java.util.function.Function
import java.util.stream.Collectors

@Grab('org.jsoup:jsoup:1.8.1')
class Roman {

    static void main(String... args) {
        String html = new URL("https://en.wikipedia.org/wiki/Avengers:_Endgame").getText()
        String text = Jsoup.parse(html).text()
        def words = (text =~ /\w+/).findAll()*.toLowerCase()
        Map<String, Long> counted = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        counted = counted.sort { a, b -> b.value <=> a.value }
        println counted
        println words.stream().collect(Collectors.groupingBy{it.length()}).max{a,b -> a.key <=> b.key }
        println counted.iterator().getAt(0).key
    }

}
