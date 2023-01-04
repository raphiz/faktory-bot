package io.github.raphiz.faktorybot

import com.squareup.kotlinpoet.*


class FaktoryGenerator {
    fun generate(model: Model): FileSpec {
        return FileSpec.builder(
            packageName = model.packageName,
            fileName = model.faktoryName
        ).addType(model.toTypeSpec())
            .build()
    }

    private fun Model.toTypeSpec(): TypeSpec {
        return TypeSpec.classBuilder(
            className = ClassName(packageName, faktoryName),
        ).addModifiers(KModifier.DATA)
            .primaryConstructor(
                toConstructorFunSpec()
            )
            .addProperties(
                toPropertySpecs()
            )
            .build()
    }

    private fun Model.toConstructorFunSpec(): FunSpec {
        return FunSpec.constructorBuilder()
            .addParameters(
                attributes.map {
                    ParameterSpec.builder(it.name, it.type).build()
                }
            ).build()
    }

    private fun Model.toPropertySpecs(): List<PropertySpec> {
        return attributes.map {
            PropertySpec.builder(it.name, it.type)
                .initializer(it.name)
                .build()
        }
    }
}

private val Model.faktoryName: String
    get() = "${name}Faktory"
