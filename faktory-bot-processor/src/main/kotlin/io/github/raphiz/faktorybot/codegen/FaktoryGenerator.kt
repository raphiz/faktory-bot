package io.github.raphiz.faktorybot.codegen

import com.squareup.kotlinpoet.*

class FaktoryGenerator {
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
            .build()
    }

    private fun Model.toConstructorFunSpec(): FunSpec {
        return FunSpec.constructorBuilder()
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
        return attributes.map {
            PropertySpec.builder(it.name, LambdaTypeName.get(returnType = it.type))
                .initializer(it.name)
                .build()
        }
    }

    private fun Model.invokeFunSpec(): FunSpec {
        return builderFunSpec("invoke")
            .addModifiers(KModifier.OPERATOR)
            .build()
    }

    private fun Model.createFunSpec(): FunSpec {
        return builderFunSpec("create")
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
        .addCode(
            CodeBlock.builder()
                .add("return %T(", type)
                .apply {
                    attributes.forEach {
                        add("%N=%N,", it.name, it.name)
                    }
                }
                .add(")", type)
                .build()
        )
        .returns(this.type)
}

private val Model.faktoryName: String
    get() = "${type.simpleName}Faktory"
