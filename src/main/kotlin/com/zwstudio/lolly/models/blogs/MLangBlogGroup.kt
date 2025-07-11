package com.zwstudio.lolly.models.blogs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MLangBlogGroups(
    @SerializedName("records")
    var lst: List<MLangBlogGroup> = emptyList()
)

data class MLangBlogGroup(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("LANGID")
    var langid: Int = 0,
    @SerializedName("NAME")
    var groupname: String = ""
) : Serializable {
    @Transient var gpid = 0
}
