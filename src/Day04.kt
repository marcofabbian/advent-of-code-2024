fun main() {
    fun checkForWord(row:Int, column:Int, word:String, input: List<String>):Int{
        var appearances = 0
        val wordSize = word.length-1
        if(input[row][column] == word[0]) {
            //check Horizontal
            if((column + wordSize) < input[row].length) {
                if(input[row].substring(column, column + wordSize+1) == word)
                    appearances++
            }
            if((column - wordSize) >= 0) {
                if(input[row].substring(column - wordSize, column+1) == word.reversed())
                    appearances++
            }

            //check vertical
            if(row + wordSize < input.size) {
                if (input.subStringVertical(column, row, row + wordSize) == word)
                    appearances++
            }
            if(row -wordSize >= 0) {
                if (input.subStringVertical(column, row - wordSize, row) == word.reversed())
                    appearances++
            }

            //check diagonal
            //SX - UP
            if(row - wordSize >= 0 && column - 3 >= 0) {
                if(input.subStringDiagonal(row-wordSize, column-wordSize, row+1, column+1) == word.reversed())
                    appearances++
            }

            //DX - UP
            if(row - wordSize >= 0 && column + wordSize < input[row].length) {
                if(input.subStringDiagonal(row, column, row-wordSize-1, column+wordSize+1) == word)
                    appearances++
            }

            //SX - DN
            if(row + wordSize < input.size && column - wordSize >= 0) {
                if(input.subStringDiagonal(row+wordSize, column-wordSize, row-1, column+1) == word.reversed())
                    appearances++
            }

            //DX - DN
            if(row + wordSize < input.size && column + wordSize < input[row].length) {
                if(input.subStringDiagonal(row, column, row+wordSize+1, column+wordSize+1) == word)
                    appearances++
            }
        }
        return appearances
    }

    fun part1(input: List<String>): Int {
        val word = "XMAS"
        var appearences = 0
        for(i in 0..input.size-1){
            for(j in 0..input[i].length-1){
                appearences += checkForWord(i, j, word, input)
            }
        }

        return appearences
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04_test")
    part1(input).println()
    part2(input).println()
}