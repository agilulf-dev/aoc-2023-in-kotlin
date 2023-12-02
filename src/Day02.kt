fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val range = IntRange(line.indexOf(GAME_ID_DELIMITER), line.length)
            if (isPossibleGame(line.substring(range.first + 1, range.last))) {
                line.substring("Game ".length, range.first).toInt()
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val maxCubes = mutableMapOf(Color.RED.text to 0, Color.GREEN.text to 0, Color.BLUE.text to 0)
            val range = IntRange(line.indexOf(GAME_ID_DELIMITER) + 1, line.length)
            val colorCubesMap = getColorCubesMap(line.substring(range.first, range.last))
            colorCubesMap.forEach { set ->
                set.forEach { (color, cubes) ->
                    maxCubes[color] = maxOf(maxCubes[color] ?: 0, cubes)
                }
            }
            maxCubes.values.reduce { acc, currentCubes -> acc * currentCubes }
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    check(part1(input) == 2486)
    part2(input).println()
    check(part2(input) == 87984)
}

private const val SETS_DELIMITER = ";"
private const val COLOR_DELIMITER = ","
private const val GAME_ID_DELIMITER = ":"
private fun getColorCubesMap(line: String): List<List<Pair<String, Int>>> {
    val gameSets = line.split(SETS_DELIMITER).toList()
    return gameSets.map {
        it.trim().split(COLOR_DELIMITER).map {
            val (cubesString, color) = it.trim().split(" ")
            val cubes = cubesString.toInt()
            color to cubes
        }
    }
}

private fun isPossibleGame(line: String): Boolean {
    val colorCubesMap = getColorCubesMap(line)
    return colorCubesMap.all { set ->
        set.all { (color, cubes) ->
            when (color) {
                Color.RED.text -> cubes <= 12
                Color.GREEN.text -> cubes <= 13
                Color.BLUE.text -> cubes <= 14
                else -> false
            }
        }
    }
}

enum class Color(val text: String) {
    RED("red"),
    GREEN("green"),
    BLUE("blue")
}
