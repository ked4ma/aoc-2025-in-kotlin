import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        var n = 50
        for (s in input) {
            val d = s.substring(1).toInt().let {
                if (s[0] == 'L') -it else it
            }
            n = ((n + d) % 100 + 100) % 100
            if (n == 0) ans++
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        var n = 50
        for (s in input) {
            val d = s.substring(1).toInt().let {
                if (s[0] == 'L') -it else it
            }
            val n2 = n + d
            if (n2 < 0) {
                ans += abs(n2) / 100
                if (n > 0) ans++
            } else if (n2 >= 100) {
                ans += n2 / 100
            } else if (n2 == 0) {
                ans++
            }
            n = (n2 % 100 + 100) % 100
            println("$s: $ans $n")
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
