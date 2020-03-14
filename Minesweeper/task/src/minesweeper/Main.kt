package minesweeper

import kotlin.random.Random

const val rows = 9
const val cols = 9

private fun plantMines(mines: Int): Array<CharArray> {
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
    return minefield
}

fun main() {
    print("How many mines do you want on the field? ")
    val minefield = plantMines(readLine()!!.toInt())
    for (i in 1..rows) {
        for (j in 1..cols) {
            print(minefield[i - 1][j - 1])
        }
        println()
    }
}
