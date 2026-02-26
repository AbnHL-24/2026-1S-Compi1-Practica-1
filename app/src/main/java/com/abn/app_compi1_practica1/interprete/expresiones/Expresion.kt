package com.abn.app_compi1_practica1.interprete.expresiones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Interfaz base para todas las expresiones
 * Toda expresión puede ser interpretada y devuelve un valor
 */
interface Expresion {
    /**
     * Interpreta la expresión en el contexto del entorno dado
     * @param entorno El entorno de ejecución (tabla de símbolos)
     * @return El valor resultante de interpretar la expresión
     */
    fun interpretar(entorno: Entorno): Any?
}
