import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Long {
        val ranges = input.first().split(",").map { range ->
            val (l, r) = range.split("-")
            l to r
        }
        var ans = 0L
        for ((l, r) in ranges) {
            val range = l.toLong()..r.toLong()
            val a = l.substring(0, max(1, l.length / 2)).toLong()
            val b = r.substring(0, r.length - r.length / 2).toLong()
            for (k in a..b) {
                val n = k.toString().repeat(2).toLong()
                if (n in range) ans += n
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val ranges = input.first().split(",").map { range ->
            val (l, r) = range.split("-")
            l to r
        }
        var ans = 0L
        for ((l, r) in ranges) {
            val tmp = mutableSetOf<Long>()
            for (m in 2..r.length) {
                val range = l.toLong()..r.toLong()
                val a = if (l.length / m == 0) 1 else l.substring(0, l.length / m).toLong()
                val b = r.substring(0, (r.length + m - 1) / m).toLong()
                "$l..$r($m): ${l.length / m} ${r.length / m} -> $a $b".println()
                for (k in a..b) {
                    val n = k.toString().repeat(m).toLong()
                    if (n in range) tmp.add(n)
                }
            }
            ans += tmp.sum()
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1_227_775_554L)
    check(part2(testInput) == 4_174_379_265L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}