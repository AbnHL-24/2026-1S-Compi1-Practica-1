# Manual Técnico - Intérprete de Pseudocódigo
## Práctica 1 - Compiladores 1

---

## 1. Descripción General

Aplicación Android desarrollada en Kotlin con Jetpack Compose que implementa un intérprete de pseudocódigo en español. Utiliza JFlex para análisis léxico y CUP para análisis sintáctico, con el patrón de diseño Intérprete (AST) para la ejecución del código.

### Tecnologías utilizadas:
- **Lenguaje:** Kotlin 1.9.0
- **Framework UI:** Jetpack Compose (Material Design 3)
- **Analizador Léxico:** JFlex 1.9.1
- **Analizador Sintáctico:** CUP 11b
- **Patrón de Diseño:** Intérprete (Abstract Syntax Tree)
- **Build System:** Gradle 8.0

---

## 2. Arquitectura del Sistema

### 2.1 Componentes Principales

```
app/
├── src/main/
│   ├── java/com/abn/app_compi1_practica1/
│   │   ├── MainActivity.kt              # Interfaz gráfica principal
│   │   ├── compiler/                    # Código generado por JFlex/CUP
│   │   │   ├── Lexer.java               # Analizador léxico (generado)
│   │   │   ├── Parser.java              # Analizador sintáctico (generado)
│   │   │   └── sym.java                 # Símbolos de tokens (generado)
│   │   └── interprete/                  # Patrón Intérprete
│   │       ├── simbolo/
│   │       │   └── Entorno.kt           # Tabla de símbolos
│   │       ├── expresiones/             # Nodos de expresiones
│   │       │   ├── Expresion.kt
│   │       │   ├── Literal.kt
│   │       │   ├── Variable.kt
│   │       │   ├── OperacionAritmetica.kt
│   │       │   ├── OperacionRelacional.kt
│   │       │   └── OperacionLogica.kt
│   │       └── instrucciones/           # Nodos de instrucciones
│   │           ├── Instruccion.kt
│   │           ├── Declaracion.kt
│   │           ├── Asignacion.kt
│   │           ├── Mostrar.kt
│   │           ├── Leer.kt
│   │           ├── Si.kt
│   │           └── Mientras.kt
│   └── jflex-cup/                       # Especificaciones de gramática
│       ├── lexer.jflex                  # Especificación léxica
│       └── parser.cup                   # Gramática sintáctica
└── build.gradle.kts                     # Configuración de build
```

---

## 3. Análisis Léxico (JFlex)

### 3.1 Archivo: `lexer.jflex`

**Tokens reconocidos:**

| Categoría | Tokens | Expresión Regular |
|-----------|--------|-------------------|
| **Palabras reservadas** | VAR, MOSTRAR, LEER, SI, ENTONCES, SINO, FINSI, MIENTRAS, HACER, FINMIENTRAS | Literales exactos |
| **Operadores aritméticos** | `+`, `-`, `*`, `/` | Caracteres especiales |
| **Operadores relacionales** | `==`, `!=`, `<`, `>`, `<=`, `>=` | Secuencias de caracteres |
| **Operadores lógicos** | `&&`, `\|\|`, `!` | Secuencias de caracteres |
| **Literales** | ENTERO, DECIMAL, CADENA | `[0-9]+`, `[0-9]+\.[0-9]+`, `\"[^\"]*\"` |
| **Identificadores** | ID | `[a-zA-Z_][a-zA-Z0-9_]*` |
| **Delimitadores** | `(`, `)`, `=` | Caracteres especiales |
| **Comentarios** | `#` | `#.*` (ignorado) |

**Manejo de errores:**
- Caracteres no reconocidos se reportan con línea y columna
- Lista `erroresLexicos` almacena todos los errores encontrados

---

## 4. Análisis Sintáctico (CUP)

### 4.1 Archivo: `parser.cup`

**Gramática libre de contexto:**

#### 4.1.1 Producciones principales

```
PROGRAMA ::= INSTRUCCIONES

INSTRUCCIONES ::= INSTRUCCIONES INSTRUCCION
                | INSTRUCCION

INSTRUCCION ::= DECLARACION
              | ASIGNACION_VAR
              | MOSTRAR_INST
              | LEER_INST
              | CONDICIONAL_SI
              | CICLO_MIENTRAS
              | EXPRESION

DECLARACION ::= VAR ID ASIGNACION EXPRESION
              | VAR ID

ASIGNACION_VAR ::= ID ASIGNACION EXPRESION

MOSTRAR_INST ::= MOSTRAR EXPRESION

LEER_INST ::= LEER ID

CONDICIONAL_SI ::= SI PARENT_IZQ EXPRESION PARENT_DER ENTONCES BLOQUE FINSI
                 | SI PARENT_IZQ EXPRESION PARENT_DER ENTONCES BLOQUE SINO BLOQUE FINSI

CICLO_MIENTRAS ::= MIENTRAS PARENT_IZQ EXPRESION PARENT_DER HACER BLOQUE FINMIENTRAS

BLOQUE ::= INSTRUCCIONES
```

#### 4.1.2 Expresiones unificadas

```
EXPRESION ::= MENOS EXPRESION %prec UMENOS              # Negación unaria
            | EXPRESION MAS EXPRESION                    # Suma
            | EXPRESION MENOS EXPRESION                  # Resta
            | EXPRESION MULT EXPRESION                   # Multiplicación
            | EXPRESION DIV EXPRESION                    # División
            | EXPRESION IGUAL EXPRESION                  # Igualdad
            | EXPRESION DIFERENTE EXPRESION              # Diferencia
            | EXPRESION MAYOR EXPRESION                  # Mayor que
            | EXPRESION MENOR EXPRESION                  # Menor que
            | EXPRESION MAYOR_IGUAL EXPRESION            # Mayor o igual
            | EXPRESION MENOR_IGUAL EXPRESION            # Menor o igual
            | EXPRESION AND EXPRESION                    # AND lógico
            | EXPRESION OR EXPRESION                     # OR lógico
            | NOT EXPRESION                              # NOT lógico
            | PARENT_IZQ EXPRESION PARENT_DER            # Agrupación
            | ID                                         # Variable
            | ENTERO                                     # Literal entero
            | DECIMAL                                    # Literal decimal
            | CADENA                                     # Literal cadena
```

#### 4.1.3 Precedencia de operadores

```
precedence left OR;                                      # Menor precedencia
precedence left AND;
precedence right NOT;
precedence left IGUAL, DIFERENTE, MAYOR, MENOR, MAYOR_IGUAL, MENOR_IGUAL;
precedence left MAS, MENOS;
precedence left MULT, DIV;
precedence right UMENOS;                                 # Mayor precedencia (negación unaria)
```

**Resultado de compilación:**
- 0 conflictos shift/reduce
- 0 conflictos reduce/reduce
- 40 producciones
- 78 estados

---

## 5. Patrón Intérprete (AST)

### 5.1 Jerarquía de Clases

#### 5.1.1 Expresiones

```kotlin
interface Expresion {
    fun interpretar(entorno: Entorno): Any?
}
```

**Clases que implementan:**
- `Literal`: Valores constantes (Double, String)
- `Variable`: Acceso a variables del entorno
- `OperacionAritmetica`: Suma, Resta, Multiplicación, División, Negación Unaria
- `OperacionRelacional`: Igualdad, Diferencia, Comparaciones
- `OperacionLogica`: AND, OR, NOT

**Retorno de tipos:**
- Aritméticas: `Double`
- Relacionales: `Boolean`
- Lógicas: `Boolean`
- Literales: `Double`, `String`, o `Boolean`

#### 5.1.2 Instrucciones

```kotlin
interface Instruccion {
    fun ejecutar(entorno: Entorno): Any?
}
```

**Clases que implementan:**
- `Declaracion`: Declara variable en el entorno
- `Asignacion`: Modifica variable existente
- `Mostrar`: Agrega texto a la lista de salida
- `Leer`: Lee valor de entrada simulada
- `Si`: Ejecución condicional (if-else)
- `Mientras`: Ciclo con condición

**Retorno:**
- Todas retornan `null` para evitar salida duplicada
- Solo `Mostrar` agrega a la lista `salida`

### 5.2 Tabla de Símbolos

```kotlin
class Entorno(private val padre: Entorno? = null) {
    private val tabla = mutableMapOf<String, Any?>()
    
    fun declarar(nombre: String, valor: Any?)
    fun asignar(nombre: String, valor: Any?)
    fun obtener(nombre: String): Any?
    fun existe(nombre: String): Boolean
}
```

**Características:**
- Soporte para ámbitos anidados (scopes)
- Búsqueda recursiva en ámbitos padres
- Almacena valores de tipo `Any?` (Boolean, Double, String, null)

---

## 6. Flujo de Ejecución

### 6.1 Proceso de Compilación e Interpretación

```
1. Usuario ingresa código → Editor de texto (MainActivity.kt)
                                    ↓
2. Clic en "Ejecutar" → Crear Lexer(StringReader(código))
                                    ↓
3. Crear Parser(lexer) → Inicializar entorno
                                    ↓
4. parser.parse() → Construye AST (sin ejecutar)
                                    ↓
5. PROGRAMA itera instrucciones → Ejecuta cada Instruccion.ejecutar(entorno)
                                    ↓
6. Mostrar.ejecutar() → Agrega a lista 'salida'
                                    ↓
7. MainActivity muestra → Lista de salida en UI
```

### 6.2 Ejemplo de ejecución

**Código fuente:**
```
VAR edad = 20
SI (edad >= 18) ENTONCES
    MOSTRAR "Mayor de edad"
FINSI
```

**AST generado:**
```
PROGRAMA
├── Declaracion("edad", Literal(20.0))
└── Si(
      condicion: OperacionRelacional(MAYOR_IGUAL, Variable("edad"), Literal(18.0)),
      bloqueVerdadero: [
          Mostrar(Literal("Mayor de edad"))
      ]
    )
```

**Ejecución:**
```
1. Declaracion.ejecutar():
   - entorno.declarar("edad", 20.0)

2. Si.ejecutar():
   - Evaluar condicion: edad >= 18 → true
   - Ejecutar bloqueVerdadero:
     - Mostrar.ejecutar(): salida.add("Mayor de edad")
```

**Salida:**
```
Mayor de edad
```

---

## 7. Reportes

### 7.1 Reporte de Operadores

**Estructura:**
```kotlin
data class ReporteOperador(
    val tipo: String,           // "Aritmético", "Relacional", "Lógico"
    val operador: String,       // "Suma (+)", "Mayor que (>)", etc.
    val linea: Int,
    val columna: Int,
    val expresion: String
)
```

**Generación:**
- Durante el parseo, se llama `parser.agregarOperador(...)` en cada producción de operador
- Se almacena en `parser.reporteOperadores`

### 7.2 Reporte de Estructuras de Control

**Estructura:**
```kotlin
data class ReporteEstructura(
    val estructura: String,     // "SI", "SI-SINO", "MIENTRAS"
    val condicion: String,
    val linea: Int,
    val columna: Int
)
```

**Generación:**
- Durante el parseo, se llama `parser.agregarEstructura(...)` al reconocer SI o MIENTRAS
- Se almacena en `parser.reporteEstructuras`

### 7.3 Reporte de Errores

**Tipos de errores:**
1. **Léxicos:** Caracteres no reconocidos (lexer.jflex)
2. **Sintácticos recuperables:** Tokens inesperados con recuperación
3. **Sintácticos no recuperables:** Errores fatales que detienen el parseo
4. **Semánticos:** Variable no declarada, operación inválida

---

## 8. Configuración del Proyecto

### 8.1 Tareas Gradle (build.gradle.kts)

```kotlin
// Generar parser con CUP
tasks.register<JavaExec>("generateCUP") {
    mainClass.set("java_cup.Main")
    args = listOf(
        "-destdir", "$buildDir/generated/source/jflex-cup/.../compiler",
        "-parser", "Parser",
        "-symbols", "sym",
        "$projectDir/src/main/jflex-cup/parser.cup"
    )
}

// Generar lexer con JFlex
tasks.register<JavaExec>("generateJFlex") {
    mainClass.set("jflex.Main")
    args = listOf(
        "-d", "$buildDir/generated/source/jflex-cup/.../compiler",
        "$projectDir/src/main/jflex-cup/lexer.jflex"
    )
}

// Tarea compuesta
tasks.register("generateCompiler") {
    dependsOn("generateCUP", "generateJFlex")
}
```

### 8.2 Comandos de Compilación

```bash
# Limpiar y generar parser/lexer
./gradlew clean generateCompiler

# Compilar e instalar APK
./gradlew assembleDebug installDebug

# Todo en un comando
./gradlew clean generateCompiler assembleDebug installDebug
```

---

## 9. Limitaciones Conocidas

1. **Ciclos infinitos:** Limitados a 10,000 iteraciones
2. **Entrada de usuario:** Valores simulados (no hay diálogo interactivo)
3. **Tipos de datos:** Solo Double, Boolean, String (no hay enteros separados)
4. **Declaración de variables:** No permite múltiples en una línea
5. **Bloques:** No hay llaves explícitas, se infieren por palabras clave

---

## 10. Extensiones Futuras

### Funcionalidades pendientes (según hoja de calificación):

1. **Lenguaje de configuración de diagramas (23 pts)**
   - Instrucciones `_si`, `_mientras`, `_bloque`
   - Configuración de colores RGB/hexadecimal
   - Expresiones numéricas para configuración

2. **Generación de diagramas de flujo (20 pts)**
   - Renderizado de figuras (rectángulos, rombos, óvalos)
   - Conexiones entre nodos
   - Aplicación de estilos configurados

3. **Mejoras en manejo de errores (5 pts)**
   - Recuperación de errores más robusta
   - Mensajes de error más descriptivos
   - Sugerencias de corrección

---

## 11. Bibliografía y Referencias

- **JFlex:** https://jflex.de/manual.html
- **CUP:** http://www2.cs.tum.edu/projects/cup/
- **Patrón Intérprete:** "Design Patterns" - Gamma, Helm, Johnson, Vlissides
- **CompScript:** Proyecto de referencia proporcionado en `context/compscript/`
- **Kotlin:** https://kotlinlang.org/docs/home.html
- **Jetpack Compose:** https://developer.android.com/jetpack/compose

---

**Autor:** Abner Alberto Hernández López
**Carnet:** 201730191
**Fecha:** Febrero 2026  
**Versión:** 1.0
