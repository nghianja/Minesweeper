package minesweeper

import kotlin.random.Random

const val rows = 9
const val cols = 9

val minefield = Array(rows) { CharArray(cols) { '.' } }
val mineboard = Array(rows) { CharArray(cols) { '.' } }

private fun plantMines(mines: Int) {
    var m = 0
    while (m < mines) {
        val i = Random.nextInt(1, rows) - 1
        val j = Random.nextInt(1, cols) - 1
        if (mineboard[i][j] != 'X') {
            mineboard[i][j] = 'X'
            m++
        }
    }
}

private fun calculateMines() {
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (mineboard[i][j] == '.') {
                var count = 0
                if (i - 1 >= 0) {
                    if (j - 1 >= 0 && mineboard[i - 1][j - 1] == 'X')
                        count++
                    if (mineboard[i - 1][j] == 'X')
                        count++
                    if (j + 1 < rows && mineboard[i - 1][j + 1] == 'X')
                        count++
                }
                if (i + 1 < rows) {
                    if (j - 1 >= 0 && mineboard[i + 1][j - 1] == 'X')
                        count++
                    if (mineboard[i + 1][j] == 'X')
                        count++
                    if (j + 1 < rows && mineboard[i + 1][j + 1] == 'X')
                        count++
                }
                if (j - 1 >= 0 && mineboard[i][j - 1] == 'X')
                    count++
                if (j + 1 < rows && mineboard[i][j + 1] == 'X')
                    count++
                if (count > 0)
                    minefield[i][j] = "$count".first()
            }
        }
    }
}

private fun printField() {
    println()
    print(" │")
    for (j in 1..cols)
        print(j)
    println("│")
    print("—│")
    for (j in 1..cols)
        print("—")
    println("│")
    for (i in 1..rows) {
        print("$i│")
        for (j in 1..cols) {
            print(minefield[i - 1][j - 1])
        }
        println("│")
    }
    print("—│")
    for (j in 1..cols)
        print("—")
    println("│")
}

fun main() {
    print("How many mines do you want on the field? ")
    val mines = readLine()!!.toInt()
    plantMines(mines)
    calculateMines()
    printField()
    var marked = mines
    var empty = 0
    while (marked > 0 || empty > 0) {
        print("Set/delete mines marks (x and y coordinates): ")
        val (xstr, ystr) = readLine()!!.split(" ")
        val x = xstr.toInt() - 1
        val y = ystr.toInt() - 1
        if (minefield[y][x].isDigit()) {
            println("There is a number here!")
            continue
        }
        when (minefield[y][x]) {
            '.' -> {
                minefield[y][x] = '*'
                if (mineboard[y][x] == 'X')
                    marked--
                else
                    empty++
            }
            '*' -> {
                minefield[y][x] = '.'
                if (mineboard[y][x] == 'X')
                    marked++
                else
                    empty--
            }
        }
        printField()
    }
    println("Congratulations! You found all mines!")
}
