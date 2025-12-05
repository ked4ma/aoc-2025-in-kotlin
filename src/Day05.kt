import kotlin.math.max

fun main() {
    fun parse(input: List<String>): Pair<List<LongRange>, List<Long>> {
        val ranges = mutableListOf<LongRange>()
        var i = 0
        while (i < input.size) {
            ranges.add(input[i].split("-").let { (l, r) -> l.toLong()..r.toLong() })
            i++
            if (input[i].isBlank()) break
        }
        ranges.sortBy { it.first }
        val mergedRanges = mutableListOf<LongRange>()
        var (l, r) = ranges[0].let { it.first to it.last }
        for (i in 1 until ranges.size) {
            val (l2, r2) = ranges[i].let { it.first to it.last }
            if (r + 1 < l2) {
                mergedRanges.add(l..r)
                l = l2
                r = r2
            } else {
                r = max(r, r2)
            }
        }
        mergedRanges.add(l..r)
        val values = mutableListOf<Long>()
        i++
        while (i < input.size) {
            values.add(input[i].toLong())
            i++
        }
        return mergedRanges to values.sorted()
    }

    fun part1(input: List<String>): Int {
        val (ranges, values) = parse(input)
        var ans = 0
        var i = 0
        for (value in values) {
            while (i < ranges.size && value > ranges[i].last) i++
            if (i < ranges.size && value in ranges[i]) ans++
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val (ranges, values) = parse(input)
        val freshRanges = mutableSetOf<LongRange>()
        var i = 0
        for (value in values) {
            while (i < ranges.size && value > ranges[i].last) i++
            if (i < ranges.size && value in ranges[i]) freshRanges.add(ranges[i])
        }
        return ranges.sumOf { it.last - it.first + 1 }
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
