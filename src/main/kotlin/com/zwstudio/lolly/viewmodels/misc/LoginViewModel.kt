package com.zwstudio.lolly.viewmodels.misc

import com.zwstudio.lolly.services.misc.UserService
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleStringProperty

class LoginViewModel {
    val username = SimpleStringProperty("")
    val password = SimpleStringProperty("")

    private val userService = UserService()

    fun login(): Observable<String> =
        userService.getData(username.value!!, password.value!!)
            .map { if (it.isEmpty()) "" else it[0].userid }
            .applyIO()
}