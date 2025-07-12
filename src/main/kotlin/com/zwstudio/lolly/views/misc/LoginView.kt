package com.zwstudio.lolly.views.misc

import com.zwstudio.lolly.common.GlobalUser
import com.zwstudio.lolly.common.configFile
import com.zwstudio.lolly.viewmodels.misc.LoginViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.geometry.Pos
import javafx.scene.control.Alert
import tornadofx.*
import java.io.FileOutputStream
import java.util.*

class LoginView : Fragment("Login") {
    val vm = LoginViewModel()
    var result = false

    override val root = form {
        hbox {
            alignment = Pos.CENTER
            label("Lolly")
        }
        fieldset {
            field("USERNAME") {
                textfield(vm.username)
            }
            field("PASSWORD") {
                passwordfield(vm.password)
            }
        }
        buttonbar {
            button("Login") {

            }.action {
                vm.login().subscribeBy {
                    GlobalUser.userid = it
                    if (GlobalUser.userid.isEmpty())
                        Alert(Alert.AlertType.ERROR).apply {
                            title = "Login"
                            contentText = "Wrong username or password!"
                        }.showAndWait()
                    else {
                        val props = Properties()
                        props.setProperty("userid", GlobalUser.userid)
                        props.setProperty("username", "")
                        val outStream = FileOutputStream(configFile)
                        props.storeToXML(outStream, "Configuration")
                        result = true
                        close()
                    }
                }
            }
            button("Exit") {

            }.action {
                close()
            }
        }
    }
}
