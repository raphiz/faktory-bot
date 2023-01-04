package io.github.raphiz.faktorybot.example

import java.util.*

object Addresses {
    val validAddress = AddressSpec(
        street = { "Sesame Street" },
        city = { "Manhattan" }
    )

}

class UserFactory(userRepository: UserRepository) {
    val validUser = UserSpec(
        faktoryInsertFn = userRepository::save,
        id = { UUID.randomUUID() },
        name = { "Peter" }
    )

    val userWithAddress = validUser.copy(
        address = { Addresses.validAddress() }
    )

}

