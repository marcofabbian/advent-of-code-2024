import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun List<String>.subStringVertical(column:Int, start:Int, end:Int):String {
    var subString = ""
    for(i in start..end){
        subString += this[i][column]
    }
    return subString
}

fun List<String>.subStringDiagonal(row:Int, column:Int, rowEnd:Int, columnEnd:Int): String {
    var subString = ""
    var i = row
    var j = column
    if(i < rowEnd && j < columnEnd) {
        while (i < rowEnd && j < columnEnd) {
            subString += this[i++][j++]
        }
    } else {
        while (i > rowEnd && j < columnEnd) {
            subString += this[i--][j++]
        }
    }

    return subString
}
