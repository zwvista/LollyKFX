package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.misc.MTextbook
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MUnitPhrases {

    @SerializedName("records")
    var lst: List<MUnitPhrase>? = null
}

class MUnitPhrase: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("TEXTBOOKID")
    var textbookid = 0
    @SerializedName("TEXTBOOKNAME")
    var textbookname = ""
    @SerializedName("UNIT")
    var unit = 0
    @SerializedName("PART")
    var part = 0
    @SerializedName("SEQNUM")
    var seqnum = 0
    @SerializedName("PHRASEID")
    var phraseid = 0
    @SerializedName("PHRASE")
    val phraseProperty = SimpleStringProperty("")
    var phrase: String get() = phraseProperty.value; set(value) { phraseProperty.value = value }
    @SerializedName("TRANSLATION")
    val translationProperty = SimpleStringProperty("")
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
