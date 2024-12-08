enum class Direction {
    UP,RIGHT,DOWN,LEFT
}

fun List<String>.searchCharacter(character:Char):Pair<Int,Int>{
    this.forEachIndexed { index, line ->
        if(line.contains(character)){
            return Pair<Int,Int>(index, line.indexOf(character))
        }
    }
    throw IllegalArgumentException("Character not found : $character")
}
fun List<String>.canMoveUp(row:Int, column:Int): Boolean = (row-1 >= 0 && this[row-1][column] != '#' )
fun List<String>.canMoveRight(row:Int, column:Int): Boolean = (column+1 < this[row].length && this[row][column+1] != '#')
fun List<String>.canMoveDown(row:Int, column:Int): Boolean = (row+1 <= this.size && this[row+1][column] != '#')
fun List<String>.canMoveLeft(row:Int, column:Int): Boolean = (column-1 >= 0 && this[row][column-1] != '#')

fun List<String>.startWalking(startingPoint:Pair<Int,Int>):Int{
    var positions = mutableListOf<Pair<Int,Int>>()
    positions.add(startingPoint)
    var running = true
    var row = startingPoint.first
    var column = startingPoint.second
    var dir = Direction.UP
    do {
        if( (row-1 < 0 && dir == Direction.UP)
            ||
            ((row+1 >= this.size ) && dir == Direction.DOWN )
            ||
            (column-1 < 0 && dir == Direction.LEFT )
            ||
            (column+1 >= this[row].length && dir == Direction.RIGHT )
            ) {
            break
        }
        when(dir){
            Direction.UP -> {
                if(this.canMoveUp(row,column)) {
                    row -= 1
                    positions.add(Pair(row, column))
                } else {
                    dir = Direction.RIGHT
                }
            }
            Direction.RIGHT -> {
                if(this.canMoveRight(row,column)) {
                    column += 1
                    positions.add(Pair(row, column))
                } else {
                    dir = Direction.DOWN
                }
            }
            Direction.DOWN -> {
                if(this.canMoveDown(row,column)) {
                    row += 1
                    positions.add(Pair(row, column))
                } else {
                    dir = Direction.LEFT
                }
            }
            Direction.LEFT -> {
                if(this.canMoveLeft(row,column)) {
                    column -= 1
                    positions.add(Pair(row, column))
                } else {
                    dir = Direction.UP
                }
            }

            else -> {
                throw IllegalArgumentException("Direction not defined")
            }
        }
    } while(running)

    return positions.distinct().count()
}
//5079
//5078
//


fun main() {
    fun part1(input: List<String>): Int {
        val guard = '^'
        val startingPoint = input.searchCharacter(guard)
        return input.startWalking(startingPoint)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06_test")
    part1(input).println()
    part2(input).println()
}
