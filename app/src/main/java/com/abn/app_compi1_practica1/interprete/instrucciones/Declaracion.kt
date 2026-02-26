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
            valorInicial.interpretar(entorno)
        } else {
            null
        }
        
        // Declarar la variable en el entorno
        entorno.declarar(nombre, valor)
        
        // No retornar mensaje para evitar duplicados en la salida
        return null
    }
    
    override fun toString(): String {
        return if (valorInicial != null) {
            "VAR $nombre = $valorInicial"
        } else {
            "VAR $nombre"
        }
    }
}
