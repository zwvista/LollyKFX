package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MLangPhrases {

    @SerializedName("records")
    var lst: List<MLangPhrase>? = null
}

class MLangPhrase : Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("PHRASE")
    val phraseProperty = SimpleStringProperty("")
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    @SerializedName("TRANSLATION")
    val translationProperty = SimpleStringProperty("")
    var translation: String get() = translationProperty.value; set(value) { translationProperty.value = value }
    val isChecked = SimpleBooleanProperty()
}
