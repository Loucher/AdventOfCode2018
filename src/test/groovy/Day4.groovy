import org.junit.Test

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.stream.Collectors

class Day4 {

    @Test
    void part1() {
        def records = loadSchedule()
        Map guards = processSchedule(records)
        def candidateGuard = guards.max { it.value.slept }
        def candidateMinute = (0..59).max { candidateGuard.value.schedule[it] }
        println "Part1: ${candidateGuard.key}+${candidateMinute}=${candidateGuard.key * candidateMinute}"

        candidateGuard = guards.max { ((it.value.schedule) as List).max()}
        candidateMinute = (0..59).max { candidateGuard.value.schedule[it] }
        println "Part2: ${candidateGuard.key}+${candidateMinute}=${candidateGuard.key * candidateMinute}"
    }

    private HashMap processSchedule(List<LinkedHashMap<String, Serializable>> records) {
        Map guards
        guards = new HashMap()
        def iterator = records.iterator()

        def guard
        while (iterator.hasNext()) {
            def item = iterator.next()
            if (item.action instanceof Integer) {
                if (!guards.containsKey(item.action)) {
                    guards.put(item.action,
                            [
                                    schedule: new int[60],
                                    slept   : 0
                            ]
                    )
                }
                guard = guards.get(item.action)
            } else {
                def wakeUp = iterator.next()
                for (int i = item.minute; i < wakeUp.minute; i++) {
                    guard.schedule[i]++
                    guard.slept++
                }
            }
        }
        guards
    }

    private List<LinkedHashMap<String, Serializable>> loadSchedule() {
        getClass().getResource('day4.txt').readLines().stream()
                .map { it ->
            def matchDate = (it =~ /\[(.*)]/)
            matchDate.find()
            String date = matchDate.group(1)
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd mm:ss")
            def action
            if (it.contains("wakes up")) {
                action = "wakes"
            } else if (it.contains("falls asleep")) {
                action = "sleep"
            } else {
                def matchGuard = (it =~ /#(\d+)/)
                matchGuard.find()
                String id = matchGuard.group(1)
                action = Integer.parseInt(id)
            }
            def matchMinute = (it =~ /(\d+)]/)
            matchMinute.find()
            int minute = matchMinute.group(1).toInteger()
            [
                    date  : dateFormat.parse(date),
                    minute: minute,
                    action: action
            ]
        }
        .sorted { a, b -> a.date <=> b.date }
                .collect(Collectors.toList())
    }


}
