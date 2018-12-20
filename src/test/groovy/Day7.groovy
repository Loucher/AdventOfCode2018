import org.junit.Test

class Day7 {
    @Test
    void part1() {
        Instructions instructions = new Instructions(getClass().getResource('day7.txt').readLines(), false)
        println instructions.findOrder()
    }


    @Test
    void part2() {
        Instructions instructions = new Instructions(getClass().getResource('day7.txt').readLines(), true, 60)
        println instructions.workOnInstructions(5)
    }

    private static class Instructions {
        Map<String, Step> steps = new HashMap<>()
        boolean incremental
        int startingTime

        Instructions(List<String> instructions, boolean incremental) {
            this(instructions, incremental, 0)
        }

        Instructions(List<String> instructions, boolean incremental, int startingTime) {
            instructions.each {
                def match = (it =~ /Step (.) must be finished before step (.) can begin./)
                match.find()
                String previous = match.group(1)
                String next = match.group(2)
                this.incremental = incremental
                this.startingTime = startingTime
                addInstruction(previous, next)
            }
        }

        String findOrder() {
            String order = ""
            Set<String> requiredSteps = new TreeSet<>(steps.keySet())
            while (!requiredSteps.isEmpty()) {
                String nextAvailableStepName = findNextAvailableStep(requiredSteps, null)
                getStep(nextAvailableStepName).work()
                requiredSteps.remove(nextAvailableStepName)
                order += nextAvailableStepName
            }
            return order
        }

        int workOnInstructions(int numberOfWorkers) {
            int second = 0
            String order = ""
            Set<String> requiredSteps = new TreeSet<>(steps.keySet())
            String[] workedSteps = new String[numberOfWorkers]
            println "Second\tWorkers\t\tDone"
            while (!requiredSteps.isEmpty()) {
                for (int i = 0; i < numberOfWorkers; i++) {
                    def workedStepName = workedSteps[i]
                    Step workedStep = getStep(workedStepName)
                    if (workedStep != null && workedStep.isDone()) {
                        order += workedStepName
                        requiredSteps.remove(workedStepName)
                        workedSteps[i] = null
                    }
                    if (workedSteps[i] == null) {
                        workedSteps[i] = findNextAvailableStep(requiredSteps, workedSteps)
                    }
                }
                for (int i = 0; i < numberOfWorkers; i++) {
                    getStep(workedSteps[i])?.work()
                }
                println "$second\t\t$workedSteps\t\t$order"
                second++
            }
            return second
        }

        String findNextAvailableStep(TreeSet<String> steps, String[] workedSteps) {
            for (String step : steps) {
                if (!workedSteps.contains(step) && isAvailable(step)) {
                    return step
                }
            }
            return null
        }

        private void addInstruction(String previous, String next) {
            getStep(previous).nextNames.add(next)
            getStep(next).previousNames.add(previous)
        }

        private Step getStep(String a) {
            if (a == null || a.isEmpty()) {
                return null
            }
            if (!steps.containsKey(a)) {
                int workRequired = incremental ? startingTime + (a.charAt(0) - ('@' as char)) : 1
                Step step = new Step(workRequired)
                steps.put(a, step)
                return step
            }
            return steps.get(a)
        }

        private boolean isAvailable(String name) {
            Step step = getStep(name)
            for (String previousName : step.previousNames) {
                Step previousStep = getStep(previousName)
                if (!previousStep.isDone()) {
                    return false
                }
            }
            return true
        }

    }

    private static class Step {
        private final int workRequired
        private int workDone = 0
        final Set<String> previousNames = new HashSet<>()
        final Set<String> nextNames = new HashSet<>()

        Step(int workRequired) {
            this.workRequired = workRequired
        }

        void work() {
            workDone++
        }

        boolean isDone() {
            return workDone == workRequired
        }

    }

}
