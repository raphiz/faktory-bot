package io.github.raphiz.faktorybot

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
            .primaryConstructor(
                toConstructorFunSpec()
            )
            .addProperties(
                toPropertySpecs()
            )
            .addFunction(
                invokeFunSpec()
            )
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
        return FunSpec.builder("invoke")
            .addModifiers(KModifier.OPERATOR)
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
            .build()
    }
}

private val Model.faktoryName: String
    get() = "${type.simpleName}Faktory"
