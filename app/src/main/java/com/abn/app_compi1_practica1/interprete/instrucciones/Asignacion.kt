package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno
import com.abn.app_compi1_practica1.interprete.expresiones.Expresion

/**
 * Clase que representa la asignación de valor a una variable existente
 * Sintaxis: nombre = expresion
 */
class Asignacion(
    private val nombre: String,
    private val valor: Expresion
) : Instruccion {
    
    override fun ejecutar(entorno: Entorno): Any? {
        // Verificar que la variable existe
        if (!entorno.existe(nombre)) {
            throw RuntimeException("Error Semántico: La variable '$nombre' no ha sido declarada")
        }
        
        // Evaluar la expresión
        val resultado = valor.interpretar(entorno)
        
        // Convertir a Double
        val valorDouble = when (resultado) {
            is Double -> resultado
            is Int -> resultado.toDouble()
            else -> throw RuntimeException("La expresión no produce un valor numérico")
        }
        
        // Asignar el valor
        entorno.asignar(nombre, valorDouble)
        
        return "Variable '$nombre' = $valorDouble"
    }
    
    override fun toString(): String {
        return "$nombre = $valor"
    }
}
