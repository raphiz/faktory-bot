package io.github.raphiz.faktorybot.example

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserRepositoryTest {

    @Test
    fun `it returns all inserted users`() {
        val users = arrayOf(
            Users.validUser(),
            Users.userWithAddress()
        )
        val userRepository = UserRepository(*users)

        val allUsers = userRepository.findAll()

        assertThat(allUsers).containsExactly(*users)
    }
}