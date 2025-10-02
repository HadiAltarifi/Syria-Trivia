package com.syriantrivia.data.model

enum class ModuleType(val id: String) {
    CULTURE("culture"),
    HISTORY("history");

    companion object {
        fun fromId(id: String): ModuleType {
            return values().find { it.id == id } ?: CULTURE
        }
    }
}
