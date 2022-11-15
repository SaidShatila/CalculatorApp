package said.shatila.calculatorapp.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {
    private lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {
        // Given
        parser = ExpressionParser("3+5-3x4/3")
        // Do something with what's given
        val parts = parser.parse()
        // Assert that the result is what you expect
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )
        assertThat(expected).isEqualTo(parts)
    }

    @Test
    fun `Expression with parentheses is properly parsed`() {
        parser = ExpressionParser("4-(4x5)")
        val parts = parser.parse()
        val expected = listOf(
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parenthesis(ParenthesisType.Open),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parenthesis(ParenthesisType.Close)
        )
        assertThat(expected).isEqualTo(parts)
    }
}