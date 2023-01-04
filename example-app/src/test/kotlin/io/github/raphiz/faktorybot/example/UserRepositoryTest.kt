package io.github.raphiz.faktorybot.example

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class UserRepositoryTest {

    private val userRepository = UserRepository()
    private val userFactory = UserFactory(userRepository)

    @Test
    fun `it returns all inserted users`() {
        val expectedUsers = arrayOf(
            userFactory.validUser.insert(),
            userFactory.userWithAddress.insert()
        )

        val allUsers = userRepository.findAll()

        assertThat(allUsers).containsExactly(*expectedUsers)
    }

    @Test
    fun `it saves a user`() {
        val user = userFactory.validUser.create()

        userRepository.save(user)

        val allUsers = userRepository.findAll()
        assertThat(allUsers).containsExactly(user)
    }


    @Test
    fun `it cannot save users with NIL UUID`() {
        val invalidUUID = UUID.fromString(NIL_UUID)
        val user = userFactory.validUser(id = invalidUUID)

        assertThrows<IllegalArgumentException> {
            userRepository.save(user)
        }

        val allUsers = userRepository.findAll()
        assertThat(allUsers).isEmpty()
    }

}