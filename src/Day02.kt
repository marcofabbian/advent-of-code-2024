import kotlin.math.abs

fun main() {
    fun Pair<Int,Int>.isNotAdjacent():Boolean = (abs(this.first-this.second) > 3)

    fun List<Dir>.haveNotTheSameDirection():Boolean = ((this.any { it == Dir.DEC } && this.none { it == Dir.INC } && this.none{ it == Dir.EQL }) || (this.any { it == Dir.INC } && this.none { it == Dir.DEC } && this.none{ it == Dir.EQL })).not()

    fun part1(input: List<String>): Int {
        var reg = "(\\d+)".toRegex()
        var results = mutableListOf<Result>()
        input.forEach {
            val matchResult = reg.findAll(it).map { el -> el.value.toInt() }.toList()
            var previous:Int = matchResult.first()
            var current:Int
            var directions = mutableListOf<Dir>()

            for(current in (matchResult - matchResult.first())) {
                directions.add(Dir.calculate(Pair(current, previous)))

                if (Pair(current, previous).isNotAdjacent() || directions.haveNotTheSameDirection() ) {
                    results.add(Result.Unsafe)
                    return@forEach
                }

                previous = current
            }
        }
        return input.count() - results.count()
    }


    fun createCouple(list: List<Int>): List<Pair<Int, Int>> {
        return list.mapIndexedNotNull { index, i ->
                if (index == list.size-1) null
                else Pair(i, list.elementAt(index+1))
            }
    }

    fun part2(input: List<String>): Int {
        var reg = "(\\d+)".toRegex()
        var results = mutableListOf<Result>()
        input.forEach {
            val matchResult = reg.findAll(it).map { el -> el.value.toInt() }.toList()
            var previous:Int = matchResult.first()
            var directions = mutableListOf<Dir>()
            var singleBadLevel = false

            for(i in 1..matchResult.size-1) {
                val el = Pair(matchResult[i], previous)
                directions.add(Dir.calculate(el))

                if (el.isNotAdjacent() || directions.haveNotTheSameDirection()) {
                    if(singleBadLevel) {
                        results.add(Result.Unsafe)
                        return@forEach
                    } else {
                        singleBadLevel = true
                        directions.removeAt(i-1)
                        previous = matchResult[(i-1)]
                    }
                } else
                    previous = el.first
            }
        }
        return input.count() - results.count()
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02_test")
    part1(input).println()
    part2(input).println()
}

enum class Dir {
    INC,
    DEC,
    EQL;

    companion object {
        fun calculate(el:Pair<Int,Int>): Dir = if (el.first > el.second) Dir.INC else if (el.first < el.second) Dir.DEC else Dir.EQL
    }
}

enum class Result {
    Safe,
    Unsafe
}