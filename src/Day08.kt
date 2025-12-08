import java.util.*

fun main() {
    data class Point(val x: Long, val y: Long, val z: Long)

    fun parse(input: List<String>) = input.map { line ->
        line.split(",").map { it.toLong() }.let { (a, b, c) ->
            Point(a, b, c)
        }
    }

    fun distQueue(list: List<Point>): PriorityQueue<Pair<Int, Int>> {
        val queue = PriorityQueue<Pair<Int, Int>>(compareBy {
            val a = list[it.first]
            val b = list[it.second]
            (a.x - b.x).pow(2) + (a.y - b.y).pow(2) + (a.z - b.z).pow(2)
        })
        for (i in 0 until list.size) {
            for (j in i + 1 until list.size) {
                queue.offer(i to j)
            }
        }
        return queue
    }

    fun part1(input: List<String>, n: Int): Int {
        val list = parse(input)
        val queue = distQueue(list)
        val uf = UnionFind(list.size)
        var cnt = 0
        while (cnt < n) {
            val (i, j) = queue.poll()
            uf.unite(i, j)
            cnt++
        }
        uf.uniqueRoots.map { uf.size(it) }.sortedDescending().println()
        return uf.uniqueRoots.map { uf.size(it) }.sortedDescending().take(3).fold(1) { acc, n -> acc * n }
    }

    fun part2(input: List<String>): Long {
        val list = parse(input)
        val queue = distQueue(list)
        val uf = UnionFind(list.size)
        while (queue.isNotEmpty()) {
            val (i, j) = queue.poll()
            uf.unite(i, j)
            if (uf.size(i) == list.size) {
                return list[i].x * list[j].x
            }
        }
        return -1
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput, 10) == 40)
    check(part2(testInput) == 25272L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input, 1000).println()
    part2(input).println()
}
