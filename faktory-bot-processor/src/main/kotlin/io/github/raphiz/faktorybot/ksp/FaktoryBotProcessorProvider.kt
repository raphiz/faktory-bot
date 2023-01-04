package io.github.raphiz.faktorybot.ksp

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class FaktoryBotProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) =
        FaktoryBotProcessor(
            environment.logger,
            environment.codeGenerator,
        )
}