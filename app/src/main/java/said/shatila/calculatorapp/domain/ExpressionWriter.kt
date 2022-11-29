package said.shatila.calculatorapp.domain

//+--3-5

// 5.+3 doesn't make sense
class ExpressionWriter {

    var expression: String = ""

    fun processAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> {
                expression += action.number
            }
            is CalculatorAction.Op -> {
                expression += action.operation.symbol
            }
            is CalculatorAction.Clear -> {
                expression = ""
            }
            is CalculatorAction.Delete -> {
                expression = expression.dropLast(1)
            }
            is CalculatorAction.Parenthesis -> {
                processParenthesis()
            }
            is CalculatorAction.Calculate -> {
                val parser = ExpressionParser(prepareForCalculation())
                val evaluator = ExpressionEvaluator(parser.parse())
                expression = evaluator.evaluate().toString()
            }
            is CalculatorAction.Decimal -> {
                if (canEnterDecimal()) {
                    expression += "."
                }
            }
        }
    }

    private fun prepareForCalculation(): String {
        // 3+4-
        val newExpression = expression.takeLastWhile {
            it in "$operationSymbols(."
        }
        if (newExpression.isEmpty()) {
            return "0"
        }
        return newExpression
    }

    // (3+4*(5-5) second expression
    private fun processParenthesis() {
        val openingCount = expression.count { it == '(' }
        val closingCount = expression.count { it == ')' }
        expression += when {
            expression.isEmpty() || expression.last() in "$operationSymbols(" -> "("
            expression.last() in "0123456789)" && openingCount > closingCount -> return
            else -> ")"
        }
    }

    private fun canEnterDecimal(): Boolean {
        if (expression.isEmpty() || expression.last() in "$operationSymbols.()") {
            return false
        }
        //4+5.56
        return !expression.takeLastWhile {
            it in "0123456789"
        }.contains(".")
    }

    private fun canEnterOperation(operation: Operation): Boolean {
        if (operation in listOf(Operation.ADD, Operation.SUBTRACT)) {
            return expression.isEmpty() || expression.last() in "$operationSymbols()0123456789"
        }
        return expression.isNotEmpty() || expression.last() in "0123456789)"
    }
}