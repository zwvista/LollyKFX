package com.zwstudio.lolly.viewmodels.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import com.zwstudio.lolly.models.blogs.MLangBlogPost
import com.zwstudio.lolly.services.blogs.BlogService
import com.zwstudio.lolly.services.blogs.LangBlogGroupService
import com.zwstudio.lolly.services.blogs.LangBlogPostContentService
import com.zwstudio.lolly.services.blogs.LangBlogPostService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.asObservable
import tornadofx.onChange

open class LangBlogViewModel : BaseViewModel() {

    var lstLangBlogGroupsAll = mutableListOf<MLangBlogGroup>()
    var lstLangBlogGroups = mutableListOf<MLangBlogGroup>().asObservable()
    var groupFilter = SimpleStringProperty("")
    val noGroupFilter get() = groupFilter.value.isEmpty()
    var selectedGroup = SimpleObjectProperty<MLangBlogGroup?>()

    var lstLangBlogPostsAll = mutableListOf<MLangBlogPost>()
    var lstLangBlogPosts = mutableListOf<MLangBlogPost>().asObservable()
    var postFilter = SimpleStringProperty("")
    val noPostFilter get() = postFilter.value.isEmpty()
    var selectedPost = SimpleObjectProperty<MLangBlogPost?>()

    var postHtml = SimpleStringProperty("")
    private val blogService: BlogService by inject()

    protected val langBlogGroupService: LangBlogGroupService by inject()
    protected val langBlogPostService: LangBlogPostService by inject()
    protected val langBlogPostContentService: LangBlogPostContentService by inject()

    init {
        selectedPost.onChange {
            langBlogPostContentService.getDataById(it?.id ?: 0).subscribeBy {
                postHtml.value = blogService.markedToHtml(it.map { it.content }.orElse(""))
            }
        }
    }

    fun applyGroupFilters() {
        lstLangBlogGroups.setAll(if (noGroupFilter) lstLangBlogGroupsAll else lstLangBlogGroupsAll.filter {
            it.groupname.contains(groupFilter.value, true)
        })
    }

    fun applyPostFilters() {
        lstLangBlogPosts.setAll(if (noPostFilter) lstLangBlogPostsAll else lstLangBlogPostsAll.filter {
            it.title.contains(postFilter.value, true)
        })
    }

    fun newGroup() = MLangBlogGroup().apply {
        langid = vmSettings.selectedLang.id
    }

    fun updateGroup(item: MLangBlogGroup): Completable =
        langBlogGroupService.update(item)
            .applyIO()

    fun createGroup(item: MLangBlogGroup): Single<Int> =
        langBlogGroupService.create(item)
            .applyIO()

    fun deleteGroup(id: Int): Completable =
        langBlogGroupService.delete(id)
            .applyIO()

    fun newPost() = MLangBlogPost().apply {
        langid = vmSettings.selectedLang.id
    }

    fun updatePost(item: MLangBlogPost): Completable =
        langBlogPostService.update(item)
            .applyIO()

    fun createPost(item: MLangBlogPost): Single<Int> =
        langBlogPostService.create(item)
            .applyIO()

    fun deletePost(id: Int): Completable =
        langBlogPostService.delete(id)
            .applyIO()
}
