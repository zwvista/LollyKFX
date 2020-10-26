package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MLangPhrases {

    @SerializedName("records")
    @Expose
    var lst: List<MLangPhrase>? = null
}

class MLangPhrase(): Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("PHRASE")
    @Expose
    val phraseProperty = SimpleStringProperty("")
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    @SerializedName("TRANSLATION")
    @Expose
    val translationProperty = SimpleStringProperty("")
    var translation: String get() = translationProperty.value; set(value) { translationProperty.value = value }
}
