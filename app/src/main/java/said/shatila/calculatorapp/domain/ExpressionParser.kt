package said.shatila.calculatorapp.domain

class ExpressionParser(private val calculation: String) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()
        var i = 0
        while (i < calculation.length) {
            val char = calculation[i]
            when {
                char in operationSymbols -> {
                    val operation = operationFromSymbol(char)
                    operation?.let { ExpressionPart.Op(it) }?.let { result.add(it) }
                }
                char.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
                char in "()" -> {
                    parseParentheses(char, result)
                }
            }
            i++
        }
        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var i = startingIndex
        val numberAsString = buildString {
            while (startingIndex < calculation.length && calculation[i] in "0123456789.") {
                append(calculation[i])
                i++
            }
        }
        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return i -1
    }

    private fun parseParentheses(curChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parenthesis(
                when (curChar) {
                    '(' -> ParenthesisType.Open
                    ')' -> ParenthesisType.Close
                    else -> throw IllegalArgumentException("Invalid parenthesis")
                }
            )
        )
    }
}