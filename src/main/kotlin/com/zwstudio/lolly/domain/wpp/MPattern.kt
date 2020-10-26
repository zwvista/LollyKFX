package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MPatterns {

    @SerializedName("records")
    @Expose
    var lst: List<MPattern>? = null
}

class MPattern: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("PATTERN")
    @Expose
    var pattern = ""
    @SerializedName("NOTE")
    @Expose
    val noteProperty = SimpleStringProperty("")
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
    @SerializedName("TAGS")
    @Expose
    val tagsProperty = SimpleStringProperty("")
    var tags: String get() = tagsProperty.value; set(value) { tagsProperty.value = value }
    @SerializedName("IDS_MERGE")
    @Expose
    var idsMerge = ""
    @SerializedName("PATTERNS_SPLIT")
    @Expose
    var patternsSplit = ""
}
