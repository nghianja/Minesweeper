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

private fun calculateMines(minefield: Array<CharArray>) {
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (minefield[i][j] == '.') {
                var count = 0
                if (i - 1 >= 0) {
                    if (j - 1 >= 0 && minefield[i - 1][j - 1] == 'X')
                        count++
                    if (minefield[i - 1][j] == 'X')
                        count++
                    if (j + 1 < rows && minefield[i - 1][j + 1] == 'X')
                        count++
                }
                if (i + 1 < rows) {
                    if (j - 1 >= 0 && minefield[i + 1][j - 1] == 'X')
                        count++
                    if (minefield[i + 1][j] == 'X')
                        count++
                    if (j + 1 < rows && minefield[i + 1][j + 1] == 'X')
                        count++
                }
                if (j - 1 >= 0 && minefield[i][j - 1] == 'X')
                    count++
                if (j + 1 < rows && minefield[i][j + 1] == 'X')
                    count++
                if (count > 0)
                    minefield[i][j] = "$count".first()
            }
        }
    }
}

fun main() {
    print("How many mines do you want on the field? ")
    val minefield = plantMines(readLine()!!.toInt())
    calculateMines(minefield)
    for (i in 1..rows) {
        for (j in 1..cols) {
            print(minefield[i - 1][j - 1])
        }
        println()
    }
}
