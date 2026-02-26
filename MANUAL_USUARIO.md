# Manual de Usuario - Intérprete de Pseudocódigo
## Práctica 1 - Compiladores 1

---

## 1. Introducción

Bienvenido al **Intérprete de Pseudocódigo**, una aplicación Android que te permite escribir y ejecutar código en pseudocódigo en español. Esta herramienta es ideal para aprender programación sin preocuparte por la sintaxis compleja de lenguajes reales.

### ¿Qué puedes hacer con esta aplicación?

- ✅ Declarar y usar variables
- ✅ Realizar operaciones matemáticas (+, -, *, /)
- ✅ Comparar valores (==, !=, <, >, <=, >=)
- ✅ Usar operadores lógicos (&&, ||, !)
- ✅ Crear estructuras condicionales (SI-ENTONCES-SINO)
- ✅ Crear ciclos repetitivos (MIENTRAS)
- ✅ Mostrar resultados en pantalla
- ✅ Leer valores de entrada
- ✅ Ver reportes de operadores y estructuras
- ✅ Detectar errores en tu código

---

## 2. Interfaz de la Aplicación

### 2.1 Pantalla Principal

La aplicación tiene 3 pestañas principales:

```
┌─────────────────────────────────────┐
│  [Editor]                           │
├─────────────────────────────────────┤
│  (Boton ejecutar)                   │
├─────────────────────────────────────┤
│  [Salida] [Reportes]                │
└─────────────────────────────────────┘

## 3. Sintaxis del Pseudocódigo

### 3.1 Comentarios

Los comentarios se escriben con `#` al inicio de la línea:

```
# Esto es un comentario
# Los comentarios no se ejecutan
VAR x = 10  # También pueden ir al final de una línea
```

### 3.2 Variables

#### Declarar variables:

```
VAR nombre = valor
```

**Ejemplos:**
```
VAR edad = 25
VAR precio = 19.99
VAR nombre = "Juan"
VAR activo = 10 > 5    # activo vale 'true'
```

#### Asignar valores a variables existentes:

```
edad = 26
precio = 20.50
```

⚠️ **Importante:** La variable debe existir antes de asignarle un valor.

### 3.3 Tipos de Datos

El intérprete maneja automáticamente estos tipos:

| Tipo | Descripción | Ejemplo |
|------|-------------|---------|
| **Número** | Enteros o decimales | `10`, `3.14`, `-5` |
| **Cadena** | Texto entre comillas | `"Hola mundo"` |
| **Booleano** | Resultado de comparaciones | `true`, `false` |

### 3.4 Operadores

#### Operadores Aritméticos:

```
VAR suma = 5 + 3        # 8
VAR resta = 10 - 4      # 6
VAR mult = 6 * 7        # 42
VAR div = 20 / 4        # 5.0
VAR negativo = -5       # -5
```

#### Operadores Relacionales:

```
VAR igual = 5 == 5          # true
VAR diferente = 5 != 3      # true
VAR mayor = 10 > 5          # true
VAR menor = 3 < 8           # true
VAR mayorIgual = 5 >= 5     # true
VAR menorIgual = 4 <= 6     # true
```

#### Operadores Lógicos:

```
VAR y = true && false       # false (ambos deben ser true)
VAR o = true || false       # true (al menos uno debe ser true)
VAR no = !true              # false (invierte el valor)
```

#### Precedencia de Operadores (de mayor a menor):

1. Negación unaria: `-5`
2. Multiplicación y división: `*`, `/`
3. Suma y resta: `+`, `-`
4. Comparaciones: `<`, `>`, `<=`, `>=`
5. Igualdad: `==`, `!=`
6. NOT lógico: `!`
7. AND lógico: `&&`
8. OR lógico: `||`

**Usa paréntesis para cambiar el orden:**
```
VAR resultado = (5 + 3) * 2    # 16, no 11
```

### 3.5 Mostrar en Pantalla

```
MOSTRAR expresion
```

**Ejemplos:**
```
MOSTRAR "Hola mundo"
MOSTRAR edad
MOSTRAR 5 + 3
MOSTRAR edad * 2
```

### 3.6 Leer Valores

```
LEER nombre_variable
```

**Ejemplo:**
```
VAR numero = 0
LEER numero
MOSTRAR numero
```

⚠️ **Nota:** En esta versión, los valores se leen de una lista predefinida:
- `x` = 15
- `y` = 25
- `numero` = 42
- `n` = 10
- `contador` = 0

### 3.7 Condicionales (SI-ENTONCES-SINO)

#### Sintaxis básica:

```
SI (condicion) ENTONCES
    instrucciones
FINSI
```

**Ejemplo:**
```
VAR edad = 20

SI (edad >= 18) ENTONCES
    MOSTRAR "Eres mayor de edad"
FINSI
```

#### Con alternativa (SINO):

```
SI (condicion) ENTONCES
    instrucciones_si_verdadero
SINO
    instrucciones_si_falso
FINSI
```

**Ejemplo:**
```
VAR nota = 75

SI (nota >= 60) ENTONCES
    MOSTRAR "Aprobado"
SINO
    MOSTRAR "Reprobado"
FINSI
```

### 3.8 Ciclos (MIENTRAS)

```
MIENTRAS (condicion) HACER
    instrucciones
FINMIENTRAS
```

**Ejemplo:**
```
VAR contador = 1

MIENTRAS (contador <= 5) HACER
    MOSTRAR contador
    contador = contador + 1
FINMIENTRAS
```

**Resultado:**
```
1.0
2.0
3.0
4.0
5.0
```

⚠️ **Advertencia:** Los ciclos tienen un límite de 10,000 iteraciones para evitar ciclos infinitos.

---

## 4. Ejemplos Completos

### Ejemplo 1: Calculadora Simple

```
# Programa: Calculadora de suma y promedio
VAR num1 = 10
VAR num2 = 20
VAR suma = num1 + num2
VAR promedio = suma / 2

MOSTRAR "Primer número: "
MOSTRAR num1
MOSTRAR "Segundo número: "
MOSTRAR num2
MOSTRAR "Suma: "
MOSTRAR suma
MOSTRAR "Promedio: "
MOSTRAR promedio
```

**Salida:**
```
Primer número: 
10.0
Segundo número: 
20.0
Suma: 
30.0
Promedio: 
15.0
```

### Ejemplo 2: Determinar Mayor de Dos Números

```
# Programa: Comparar dos números
VAR a = 25
VAR b = 18

SI (a > b) ENTONCES
    MOSTRAR "El primer número es mayor"
SINO
    MOSTRAR "El segundo número es mayor o son iguales"
FINSI
```

**Salida:**
```
El primer número es mayor
```

### Ejemplo 3: Tabla de Multiplicar

```
# Programa: Tabla del 5
VAR numero = 5
VAR contador = 1

MIENTRAS (contador <= 10) HACER
    VAR resultado = numero * contador
    MOSTRAR resultado
    contador = contador + 1
FINMIENTRAS
```

**Salida:**
```
5.0
10.0
15.0
20.0
25.0
30.0
35.0
40.0
45.0
50.0
```

### Ejemplo 4: Verificar Edad y Descuento

```
# Programa: Sistema de descuentos
VAR edad = 65
VAR precio = 100
VAR descuento = 0

SI (edad >= 60) ENTONCES
    descuento = precio * 0.5
    MOSTRAR "Descuento de adulto mayor: 50%"
SINO
    SI (edad < 18) ENTONCES
        descuento = precio * 0.25
        MOSTRAR "Descuento de menor de edad: 25%"
    SINO
        MOSTRAR "Sin descuento"
    FINSI
FINSI

VAR precioFinal = precio - descuento
MOSTRAR "Precio final: "
MOSTRAR precioFinal
```

### Ejemplo 5: Suma de Números Pares

```
# Programa: Sumar números pares del 1 al 10
VAR suma = 0
VAR numero = 1

MIENTRAS (numero <= 10) HACER
    VAR residuo = numero - ((numero / 2) * 2)
    SI (residuo == 0) ENTONCES
        suma = suma + numero
    FINSI
    numero = numero + 1
FINMIENTRAS

MOSTRAR "Suma de pares del 1 al 10: "
MOSTRAR suma
```

---

## 5. Guía Paso a Paso

### 5.1 Escribir tu Primer Programa

**Paso 1:** Abre la aplicación

**Paso 2:** Asegúrate de estar en la pestaña **"Editor"**

**Paso 3:** Escribe este código:
```
VAR nombre = "Maria"
MOSTRAR "Hola "
MOSTRAR nombre
```

**Paso 4:** Presiona el botón **"Ejecutar Código"**

**Paso 5:** Cambia a la pestaña **"Salida"** para ver el resultado:
```
Hola 
Maria
```

### 5.2 Ver Reportes

**Paso 1:** Escribe código con operaciones:
```
VAR x = 10
VAR y = 5
VAR suma = x + y
VAR esMayor = x > y

SI (esMayor) ENTONCES
    MOSTRAR "X es mayor"
FINSI
```

**Paso 2:** Ejecuta el código

**Paso 3:** Ve a la pestaña **"Reportes"**

**Paso 4:** Observa:
- **Reporte de Operadores:** Suma (+), Mayor que (>)
- **Reporte de Estructuras:** SI con su condición

### 5.3 Corregir Errores

Si tu código tiene errores, aparecerán en la pestaña **"Salida"**.

**Error común 1: Variable no declarada**
```
x = 10  # ❌ Error: x no existe
```
**Corrección:**
```
VAR x = 10  # ✅ Primero declarar
```

**Error común 2: Falta FINSI**
```
SI (x > 5) ENTONCES
    MOSTRAR x
# ❌ Falta FINSI
```
**Corrección:**
```
SI (x > 5) ENTONCES
    MOSTRAR x
FINSI  # ✅ Cerrar la estructura
```

**Error común 3: Comillas sin cerrar**
```
MOSTRAR "Hola  # ❌ Falta comilla final
```
**Corrección:**
```
MOSTRAR "Hola"  # ✅ Cerrar comillas
```

---

## 6. Consejos y Buenas Prácticas

### ✅ Recomendaciones:

1. **Usa nombres descriptivos para variables:**
   - ✅ Bueno: `VAR precioTotal = 100`
   - ❌ Malo: `VAR x = 100`

2. **Indenta tu código (espacios al inicio):**
   ```
   SI (edad > 18) ENTONCES
       MOSTRAR "Mayor"    # ← Usa espacios para mejor lectura
   FINSI
   ```

3. **Agrega comentarios explicativos:**
   ```
   # Calcular el IVA del 12%
   VAR iva = precio * 0.12
   ```

4. **Declara variables al inicio:**
   ```
   VAR edad = 0
   VAR nombre = ""
   VAR activo = true
   
   # ... resto del código
   ```

5. **Prueba con valores pequeños primero:**
   - Antes de hacer `MIENTRAS (i <= 1000)`, prueba con `MIENTRAS (i <= 5)`

### ⚠️ Advertencias:

- ❌ No uses acentos en nombres de variables: `VAR edád` → `VAR edad`
- ❌ No uses espacios en nombres: `VAR precio total` → `VAR precioTotal`
- ❌ Cuida las mayúsculas: `var edad` debe ser `VAR edad`
- ❌ No olvides cerrar estructuras: Cada `SI` necesita su `FINSI`

---

## 7. Limitaciones Actuales

1. **Entrada interactiva:** Los valores de `LEER` están predefinidos
2. **Ciclos:** Máximo 10,000 iteraciones
3. **Tipos:** No hay conversión manual entre tipos
4. **Arrays:** No soportados en esta versión
5. **Funciones:** No se pueden crear funciones propias

---

## 8. Preguntas Frecuentes (FAQ)

**P: ¿Puedo usar números con decimales?**  
R: Sí, usa el punto como separador: `VAR precio = 19.99`

**P: ¿Cómo hago un SI dentro de otro SI?**  
R: Anida las estructuras:
```
SI (edad >= 18) ENTONCES
    SI (tieneLicencia) ENTONCES
        MOSTRAR "Puede conducir"
    FINSI
FINSI
```

**P: ¿Por qué mi variable muestra "true" en lugar de 1?**  
R: Es correcto. Las comparaciones retornan valores booleanos (true/false).

**P: ¿Cómo detengo un ciclo antes de que termine?**  
R: Cambia la condición del MIENTRAS:
```
VAR seguir = true
MIENTRAS (seguir) HACER
    # ... código
    SI (algunaCondicion) ENTONCES
        seguir = false  # Esto detendrá el ciclo
    FINSI
FINMIENTRAS
```

**P: ¿Puedo concatenar cadenas?**  
R: No directamente, pero puedes usar múltiples MOSTRAR:
```
MOSTRAR "Hola "
MOSTRAR nombre
```

---

## 9. Soporte y Contacto

Si encuentras problemas o tienes sugerencias:

- 📧 **Email:** abneralberto-hernandezlopez@cunoc.edu.gt
- 📚 **Documentación técnica:** Ver `MANUAL_TECNICO.md`
- 💻 **Código fuente:** https://github.com/AbnHL-24/2026-1S-Compi1-Practica-1

---

**¡Gracias por usar el Intérprete de Pseudocódigo!**

**Versión:** 1.0  
**Fecha:** Febrero 2026  
**Curso:** Compiladores 1
