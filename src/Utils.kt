import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

// UnionFind
// @param size num of elements
// @param unionBySize enable "union-by-size" (default: true)
class UnionFind(val size: Int, private val unionBySize: Boolean = true) {
    private val r = IntArray(size) { -1 }

    // find root
    fun find(x: Int): Int {
        if (r[x] < 0) return x
        // route compression
//        val comp = mutableListOf<Int>()
//        var i = x
//        while (r[i] >= 0) {
//            comp.add(i)
//            i = r[i]
//        }
//        for (j in comp) {
//            r[j] = i
//        }
//        return i
        val res = find(r[x])
        r[x] = res
        return res
    }

    val roots: List<Int>
        get() = r.indices.map { find(it) }
    val uniqueRoots: Set<Int>
        get() = buildSet {
            r.indices.forEach {
                add(find(it))
            }
        }

    fun unite(x: Int, y: Int) {
        var rx = find(x)
        var ry = find(y)
        if (rx == ry) return
        if (unionBySize && r[rx] > r[ry]) {
            val tmp = rx
            rx = ry
            ry = tmp
            // following code affect to performance
            // rx = ry.also { ry = rx }
        }
        r[rx] += r[ry]
        r[ry] = rx
    }

    fun size(x: Int) = -r[find(x)]
    fun size() = uniqueRoots.size

    fun same(x: Int, y: Int) = find(x) == find(y)

    override fun toString(): String {
        return r.joinToString(separator = ", ")
    }
}

fun Long.pow(n: Int): Long {
    var res = 1L
    var i = n
    var m = this
    while (i > 0) {
        if (i and 1 == 1) {
            res *= m
        }
        m *= m
        i = i shr 1
    }
    return res
}

class CumulativeSum2D {
    private val h: Int
    private val w: Int
    val data: Array<LongArray>

    // NOTE: only use for one time initialization.
    // If need to recreate several time, use set and build func.
    constructor(arr: Array<Array<out Number>>) : this(arr.size, arr.first().size) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                data[i + 1][j + 1] = data[i][j + 1] + data[i + 1][j] - data[i][j] + arr[i][j].toLong()
            }
        }
    }

    constructor(arr: Array<IntArray>) : this(arr.size, arr.first().size) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                data[i + 1][j + 1] = data[i][j + 1] + data[i + 1][j] - data[i][j] + arr[i][j]
            }
        }
    }

    constructor(arr: Array<LongArray>) : this(arr.size, arr.first().size) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                data[i + 1][j + 1] = data[i][j + 1] + data[i + 1][j] - data[i][j] + arr[i][j]
            }
        }
    }

    constructor(list: List<List<Number>>) : this(list.size, list.first().size) {
        for (i in 0 until h) {
            for (j in 0 until w) {
                data[i + 1][j + 1] = data[i][j + 1] + data[i + 1][j] - data[i][j] + list[i][j].toLong()
            }
        }
    }

    constructor(h: Int, w: Int) {
        this.h = h
        this.w = w
        data = Array(h + 1) { LongArray(w + 1) { 0L } }
    }

    fun set(h: Int, w: Int, n: Number) {
        data[h + 1][w + 1] = n.toLong()
    }

    fun add(h: Int, w: Int, n: Number) {
        data[h + 1][w + 1] += n.toLong()
    }

    fun build() {
        for (i in 0 until h) {
            for (j in 0 until w) {
                data[i + 1][j + 1] = data[i][j + 1] + data[i + 1][j] - data[i][j] + data[i + 1][j + 1]
            }
        }
    }

    // get sum
    // NOTE: [startX,endX], [startY,endY]
    //   sections are all inclusive
    fun getSumOf(startX: Int, startY: Int, endX: Int, endY: Int): Long =
        data[endY + 1][endX + 1] - data[endY + 1][startX] - data[startY][endX + 1] + data[startY][startX]
}

const val INT_INF = Int.MAX_VALUE / 2
