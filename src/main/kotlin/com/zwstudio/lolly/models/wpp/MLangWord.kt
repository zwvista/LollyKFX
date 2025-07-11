package com.zwstudio.lolly.models.wpp

// Generated 2014-10-12 21:44:14 by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

data class MLangWords(
    @SerializedName("records")
    var lst: List<MLangWord> = emptyList()
)

data class MLangWord(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("LANGID")
    var langid: Int = 0,
    @SerializedName("WORD")
    val wordProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("NOTE")
    val noteProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("FAMIID")
    var famiid: Int = 0,
    @SerializedName("CORRECT")
    var correct: Int = 0,
    @SerializedName("TOTAL")
    var total: Int = 0,
) : Serializable {
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
    val wordnote: String
        get() = word + (if (note.isEmpty()) "" else "($note)")
    val accuracy: String
        get() = if (total == 0) "N/A" else "${Math.floor(correct.toDouble() / total.toDouble() * 1000) / 10}%"
    val isChecked = SimpleBooleanProperty()
}
