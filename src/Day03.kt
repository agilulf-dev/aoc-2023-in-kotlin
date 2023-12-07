/**
 * For the solution, thanks to https://todd.ginsberg.com/post/advent-of-code/2023/day3/
 */
fun main() {

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = parseInput(input)
        return numbers.filter { number -> number.isNearTo(symbols) }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = parseInput(input) { it == '*' }
        return symbols.sumOf { symbol ->
            val neighbors = numbers.filter { it.containPoint(symbol) }
            if (neighbors.size == 2) {
                neighbors.first().toInt() * neighbors.last().toInt()
            } else 0
        }
    }

    val input = readInput("Day03")
    part1(input).println()
    check(part1(input) == 525911)
    part2(input).println()
    check(part2(input) == 75805607)

}

private data class Point2D(val x: Int, val y: Int) {
    fun neighbors(): Set<Point2D> =
        setOf(
            Point2D(x - 1, y - 1), // top left
            Point2D(x, y - 1), // left
            Point2D(x + 1, y - 1), // bottom left
            Point2D(x - 1, y), // top
            Point2D(x + 1, y), // bottom
            Point2D(x - 1, y + 1), // top right
            Point2D(x, y + 1), // right
            Point2D(x + 1, y + 1) // bottom right
        )
}

private class NumberLocation {
    val number = mutableListOf<Char>()
    val locations = mutableSetOf<Point2D>()

    fun addNumberWithLocation(num: Char, location: Point2D) {
        number.add(num)
        locations.addAll(location.neighbors())
    }

    fun isNotEmpty(): Boolean {
        return number.isNotEmpty()
    }

    fun isNearTo(points: Set<Point2D>): Boolean {
        return locations.intersect(points).isNotEmpty()
    }

    fun containPoint(point: Point2D): Boolean {
        return point in locations
    }

    fun toInt(): Int {
        return number.joinToString("").toInt()
    }
}


private fun parseInput(
    input: List<String>,
    takeSymbol: (Char) -> Boolean = { it != '.' }
): Pair<Set<NumberLocation>, Set<Point2D>> {
    val numbers = mutableSetOf<NumberLocation>()
    val symbols = mutableSetOf<Point2D>()
    var workingNumber = NumberLocation()

    input.forEachIndexed { y, row ->
        row.forEachIndexed { x, num ->
            if (num.isDigit()) {
                workingNumber.addNumberWithLocation(num, Point2D(x, y))
            } else {
                if (workingNumber.isNotEmpty()) {
                    numbers.add(workingNumber)
                    workingNumber = NumberLocation()
                }
                if (takeSymbol(num)) {
                    symbols.add(Point2D(x, y))
                }
            }
        }
        if (workingNumber.isNotEmpty()) {
            numbers.add(workingNumber)
            workingNumber = NumberLocation()
        }
    }
    return numbers to symbols
}
