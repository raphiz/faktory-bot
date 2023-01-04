package io.github.raphiz.faktorybot

import com.squareup.kotlinpoet.asTypeName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class FaktoryGeneratorTest {
    @Test
    fun `it generates a data class with all properties and invoke method`() {
        val model = modelOf<com.example.User>()

        val result = FaktoryGenerator().generate(model)

        assertThat(result.name).isEqualTo("UserFaktory")
        assertThat(result.packageName).isEqualTo("com.example")
        assertThat(result.toString()).isEqualToIgnoringWhitespace(
            """
            package com.example
            
            import kotlin.Int
            import kotlin.String
            
            public data class UserFaktory(
                public val name: () -> String,
                public val age: () -> Int? = { null },
            ) {
                public operator fun invoke(name: String = this.name(), age: Int? = this.age()): User =
                    User(
                        name = name,
                        age = age,
                    )
            }
            """.trimIndent()
        )
    }
}

inline fun <reified T> modelOf(): Model {
    val clazz = T::class as KClass<*>
    return Model(
        type = clazz.asTypeName(),
        attributes = clazz.primaryConstructor!!.parameters.map {
            Attribute(it.name!!, it.type.asTypeName())
        }
    )
}