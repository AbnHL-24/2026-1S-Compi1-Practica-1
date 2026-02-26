plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.abn.app_compi1_practica1"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.abn.app_compi1_practica1"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    
    // Java CUP Runtime - Necesario para ejecutar el parser generado
    implementation("com.github.vbmacher:java-cup-runtime:11b")
}

// ==================================================================================
// CONFIGURACIÓN DE JFLEX Y CUP - GENERACIÓN AUTOMÁTICA DE COMPILADORES
// ==================================================================================

// Directorios
val jflexCupSourceDir = file("src/main/jflex-cup")
val generatedSourceDir = file("${layout.buildDirectory.get()}/generated/source/jflex-cup")
val jflexOutputDir = file("$generatedSourceDir/com/abn/app_compi1_practica1/compiler")
val cupOutputDir = file("$generatedSourceDir/com/abn/app_compi1_practica1/compiler")

// Archivos fuente
val lexerFile = file("$jflexCupSourceDir/lexer.jflex")
val parserFile = file("$jflexCupSourceDir/parser.cup")

// JARs de las herramientas
val jflexJar = file("libs/jflex-full-1.9.1.jar")
val cupJar = file("libs/java-cup-11b.jar")

// ==================================================================================
// TAREA: Generar Parser con Java CUP
// ==================================================================================
val generateCUP by tasks.register<JavaExec>("generateCUP") {
    description = "Genera el parser con Java CUP"
    group = "compiler"
    
    inputs.file(parserFile)
    outputs.dir(cupOutputDir)
    
    doFirst {
        cupOutputDir.mkdirs()
        println("🔨 Generando parser con CUP desde: ${parserFile.absolutePath}")
        println("📁 Salida en: ${cupOutputDir.absolutePath}")
    }
    
    mainClass.set("java_cup.Main")
    classpath = files(cupJar)
    args = listOf(
        "-destdir", cupOutputDir.absolutePath,
        "-parser", "Parser",
        "-symbols", "sym",
        parserFile.absolutePath
    )
    
    doLast {
        println("✅ Parser generado exitosamente")
        println("   - Parser.java")
        println("   - sym.java")
    }
}

// ==================================================================================
// TAREA: Generar Lexer con JFlex
// ==================================================================================
val generateJFlex by tasks.register<JavaExec>("generateJFlex") {
    description = "Genera el lexer con JFlex"
    group = "compiler"
    
    dependsOn(generateCUP) // CUP debe ejecutarse primero para generar sym.java
    
    inputs.file(lexerFile)
    outputs.dir(jflexOutputDir)
    
    doFirst {
        jflexOutputDir.mkdirs()
        println("🔨 Generando lexer con JFlex desde: ${lexerFile.absolutePath}")
        println("📁 Salida en: ${jflexOutputDir.absolutePath}")
    }
    
    mainClass.set("jflex.Main")
    // JFlex necesita CUP runtime en el classpath para generar el scanner
    classpath = files(jflexJar, cupJar)
    args = listOf(
        "-d", jflexOutputDir.absolutePath,
        lexerFile.absolutePath
    )
    
    doLast {
        println("✅ Lexer generado exitosamente")
        println("   - Lexer.java")
    }
}

// ==================================================================================
// TAREA: Generar todos los compiladores (CUP + JFlex)
// ==================================================================================
val generateCompiler by tasks.register("generateCompiler") {
    description = "Genera todos los analizadores (CUP + JFlex)"
    group = "compiler"
    
    dependsOn(generateCUP, generateJFlex)
    
    doLast {
        println("✅ Todos los compiladores generados exitosamente")
        println("📂 Código generado en: ${generatedSourceDir.absolutePath}")
    }
}

// ==================================================================================
// INTEGRACIÓN CON EL CICLO DE COMPILACIÓN
// ==================================================================================

// Registrar el directorio de código generado como sourceSet
android.sourceSets.getByName("main") {
    java.srcDir(generatedSourceDir)
}

// Asegurar que los compiladores se generen antes de compilar Java/Kotlin
tasks.named("preBuild") {
    dependsOn(generateCompiler)
}

// También asegurar que se ejecute antes de las tareas de Kotlin
tasks.configureEach {
    if (name.startsWith("compile") && name.contains("Kotlin", ignoreCase = true)) {
        dependsOn(generateCompiler)
    }
}

// Limpiar archivos generados al ejecutar clean
tasks.named("clean") {
    doLast {
        delete(generatedSourceDir)
        println("🗑️ Código generado eliminado")
    }
}