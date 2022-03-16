package fr.isen.surre.androiderestaurant.model
import androidx.annotation.Keep
import java.io.Serializable

@Keep data class DataBasket (var data: MutableList<DataBasketItem> = arrayListOf()): Serializable {

    fun getCountItemsInBasket(): Int{
        var itemsInBasket = 0
        for (item in this.data){
            itemsInBasket+=item.quantity
        }
        return itemsInBasket
    }
}

@Keep data class DataBasketItem (var dish: DishModel = DishModel(), var quantity: Int = 0): Serializable
