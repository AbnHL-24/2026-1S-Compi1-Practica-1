package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno
import com.abn.app_compi1_practica1.interprete.expresiones.Expresion

/**
 * Clase que representa la declaración de una variable
 * Sintaxis: VAR nombre = expresion  o  VAR nombre
 */
class Declaracion(
    private val nombre: String,
    private val valorInicial: Expresion? = null
) : Instruccion {
    
    override fun ejecutar(entorno: Entorno): Any? {
        // Evaluar la expresión inicial si existe
        val valor = if (valorInicial != null) {
            val resultado = valorInicial.interpretar(entorno)
            // Convertir a Double
            when (resultado) {
                is Double -> resultado
                is Int -> resultado.toDouble()
                else -> 0.0
            }
        } else {
            0.0
        }
        
        // Declarar la variable en el entorno
        entorno.declarar(nombre, valor)
        
        return "Variable '$nombre' declarada con valor: $valor"
    }
    
    override fun toString(): String {
        return if (valorInicial != null) {
            "VAR $nombre = $valorInicial"
        } else {
            "VAR $nombre"
        }
    }
}
