package fr.isen.surre.androiderestaurant.model
import java.io.Serializable

data class DataBasket (var data: MutableList<DataBasketItem> = arrayListOf()): Serializable {

    fun getCountItemsInBasket(): Int{
        var itemsInBasket = 0
        for (item in this.data){
            itemsInBasket+=item.quantity
        }
        return itemsInBasket
    }
}

data class DataBasketItem (var dish: DishModel = DishModel(), var quantity: Int = 0): Serializable
