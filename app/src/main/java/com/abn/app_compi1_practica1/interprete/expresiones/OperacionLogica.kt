package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Enumeración para los operadores lógicos
 */
enum class OperadorLogico {
    AND,
    OR,
    NOT
}

/**
 * Clase que representa una operación lógica
 */
class OperacionLogica @JvmOverloads constructor(
    private val operador: OperadorLogico,
    private val operandoIzq: Expresion,
    private val operandoDer: Expresion? = null
) : Expresion {
    
    override fun interpretar(entorno: Entorno): Any? {
        val izq = operandoIzq.interpretar(entorno)
        
        // Convertir a Boolean
        val valorIzq = when (izq) {
            is Boolean -> izq
            is Double -> izq != 0.0
            is Int -> izq != 0
            is String -> izq.isNotEmpty()
            else -> false
        }
        
        // Operación unaria (NOT)
        if (operador == OperadorLogico.NOT) {
            return !valorIzq
        }
        
        // Operaciones binarias (AND, OR)
        val der = operandoDer?.interpretar(entorno) 
            ?: throw RuntimeException("Operación lógica requiere dos operandos")
        
        val valorDer = when (der) {
            is Boolean -> der
            is Double -> der != 0.0
            is Int -> der != 0
            is String -> der.isNotEmpty()
            else -> false
        }
        
        return when (operador) {
            OperadorLogico.AND -> valorIzq && valorDer
            OperadorLogico.OR -> valorIzq || valorDer
            OperadorLogico.NOT -> !valorIzq
        }
    }
    
    override fun toString(): String {
        return when (operador) {
            OperadorLogico.NOT -> "!$operandoIzq"
            else -> "($operandoIzq ${operadorToString()} $operandoDer)"
        }
    }
    
    private fun operadorToString(): String {
        return when (operador) {
            OperadorLogico.AND -> "&&"
            OperadorLogico.OR -> "||"
            OperadorLogico.NOT -> "!"
        }
    }
}
