package com.zwstudio.lolly.domain.misc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MSelectItem(val value: Int, val label: String): Serializable {
    override fun toString() = label
}

class MSPResult: Serializable {

    @SerializedName("NEW_ID")
    @Expose
    var newid: String? = null
    @SerializedName("result")
    @Expose
    var result = ""
}

class MCodes {

    @SerializedName("records")
    @Expose
    var lst: List<MCode>? = null
}

class MCode: Serializable {

    @SerializedName("CODE")
    @Expose
    var code = 0
    @SerializedName("NAME")
    @Expose
    var name = ""
}

class MTransformItem {
    var index = 0
    var extractor = ""
    var replacement = ""
}
