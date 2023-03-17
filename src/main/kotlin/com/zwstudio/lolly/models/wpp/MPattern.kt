package com.zwstudio.lolly.models.wpp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MPatterns {

    @SerializedName("records")
    var lst: List<MPattern>? = null
}

class MPattern: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("LANGID")
    var langid = 0
    @SerializedName("PATTERN")
    var pattern = ""
    @SerializedName("TAGS")
    var tags = ""
    @SerializedName("TITLE")
    var title = ""
    @SerializedName("URL")
    var url = ""
}
