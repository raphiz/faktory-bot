package io.github.raphiz.faktorybot

import com.squareup.kotlinpoet.TypeName

data class Model(val name: String, val packageName: String, val attributes: List<Attribute>, val clazz: TypeName)