package said.shatila.calculatorapp.domain

// -3 + (5*3)
/**
 * Uses the following grammar
 * expression : term| term+term| term-term
 * term : factor| factor*factor| factor/factor |factor % factor
 * factor : number| (expression)| + factor| - factor
 */
class ExpressionEvaluator(private val expression: List<ExpressionPart>) {

    fun evaluate(): Double {
        return evalExpression(expression).value
    }

    // A factor is a number or a parenthesized expression
    // e.g. 5.0, -(5+3), +5, -5
    // But not something like 5+3, 5*3, 5/3
    private fun evaluateFactor(factor: List<ExpressionPart>): ExpressionResult {
        return when (val part = expression.firstOrNull()) {
            ExpressionPart.Op(Operation.ADD) -> {
                evaluateFactor(factor.drop(1))
            }
            ExpressionPart.Op(Operation.SUBTRACT) -> {
                evaluateFactor(factor.drop(1)).run {
                    ExpressionResult(remainingExpression, -value)
                }
            }
            ExpressionPart.Parenthesis(ParenthesisType.Open) -> {
                evalExpression(expression.drop(1)).run {
                    ExpressionResult(remainingExpression.drop(1), value)
                }
            }
            ExpressionPart.Op(Operation.PERCENT) -> evalTerm(expression.drop(1))
            is ExpressionPart.Number -> {
                ExpressionResult(expression.drop(1), part.value)
            }
            else -> {
                throw java.lang.RuntimeException("Invalid part")
            }


        }
    }

    private fun evalExpression(drop: List<ExpressionPart>): ExpressionResult {
        val result = evalTerm(drop)
        var remaining = result.remainingExpression
        var sum = result.value
        while (true) {
            when (remaining.firstOrNull()) {
                ExpressionPart.Op(Operation.ADD) -> {
                    val term = evalTerm(remaining.drop(1))
                    sum += term.value
                    remaining = term.remainingExpression
                }
                ExpressionPart.Op(Operation.SUBTRACT) -> {
                    val term = evalTerm(remaining.drop(1))
                    sum -= term.value
                    remaining = term.remainingExpression
                }

                else -> {
                    return ExpressionResult(remaining, sum)
                }
            }
        }
    }

    private fun evalTerm(drop: List<ExpressionPart>): ExpressionResult {
        val result = evaluateFactor(drop)
        var remaining = result.remainingExpression
        var sum = result.value
        while (true) {
            when (remaining.firstOrNull()) {
                ExpressionPart.Op(Operation.MULTIPLY) -> {
                    val nextFactor = evaluateFactor(remaining.drop(1))
                    sum *= nextFactor.value
                    remaining = nextFactor.remainingExpression
                }
                ExpressionPart.Op(Operation.DIVIDE) -> {
                    val nextFactor = evaluateFactor(remaining.drop(1))
                    sum /= nextFactor.value
                    remaining = nextFactor.remainingExpression
                }
                ExpressionPart.Op(Operation.PERCENT) -> {
                    val nextFactor = evaluateFactor(remaining.drop(1))
                    sum *= (nextFactor.value / 100.0)
                    remaining = nextFactor.remainingExpression
                }
                else -> {
                    return ExpressionResult(remaining, sum)
                }
            }
        }
    }

    data class ExpressionResult(val remainingExpression: List<ExpressionPart>, val value: Double)
}