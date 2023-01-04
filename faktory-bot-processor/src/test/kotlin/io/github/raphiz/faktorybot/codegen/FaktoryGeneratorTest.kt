package io.github.raphiz.faktorybot.codegen

import com.example.User
import com.squareup.kotlinpoet.asTypeName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class FaktoryGeneratorTest {
    @Test
    fun `it generates a data class with all properties and methods`() {
        val model = modelOf<User>()

        val result = FaktoryGenerator(model).fileSpec()

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
                public fun create(name: String = this.name(), age: Int? = this.age()): User =
                    User(
                        name = name,
                        age = age,
                    )
            }
            """.trimIndent()
        )
    }

    @Test
    fun `it generates an insert method on the faktory when FaktoryInsert is supplied`() {
        val model = modelOf<User>()

        val result = FaktoryGenerator(model, withInsert = true).fileSpec()

        assertThat(result.toString()).containsIgnoringWhitespaces(
            """
                private val faktoryInsertFn: (User) -> User,
            """.trimIndent()
        )
        assertThat(result.toString()).containsIgnoringWhitespaces(
            """
                public fun insert(name: String = this.name(), age: Int? = this.age()): User {
                    val user = User(
                        name = name,
                        age = age,
                    )
                    faktoryInsertFn(user)
                    return user
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