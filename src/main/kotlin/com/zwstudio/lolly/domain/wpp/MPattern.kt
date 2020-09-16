package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MPatterns {

    @SerializedName("records")
    @Expose
    var lst: List<MPattern>? = null
}

class MPattern: Serializable {

    @SerializedName("ID")
    @Expose
    var id = 0
    @SerializedName("LANGID")
    @Expose
    var langid = 0
    @SerializedName("PATTERN")
    @Expose
    var pattern = ""
    @SerializedName("NOTE")
    @Expose
    var note: String? = null
    @SerializedName("TAGS")
    @Expose
    var tags: String? = null
}
