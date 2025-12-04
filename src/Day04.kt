fun main() {
    fun cntNeighbors(input: List<String>): Array<IntArray> {
        val h = input.size
        val w = input[0].length
        val cnt = Array(h) { IntArray(w) }
        for (i in 0 until h) {
            for (j in 0 until w) {
                if (input[i][j] == '.') {
                    cnt[i][j] += Int.MAX_VALUE / 2
                    continue
                }
                for (a in -1..1) {
                    for (b in -1..1) {
                        val ni = i + a
                        val nj = j + b
                        if (ni !in 0 until h || nj !in 0 until w || (i == ni && j == nj)) continue
                        cnt[ni][nj]++
                    }
                }
            }
        }
        return cnt
    }

    fun part1(input: List<String>): Int {
        val h = input.size
        val w = input[0].length
        val cnt = cntNeighbors(input)
        var ans = 0
        for (i in 0 until h) {
            for (j in 0 until w) {
                if (cnt[i][j] < 4) ans++
            }
        }
        return ans
    }

    fun part2(input: List<String>): Int {
        val h = input.size
        val w = input[0].length
        val cnt = cntNeighbors(input)
        val queue = ArrayDeque<Pair<Int, Int>>()
        for (i in 0 until h) {
            for (j in 0 until w) {
                if (cnt[i][j] < 4) queue.add(i to j)
            }
        }
        var ans = 0
        while (queue.isNotEmpty()) {
            val (i, j) = queue.removeFirst()
            ans++
            for (a in -1..1) {
                for (b in -1..1) {
                    val ni = i + a
                    val nj = j + b
                    if (ni !in 0 until h || nj !in 0 until w || (i == ni && j == nj)) continue
                    cnt[ni][nj]--
                    if (cnt[ni][nj] == 3) queue.add(ni to nj)
                }
            }
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
