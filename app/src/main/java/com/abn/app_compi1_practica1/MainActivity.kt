package com.abn.app_compi1_practica1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abn.app_compi1_practica1.ui.theme.AppCompi1Practica1Theme
import com.abn.app_compi1_practica1.compiler.Lexer
import com.abn.app_compi1_practica1.compiler.Parser
import java.io.StringReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // ==================================================================================
        // EJEMPLO DE USO DEL COMPILADOR (JFlex + CUP)
        // ==================================================================================
        demonstrateCompiler()
        
        setContent {
            AppCompi1Practica1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    
    /**
     * Función que demuestra el uso del compilador (Lexer + Parser)
     * generado automáticamente por JFlex y CUP
     */
    private fun demonstrateCompiler() {
        println("\n==================== COMPILADOR DE EXPRESIONES ====================")
        
        try {
            // Ejemplo 1: Expresión aritmética simple
            val expresion1 = "2 + 3 * 4"
            val resultado1 = evaluarExpresion(expresion1)
            println("✅ $expresion1 = $resultado1")
            
            // Ejemplo 2: Expresión con paréntesis
            val expresion2 = "(2 + 3) * 4"
            val resultado2 = evaluarExpresion(expresion2)
            println("✅ $expresion2 = $resultado2")
            
            // Ejemplo 3: Expresión con potencias
            val expresion3 = "2 ^ 3 + 1"
            val resultado3 = evaluarExpresion(expresion3)
            println("✅ $expresion3 = $resultado3")
            
            // Ejemplo 4: Variables
            val expresion4 = "x = 10; y = 20; x + y"
            val resultado4 = evaluarExpresion(expresion4)
            println("✅ Variables: x + y = $resultado4")
            
            println("====================================================================\n")
            
        } catch (e: Exception) {
            println("❌ Error en el compilador: ${e.message}")
            e.printStackTrace()
        }
    }
    
    /**
     * Evalúa una expresión aritmética usando el compilador generado
     */
    private fun evaluarExpresion(expresion: String): Any? {
        val lexer = Lexer(StringReader(expresion))
        val parser = Parser(lexer)
        val resultado = parser.parse()
        return resultado.value
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppCompi1Practica1Theme {
        Greeting("Android")
    }
}