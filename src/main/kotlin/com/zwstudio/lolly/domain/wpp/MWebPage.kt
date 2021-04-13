package com.zwstudio.lolly.domain.wpp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MWebPages {

    @SerializedName("records")
    var lst: List<MWebPage>? = null
}

class MWebPage: Serializable {

    @SerializedName("ID")
    var id = 0
    @SerializedName("TITLE")
    var title = ""
    @SerializedName("URL")
    var url = ""
}
