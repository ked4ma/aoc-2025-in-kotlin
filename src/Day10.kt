import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        data class D(val target: Int, val buttons: List<Int>)

        val data = input.map { line ->
            val d = line.split(" ")
            val s = d[0]
            var n = 0
            for (i in (1 until s.lastIndex).reversed()) {
                n *= 2
                if (s[i] == '#') n += 1
            }
            val list = mutableListOf<Int>()
            for (i in 1 until d.lastIndex) {
                var m = 0
                d[i].substring(1, d[i].lastIndex).split(",").map { it.toInt() }.forEach { j ->
                    m += 1 shl j
                }
                list.add(m)
            }
            D(n, list)
        }
        var ans = 0
        data.forEach { (n, buttons) ->
            var cnt = INT_INF
            for (i in 1 until (1 shl buttons.size)) {
                var m = 0
                for (j in 0 until buttons.size) {
                    if ((i shr j) and 1 != 1) continue
                    m = m xor buttons[j]
                }
                if (m == n) {
                    cnt = min(cnt, i.countOneBits())
                }
            }
            ans += cnt
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 7)
    // check(part2(testInput) == 33)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
