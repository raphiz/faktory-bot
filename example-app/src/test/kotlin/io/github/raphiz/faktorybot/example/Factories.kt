package io.github.raphiz.faktorybot.example

import java.util.*

object Addresses {
    val validAddress = AddressFaktory(
        street = { "Sesame Street" },
        city = { "Manhattan" }
    )

}

class UserFactory(userRepository: UserRepository) {
    val validUser = UserFaktory(
        faktoryInsertFn = userRepository::save,
        id = { UUID.randomUUID() },
        name = { "Peter" }
    )

    val userWithAddress = validUser.copy(
        address = { Addresses.validAddress() }
    )

}

