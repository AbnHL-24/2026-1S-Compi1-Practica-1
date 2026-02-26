package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno
import com.abn.app_compi1_practica1.interprete.expresiones.Expresion

/**
 * Clase que representa la estructura de ciclo MIENTRAS
 * Sintaxis: MIENTRAS (condicion) HACER bloque FINMIENTRAS
 */
class Mientras(
    private val condicion: Expresion,
    private val bloque: List<Instruccion>
) : Instruccion {
    
    companion object {
        // Límite de seguridad para evitar ciclos infinitos
        const val MAX_ITERACIONES = 10000
    }
    
    override fun ejecutar(entorno: Entorno): Any? {
        var iteraciones = 0
        
        // Evaluar la condición
        var valorCondicion = condicion.interpretar(entorno)
        var resultado = convertirABooleano(valorCondicion)
        
        // Ejecutar el ciclo mientras la condición sea verdadera
        while (resultado) {
            // Verificar límite de seguridad
            if (iteraciones >= MAX_ITERACIONES) {
                throw RuntimeException("Error: Ciclo MIENTRAS excedió el límite de $MAX_ITERACIONES iteraciones (posible ciclo infinito)")
            }
            
            // Ejecutar cada instrucción del bloque
            for (instruccion in bloque) {
                instruccion.ejecutar(entorno)
            }
            
            iteraciones++
            
            // Re-evaluar la condición para la siguiente iteración
            valorCondicion = condicion.interpretar(entorno)
            resultado = convertirABooleano(valorCondicion)
        }
        
        // No retornar mensaje para evitar duplicados en la salida
        return null
    }
    
    /**
     * Convierte un valor a Boolean según las reglas del intérprete
     */
    private fun convertirABooleano(valor: Any?): Boolean {
        return when (valor) {
            is Boolean -> valor
            is Double -> valor != 0.0
            is Int -> valor != 0
            else -> false
        }
    }
    
    override fun toString(): String {
        return "MIENTRAS ($condicion) HACER ... FINMIENTRAS"
    }
}
