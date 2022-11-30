package said.shatila.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import said.shatila.calculatorapp.presentation.CalculatorScreen
import said.shatila.calculatorapp.ui.theme.MaterialCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Constants.BASE_URL
        setContent {
            MaterialCalculatorTheme {
                // A surface container using the 'background' color from the theme
                CalculatorScreen()
            }
        }
    }
}


