package io.github.raphiz.faktorybot

import com.squareup.kotlinpoet.ClassName.Companion.bestGuess
import com.squareup.kotlinpoet.asTypeName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class FaktoryGeneratorTest {
    @Test
    fun `it generates a data class with all properties and invoke method`() {

        val model = Model(
            name = "User",
            packageName = "com.example",
            attributes = listOf(
                Attribute("name", String::class.asTypeName()),
                Attribute("age", Int::class.asTypeName().copy(nullable = true)),
            ),
            clazz = bestGuess("com.example.User")
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
                public val age: Int? = null,
            ) {
                public operator fun invoke(name: String? = this.name, age: Int? = this.age): User =
                    User(
                        name = name!!,
                        age = age,
                    )
            }
            """.trimIndent()
        )

    }
}