package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

data class MLangPhrases(
    @SerializedName("records")
    var lst: List<MLangPhrase> = emptyList()
)

data class MLangPhrase(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("LANGID")
    var langid: Int = 0,
    @SerializedName("PHRASE")
    val phraseProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("TRANSLATION")
    val translationProperty: SimpleStringProperty = SimpleStringProperty(""),
) : Serializable {
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    var translation: String get() = translationProperty.value; set(value) { translationProperty.value = value }
    val isChecked = SimpleBooleanProperty()
}
