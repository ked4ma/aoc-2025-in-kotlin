fun main() {
    fun beam(input: List<String>, splitAction: () -> Unit = {}): LongArray {
        val w = input.first().length
        var arr = LongArray(w)
        for (i in 0 until w) {
            if (input[0][i] == 'S') arr[i] = 1
        }
        (1 until input.size).filterNot { i ->
            input[i].all { it == '.' }
        }.forEach { i ->
            val next = LongArray(w)
            for (j in 0 until w) {
                if (arr[j] == 0L) continue
                if (input[i][j] == '^') {
                    if (j - 1 >= 0) next[j - 1] += arr[j]
                    if (j + 1 < w) next[j + 1] += arr[j]
                    splitAction()
                } else {
                    next[j] += arr[j]
                }
            }
            arr = next
        }
        return arr
    }

    fun part1(input: List<String>): Int {
        var ans = 0
        beam(input) { ans++ }
        return ans
    }

    fun part2(input: List<String>): Long {
        return beam(input).sum()
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
