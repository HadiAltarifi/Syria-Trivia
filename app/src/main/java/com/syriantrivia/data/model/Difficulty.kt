package com.syriantrivia.data.model

enum class Difficulty(val id: String) {
    EASY("easy"),
    MODERATE("moderate"),
    HARD("hard");

    companion object {
        fun fromId(id: String): Difficulty {
            return values().find { it.id == id } ?: EASY
        }
    }
}
