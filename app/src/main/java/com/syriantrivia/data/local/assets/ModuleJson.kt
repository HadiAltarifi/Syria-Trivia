package com.syriantrivia.data.local.assets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModuleJson(
    @SerialName("module")
    val module: String,

    @SerialName("title_ar")
    val titleAr: String,

    @SerialName("title_en")
    val titleEn: String,

    @SerialName("questions")
    val questions: List<QuestionJson>
)
