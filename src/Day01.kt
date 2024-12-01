import kotlin.math.abs

fun main() {
    fun part1(left: List<Int>, right: List<Int>): Int {
        if(left.size != right.size) throw IllegalStateException("The lists needs to have the same size")

        return mutableListOf<Int>().apply {
            for(i in 0..left.size-1) {
                this.add(abs(left[i] - right[i]))
            }
        }.sum()
    }

    fun part2(left: List<Int>, right: List<Int>): Int {
        return mutableListOf<Int>().apply {
            for(i in 0..left.size-1) {
                this.add(abs(left[i] * right.filter { it == left[i]}.count()))
            }
        }.sum()
    }

    var leftList = mutableListOf<Int>()
    var rightList = mutableListOf<Int>()

    var reg = "(?<left>[0-9]+)([\\s]+)(?<right>[0-9]+)".toRegex()
    readInput("Day01_test").forEach {
        val matchResult = reg.find(it) !!
        val leftValue = matchResult.groups["left"]?.value !!
        val rightValue = matchResult.groups["right"]?.value !!
        leftList.add(leftValue.toInt())
        rightList.add(rightValue.toInt())
    }

    leftList.sort()
    rightList.sort()
    part1(leftList, rightList).println()
    part2(leftList, rightList).println()
}