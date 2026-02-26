package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Clase que representa la instrucción LEER para entrada de datos
 * Sintaxis: LEER variable
 */
class Leer(
    private val nombreVariable: String,
    private val entradaSimulada: Map<String, Double>,
    private val salida: MutableList<String>
) : Instruccion {
    
    override fun ejecutar(entorno: Entorno): Any? {
        // Obtener valor de entrada simulada
        val valor = entradaSimulada.getOrDefault(nombreVariable, 0.0)
        
        // Si la variable no existe, crearla
        if (!entorno.existe(nombreVariable)) {
            entorno.declarar(nombreVariable, valor)
        } else {
            // Si ya existe, asignarle el valor
            entorno.asignar(nombreVariable, valor)
        }
        
        // Agregar a la salida para que el usuario vea qué se leyó
        val mensaje = "LEER $nombreVariable = $valor"
        salida.add(mensaje)
        
        return mensaje
    }
    
    override fun toString(): String {
        return "LEER $nombreVariable"
    }
}
