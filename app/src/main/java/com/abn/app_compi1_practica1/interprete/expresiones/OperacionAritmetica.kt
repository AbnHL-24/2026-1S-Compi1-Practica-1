package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Enumeración para los operadores aritméticos
 */
enum class OperadorAritmetico {
    SUMA,
    RESTA,
    MULTIPLICACION,
    DIVISION,
    NEGACION_UNARIA
}

/**
 * Clase que representa una operación aritmética binaria o unaria
 */
class OperacionAritmetica @JvmOverloads constructor(
    private val operador: OperadorAritmetico,
    private val operandoIzq: Expresion,
    private val operandoDer: Expresion? = null
) : Expresion {
    
    override fun interpretar(entorno: Entorno): Any? {
        val izq = operandoIzq.interpretar(entorno)
        
        // Convertir a Double
        val valorIzq = when (izq) {
            is Double -> izq
            is Int -> izq.toDouble()
            is String -> izq.toDoubleOrNull() ?: throw RuntimeException("No se puede convertir '$izq' a número")
            else -> throw RuntimeException("Tipo no numérico en operación aritmética: $izq")
        }
        
        // Operación unaria (negación)
        if (operador == OperadorAritmetico.NEGACION_UNARIA) {
            return -valorIzq
        }
        
        // Operaciones binarias
        val der = operandoDer?.interpretar(entorno) 
            ?: throw RuntimeException("Operación aritmética requiere dos operandos")
        
        val valorDer = when (der) {
            is Double -> der
            is Int -> der.toDouble()
            is String -> der.toDoubleOrNull() ?: throw RuntimeException("No se puede convertir '$der' a número")
            else -> throw RuntimeException("Tipo no numérico en operación aritmética: $der")
        }
        
        return when (operador) {
            OperadorAritmetico.SUMA -> valorIzq + valorDer
            OperadorAritmetico.RESTA -> valorIzq - valorDer
            OperadorAritmetico.MULTIPLICACION -> valorIzq * valorDer
            OperadorAritmetico.DIVISION -> {
                if (valorDer == 0.0) {
                    throw RuntimeException("Error: División por cero")
                }
                valorIzq / valorDer
            }
            else -> throw RuntimeException("Operador aritmético no soportado: $operador")
        }
    }
    
    override fun toString(): String {
        return when (operador) {
            OperadorAritmetico.NEGACION_UNARIA -> "-$operandoIzq"
            else -> "($operandoIzq ${operadorToString()} $operandoDer)"
        }
    }
    
    private fun operadorToString(): String {
        return when (operador) {
            OperadorAritmetico.SUMA -> "+"
            OperadorAritmetico.RESTA -> "-"
            OperadorAritmetico.MULTIPLICACION -> "*"
            OperadorAritmetico.DIVISION -> "/"
            OperadorAritmetico.NEGACION_UNARIA -> "-"
        }
    }
}
