package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.misc.MTextbook
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

data class MUnitWords(
    @SerializedName("records")
    var lst: List<MUnitWord> = emptyList()
)

data class MUnitWord(
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
    @SerializedName("WORD")
    val wordProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("NOTE")
    val noteProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("WORDID")
    var wordid: Int = 0,
    @SerializedName("FAMIID")
    var famiid: Int = 0,
    @SerializedName("CORRECT")
    var correct: Int = 0,
    @SerializedName("TOTAL")
    var total: Int = 0,
) : Serializable {
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
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
