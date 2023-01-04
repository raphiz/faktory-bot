package io.github.raphiz.faktorybot.example

import io.github.raphiz.faktorybot.Faktory
import java.util.*

@Faktory
data class User(val id: UUID, val name: String, val address: Address?)