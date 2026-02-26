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
        
        // Evaluar la expresión y asignar directamente
        // Permitir tipos nativos: Boolean, Double, String, null
        val resultado = valor.interpretar(entorno)
        
        // Asignar el valor preservando su tipo
        entorno.asignar(nombre, resultado)
        
        return "Variable '$nombre' = $resultado"
    }
    
    override fun toString(): String {
        return "$nombre = $valor"
    }
}
