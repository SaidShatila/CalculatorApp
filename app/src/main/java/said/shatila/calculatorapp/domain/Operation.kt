package said.shatila.calculatorapp.domain

enum class Operation(val symbol: Char) {
    //Different operations that can be performed while using the calculator

    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('*'),
    DIVIDE('/'),
    PERCENTAGE('%'),
    EQUALS('=')

}

val operationSymbols = Operation.values().map { it.symbol }.joinToString("")

fun operationFromSymbol(symbol: Char): Operation? {
    return Operation.values().find { it.symbol == symbol }
        ?: throw IllegalArgumentException("Invalid operation symbol")
}