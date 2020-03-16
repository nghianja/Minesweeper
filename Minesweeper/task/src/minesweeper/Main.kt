package minesweeper

import kotlin.random.Random

const val rows = 9
const val cols = 9

var minefield = Array(rows) { CharArray(cols) { '.' } }
var mineboard = Array(rows) { CharArray(cols) { '/' } }
var dots = rows * cols
var marked = 0
var empty = 0
var mines = 0

private fun setup() {
    minefield = Array(rows) { CharArray(cols) { '.' } }
    dots = rows * cols
    marked = 0
    empty = 0
    print("How many mines do you want on the field? ")
    mines = readLine()!!.toInt()
}

private fun plantMines(y: Int, x: Int) {
    if (mines <= rows * cols / 2) {
        mineboard = Array(rows) { CharArray(cols) { '/' } }
        var m = 0
        while (m < mines) {
            val i = Random.nextInt(1, rows) - 1
            val j = Random.nextInt(1, cols) - 1
            if (!(i == y && j == x) && mineboard[i][j] != 'X') {
                mineboard[i][j] = 'X'
                m++
            }
        }
    } else {
        mineboard = Array(rows) { CharArray(cols) { 'X' } }
        var n = 0
        while (n < rows * cols - mines) {
            val i = Random.nextInt(1, rows) - 1
            val j = Random.nextInt(1, cols) - 1
            if ((i != y || j != x) && mineboard[i][j] != '/') {
                mineboard[i][j] = '/'
                n++
            }
        }
    }
}

private fun calculateMines() {
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (mineboard[i][j] == '/') {
                var count = 0
                if (i - 1 >= 0) {
                    if (j - 1 >= 0 && mineboard[i - 1][j - 1] == 'X')
                        count++
                    if (mineboard[i - 1][j] == 'X')
                        count++
                    if (j + 1 < cols && mineboard[i - 1][j + 1] == 'X')
                        count++
                }
                if (i + 1 < rows) {
                    if (j - 1 >= 0 && mineboard[i + 1][j - 1] == 'X')
                        count++
                    if (mineboard[i + 1][j] == 'X')
                        count++
                    if (j + 1 < cols && mineboard[i + 1][j + 1] == 'X')
                        count++
                }
                if (j - 1 >= 0 && mineboard[i][j - 1] == 'X')
                    count++
                if (j + 1 < cols && mineboard[i][j + 1] == 'X')
                    count++
                if (count > 0)
                    mineboard[i][j] = "$count".first()
            }
        }
    }
}

private fun printField(exploded: Boolean = false) {
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
            if (exploded && mineboard[i - 1][j - 1] == 'X')
                print('X')
            else
                print(minefield[i - 1][j - 1])
        }
        println("│")
    }
    print("—│")
    for (j in 1..cols)
        print("—")
    println("│")
}

private fun autoFree(x: Int, y: Int) {
    if (minefield[y][x] == '.' || minefield[y][x] == '*') {
        minefield[y][x] = mineboard[y][x]
        dots--
        if (minefield[y][x] == '/') {
            if (y - 1 >= 0) {
                if (x - 1 >= 0) autoFree(x - 1, y - 1)
                autoFree(x, y - 1)
                if (x + 1 < cols) autoFree(x + 1, y - 1)
            }
            if (y + 1 < rows) {
                if (x - 1 >= 0) autoFree(x - 1, y + 1)
                autoFree(x, y + 1)
                if (x + 1 < cols) autoFree(x + 1, y + 1)
            }
            if (x - 1 >= 0) {
                autoFree(x - 1, y)
            }
            if (x + 1 < cols) {
                autoFree(x + 1, y)
            }
        }
    }
}

private fun processMove(word: String, y: Int, x: Int): Boolean {
    when (word) {
        "mine" -> {
            when (minefield[y][x]) {
                '.' -> {
                    minefield[y][x] = '*'
                    if (mineboard[y][x] == 'X')
                        marked++
                    else
                        empty++
                }
                '*' -> {
                    minefield[y][x] = '.'
                    if (mineboard[y][x] == 'X')
                        marked--
                    else
                        empty--
                }
            }
        }
        "free" -> {
            if (mineboard[y][x] == 'X') {
                return true
            }
            autoFree(x, y)
        }
    }
    return false
}

fun main() {
    setup()
    do {
        printField()
        print("Set/delete mines marks (x and y coordinates): ")
        val (xstr, ystr, word) = readLine()!!.split(" ")
        val x = xstr.toInt() - 1
        val y = ystr.toInt() - 1
        if (word == "free") {
            plantMines(y, x)
            calculateMines()
        }
        processMove(word, y, x)
    } while (word != "free")
    while (true) {
        printField()
        if ((marked == mines && empty == 0) || dots == mines) {
            println("Congratulations! You found all mines!")
            break
        }
        print("Set/delete mines marks (x and y coordinates): ")
        val (xstr, ystr, word) = readLine()!!.split(" ")
        val x = xstr.toInt() - 1
        val y = ystr.toInt() - 1
        if (minefield[y][x].isDigit()) {
            println("There is a number here!")
        } else if (processMove(word, y, x)) {
            printField(true)
            println("You stepped on a mine and failed!")
            break
        }
    }
}
