package io.github.raphiz.faktorybot

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class FaktoryGeneratorTest {
    @Test
    fun `it generates a data class with all properties`() {

        val model = Model(
            name = "User",
            packageName = "com.example",
            attributes = listOf(
                Attribute("name", String::class),
                Attribute("age", Int::class)
            )
        )

        val spec = FaktoryGenerator().generate(model)

        assertThat(spec.name).isEqualTo("UserFaktory")
        assertThat(spec.packageName).isEqualTo("com.example")
        assertThat(spec.toString()).isEqualToIgnoringWhitespace(
            """
            package com.example
            
            import kotlin.Int
            import kotlin.String
            
            public data class UserFaktory(
                public val name: String,
                public val age: Int,
            )
            """.trimIndent()
        )

    }
}