fun main() {

    fun part1(orderingRules: List<Pair<Int,Int>>, lines:List<String>): Int {
        var filteredLines = mutableListOf<List<Int>>()
        for(line in lines) {
            val updateList = line.split(",").map {
                it.toInt()
            }.toList()

            var prev = Int.MIN_VALUE
            var orderedList = true
            updateList.forEach { el ->
                if(prev != Int.MIN_VALUE) {
                    if(orderingRules.none { it.first == prev && it.second == el }){
                        orderedList = false
                        return@forEach
                    }
                }
                prev= el
            }
            if(orderedList) filteredLines.add(updateList)
        }

        return filteredLines.map {
            val list = it

            list.elementAt(list.size.div(2))
        }.toList().sum()
    }

    fun part2(input: List<String>): Int {

        return input.size
    }


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05_test")
    val orderingRules = input.filter { it.contains("|") }.map {
        val splittedRules = it.split("|")
        Pair<Int,Int>(splittedRules[0].toInt(),splittedRules[1].toInt())
    }.toList()

    val updates = input.filter { it.contains(",") }.map { it }.toList()

    part1(orderingRules, updates).println()
    part2(input).println()
}