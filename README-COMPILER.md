# 🔧 Integración de JFlex y CUP en Android/Kotlin

Este proyecto demuestra cómo integrar **JFlex** (analizador léxico) y **Java CUP** (analizador sintáctico) en una aplicación Android con Kotlin, generando automáticamente las clases Java necesarias durante el proceso de compilación.

---

## 📋 Tabla de Contenidos

- [Estructura del Proyecto](#-estructura-del-proyecto)
- [¿Cómo Funciona?](#-cómo-funciona)
- [Cómo Usar](#-cómo-usar)
- [Tareas Gradle Disponibles](#-tareas-gradle-disponibles)
- [Modificar la Gramática](#-modificar-la-gramática)
- [Ejemplo de Uso en Kotlin](#-ejemplo-de-uso-en-kotlin)
- [Troubleshooting](#-troubleshooting)

---

## 📁 Estructura del Proyecto

```
app/
├── src/
│   └── main/
│       ├── jflex-cup/              ← Archivos fuente (EDITAR AQUÍ)
│       │   ├── lexer.jflex         ← Definición del analizador léxico
│       │   └── parser.cup          ← Definición del analizador sintáctico
│       └── java/
│           └── com/abn/app_compi1_practica1/
│               ├── MainActivity.kt  ← Ejemplo de uso
│               └── compiler/        ← (Generado automáticamente)
│                   ├── Lexer.java
│                   ├── Parser.java
│                   └── sym.java
├── libs/
│   ├── jflex-full-1.9.1.jar       ← Herramienta JFlex
│   └── java-cup-11b.jar           ← Herramienta Java CUP
└── build.gradle.kts               ← Configuración de tareas automáticas
```

---

## ⚙️ ¿Cómo Funciona?

### Flujo Automático

1. **Editas** los archivos `.jflex` y `.cup` en `app/src/main/jflex-cup/`
2. **Compilas** el proyecto (Build → Make Project)
3. **Gradle ejecuta automáticamente:**
   - `generateCUP` → Genera `Parser.java` y `sym.java`
   - `generateJFlex` → Genera `Lexer.java` (necesita `sym.java`)
4. **Las clases generadas** se colocan en `build/generated/source/jflex-cup/`
5. **Android Studio** reconoce automáticamente las clases generadas
6. **Puedes usar** las clases desde tu código Kotlin

### Ventajas de este Enfoque

✅ **Cero copiar/pegar** - Todo se genera automáticamente  
✅ **Integrado con Gradle** - Parte del ciclo de compilación normal  
✅ **Compatible con Git** - Solo versionas los `.jflex` y `.cup`, no el código generado  
✅ **IntelliSense funciona** - Android Studio reconoce las clases generadas  
✅ **Reproducible** - Otros desarrolladores pueden compilar sin pasos manuales  

---

## 🚀 Cómo Usar

### Primera Compilación

1. **Sincronizar el proyecto:**
   ```
   File → Sync Project with Gradle Files
   ```

2. **Compilar el proyecto:**
   ```
   Build → Make Project
   ```
   O desde la terminal:
   ```bash
   ./gradlew build
   ```

3. **Verificar archivos generados:**
   - Busca en `app/build/generated/source/jflex-cup/com/abn/app_compi1_practica1/compiler/`
   - Deberías ver: `Lexer.java`, `Parser.java`, `sym.java`

### Uso en Código Kotlin

```kotlin
import com.abn.app_compi1_practica1.compiler.Lexer
import com.abn.app_compi1_practica1.compiler.Parser
import java.io.StringReader

fun evaluarExpresion(expresion: String): Any? {
    val lexer = Lexer(StringReader(expresion))
    val parser = Parser(lexer)
    val resultado = parser.parse()
    return resultado.value
}

// Ejemplos:
val resultado1 = evaluarExpresion("2 + 3 * 4")        // 14.0
val resultado2 = evaluarExpresion("(2 + 3) * 4")      // 20.0
val resultado3 = evaluarExpresion("2 ^ 3")            // 8.0
val resultado4 = evaluarExpresion("x = 10; x * 2")    // 20.0
```

---

## 🛠️ Tareas Gradle Disponibles

Puedes ejecutar estas tareas desde Android Studio o la terminal:

### `generateCUP`

Genera el analizador sintáctico (Parser) a partir de `parser.cup`

```bash
./gradlew generateCUP
```

**Genera:**
- `Parser.java` - Analizador sintáctico
- `sym.java` - Símbolos terminales y no terminales

### `generateJFlex`

Genera el analizador léxico (Lexer) a partir de `lexer.jflex`

```bash
./gradlew generateJFlex
```

**Genera:**
- `Lexer.java` - Analizador léxico (scanner)

**Nota:** Depende de `generateCUP` porque necesita `sym.java`

### `generateCompiler`

Ejecuta ambas tareas (CUP + JFlex) en el orden correcto

```bash
./gradlew generateCompiler
```

**Recomendado:** Usa esta tarea para regenerar todo el compilador

### Integración Automática

Las tareas se ejecutan automáticamente antes de `preBuild`, por lo que normalmente **no necesitas ejecutarlas manualmente**. Simplemente compila el proyecto y listo.

---

## ✏️ Modificar la Gramática

### Editar el Analizador Léxico (Lexer)

1. Abre `app/src/main/jflex-cup/lexer.jflex`
2. Modifica las reglas léxicas (tokens, patrones regex)
3. Guarda el archivo
4. Compila el proyecto → JFlex se ejecuta automáticamente

**Ejemplo:** Agregar un nuevo operador módulo `%`

```jflex
/* En la sección de reglas */
"%"             { return symbol(sym.MOD); }
```

### Editar el Analizador Sintáctico (Parser)

1. Abre `app/src/main/jflex-cup/parser.cup`
2. Modifica la gramática (terminales, no terminales, producciones)
3. Guarda el archivo
4. Compila el proyecto → CUP se ejecuta automáticamente

**Ejemplo:** Agregar la operación módulo

```cup
/* En terminales */
terminal MOD;

/* En precedencia */
precedence left MOD;

/* En producciones */
expr ::=
    expr:e1 MOD expr:e2
    {: RESULT = e1 % e2; :}
    ;
```

### Flujo de Trabajo Recomendado

1. **Edita** `parser.cup` y `lexer.jflex`
2. **Compila** (Build → Make Project)
3. **Revisa errores** en el Build Output
4. **Prueba** desde `MainActivity.kt`
5. **Itera** según sea necesario

---

## 📖 Ejemplo de Uso en Kotlin

Ver `MainActivity.kt` para un ejemplo completo. Aquí un resumen:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Ejemplo: Calculadora simple
        val resultado = evaluarExpresion("2 + 3 * 4")
        println("Resultado: $resultado")  // 14.0
        
        // Ejemplo: Variables
        val parser = crearParser("x = 10; y = 20; x + y")
        val res = parser.parse().value
        println("x + y = $res")  // 30.0
        
        // Acceder a la tabla de símbolos
        val variables = parser.getSymbolTable()
        println("Variables: $variables")  // {x=10.0, y=20.0}
    }
    
    private fun evaluarExpresion(expresion: String): Any? {
        val lexer = Lexer(StringReader(expresion))
        val parser = Parser(lexer)
        return parser.parse().value
    }
    
    private fun crearParser(expresion: String): Parser {
        val lexer = Lexer(StringReader(expresion))
        return Parser(lexer)
    }
}
```

---

## 🔧 Troubleshooting

### Problema: "Unresolved reference: Lexer"

**Causa:** Las clases no se han generado o Android Studio no las reconoce

**Solución:**
1. Ejecuta `./gradlew generateCompiler`
2. Sincroniza Gradle: `File → Sync Project with Gradle Files`
3. Invalida caché: `File → Invalidate Caches → Invalidate and Restart`

### Problema: Error en `generateCUP`

**Causa:** Error de sintaxis en `parser.cup`

**Solución:**
1. Revisa el Build Output para ver el error específico
2. Verifica la sintaxis de CUP (terminales, precedencia, producciones)
3. Consulta la [documentación de CUP](http://www2.cs.tum.edu/projects/cup/)

### Problema: Error en `generateJFlex`

**Causa:** Error de sintaxis en `lexer.jflex`

**Solución:**
1. Revisa el Build Output para ver el error específico
2. Verifica los patrones regex y las acciones
3. Consulta la [documentación de JFlex](https://jflex.de/manual.html)

### Problema: "Symbol not found: sym.XXX"

**Causa:** El token no está definido en `parser.cup`

**Solución:**
1. Agrega el terminal en la sección `terminal` de `parser.cup`
2. Regenera con `./gradlew generateCompiler`

### Problema: Las clases generadas desaparecen

**Causa:** Ejecutaste `./gradlew clean`

**Solución:**
1. Es normal, `clean` borra todo `build/`
2. Simplemente recompila: `./gradlew build`

---

## 📚 Referencias

- **JFlex:** https://jflex.de/
- **Java CUP:** http://www2.cs.tum.edu/projects/cup/
- **Gradle:** https://gradle.org/

---

## 📝 Notas Importantes

### Control de Versiones (Git)

- ✅ **Versionar:** `lexer.jflex`, `parser.cup`, archivos `.jar` en `libs/`
- ❌ **NO versionar:** Código generado en `build/generated/`

El `.gitignore` ya está configurado correctamente.

### Compatibilidad

- **JFlex:** 1.9.1
- **Java CUP:** 11b
- **JDK:** 11+
- **Kotlin:** Compatible con todas las clases Java generadas
- **Android:** Todas las versiones

### Performance

La generación de las clases solo ocurre cuando:
- Los archivos `.jflex` o `.cup` cambian
- Ejecutas `clean` y luego `build`

Gradle cachea los resultados, así que builds subsecuentes son muy rápidos.

---

## 🎓 Para el Curso de Compiladores

Este setup es ideal para proyectos de compiladores porque:

1. **Profesional:** Así se hace en proyectos reales
2. **Educativo:** Te enfocas en la gramática, no en copiar archivos
3. **Escalable:** Fácil agregar más analizadores o módulos
4. **Reproducible:** Funciona igual para todos los estudiantes
5. **Android-ready:** Puedes crear apps de compiladores móviles

### Sugerencias para el Proyecto

- Usa `parser.cup` para definir la gramática de tu lenguaje
- Usa `lexer.jflex` para tokenizar el código fuente
- Agrega manejo de errores personalizado en las acciones semánticas
- Implementa una tabla de símbolos en el parser
- Crea una interfaz Android para probar tu compilador

---

**¡Éxito con tu proyecto de compiladores!** 🚀

Si tienes dudas, revisa los archivos generados en `build/generated/source/jflex-cup/` para entender cómo JFlex y CUP traducen tus definiciones a código Java.
