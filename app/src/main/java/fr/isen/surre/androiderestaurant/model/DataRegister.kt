package fr.isen.surre.androiderestaurant.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class DataRegister (val data: UserData = UserData(), val code: String = ""): Serializable

@Keep data class UserData (
    val id: String = "",
    val code: String = "",
    val id_shop: String = "",
    val email: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val phone: String = "",
    val address: String = "",
    val postal_code: String = "",
    val birth_date: String = "",
    val town: String = "",
    val points: String = "",
    val token: String = "",
    val gcmtoken: String ="",
    val create_date: String = "",
    val update_date: String = ""
): Serializable
