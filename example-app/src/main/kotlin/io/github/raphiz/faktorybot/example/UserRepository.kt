package io.github.raphiz.faktorybot.example

import java.util.Collections.unmodifiableList

class UserRepository(vararg initialUsers: User) {
    private val users = mutableListOf(*initialUsers)

    fun findAll(): Iterable<User> {
        return unmodifiableList(users)
    }
}