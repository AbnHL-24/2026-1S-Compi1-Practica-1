package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Clase que representa el acceso a una variable
 */
class Variable(
    private val nombre: String
) : Expresion {
    
    override fun interpretar(entorno: Entorno): Any? {
        if (!entorno.existe(nombre)) {
            throw RuntimeException("Error Semántico: La variable '$nombre' no ha sido declarada")
        }
        return entorno.obtener(nombre)
    }
    
    override fun toString(): String {
        return nombre
    }
}
