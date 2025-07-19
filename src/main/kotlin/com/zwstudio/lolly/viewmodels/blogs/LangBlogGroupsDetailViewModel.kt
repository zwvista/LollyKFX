package com.zwstudio.lolly.viewmodels.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import tornadofx.ItemViewModel

class LangBlogGroupsDetailViewModel(val vm: LangBlogViewModel, item: MLangBlogGroup) : ItemViewModel<MLangBlogGroup>() {
    val id = bind(MLangBlogGroup::id)
    val langid = bind(MLangBlogGroup::langid)
    val groupname = bind(MLangBlogGroup::groupname)

}
