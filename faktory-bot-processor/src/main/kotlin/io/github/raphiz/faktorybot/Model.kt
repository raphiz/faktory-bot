package io.github.raphiz.faktorybot

import com.squareup.kotlinpoet.ClassName

data class Model(val type: ClassName, val attributes: List<Attribute>)