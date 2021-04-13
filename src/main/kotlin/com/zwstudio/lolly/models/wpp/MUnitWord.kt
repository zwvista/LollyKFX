package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.misc.MTextbook
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MUnitWords {

    @SerializedName("records")
    var lst: List<MUnitWord>? = null
}

class MUnitWord: Serializable {

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
    @SerializedName("WORD")
    val wordProperty = SimpleStringProperty("")
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    @SerializedName("NOTE")
    val noteProperty = SimpleStringProperty("")
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
    @SerializedName("WORDID")
    var wordid = 0
    @SerializedName("FAMIID")
    var famiid = 0
    @SerializedName("CORRECT")
    var correct = 0
    @SerializedName("TOTAL")
    var total = 0

    lateinit var textbook: MTextbook
    val unitstr: String
        get() = textbook.unitstr(unit)
    var unititem: MSelectItem? = null
    val partstr: String
        get() = textbook.partstr(part)
    var partitem: MSelectItem? = null
    val accuracy: String
        get() = if (total == 0) "N/A" else "${Math.floor(correct.toDouble() / total.toDouble() * 1000) / 10}%"
    val isChecked = SimpleBooleanProperty()
}
