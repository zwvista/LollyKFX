package com.zwstudio.lolly.models.misc

// Generated 2014-10-4 23:22:52 by Hibernate Tools 4.3.1

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MWebTextbooks {

    @SerializedName("records")
    var lst: List<MWebTextbook>? = null
}

class MWebTextbook: Serializable {

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
    @SerializedName("WEBPAGEID")
    var webpageid = 0
    @SerializedName("TITLE")
    var title = ""
    @SerializedName("URL")
    var url = ""
}
