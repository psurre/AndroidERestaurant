package fr.isen.surre.androiderestaurant.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class DataModel(val data: List<DishCategoryModel> = listOf()): Serializable

@Keep data class  DishPrices(
    val id: String = "",
    val id_pizza: String = "",
    val id_size: String = "",
    val price: String = "",
    val create_date: String = "",
    val update_date: String = "",
    val size: String = ""
): Serializable

@Keep data class  DishIngredients(
    val id: String = "",
    val id_shop: String = "",
    val name_fr: String = "",
    val name_en: String = "",
    val create_date: String = "",
    val update_date: String = "",
    val id_pizza: String = ""
): Serializable

@Keep data class DishModel(
    val id: String = "",
    val name_fr: String = "",
    val name_en: String = "",
    val id_category: String = "",
    val categ_name_fr: String = "",
    val categ_name_en: String = "",
    val images: List<String> = listOf(),
    val ingredients: List<DishIngredients> = listOf(),
    val prices: List<DishPrices> = listOf()
): Serializable {
    fun getFirstPicture (dish: DishModel): String{
        var firstImage: String
        if (dish.images.isEmpty()){
            firstImage=""
        }else{
            firstImage=dish.images[0]
        }
        return firstImage
    }
}

@Keep data class  DishCategoryModel(
    val name_fr: String = "",
    val name_en: String = "",
    val items: List<DishModel> = listOf()
): Serializable


