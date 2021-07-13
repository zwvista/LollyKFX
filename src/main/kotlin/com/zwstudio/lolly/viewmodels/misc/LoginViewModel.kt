package com.zwstudio.lolly.viewmodels.misc

import com.zwstudio.lolly.services.misc.UserService
import io.reactivex.rxjava3.core.Single
import javafx.beans.property.SimpleStringProperty

class LoginViewModel {
    val username = SimpleStringProperty("")
    val password = SimpleStringProperty("")

    private val userService = UserService()

    fun login(): Single<String> =
        userService.getData(username.value!!, password.value!!)
            .map { if (it.isEmpty()) "" else it[0].userid }
            .applyIO()
}