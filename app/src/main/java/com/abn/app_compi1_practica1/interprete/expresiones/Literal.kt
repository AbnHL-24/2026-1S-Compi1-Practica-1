package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Clase que representa un literal numérico (entero o decimal)
 */
class Literal(
    private val valor: Double
) : Expresion {
    
    override fun interpretar(entorno: Entorno): Any? {
        return valor
    }
    
    override fun toString(): String {
        return valor.toString()
    }
}
