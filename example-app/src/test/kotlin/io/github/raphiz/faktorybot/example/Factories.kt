package io.github.raphiz.faktorybot.example

import java.util.*

object Addresses {
    val validAddress = AddressFaktory(
        street = { "Sesame Street" },
        city = { "Manhattan" }
    )

}

object Users {
    val validUser = UserFaktory(
        id = { UUID.randomUUID() },
        name = { "Peter" }
    )

    val userWithAddress = validUser.copy(
        address = { Addresses.validAddress() }
    )

}

