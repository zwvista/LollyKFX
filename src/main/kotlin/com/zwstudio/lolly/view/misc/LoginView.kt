package com.zwstudio.lolly.view.misc

import tornadofx.*

class LoginView : Fragment("Login") {
    override val root = form {
        fieldset {
            field("USERNAME") {
                textfield()
            }
            field("PASSWORD") {
                textfield()
            }
        }
    }
}