fun forOrderedManual(orderingRules: List<Pair<Int,Int>>, lines:List<String>) = OrderedManual(orderingRules, lines)
fun forUnOrderedManual(orderingRules: List<Pair<Int,Int>>, lines:List<String>) = UnOrderedManual(orderingRules, lines)

open abstract class SafetyManual(val orderingRules: List<Pair<Int,Int>>, val lines:List<String>)
class OrderedManual(orderingRules: List<Pair<Int,Int>>, lines:List<String>) : SafetyManual(orderingRules, lines)
class UnOrderedManual(orderingRules: List<Pair<Int,Int>>, lines:List<String>) : SafetyManual(orderingRules, lines)

fun List<Int>.isLast(ind:Int):Boolean = ind == this.size-1
fun List<Int>.isNotFirst(ind:Int):Boolean = ind != 0
fun List<Int>.sortBy(orderingRules: List<Pair<Int, Int>>):List<Int> {
    var orderedList = mutableListOf<Int>()
    var next:Int = Int.MIN_VALUE
    this.forEachIndexed() { ind, el ->
        if(!this.isLast(ind)) {
            var next = this[ind + 1]
            if (orderingRules.any { it.first == el && it.second == next }) {
                if(!orderedList.contains(el)) orderedList.add(el)
                if(!orderedList.contains(next)) orderedList.add(next)
            }  else if (orderingRules.any { it.first == next && it.second == el }) {
                if(orderedList.contains(el)) orderedList.remove(el)
                if(!orderedList.contains(next)) orderedList.add(next)
                if(!orderedList.contains(el)) orderedList.add(el)
            }
        }
    }

    return orderedList
}

fun filterUpdates(manual:SafetyManual):List<List<Int>>{
    var filteredLines = mutableListOf<List<Int>>()

    for(line in manual.lines) {
        val updateList = line.split(",").map {
            it.toInt()
        }.toList()

        var prev = Int.MIN_VALUE
        var orderedList = true
        updateList.forEachIndexed{ ind, el ->
            if(updateList.isNotFirst(ind)) {
                if(manual.orderingRules.none { it.first == prev && it.second == el }){
                    orderedList = false
                    return@forEachIndexed
                }
            }
            prev= el
        }
        if((orderedList && manual is OrderedManual) || (!orderedList && manual is UnOrderedManual))
            filteredLines.add(updateList)
    }

    return filteredLines
}

fun main() {

    fun part1(orderingRules: List<Pair<Int,Int>>, lines:List<String>): Int {

        return filterUpdates(forOrderedManual(orderingRules, lines)).map {
            it.elementAt(it.size.div(2))
        }.toList().sum()
    }

    fun part2(orderingRules: List<Pair<Int, Int>>, lines: List<String>): Int {

        return filterUpdates(forUnOrderedManual(orderingRules, lines)).map {
            it.sortBy(orderingRules).elementAt(it.size.div(2))
        }.sum()
    }


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05_test")
    val orderingRules = input.filter { it.contains("|") }.map {
        val splittedRules = it.split("|")
        Pair<Int,Int>(splittedRules[0].toInt(),splittedRules[1].toInt())
    }.toList()

    val updates = input.filter { it.contains(",") }.map { it }.toList()

    part1(orderingRules, updates).println()
    part2(orderingRules, updates).println()
}
