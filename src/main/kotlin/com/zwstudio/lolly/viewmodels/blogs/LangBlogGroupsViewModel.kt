package com.zwstudio.lolly.viewmodels.blogs

import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.kotlin.subscribeBy
import tornadofx.onChange

class LangBlogGroupsViewModel: LangBlogViewModel() {

    fun reloadGroups() {
        langBlogGroupService.getDataByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstLangBlogGroupsAll = it.toMutableList(); applyGroupFilters() }
    }

    fun reloadPosts() {
        langBlogPostService.getDataByLangGroup(vmSettings.selectedLang.id, selectedGroup.value?.id ?: 0)
            .applyIO()
            .subscribeBy { lstLangBlogPostsAll = it.toMutableList(); applyPostFilters() }
    }

    init {
        selectedGroup.onChange { reloadPosts() }
    }
}
