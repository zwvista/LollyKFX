package com.zwstudio.lolly.models.blogs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javafx.beans.property.SimpleStringProperty
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
    val groupnameProperty: SimpleStringProperty = SimpleStringProperty(""),
) : Serializable {
    @Transient var gpid = 0
    var groupname: String get() = groupnameProperty.value; set(value) { groupnameProperty.value = value }
}
