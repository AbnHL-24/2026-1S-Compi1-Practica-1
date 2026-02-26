package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno
import com.abn.app_compi1_practica1.interprete.expresiones.Expresion

/**
 * Clase que representa la instrucción MOSTRAR para imprimir texto
 * Sintaxis: MOSTRAR expresion
 */
class Mostrar(
    private val expresion: Expresion,
    private val salida: MutableList<String>
) : Instruccion {
    
    override fun ejecutar(entorno: Entorno): Any? {
        // Evaluar la expresión
        val valor = expresion.interpretar(entorno)
        
        // Convertir el valor a String
        var textoMostrar = when (valor) {
            null -> "null"
            is String -> {
                // Si es cadena literal, remover comillas y procesar escapes
                var texto = valor
                if (texto.startsWith("\"") && texto.endsWith("\"")) {
                    texto = texto.substring(1, texto.length - 1)
                }
                texto.replace("\\\"", "\"")
                     .replace("\\n", "\n")
                     .replace("\\t", "\t")
                     .replace("\\\\", "\\")
            }
            else -> valor.toString()
        }
        
        // Agregar a la salida
        salida.add(textoMostrar)
        
        // No retornar mensaje para evitar duplicados
        return null
    }
    
    override fun toString(): String {
        return "MOSTRAR $expresion"
    }
}
