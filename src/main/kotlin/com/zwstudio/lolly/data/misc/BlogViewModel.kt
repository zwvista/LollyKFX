package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.service.misc.BlogService

class BlogViewModel : BaseViewModel() {
    val blogService: BlogService by inject()

    init {

    }
}
