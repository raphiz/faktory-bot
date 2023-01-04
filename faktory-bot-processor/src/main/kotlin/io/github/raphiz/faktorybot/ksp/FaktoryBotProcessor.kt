package io.github.raphiz.faktorybot.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.raphiz.faktorybot.Faktory
import io.github.raphiz.faktorybot.codegen.Attribute
import io.github.raphiz.faktorybot.codegen.FaktoryGenerator
import io.github.raphiz.faktorybot.codegen.Model

class FaktoryBotProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Faktory::class.java.name)
            .filterIsInstance<KSClassDeclaration>()

        val invalidSymbols = symbols.filterNot { it.validate() }.toList()

        symbols
            .filter { it.validate() }
            .forEach {
                logger.info("Creating Faktory for User: UserFaktory")
                it.accept(FaktoryAnnotatedProcessorVisitor(), Unit)
            }

        return invalidSymbols
    }

    inner class FaktoryAnnotatedProcessorVisitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val model = classDeclaration.toModel()
            FaktoryGenerator(model).fileSpec().writeTo(
                codeGenerator = codeGenerator,
                aggregating = false
            )
        }
    }

    private fun KSClassDeclaration.toModel(): Model {
        return Model(
            this.toClassName(),
            primaryConstructor!!.parameters.map {
                Attribute(it.name!!.asString(), it.type.toTypeName())
            },
        )
    }

}

