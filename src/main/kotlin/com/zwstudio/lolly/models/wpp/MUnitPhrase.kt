package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.misc.MTextbook
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

data class MUnitPhrases(
    @SerializedName("records")
    var lst: List<MUnitPhrase> = emptyList()
)

data class MUnitPhrase(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("LANGID")
    var langid: Int = 0,
    @SerializedName("TEXTBOOKID")
    var textbookid: Int = 0,
    @SerializedName("TEXTBOOKNAME")
    var textbookname: String = "",
    @SerializedName("UNIT")
    var unit: Int = 0,
    @SerializedName("PART")
    var part: Int = 0,
    @SerializedName("SEQNUM")
    var seqnum: Int = 0,
    @SerializedName("PHRASEID")
    var phraseid: Int = 0,
    @SerializedName("PHRASE")
    val phraseProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("TRANSLATION")
    val translationProperty: SimpleStringProperty = SimpleStringProperty(""),
) : Serializable {
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    var translation: String get() = translationProperty.value; set(value) { translationProperty.value = value }
    lateinit var textbook: MTextbook
    val unitstr: String
        get() = textbook.unitstr(unit)
    var unititem: MSelectItem? = null
    val partstr: String
        get() = textbook.partstr(part)
    var partitem: MSelectItem? = null
    val isChecked = SimpleBooleanProperty()
}
