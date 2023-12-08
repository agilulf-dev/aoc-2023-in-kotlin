import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { 2.0.pow(getWinningNumbersCount(it) - 1).toInt() }
    }

    fun part2(input: List<String>): Int {
        val cardMatches = input.map { getWinningNumbersCount(it) }
        val cards = IntArray(cardMatches.size) { 1 }
        cardMatches.forEachIndexed { index, score ->
            repeat(score) {
                cards[index + it + 1] += cards[index]
            }
        }
        return cards.sum()
    }

    val input = readInput("Day04")
    part1(input).println()
    check(part1(input) == 20829)
    part2(input).println()
    check(part2(input) == 12648035)

}

private fun getWinningNumbersCount(line: String): Int {
    val allNumbers = line.substring(line.indexOf(":") + 1, line.length)
    val winningNumbers = allNumbers
        .substringBefore("|")
        .split(" ")
        .filter { it.isNotEmpty() }
        .toSet()
    val myNumbers = allNumbers
        .substringAfter("|")
        .split(" ")
        .filter { it.isNotEmpty() }
        .toSet()
    return winningNumbers.intersect(myNumbers).size
}
