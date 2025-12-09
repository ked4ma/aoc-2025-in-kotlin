import kotlin.math.abs
import kotlin.math.max

fun main() {
    fun parse(input: List<String>): List<Pair<Int, Int>> {
        return input.map { line ->
            line.split(",").map {
                it.toInt()
            }.let { (l, r) -> l to r }
        }
    }

    fun part1(input: List<String>): Long {
        val list = parse(input)
        var ans = 0L
        fun area(i: Int, j: Int): Long {
            val (ai, bi) = list[i]
            val (aj, bj) = list[j]
            return (abs(ai - aj) + 1).toLong() * (abs(bi - bj) + 1)
        }
        for (i in 0 until list.size) {
            for (j in i + 1 until list.size) {
                ans = max(ans, area(i, j))
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val (list, aRev, bRev) = run {
            val list = parse(input)
            val aUniq = list.map { it.first }.sorted().distinct()
            val bUniq = list.map { it.second }.sorted().distinct()
            val conv = list.map { (a, b) ->
                aUniq.binarySearch(a) to bUniq.binarySearch(b)
            }
            Triple(conv, aUniq, bUniq)
        }

        val arr = run {
            val arr = Array(aRev.size) { BooleanArray(bRev.size) }
            for ((i, j) in list) {
                arr[i][j] = true
            }
            val arr2 = Array(aRev.size) { IntArray(bRev.size) { 1 } }
            for (i in 0 until aRev.size) {
                var active = false
                for (j in 0 until bRev.size) {
                    if (arr[i][j]) {
                        arr2[i][j] = 0
                        active = !active
                    } else if (active) {
                        arr2[i][j] = 0
                    }
                }
            }
            for (j in 0 until bRev.size) {
                var active = false
                for (i in 0 until aRev.size) {
                    if (arr[i][j]) {
                        arr2[i][j] = 0
                        active = !active
                    } else if (active) {
                        arr2[i][j] = 0
                    }
                }
            }
            val queue = ArrayDeque<Pair<Int, Int>>()
            run {
                for (i in 0 until aRev.size) {
                    var cnt = 0
                    for (j in 0 until bRev.size) {
                        if (arr2[i][j] == 0) {
                            cnt++
                        } else {
                            if (cnt == 1) {
                                arr2[i][j] = 0
                                queue.add(i to j)
                                return@run
                            }
                            cnt = 0
                        }
                    }
                }
            }
            val dir = listOf(
                1 to 0,
                -1 to 0,
                0 to 1,
                0 to -1,
            )
            while (queue.isNotEmpty()) {
                val (i, j) = queue.removeFirst()
                for ((di, dj) in dir) {
                    val ni = i + di
                    val nj = j + dj
                    if (ni !in 0 until aRev.size || nj !in 0 until bRev.size || arr2[ni][nj] == 0) continue
                    arr2[ni][nj] = 0
                    queue.add(ni to nj)
                }
            }
            arr2
        }
        val cum = CumulativeSum2D(arr)
        var ans = 0L
        for (i in 0 until list.size) {
            for (j in i + 1 until list.size) {
                var (ai, bi) = list[i]
                var (aj, bj) = list[j]
                if (ai > aj) ai = aj.also { aj = ai }
                if (bi > bj) bi = bj.also { bj = bi }
                if (cum.getSumOf(bi, ai, bj, aj) > 0) continue
                ans = max(ans, (aRev[aj] - aRev[ai] + 1).toLong() * (bRev[bj] - bRev[bi] + 1).toLong())
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
