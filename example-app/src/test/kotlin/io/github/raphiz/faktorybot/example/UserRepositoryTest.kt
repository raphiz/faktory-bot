package io.github.raphiz.faktorybot.example

import io.github.raphiz.faktorybot.example.Users.userWithAddress
import io.github.raphiz.faktorybot.example.Users.validUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserRepositoryTest {

    @Test
    fun `it returns all inserted users`() {
        val users = arrayOf(
            validUser.create(),
            userWithAddress.create()
        )
        val userRepository = UserRepository(*users)

        val allUsers = userRepository.findAll()

        assertThat(allUsers).containsExactly(*users)
    }
}