package io.github.raphiz.faktorybot.codegen

import com.squareup.kotlinpoet.*

const val faktoryInsertFn: String = "faktoryInsertFn"

class FaktoryGenerator(val withInsert: Boolean = false) {
    fun generate(model: Model): FileSpec {
        return FileSpec.builder(
            packageName = model.type.packageName,
            fileName = model.faktoryName
        ).addType(model.toTypeSpec())
            .build()
    }

    private fun Model.toTypeSpec(): TypeSpec {
        return TypeSpec.classBuilder(
            className = ClassName(type.packageName, faktoryName),
        ).addModifiers(KModifier.DATA)
            .primaryConstructor(toConstructorFunSpec())
            .addProperties(toPropertySpecs())
            .addFunction(invokeFunSpec())
            .addFunction(createFunSpec())
            .also {
                if (withInsert) it.addFunction(insertFunSpec())
            }
            .build()
    }

    private fun Model.toConstructorFunSpec(): FunSpec {
        return FunSpec.constructorBuilder()
            .also {
                if (withInsert)
                    it.addParameter(
                        ParameterSpec.builder(
                            faktoryInsertFn,
                            LambdaTypeName.get(
                                parameters = listOf(
                                    ParameterSpec.unnamed(type)
                                ), returnType = type
                            )
                        )
                            .build()
                    )
            }
            .addParameters(
                attributes.map {
                    ParameterSpec.builder(it.name, LambdaTypeName.get(returnType = it.type))
                        .apply {
                            if (it.type.isNullable) defaultValue("{ null }")
                        }
                        .build()
                }
            ).build()
    }

    private fun Model.toPropertySpecs(): List<PropertySpec> {
        val propertySpecs = attributes.map {
            PropertySpec.builder(it.name, LambdaTypeName.get(returnType = it.type))
                .initializer(it.name)
                .build()
        }
        if (withInsert) {
            val faktoryInsertFnPropertySpec = PropertySpec.builder(
                faktoryInsertFn,
                LambdaTypeName.get(
                    parameters = listOf(
                        ParameterSpec.unnamed(type)
                    ), returnType = type
                )
            )
                .initializer(faktoryInsertFn)
                .addModifiers(KModifier.PRIVATE)
                .build()
            return propertySpecs + listOf(faktoryInsertFnPropertySpec)
        }
        return propertySpecs

    }

    private fun Model.invokeFunSpec(): FunSpec {
        return builderFunSpec("invoke")
            .addModifiers(KModifier.OPERATOR)
            .addCode(
                CodeBlock.builder()
                    .add("return ")
                    .createModel(this)
                    .build()
            )
            .build()
    }

    private fun Model.createFunSpec(): FunSpec {
        return builderFunSpec("create")
            .addCode(
                CodeBlock.builder()
                    .add("return ")
                    .createModel(this)
                    .build()
            )
            .build()
    }

    private fun Model.insertFunSpec(): FunSpec {
        val variableName = this.type.simpleName.lowercase()
        return builderFunSpec("insert")
            .addCode(
                CodeBlock.builder()
                    .add("val %N = ", variableName)
                    .createModel(this)
                    .add("$faktoryInsertFn(%N)", variableName)
                    .add("\nreturn %N", variableName)
                    .build()
            )
            .build()
    }

    private fun Model.builderFunSpec(name: String) = FunSpec.builder(name)
        .addParameters(
            attributes.map {
                ParameterSpec.builder(
                    it.name, it.type
                ).defaultValue("this.%N()", it.name)
                    .build()
            }
        )
        .returns(this.type)
}

private fun CodeBlock.Builder.createModel(model: Model): CodeBlock.Builder {
    add("%T(", model.type)
    model.attributes.forEach {
        add("%N=%N,", it.name, it.name)
    }
    add(")", model.type)
    return this
}

private val Model.faktoryName: String
    get() = "${type.simpleName}Faktory"
