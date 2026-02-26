/* ========================================================================================
   ANALIZADOR LÉXICO - Calculadora Simple
   Generado por JFlex para el curso de Compiladores 1
   ======================================================================================== */

package com.abn.app_compi1_practica1.compiler;

import java_cup.runtime.Symbol;

%%

%class Lexer
%public
%line
%column
%cup
%unicode

%{
    // Método auxiliar para crear símbolos con información de posición
    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

/* Definiciones de patrones regulares */
WHITESPACE = [ \t\r\n]+
DIGIT = [0-9]
INTEGER = {DIGIT}+
DECIMAL = {INTEGER}\.{INTEGER}
NUMBER = {INTEGER}|{DECIMAL}
IDENTIFIER = [a-zA-Z][a-zA-Z0-9_]*

%%

/* Reglas léxicas */

/* Ignorar espacios en blanco */
{WHITESPACE}    { /* Ignorar */ }

/* Números */
{NUMBER}        { return symbol(sym.NUMBER, Double.parseDouble(yytext())); }

/* Operadores aritméticos */
"+"             { return symbol(sym.PLUS); }
"-"             { return symbol(sym.MINUS); }
"*"             { return symbol(sym.TIMES); }
"/"             { return symbol(sym.DIVIDE); }
"^"             { return symbol(sym.POWER); }

/* Paréntesis */
"("             { return symbol(sym.LPAREN); }
")"             { return symbol(sym.RPAREN); }

/* Identificadores (variables) */
{IDENTIFIER}    { return symbol(sym.IDENTIFIER, yytext()); }

/* Asignación */
"="             { return symbol(sym.EQUALS); }

/* Punto y coma */
";"             { return symbol(sym.SEMICOLON); }

/* Error: cualquier otro carácter */
.               { throw new Error("Carácter ilegal <" + yytext() + "> en línea " + (yyline + 1) + ", columna " + (yycolumn + 1)); }
