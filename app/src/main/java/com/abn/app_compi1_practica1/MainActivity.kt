package com.abn.app_compi1_practica1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abn.app_compi1_practica1.ui.theme.AppCompi1Practica1Theme
import com.abn.app_compi1_practica1.compiler.Lexer
import com.abn.app_compi1_practica1.compiler.Parser
import java.io.StringReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            AppCompi1Practica1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EvaluadorExpresionesScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluadorExpresionesScreen() {
    // Estado para el texto de entrada
    var textoExpresion by remember { mutableStateOf("") }
    
    // Estado para los resultados
    var resultado by remember { mutableStateOf<String?>(null) }
    var errores by remember { mutableStateOf<List<String>>(emptyList()) }
    var reporteOperadores by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Evaluador de Expresiones",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Campo de texto para la expresión
        OutlinedTextField(
            value = textoExpresion,
            onValueChange = { textoExpresion = it },
            label = { Text("Ingrese expresión") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { 
                Text(
                    "Ejemplos:\n" +
                    "2 + 3 * 4\n" +
                    "(10 - 5) / 2\n" +
                    "5 > 3\n" +
                    "10 >= 5 && 3 < 8"
                ) 
            },
            maxLines = 6
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botón para evaluar
        Button(
            onClick = {
                try {
                    // Limpiar resultados anteriores
                    resultado = null
                    errores = emptyList()
                    reporteOperadores = emptyList()
                    
                    // Crear el lexer y parser
                    val lexer = Lexer(StringReader(textoExpresion))
                    val parser = Parser(lexer)
                    
                    // Parsear y evaluar
                    val resultadoParseo = parser.parse()
                    
                    // Obtener errores léxicos y sintácticos
                    val erroresLexicos = lexer.erroresLexicos
                    val erroresSintacticos = parser.erroresSintacticos
                    val todosLosErrores = mutableListOf<String>()
                    todosLosErrores.addAll(erroresLexicos)
                    todosLosErrores.addAll(erroresSintacticos)
                    
                    if (todosLosErrores.isEmpty()) {
                        // Si no hay errores, mostrar el resultado
                        resultado = "Resultado: ${resultadoParseo.value}"
                        
                        // Obtener reporte de operadores
                        reporteOperadores = parser.reporteOperadores.map { it.toMap() }
                    } else {
                        // Si hay errores, mostrarlos
                        errores = todosLosErrores
                    }
                    
                } catch (e: Exception) {
                    errores = listOf("Error: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Evaluar")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Área de resultados
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Mostrar resultado si existe
                if (resultado != null) {
                    item {
                        Text(
                            text = resultado!!,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                
                // Mostrar reporte de operadores
                if (reporteOperadores.isNotEmpty()) {
                    item {
                        Text(
                            text = "Reporte de Operadores:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    items(reporteOperadores) { operador ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Operador: ${operador["operador"]}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Línea: ${operador["linea"]}, Columna: ${operador["columna"]}")
                                Text(text = "Ocurrencia: ${operador["ocurrencia"]}")
                            }
                        }
                    }
                }
                
                // Mostrar errores si existen
                if (errores.isNotEmpty()) {
                    item {
                        Text(
                            text = "Errores:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    items(errores) { error ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = error,
                                modifier = Modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
                
                // Mensaje inicial si no hay nada
                if (resultado == null && errores.isEmpty()) {
                    item {
                        Text(
                            text = "Ingrese una expresión y presione 'Evaluar'",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
