package minesweeper

import kotlin.random.Random

fun main() {
    val rows = 9
    val cols = 9
    val mines = 10
    val minefield = Array<CharArray>(rows) { CharArray(cols) { '.' } }
    var m = 0
    while (m < mines) {
        val i = Random.nextInt(1, rows) - 1
        val j = Random.nextInt(1, cols) - 1
        if (minefield[i][j] != 'X') {
            minefield[i][j] = 'X'
            m++
        }
    }
    for (i in 1..rows) {
        for (j in 1..cols) {
            print(minefield[i - 1][j - 1])
        }
        println()
    }
}
