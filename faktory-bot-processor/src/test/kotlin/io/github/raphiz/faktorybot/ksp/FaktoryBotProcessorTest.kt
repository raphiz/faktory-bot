package io.github.raphiz.faktorybot.ksp

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspWithCompilation
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.reflect.full.primaryConstructor

class FaktoryBotProcessorTest {
    @Test
    fun `it finds and processes every class annotated with @Faktory`() {
        val kotlinSrc = SourceFile.kotlin(
            "User.kt",
            """
                package com.example

                import io.github.raphiz.faktorybot.Faktory

                @Faktory
                data class User(
                    val name: String,
                    val age: Int?,
                )
            """.trimIndent()
        )

        val compilation = KotlinCompilation().apply {
            sources = listOf(kotlinSrc)
            symbolProcessorProviders = listOf(FaktoryBotProcessorProvider())
            inheritClassPath = true
            kspWithCompilation = true
            messageOutputStream = System.out

        }.compile()

        assertThat(compilation.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)

        assertThat(compilation.messages).contains(
            "Creating Faktory Spec for com.example.User"
        )

        assertDoesNotThrow {
            val loadClass = compilation.classLoader.loadClass("com.example.UserSpec")
            assertThat(loadClass.kotlin.primaryConstructor!!.parameters).hasSize(2)
        }

    }
}