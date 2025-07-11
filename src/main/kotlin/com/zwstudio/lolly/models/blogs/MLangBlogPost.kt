package com.zwstudio.lolly.models.blogs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MLangBlogPosts(
    @SerializedName("records")
    var lst: List<MLangBlogPost> = emptyList()
)

data class MLangBlogPost(
    @Expose(serialize = false, deserialize = true)
    @SerializedName("ID")
    var id: Int = 0,
    @SerializedName("LANGID")
    var langid: Int = 0,
    @SerializedName("TITLE")
    var title: String = "",
    @SerializedName("URL")
    var url: String = ""
) : Serializable {
    @Transient var gpid = 0
}
