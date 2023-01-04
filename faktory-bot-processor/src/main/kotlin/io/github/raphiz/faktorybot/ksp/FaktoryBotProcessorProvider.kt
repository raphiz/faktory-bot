package io.github.raphiz.faktorybot.ksp

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.raphiz.faktorybot.codegen.FaktoryGenerator

class FaktoryBotProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) =
        FaktoryBotProcessor(
            environment.logger,
            environment.codeGenerator,
            FaktoryGenerator()
        )
}