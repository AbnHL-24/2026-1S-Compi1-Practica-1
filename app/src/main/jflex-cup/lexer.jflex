/* ========================================================================================
   ANALIZADOR LÉXICO - Expresiones Aritméticas, Relacionales y Lógicas
   Curso: Compiladores 1 - Práctica 1
   
   Este analizador reconoce:
   - Números enteros y decimales
   - Identificadores (variables)
   - Palabra reservada VAR para declaración de variables
   - Palabra reservada MOSTRAR para imprimir texto
   - Palabra reservada LEER para entrada de datos
   - Palabras reservadas SI, ENTONCES, SINO, FINSI para condicionales
   - Cadenas de texto entre comillas dobles
   - Operador de asignación =
   - Operadores aritméticos: + - * /
   - Operadores relacionales: == != < > <= >=
   - Operadores lógicos: && || !
   - Paréntesis para agrupar expresiones
   - Comentarios de una línea (empiezan con #)
   ======================================================================================== */

package com.abn.app_compi1_practica1.compiler;

// Importaciones
import java_cup.runtime.Symbol;
import java.util.LinkedList;

%%

// ========================================================================================
// CÓDIGO DE USUARIO
// ========================================================================================
%{
    // Lista para almacenar errores léxicos encontrados durante el análisis
    public LinkedList<String> erroresLexicos = new LinkedList<>();
    
    // Método auxiliar para agregar errores léxicos
    private void agregarError(String mensaje) {
        String error = String.format("Error Léxico [Línea: %d, Columna: %d] - %s: '%s'",
                                    yyline + 1, yycolumn + 1, mensaje, yytext());
        erroresLexicos.add(error);
    }
%}

// Inicialización del analizador
%init{
    yyline = 1;
    yycolumn = 1;
    erroresLexicos = new LinkedList<>();
%init}

// ========================================================================================
// OPCIONES DE JFLEX
// ========================================================================================
%cup                    // Integración con CUP
%class Lexer            // Nombre de la clase generada
%public                 // Clase pública
%line                   // Activar contador de líneas (yyline)
%column                 // Activar contador de columnas (yycolumn)
%char                   // Activar contador de caracteres (yychar)
%unicode                // Soporte Unicode completo

// ========================================================================================
// EXPRESIONES REGULARES
// ========================================================================================

// Espacios en blanco y saltos de línea
BLANCOS = [ \r\t\f\n]+

// Comentarios de una línea (empiezan con # y van hasta el final de la línea)
COMENTARIO = "#"[^\n]*

// Identificadores (variables)
ID = [a-zA-Z][a-zA-Z0-9_]*

// Palabras reservadas
VAR = "VAR"
MOSTRAR = "MOSTRAR"
LEER = "LEER"
SI = "SI"
ENTONCES = "ENTONCES"
SINO = "SINO"
FINSI = "FINSI"

// Cadenas de texto (entre comillas dobles, permite escapar comillas)
CADENA = [\"]((\\\")|[^\"\n]*)[\"]

// Números
ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+

// Operadores aritméticos
MAS = "+"
MENOS = "-"
MULT = "*"
DIV = "/"

// Operadores relacionales (IMPORTANTE: los compuestos primero)
IGUAL = "=="
DIFERENTE = "!="
MAYOR_IGUAL = ">="
MENOR_IGUAL = "<="
MAYOR = ">"
MENOR = "<"

// Asignación
ASIGNACION = "="

// Operadores lógicos
AND = "&&"
OR = "||"
NOT = "!"

// Símbolos especiales
PARENT_IZQ = "("
PARENT_DER = ")"

%%

// ========================================================================================
// REGLAS LÉXICAS
// ========================================================================================

// Ignorar espacios en blanco y comentarios
<YYINITIAL> {BLANCOS}       { /* Ignorar espacios en blanco */ }
<YYINITIAL> {COMENTARIO}    { /* Ignorar comentarios de una línea */ }

// Palabras reservadas (ANTES que los identificadores)
<YYINITIAL> {VAR}           { return new Symbol(sym.VAR, yyline, yycolumn, yytext()); }
<YYINITIAL> {MOSTRAR}       { return new Symbol(sym.MOSTRAR, yyline, yycolumn, yytext()); }
<YYINITIAL> {LEER}          { return new Symbol(sym.LEER, yyline, yycolumn, yytext()); }
<YYINITIAL> {SI}            { return new Symbol(sym.SI, yyline, yycolumn, yytext()); }
<YYINITIAL> {ENTONCES}      { return new Symbol(sym.ENTONCES, yyline, yycolumn, yytext()); }
<YYINITIAL> {SINO}          { return new Symbol(sym.SINO, yyline, yycolumn, yytext()); }
<YYINITIAL> {FINSI}         { return new Symbol(sym.FINSI, yyline, yycolumn, yytext()); }

// Cadenas de texto
<YYINITIAL> {CADENA}        { return new Symbol(sym.CADENA, yyline, yycolumn, yytext()); }

// Operadores lógicos (PRIMERO, para evitar conflictos con !)
<YYINITIAL> {AND}           { return new Symbol(sym.AND, yyline, yycolumn, yytext()); }
<YYINITIAL> {OR}            { return new Symbol(sym.OR, yyline, yycolumn, yytext()); }
<YYINITIAL> {NOT}           { return new Symbol(sym.NOT, yyline, yycolumn, yytext()); }

// Operadores relacionales (ANTES que los símbolos simples)
<YYINITIAL> {IGUAL}         { return new Symbol(sym.IGUAL, yyline, yycolumn, yytext()); }
<YYINITIAL> {DIFERENTE}     { return new Symbol(sym.DIFERENTE, yyline, yycolumn, yytext()); }
<YYINITIAL> {MAYOR_IGUAL}   { return new Symbol(sym.MAYOR_IGUAL, yyline, yycolumn, yytext()); }
<YYINITIAL> {MENOR_IGUAL}   { return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn, yytext()); }
<YYINITIAL> {MAYOR}         { return new Symbol(sym.MAYOR, yyline, yycolumn, yytext()); }
<YYINITIAL> {MENOR}         { return new Symbol(sym.MENOR, yyline, yycolumn, yytext()); }

// Asignación
<YYINITIAL> {ASIGNACION}    { return new Symbol(sym.ASIGNACION, yyline, yycolumn, yytext()); }

// Operadores aritméticos
<YYINITIAL> {MAS}           { return new Symbol(sym.MAS, yyline, yycolumn, yytext()); }
<YYINITIAL> {MENOS}         { return new Symbol(sym.MENOS, yyline, yycolumn, yytext()); }
<YYINITIAL> {MULT}          { return new Symbol(sym.MULT, yyline, yycolumn, yytext()); }
<YYINITIAL> {DIV}           { return new Symbol(sym.DIV, yyline, yycolumn, yytext()); }

// Paréntesis
<YYINITIAL> {PARENT_IZQ}    { return new Symbol(sym.PARENT_IZQ, yyline, yycolumn, yytext()); }
<YYINITIAL> {PARENT_DER}    { return new Symbol(sym.PARENT_DER, yyline, yycolumn, yytext()); }

// Números (DECIMAL primero, para que reconozca el punto)
<YYINITIAL> {DECIMAL}       { return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext()); }
<YYINITIAL> {ENTERO}        { return new Symbol(sym.ENTERO, yyline, yycolumn, yytext()); }

// Identificadores (AL FINAL, después de palabras reservadas)
<YYINITIAL> {ID}            { return new Symbol(sym.ID, yyline, yycolumn, yytext()); }

// Errores léxicos
<YYINITIAL> .               { 
                                agregarError("Símbolo no reconocido");
                                return new Symbol(sym.error, yyline, yycolumn, yytext());
                            }
