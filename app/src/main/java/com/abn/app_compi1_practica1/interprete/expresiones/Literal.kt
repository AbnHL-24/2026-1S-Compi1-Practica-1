package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Clase que representa un literal (numérico o cadena de texto)
 */
class Literal(
    private val valor: Any
) : Expresion {
    
    override fun interpretar(entorno: Entorno): Any? {
        return valor
    }
    
    override fun toString(): String {
        return valor.toString()
    }
}
