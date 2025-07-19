package com.zwstudio.lolly.models.blogs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
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
    val titleProperty: SimpleStringProperty = SimpleStringProperty(""),
    @SerializedName("URL")
    val urlProperty: SimpleStringProperty = SimpleStringProperty(""),
) : Serializable {
    @Transient var gpid = 0
    var title: String get() = titleProperty.value; set(value) { titleProperty.value = value }
    var url: String get() = urlProperty.value; set(value) { urlProperty.value = value }
}
