fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            "$firstDigit$lastDigit".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = getNumber(line, StartFrom.START)
            val lastDigit = getNumber(line, StartFrom.END)
            "$firstDigit$lastDigit".toInt()
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    check(part1(input) == 54916)
    part2(input).println()
    check(part2(input) == 54728)
}

private fun getNumber(line: String, startFrom: StartFrom): Int {
    val wordDigitMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    val lineIndex = when (startFrom) {
        StartFrom.START -> line.indices
        StartFrom.END -> line.lastIndex downTo 0
    }
    for (index in lineIndex) {
        line[index].digitToIntOrNull()?.let { return it }
        for ((key) in wordDigitMap.entries) {
            if (line.substring(index).startsWith(key)) {
                return wordDigitMap.getValue(key)
            }
        }
    }
    error("Should not to be here!!!")
}

enum class StartFrom {
    START,
    END
}
