package com.zwstudio.lolly.domain.wpp

// Generated 2014-10-12 21:44:14 by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.io.Serializable

class MLangWords {

    @SerializedName("records")
    var lst: List<MLangWord>? = null
}

class MLangWord: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("WORD")
    val wordProperty = SimpleStringProperty("")
    var word: String get() = wordProperty.value; set(value) { wordProperty.value = value }
    @SerializedName("NOTE")
    val noteProperty = SimpleStringProperty("")
    var note: String get() = noteProperty.value; set(value) { noteProperty.value = value }
    @SerializedName("FAMIID")
    var famiid = 0
    @SerializedName("CORRECT")
    var correct = 0
    @SerializedName("TOTAL")
    var total = 0

    val wordnote: String
        get() = word + (if (note.isEmpty()) "" else "($note)")
    val accuracy: String
        get() = if (total == 0) "N/A" else "${Math.floor(correct.toDouble() / total.toDouble() * 1000) / 10}%"
    val isChecked = SimpleBooleanProperty()
}
