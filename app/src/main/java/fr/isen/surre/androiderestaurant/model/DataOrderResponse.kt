package fr.isen.surre.androiderestaurant.model
import java.io.Serializable

data class DataOrderResponse(
    val id_sender: Int = 0,
    val id_receiver: Int = 0,
    val sender: String = "",
    val receiver: String = "",
    val code: String = "",
    val type_msg: Int = 0,
    val message: DataBasket = DataBasket()
) : Serializable