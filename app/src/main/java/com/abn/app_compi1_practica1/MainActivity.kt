package com.abn.app_compi1_practica1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
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
    // Texto de prueba completo que ejercita todas las funcionalidades
    val textoPruebaCompleto = """
# ===================================
# PRUEBA COMPLETA DEL INTÉRPRETE
# ===================================

# 1. DECLARACIÓN Y OPERACIONES ARITMÉTICAS
VAR a = 10
VAR b = 5
VAR suma = a + b
VAR resta = a - b
VAR mult = a * b
VAR division = a / b
VAR negativo = -a

MOSTRAR "=== Operaciones Aritméticas ==="
MOSTRAR "Suma: "
MOSTRAR suma
MOSTRAR "Resta: "
MOSTRAR resta
MOSTRAR "Multiplicación: "
MOSTRAR mult
MOSTRAR "División: "
MOSTRAR division

# 2. OPERACIONES RELACIONALES
VAR mayor = a > b
VAR menor = a < b
VAR igual = a == b
VAR diferente = a != b

MOSTRAR "=== Operaciones Relacionales ==="
MOSTRAR "10 > 5: "
MOSTRAR mayor

# 3. OPERACIONES LÓGICAS
VAR resultado_and = (a > 5) && (b < 10)
VAR resultado_or = (a < 5) || (b > 3)

MOSTRAR "=== Operaciones Lógicas ==="
MOSTRAR "AND lógico: "
MOSTRAR resultado_and

# 4. ESTRUCTURA CONDICIONAL SI-ENTONCES
VAR numero = 8
SI (numero > 5) ENTONCES
    MOSTRAR "=== Condicional SI ==="
    MOSTRAR "El número es mayor que 5"
FINSI

# 5. ESTRUCTURA CONDICIONAL SI-ENTONCES-SINO
VAR edad = 17
SI (edad >= 18) ENTONCES
    MOSTRAR "=== Condicional SI-SINO ==="
    MOSTRAR "Mayor de edad"
SINO
    MOSTRAR "=== Condicional SI-SINO ==="
    MOSTRAR "Menor de edad"
FINSI

# 6. CICLO MIENTRAS
VAR contador = 0
VAR limite = 5

MOSTRAR "=== Ciclo MIENTRAS ==="
MIENTRAS (contador < limite) HACER
    MOSTRAR "Iteración: "
    MOSTRAR contador
    contador = contador + 1
FINMIENTRAS

# 7. OPERACIONES COMPLEJAS ANIDADAS
VAR x = 10
VAR y = 20
VAR z = 0

SI ((x + y) > 25) ENTONCES
    MIENTRAS (z < 3) HACER
        SI (z == 1) ENTONCES
            MOSTRAR "=== Estructuras Anidadas ==="
            MOSTRAR "Z es igual a 1"
        FINSI
        z = z + 1
    FINMIENTRAS
FINSI

MOSTRAR "=== FIN DE LA PRUEBA ==="
MOSTRAR "Todas las funcionalidades validadas"
""".trimIndent()
    
    // Estado para el texto de entrada con ejemplo inicial
    var textoExpresion by remember { mutableStateOf(textoPruebaCompleto) }
    
    // Estado para los resultados
    var resultado by remember { mutableStateOf<String?>(null) }
    var salida by remember { mutableStateOf<List<String>>(emptyList()) }
    var errores by remember { mutableStateOf<List<String>>(emptyList()) }
    var reporteOperadores by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var reporteEstructuras by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Intérprete de Pseudocódigo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Campo de texto con números de línea
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Ingrese código",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    // Columna de números de línea
                    val numLineas = textoExpresion.count { it == '\n' } + 1
                    Column(
                        modifier = Modifier
                            .width(40.dp)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(4.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.End
                    ) {
                        for (i in 1..numLineas) {
                            Text(
                                text = "$i",
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                lineHeight = 16.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    // Campo de texto principal
                    OutlinedTextField(
                        value = textoExpresion,
                        onValueChange = { textoExpresion = it },
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botón para evaluar
        Button(
            onClick = {
                try {
                    // Limpiar resultados anteriores
                    resultado = null
                    salida = emptyList()
                    errores = emptyList()
                    reporteOperadores = emptyList()
                    reporteEstructuras = emptyList()
                    
                    // Crear el lexer y parser
                    val lexer = Lexer(StringReader(textoExpresion))
                    val parser = Parser(lexer)
                    
                    // Configurar valores simulados para LEER
                    // En una app real, estos valores vendrían de un diálogo de entrada
                    parser.entradaSimulada.put("x", 15.0)
                    parser.entradaSimulada.put("y", 25.0)
                    parser.entradaSimulada.put("numero", 42.0)
                    parser.entradaSimulada.put("n", 10.0)
                    parser.entradaSimulada.put("contador", 0.0)
                    
                    // Parsear y evaluar
                    val resultadoParseo = parser.parse()
                    
                    // Obtener errores léxicos y sintácticos
                    val erroresLexicos = lexer.erroresLexicos
                    val erroresSintacticos = parser.erroresSintacticos
                    val todosLosErrores = mutableListOf<String>()
                    todosLosErrores.addAll(erroresLexicos)
                    todosLosErrores.addAll(erroresSintacticos)
                    
                    if (todosLosErrores.isEmpty()) {
                        // Obtener salida de MOSTRAR
                        salida = parser.salida.toList()
                        
                        // Si no hay errores, mostrar el resultado
                        val valorResultado = resultadoParseo.value
                        resultado = if (valorResultado != null) {
                            "Resultado: $valorResultado"
                        } else {
                            "Código ejecutado correctamente"
                        }
                        
                        // Obtener reporte de operadores
                        reporteOperadores = parser.reporteOperadores.map { it.toMap() }
                        
                        // Obtener reporte de estructuras de control
                        reporteEstructuras = parser.reporteEstructuras.map { it.toMap() }
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
                
                // Mostrar salida de MOSTRAR
                if (salida.isNotEmpty()) {
                    item {
                        Text(
                            text = "Salida:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    items(salida) { linea ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Text(
                                text = linea,
                                modifier = Modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontSize = 16.sp
                            )
                        }
                    }
                    
                    item {
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
                                    text = "${operador["tipo"]}: ${operador["operador"]}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Línea: ${operador["linea"]}, Columna: ${operador["columna"]}")
                                Text(text = "Expresión: ${operador["ocurrencia"]}")
                            }
                        }
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                
                // Mostrar reporte de estructuras de control
                if (reporteEstructuras.isNotEmpty()) {
                    item {
                        Text(
                            text = "Reporte de Estructuras de Control:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    items(reporteEstructuras) { estructura ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Estructura: ${estructura["tipo"]}",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Condición: ${estructura["condicion"]}")
                                Text(text = "Línea: ${estructura["linea"]}, Columna: ${estructura["columna"]}")
                            }
                        }
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
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
                if (resultado == null && errores.isEmpty() && salida.isEmpty()) {
                    item {
                        Text(
                            text = "Presione 'Evaluar' para ejecutar el código de ejemplo",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
