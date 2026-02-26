package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Clase que representa la instrucción MOSTRAR para imprimir texto
 * Sintaxis: MOSTRAR "texto"
 */
class Mostrar(
    private val texto: String,
    private val salida: MutableList<String>
) : Instruccion {
    
    override fun ejecutar(entorno: Entorno): Any? {
        // Procesar el texto (remover comillas y procesar escapes)
        var textoLimpio = texto
        
        // Remover comillas si existen
        if (textoLimpio.startsWith("\"") && textoLimpio.endsWith("\"")) {
            textoLimpio = textoLimpio.substring(1, textoLimpio.length - 1)
        }
        
        // Procesar secuencias de escape
        textoLimpio = textoLimpio.replace("\\\"", "\"")
        textoLimpio = textoLimpio.replace("\\n", "\n")
        textoLimpio = textoLimpio.replace("\\t", "\t")
        textoLimpio = textoLimpio.replace("\\\\", "\\")
        
        // Agregar a la salida
        salida.add(textoLimpio)
        
        return textoLimpio
    }
    
    override fun toString(): String {
        return "MOSTRAR $texto"
    }
}
