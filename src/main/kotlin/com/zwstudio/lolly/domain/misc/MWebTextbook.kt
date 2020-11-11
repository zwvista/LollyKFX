package com.zwstudio.lolly.domain.misc

// Generated 2014-10-4 23:22:52 by Hibernate Tools 4.3.1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MWebTextbooks {

    @SerializedName("records")
    @Expose
    var lst: List<MWebTextbook>? = null
}

class MWebTextbook: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("TEXTBOOKID")
    @Expose
    var textbookid = 0
    @SerializedName("TEXTBOOKNAME")
    @Expose
    var textbookname = ""
    @SerializedName("UNIT")
    @Expose
    var unit = 0
    @SerializedName("WEBPAGEID")
    @Expose
    var webpageid = 0
    @SerializedName("TITLE")
    @Expose
    var title = ""
    @SerializedName("URL")
    @Expose
    var url = ""
}
