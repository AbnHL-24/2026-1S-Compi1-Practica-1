package com.abn.app_compi1_practica1.interprete.simbolo

/**
 * Clase que representa el entorno de ejecución (Tabla de Símbolos)
 * Almacena variables y sus valores durante la interpretación
 */
class Entorno(
    private val anterior: Entorno? = null
) {
    // Tabla de símbolos local
    private val tabla: MutableMap<String, Any?> = mutableMapOf()
    
    /**
     * Declara una nueva variable en el entorno actual
     */
    fun declarar(nombre: String, valor: Any?) {
        tabla[nombre] = valor
    }
    
    /**
     * Asigna un valor a una variable existente
     * Busca primero en el entorno actual, luego en los anteriores
     */
    fun asignar(nombre: String, valor: Any?): Boolean {
        if (tabla.containsKey(nombre)) {
            tabla[nombre] = valor
            return true
        }
        
        // Buscar en el entorno anterior (ámbito padre)
        return anterior?.asignar(nombre, valor) ?: false
    }
    
    /**
     * Obtiene el valor de una variable
     * Busca primero en el entorno actual, luego en los anteriores
     */
    fun obtener(nombre: String): Any? {
        if (tabla.containsKey(nombre)) {
            return tabla[nombre]
        }
        
        // Buscar en el entorno anterior (ámbito padre)
        return anterior?.obtener(nombre)
    }
    
    /**
     * Verifica si una variable existe en el entorno
     */
    fun existe(nombre: String): Boolean {
        if (tabla.containsKey(nombre)) {
            return true
        }
        
        return anterior?.existe(nombre) ?: false
    }
}
