package fr.isen.surre.androiderestaurant.model
import java.io.Serializable

data class DataBasket (var data: MutableList<DataBasketItem> = arrayListOf()): Serializable

data class DataBasketItem (var dish: DishModel = DishModel(), var quantity: Int = 0): Serializable
