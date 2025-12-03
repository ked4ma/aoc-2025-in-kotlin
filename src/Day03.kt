import kotlin.math.max
import kotlin.math.min

fun main() {
    fun dp(s: String, len: Int): Long {
        val pows = LongArray(len).apply {
            this[0] = 1
            for (i in 1 until len) {
                this[i] = this[i - 1] * 10
            }
        }
        val arr = LongArray(len + 1)
        for (i in (0 until s.length).reversed()) {
            for (d in (1..min(s.length - i, len)).reversed()) {
                arr[d] = max(arr[d], (s[i] - '0') * pows[d - 1] + arr[d - 1])
            }
        }
        return arr[len]
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { dp(it, 2) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { dp(it, 12) }
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3_121_910_778_619L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}