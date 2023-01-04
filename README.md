## faktory-bot

`factory-bot` allows you to cleanly create and maintain test data. 

You can use it to define your own Factories/Generators and override this logic directly when instantiating the factories in your tests.


```kotlin
object UserFactory{
    val validUser = UserSpec(
        id = { UUID.randomUUID() },
        name = { "Peter" }
    )

    val userWithAddress = validUser.copy(
        address = { Addresses.validAddress() }
    )
}

// in your tests
val user = UserFactory.validUser()
val martha = UserFactory.validUser(name = "Martha")
val userWithAddr
```

`factory-bot` is intended as a clone of Ruby's fantastic [factory_bot](https://github.com/thoughtbot/factory_bot) gem for Kotlin.


## Installation

`faktory-bot` generates boilerplate code using
the [Kotlin Symbol Processing (KSP)](https://kotlinlang.org/docs/ksp-overview.html). Therefore, you
need to apply the `ksp` gradle plugin and add the following dependencies to your `build.gradle.kts`:

```kotlin
plugins {
    kotlin("jvm") version "1.7.10"
    // The following version must match with the kotlin version
    id("com.google.devtools.ksp") version "1.7.10-1.0.7"
}

dependencies {
    compileOnly("io.github.raphiz:faktory-bot-annotations:0.1.0")
    ksp("io.github.raphiz:faktory-bot-processor:0.1.0")
}


// Make IDE aware of generated code, see also:
// https://kotlinlang.org/docs/ksp-quickstart.html#make-ide-aware-of-generated-code
kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
```

> **_NOTE:_
**  [The implementation of KSP is tied to a specific kotlin compiler version. Users of processors (`faktory-bot` is a processor) need to bump KSP version when bumping the compiler version in their project.](https://kotlinlang.org/docs/ksp-faq.html#how-to-upgrade-ksp). `faktory-bot`
> only depends on the KSP API, and _should_ therefore work with most ksp and kotlin versions. If not,
> please [open an issue](https://github.com/raphiz/faktory-bot/issues).

## Usage

Once the KSP setup is completed, the usage of `faktory-bot` is straight forward.

First, add the `@Faktory` annotation any class, for example:

```kotlin
@Faktory
data class User(val id: UUID, val name: String, val address: Address?)
```

`faktory-bot` will now generate the following code for you:

```kotlin
public data class UserSpec(
    public val id: () -> UUID,
    public val name: () -> String,
    public val address: () -> Address? = { null },
) {
    public operator fun invoke(
        id: UUID = this.id(),
        name: String = this.name(),
        address: Address? = this.address(),
    ): User = User(id = id, name = name, address = address,)

    public fun create(
        id: UUID = this.id(),
        name: String = this.name(),
        address: Address? = this.address(),
    ): User = User(id = id, name = name, address = address,)

}
```

The two generated methods, `create` and `invoke` do exactly the same. The latter is just for syntactic sugar (see example below).

Using this Faktroy, you can now simply create your own data factories

```kotlin
val validUser = UserSpec(
    id = { UUID.randomUUID() },
    name = { "Peter" }
)
```

and use them in your test (or for generating example/test data)

```kotlin
@Test
fun `it saves a user`() {
    val user = validUser.create(name="Another name for my test")
    // alternatively, using `invoke`
    // val user = validUser()

    userRepository.save(user)

    val allUsers = userRepository.findAll()
    assertThat(allUsers).containsExactly(user)
}
```

For more examples, checkout the [`example-app`](/example-app).

### Faktroy-bot for database interaction

The `create()` methods are great to create objects. However, when writing tests that interact with a database, it would be nice to have an easy way to insert data. For this, `faktory-bot` offers the `withInsert` option on the `@Faktory` annotation.

```kotlin
@Faktory(withInsert=true)
data class User(val id: UUID, val name: String, val address: Address?)
```

This will add a parameter to the generated Faktroy: A function used for persistence.

```kotlin
class UserRepositoryTest {

    private val userRepository = UserRepository()
    private val validUser = UserSpec(
        faktoryInsertFn = userRepository::save,
        id = { UUID.randomUUID() },
        name = { "Peter" }
    )

    @Test
    fun `it returns an inserted user`() {
        val expectedUser = validUser.insert()

        val allUsers = userRepository.findAll()

        assertThat(allUsers).containsExactly(expectedUser)
    }
}
```

This is just syntactic sugar to remove noise from your tests.

### Best Practices

`faktory-bot` is a [factory_bot](https://github.com/thoughtbot/factory_bot) clone for Kotlin. The API is modeled as closely as possible to factory bot.

Therefore, best practices that apply to factory_bot also apply to `faktory-bot`.

## Contribution

We welcome contributions to faktory-bot! If you would like to report a bug or request a feature,
please open an issue. If you want to submit a pull request, please make sure to include tests for
your changes.

## Acknowledgments

faktory-bot is inspired by the following projects:

- [factory_bot](https://github.com/thoughtbot/factory_bot)
- [Fixtures](https://github.com/bluegroundltd/fixtures)