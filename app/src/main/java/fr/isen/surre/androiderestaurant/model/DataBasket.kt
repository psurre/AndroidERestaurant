package fr.isen.surre.androiderestaurant.model
import java.io.Serializable

data class DataBasket(var dish: DishModel = DishModel(), var quantity: Int = 0): Serializable
