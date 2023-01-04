package io.github.raphiz.faktorybot.example

import java.util.Collections.unmodifiableList

const val NIL_UUID = "00000000-0000-0000-0000-000000000000"

class UserRepository(vararg initialUsers: User) {
    private val users = mutableListOf(*initialUsers)

    fun save(user: User): User {
        if (user.id.toString() == NIL_UUID) {
            throw IllegalArgumentException("\"nil\" UUID is not allowed")
        }
        users.add(user.copy())
        return user
    }

    fun findAll(): Iterable<User> {
        return unmodifiableList(users)
    }
}