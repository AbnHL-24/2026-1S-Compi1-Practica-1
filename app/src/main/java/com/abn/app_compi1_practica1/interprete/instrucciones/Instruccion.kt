package com.abn.app_compi1_practica1.interprete.instrucciones

import com.abn.app_compi1_practica1.interprete.simbolo.Entorno

/**
 * Interfaz base para todas las instrucciones
 * Las instrucciones ejecutan acciones pero no necesariamente devuelven un valor
 */
interface Instruccion {
    /**
     * Ejecuta la instrucción en el contexto del entorno dado
     * @param entorno El entorno de ejecución (tabla de símbolos)
     * @return Resultado de la ejecución (puede ser null para instrucciones sin valor de retorno)
     */
    fun ejecutar(entorno: Entorno): Any?
}
