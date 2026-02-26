package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Enumeración para los operadores relacionales
 */
enum class OperadorRelacional {
    IGUAL,
    DIFERENTE,
    MAYOR,
    MENOR,
    MAYOR_IGUAL,
    MENOR_IGUAL
}

/**
 * Clase que representa una operación relacional
 */
class OperacionRelacional(
    private val operador: OperadorRelacional,
    private val operandoIzq: Expresion,
    private val operandoDer: Expresion
) : Expresion {
    
    override fun interpretar(entorno: Entorno): Any? {
        val izq = operandoIzq.interpretar(entorno)
        val der = operandoDer.interpretar(entorno)
        
        // Convertir a Double para comparaciones numéricas
        val valorIzq = when (izq) {
            is Double -> izq
            is Int -> izq.toDouble()
            is Boolean -> if (izq) 1.0 else 0.0
            is String -> izq.toDoubleOrNull() ?: throw RuntimeException("No se puede comparar '$izq'")
            else -> throw RuntimeException("Tipo no comparable: $izq")
        }
        
        val valorDer = when (der) {
            is Double -> der
            is Int -> der.toDouble()
            is Boolean -> if (der) 1.0 else 0.0
            is String -> der.toDoubleOrNull() ?: throw RuntimeException("No se puede comparar '$der'")
            else -> throw RuntimeException("Tipo no comparable: $der")
        }
        
        return when (operador) {
            OperadorRelacional.IGUAL -> valorIzq == valorDer
            OperadorRelacional.DIFERENTE -> valorIzq != valorDer
            OperadorRelacional.MAYOR -> valorIzq > valorDer
            OperadorRelacional.MENOR -> valorIzq < valorDer
            OperadorRelacional.MAYOR_IGUAL -> valorIzq >= valorDer
            OperadorRelacional.MENOR_IGUAL -> valorIzq <= valorDer
        }
    }
    
    override fun toString(): String {
        return "($operandoIzq ${operadorToString()} $operandoDer)"
    }
    
    private fun operadorToString(): String {
        return when (operador) {
            OperadorRelacional.IGUAL -> "=="
            OperadorRelacional.DIFERENTE -> "!="
            OperadorRelacional.MAYOR -> ">"
            OperadorRelacional.MENOR -> "<"
            OperadorRelacional.MAYOR_IGUAL -> ">="
            OperadorRelacional.MENOR_IGUAL -> "<="
        }
    }
}
