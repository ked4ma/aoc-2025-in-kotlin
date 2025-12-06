fun main() {
    fun plus(a: Long, b: Long) = a + b
    fun mul(a: Long, b: Long) = a * b
    fun part1(input: List<String>): Long {
        val data = input.map { it.trim().split("\\s+".toRegex()) }
        val h = data.size
        val w = data.first().size
        var ans = 0L
        for (j in 0 until w) {
            val f = if (data[h - 1][j] == "+") ::plus else ::mul
            var n = data[0][j].toLong()
            for (i in 1 until h - 1) {
                n = f(n, data[i][j].toLong())
            }
            ans += n
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val h = input.size
        val w = input.maxOf { it.length }
        val whiteCnt = IntArray(w)
        for (j in 0 until w) {
            for (i in 0 until input.size) {
                if (input[i].getOrElse(j) { ' ' } == ' ') whiteCnt[j]++
            }
        }
        fun parseDigit(j: Int): Long {
            val sb = StringBuilder()
            for (i in 0 until h - 1) {
                if (input[i].getOrElse(j) { ' ' } != ' ') {
                    sb.append(input[i][j])
                }
            }
            return sb.toString().toLong()
        }

        var ans = 0L
        var l = 0
        while (l < w) {
            if (input.last()[l] == ' ') {
                l++
                continue
            }
            var r = l + 1
            while (r < w && whiteCnt[r] < h) r++
            val f = if (input[h - 1][l] == '+') ::plus else ::mul
            var n = parseDigit(l)
            for (i in l + 1 until r) {
                n = f(n, parseDigit(i))
            }
            ans += n
            l = r
        }
        return ans
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
