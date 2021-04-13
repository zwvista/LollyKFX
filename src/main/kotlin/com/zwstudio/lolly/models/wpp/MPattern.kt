package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MPatterns {

    @SerializedName("records")
    var lst: List<MPattern>? = null
}

class MPattern: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("PATTERN")
    var pattern = ""
    @SerializedName("NOTE")
    val noteProperty = SimpleStringProperty("")
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
    @SerializedName("TAGS")
    val tagsProperty = SimpleStringProperty("")
    var tags: String get() = tagsProperty.value; set(value) { tagsProperty.value = value }
    @SerializedName("IDS_MERGE")
    var idsMerge = ""
    @SerializedName("PATTERNS_SPLIT")
    var patternsSplit = ""
}

class MPatternVariation {
    var index = 0
    var variationProperty = SimpleStringProperty("")
    var variation: String get() = variationProperty.value; set(value) { variationProperty.value = value }
}
