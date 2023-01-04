package io.github.raphiz.faktorybot.codegen

import com.squareup.kotlinpoet.*

const val faktoryInsertFn: String = "faktoryInsertFn"

class SpecGenerator(
    private val model: Model,
    private val withInsert: Boolean = false
) {
    private val specClassName = "${model.type.simpleName}Spec"
    private val packageName = model.type.packageName
    private val faktoryInsertFnType = LambdaTypeName.get(
        parameters = listOf(
            ParameterSpec.unnamed(model.type)
        ), returnType = model.type
    )

    fun fileSpec(): FileSpec = FileSpec.builder(
        packageName = packageName,
        fileName = specClassName
    )
        .addType(specType())
        .build()

    private fun specType(): TypeSpec {
        val builder = TypeSpec
            .classBuilder(ClassName(packageName, specClassName))
            .addModifiers(KModifier.DATA)
            .primaryConstructor(constructor())
            .addProperties(properties())
            .addFunction(invokeFunction())
            .addFunction(crateFunction())

        if (withInsert) {
            builder.addFunction(insertFunction())
        }

        return builder.build()
    }


    private fun constructor(): FunSpec {
        val builder = FunSpec.constructorBuilder()

        if (withInsert) {
            val insertFunctionParameter = ParameterSpec.builder(
                faktoryInsertFn,
                faktoryInsertFnType
            ).build()
            builder.addParameter(insertFunctionParameter)
        }

        model.attributes.forEach {
            val parameter = ParameterSpec.builder(it.name, it.type.toSupplierType())
            if (it.type.isNullable) parameter.defaultValue("{ null }")
            builder.addParameter(parameter.build())
        }

        return builder.build()
    }

    private fun properties(): List<PropertySpec> {
        val properties = model.attributes.map {
            PropertySpec.builder(it.name, it.type.toSupplierType())
                .initializer(it.name)
                .build()
        }
        if (withInsert) {
            val faktoryInsertFnProperty = PropertySpec.builder(
                faktoryInsertFn,
                faktoryInsertFnType,
            ).initializer(faktoryInsertFn)
                .addModifiers(KModifier.PRIVATE)
                .build()
            return properties + faktoryInsertFnProperty
        }
        return properties

    }

    private fun invokeFunction(): FunSpec {
        return functionWithBuilderParameters("invoke")
            .addModifiers(KModifier.OPERATOR)
            .addCode(
                CodeBlock.builder()
                    .add("return ")
                    .createModelInstance()
                    .build()
            )
            .build()
    }

    private fun crateFunction(): FunSpec {
        return functionWithBuilderParameters("create")
            .addCode(
                CodeBlock.builder()
                    .add("return ")
                    .createModelInstance()
                    .build()
            )
            .build()
    }

    private fun insertFunction(): FunSpec {
        val variableName = model.type.simpleName.replaceFirstChar { it.lowercase() }
        return functionWithBuilderParameters("insert")
            .addCode(
                CodeBlock.builder()
                    .add("val %N = ", variableName)
                    .createModelInstance()
                    .addStatement("$faktoryInsertFn(%N)", variableName)
                    .addStatement("return %N", variableName)
                    .build()
            )
            .build()
    }

    private fun functionWithBuilderParameters(name: String) = FunSpec.builder(name)
        .addParameters(
            model.attributes.map {
                ParameterSpec.builder(
                    it.name, it.type
                ).defaultValue("this.%N()", it.name)
                    .build()
            }
        )
        .returns(model.type)

    private fun CodeBlock.Builder.createModelInstance(): CodeBlock.Builder {
        add("%T(", model.type)
        model.attributes.forEach {
            add("%N=%N,", it.name, it.name)
        }
        add(")\n", model.type)
        return this
    }

    private fun TypeName.toSupplierType(): TypeName = LambdaTypeName.get(returnType = this)
}



