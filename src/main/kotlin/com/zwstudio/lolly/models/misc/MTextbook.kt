package com.zwstudio.lolly.models.misc

// Generated 2014-10-4 23:22:52 by Hibernate Tools 4.3.1

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MTextbooks {

    @SerializedName("records")
    var lst: List<MTextbook>? = null
}

class MTextbook: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("NAME")
    var textbookname = ""
    @SerializedName("UNITS")
    var units = ""
    @SerializedName("PARTS")
    var parts = ""

    lateinit var lstUnits: List<MSelectItem>
    fun unitstr(unit: Int) = lstUnits.first { it.value == unit }.label
    lateinit var lstParts: List<MSelectItem>
    fun partstr(part: Int) = lstParts.first { it.value == part }.label
}
