package io.github.raphiz.faktorybot.codegen

import com.squareup.kotlinpoet.ClassName

data class Model(val type: ClassName, val attributes: List<Attribute>)