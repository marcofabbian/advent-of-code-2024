fun main() {
    val extractorForMultiplication = "(mul)(\\()(\\d+)\\,(\\d+)(\\))".toRegex()
    val extractorForFactors = "\\d+".toRegex()

    val centralPart = "(do\\(\\))+.+(mul)(\\()(\\d+)\\,(\\d+)(\\)).+(don\\'t\\(\\))".toRegex()
    val endPart     = "(do\\(\\)).+(mul)(\\()(\\d+)\\,(\\d+)(\\)).+".toRegex()

    fun summary(index:Int, operations:List<MatchResult>):Int{
        if(index == operations.size)
            return 0
        else {
            val operation = operations[index].value
            val factors = extractorForFactors.findAll(operation).map { it.value.toInt() }.toList()
            return factors.reduce { current, next -> current * next } + summary(index + 1, operations)
        }
    }

    fun part1(input: List<String>): Int {
        return input.map {
            summary(0, extractorForMultiplication.findAll(it).map { it }.toList())
        }.toList().sum()
    }
    //196826776

    fun part2(input: List<String>): Int {
        val multiplications = mutableListOf<Int>()
        input.forEach {
            var data = it.split("don't()")
            multiplications.add(
                summary(
                    0,
                    extractorForMultiplication.findAll(data[0])
                        .map { el -> el }.toList()
                ))

            for(i in 1..data.size-2) {
                val central = centralPart.find(data[i])
                if (central != null) {
                    multiplications.add(
                        summary(
                            0,
                            extractorForMultiplication.findAll(central.value)
                                .map { el -> el }.toList()
                        )
                    )
                }
            }

            val end = endPart.find(data[data.size-2])
            if(end != null){
                multiplications.add(summary(0,
                    extractorForMultiplication.findAll(end.value)
                        .map { el -> el }.toList()
                ))
            }
        }
        return multiplications.sum()
    }
    //471403586

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03_test")
    part1(input).println()
    part2(input).println()
}