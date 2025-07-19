package com.zwstudio.lolly.viewmodels.blogs

import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.kotlin.subscribeBy
import tornadofx.onChange

class LangBlogPostsViewModel: LangBlogViewModel() {

    fun reloadPosts() {
        langBlogPostService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstLangBlogPostsAll = it.toMutableList(); applyPostFilters() }
    }

    fun reloadGroups() {
        langBlogGroupService.getDataByLangPost(vmSettings.selectedLang.id, selectedPost.value?.id ?: 0)
            .applyIO()
            .subscribeBy { lstLangBlogGroupsAll = it.toMutableList(); applyGroupFilters() }
    }

    init {
        selectedPost.onChange { reloadGroups() }
    }
}
