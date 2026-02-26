# Referencia Rápida - Sintaxis del Lenguaje de Pseudocódigo

## 📝 Comentarios

**Formato correcto:** Usar `#` al inicio de la línea

```
# Este es un comentario válido
# Los comentarios empiezan con # (numeral/almohadilla)
```

❌ **Formato incorrecto:** NO usar `//`

```
// Esto NO es válido y genera error sintáctico
/* Esto tampoco es válido */
```

---

## 🔢 Declaración de Variables

```
VAR nombre = valor    # Declaración con inicialización
VAR contador          # Declaración sin inicialización (valor por defecto 0)
```

---

## ➕ Operadores

### Aritméticos
- `+` Suma
- `-` Resta
- `*` Multiplicación
- `/` División
- `-variable` Negación unaria

### Relacionales
- `==` Igual
- `!=` Diferente
- `<` Menor que
- `>` Mayor que
- `<=` Menor o igual
- `>=` Mayor o igual

### Lógicos
- `&&` AND lógico
- `||` OR lógico
- `!` NOT lógico

---

## 🖨️ Entrada/Salida

### MOSTRAR
```
MOSTRAR "texto"        # Muestra texto literal
MOSTRAR variable       # Muestra el valor de una variable
```

### LEER
```
LEER variable         # Lee un valor y lo asigna a la variable
```

---

## 🔀 Estructuras de Control

### Condicional SI-ENTONCES
```
SI (condicion) ENTONCES
    # instrucciones
FINSI
```

### Condicional SI-ENTONCES-SINO
```
SI (condicion) ENTONCES
    # instrucciones si verdadero
SINO
    # instrucciones si falso
FINSI
```

### Ciclo MIENTRAS
```
MIENTRAS (condicion) HACER
    # instrucciones
FINMIENTRAS
```

---

## ✅ Ejemplo Completo Válido

```
# Programa de ejemplo
VAR x = 10
VAR y = 5

# Operaciones aritméticas
VAR suma = x + y

# Condicional
SI (suma > 10) ENTONCES
    MOSTRAR "La suma es mayor que 10"
SINO
    MOSTRAR "La suma es menor o igual a 10"
FINSI

# Ciclo
VAR contador = 0
MIENTRAS (contador < 3) HACER
    MOSTRAR "Iteración: "
    MOSTRAR contador
    contador = contador + 1
FINMIENTRAS
```

---

## ⚠️ Errores Comunes

1. **Usar `//` para comentarios** ❌
   - Solución: Usar `#` ✅

2. **Olvidar FINSI o FINMIENTRAS** ❌
   - Cada SI debe terminar con FINSI
   - Cada MIENTRAS debe terminar con FINMIENTRAS

3. **No usar paréntesis en condiciones** ❌
   - Correcto: `SI (x > 5) ENTONCES` ✅
   - Incorrecto: `SI x > 5 ENTONCES` ❌

4. **Asignar sin declarar** ❌
   - Primero: `VAR x` o `VAR x = 10` ✅
   - Después: `x = 20` ✅

---

## 🎯 Palabras Reservadas

- `VAR` - Declaración de variables
- `MOSTRAR` - Salida
- `LEER` - Entrada
- `SI` - Condicional
- `ENTONCES` - Parte del condicional
- `SINO` - Alternativa del condicional
- `FINSI` - Fin del condicional
- `MIENTRAS` - Ciclo
- `HACER` - Parte del ciclo
- `FINMIENTRAS` - Fin del ciclo

---

**Nota:** Este lenguaje es sensible a mayúsculas. Todas las palabras reservadas deben escribirse en MAYÚSCULAS.
