package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno
import com.abn.app_compi1_practica1.interprete.expresiones.Expresion

/**
 * Clase que representa la estructura condicional SI-ENTONCES-SINO-FINSI
 * Sintaxis: SI (condicion) ENTONCES bloque FINSI
 *           SI (condicion) ENTONCES bloque SINO bloque FINSI
 */
class Si(
    private val condicion: Expresion,
    private val bloqueVerdadero: List<Instruccion>,
    private val bloqueFalso: List<Instruccion>? = null
) : Instruccion {
    
    override fun ejecutar(entorno: Entorno): Any? {
        // Interpretar la condición
        val valorCondicion = condicion.interpretar(entorno)
        
        // Convertir a Boolean
        val resultado = when (valorCondicion) {
            is Boolean -> valorCondicion
            is Double -> valorCondicion != 0.0
            is Int -> valorCondicion != 0
            else -> false
        }
        
        // Ejecutar el bloque correspondiente
        if (resultado) {
            // Ejecutar bloque verdadero
            for (instruccion in bloqueVerdadero) {
                instruccion.ejecutar(entorno)
            }
        } else {
            // Ejecutar bloque falso si existe
            if (bloqueFalso != null) {
                for (instruccion in bloqueFalso) {
                    instruccion.ejecutar(entorno)
                }
            }
        }
        
        // No retornar mensaje para evitar duplicados en la salida
        return null
    }
    
    override fun toString(): String {
        return if (bloqueFalso != null) {
            "SI ($condicion) ENTONCES ... SINO ... FINSI"
        } else {
            "SI ($condicion) ENTONCES ... FINSI"
        }
    }
}
