fun main() {
    data class D(
        val n: Int,
        val data: List<Pair<Int, List<Int>>>,
        val start: Int,
        val end: Int,
        val fft: Int,
        val dac: Int
    )

    fun parse(input: List<String>, start: String): D {
        val keys = buildSet {
            add("fft")
            add("dac")
            input.forEach { line ->
                line.split(": ").let {
                    add(it[0])
                    addAll(it[1].split(" "))
                }
            }
        }.sorted().withIndex().associate { it.value to it.index }
        return D(
            keys.size,
            input.map { line ->
                line.split(": ").let { (k, v) ->
                    keys.getValue(k) to v.split(" ").map { keys.getValue(it) }
                }
            },
            keys.getValue(start), keys.getValue("out"),
            keys.getValue("fft"), keys.getValue("dac"),
        )
    }

    fun part1(input: List<String>): Int {
        val (n, data, s, t) = parse(input, "you")
        val G = Array(n) { mutableListOf<Int>() }
        for ((u, list) in data) {
            for (v in list) {
                G[v].add(u)
            }
        }
        val cnt = IntArray(n) { -1 }
        cnt[s] = 1
        fun dfs(u: Int = t) {
            if (cnt[u] >= 0) return
            var n = 0
            for (v in G[u]) {
                dfs(v)
                n += cnt[v]
            }
            cnt[u] = n
        }
        dfs()
        return cnt[t]
    }

    fun part2(input: List<String>): Long {
        val (n, data, s, t, fft, dac) = parse(input, "svr")
        val G = Array(n) { mutableListOf<Int>() }
        for ((u, list) in data) {
            for (v in list) {
                G[v].add(u)
            }
        }
        val cnt = Array(n) { LongArray(4) { -1 } }
        cnt[s].fill(0)
        cnt[s][0] = 1
        fun dfs(u: Int = t) {
            if (cnt[u][0] >= 0) return
            cnt[u].fill(0)
            for (v in G[u]) {
                dfs(v)
                cnt[u][0] += cnt[v][0]
                cnt[u][1] += cnt[v][1]
                cnt[u][2] += cnt[v][2]
                cnt[u][3] += cnt[v][3]
                // fft
                if (u == fft) {
                    cnt[u][1] += cnt[v][0]
                    cnt[u][3] += cnt[v][2]
                }
                // dac
                if (u == dac) {
                    cnt[u][2] += cnt[v][0]
                    cnt[u][3] += cnt[v][1]
                }
            }
        }
        dfs()
        return cnt[t][3]
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    // val testInput = readInput("Day11_test")
    check(part1(readInput("Day11_test")) == 5)
    check(part2(readInput("Day11_test2")) == 2L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
