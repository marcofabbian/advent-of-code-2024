import kotlin.reflect.KClass

open abstract class CalibrationEquation(val result:Long, val factors:List<Long>)
class AdderMultiplierEquation(result:Long, factors:List<Long>) : CalibrationEquation(result, factors)
class AdderMultiplierCombinerEquation(result:Long, factors:List<Long>) : CalibrationEquation(result, factors)
fun withAdderMultiplierCombinerOperatior(result:Long, factors:List<Long>) = AdderMultiplierCombinerEquation(result, factors)
fun withAdderMultiplierOperatior(result:Long, factors:List<Long>) = AdderMultiplierEquation(result, factors)

val operationResult = "^(\\d+)".toRegex()
val operationFactors = "\\s(\\d+)".toRegex()
fun <T:CalibrationEquation> calculation(ind: Int, partialResult: Long, result:Long, factors: List<Long>, type: KClass<T>): Boolean {
    if(partialResult == result && ind == factors.size -1)
        return true
    else if (partialResult > result || ind == factors.size -1)
        return false
    else {
        return if(type == AdderMultiplierEquation::class)
             calculation(ind + 1, partialResult * factors[ind + 1], result, factors, type)
                        || calculation(ind + 1, partialResult + factors[ind + 1], result, factors, type)
        else if (type == AdderMultiplierCombinerEquation::class)
             calculation(ind + 1, partialResult * factors[ind + 1], result, factors, type)
                     || calculation(ind + 1, partialResult + factors[ind + 1], result, factors, type)
                     || calculation(ind + 1, (partialResult.toString()+factors[ind+1].toString()).toLong(), result, factors, type)
        else
            throw IllegalArgumentException("Operation not allowed.")
    }
}
fun checkCalculation(data:CalibrationEquation): Boolean {
    return calculation(0, data.factors[0], data.result, data.factors,
        if(data::class == AdderMultiplierEquation::class) AdderMultiplierEquation::class else AdderMultiplierCombinerEquation::class)
}

fun main() {

    fun part1(input: List<String>): Long {
        return mutableListOf<Long>().apply {
            input.forEach {line ->
                val result = operationResult.find(line)!!.value.toLong()
                val factors = operationFactors.findAll(line).map { it.value.trim().toLong() }.toList()
                if(checkCalculation(withAdderMultiplierOperatior(result, factors)))
                    this.add(result)
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return mutableListOf<Long>().apply {
            input.forEach {line ->
                val result = operationResult.find(line)!!.value.toLong()
                val factors = operationFactors.findAll(line).map { it.value.trim().toLong() }.toList()
                if(checkCalculation(withAdderMultiplierCombinerOperatior(result, factors)))
                    this.add(result)
            }
        }.sum()
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07_test")
    part1(input).println()
    part2(input).println()
}