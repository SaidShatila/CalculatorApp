package said.shatila.calculatorapp.domain

sealed interface ExpressionPart {
    data class Number(val value: Double) : ExpressionPart
    data class Op(val operation: Operation) : ExpressionPart
    data class Parenthesis(val parenthesis: ParenthesisType) : ExpressionPart
}

sealed interface ParenthesisType : ExpressionPart {
    object Open : ParenthesisType
    object Close : ParenthesisType
}
