package io.github.raphiz.faktorybot.example

import io.github.raphiz.faktorybot.Faktory
import java.util.*

@Faktory(withInsert = true)
data class User(val id: UUID, val name: String, val address: Address?)