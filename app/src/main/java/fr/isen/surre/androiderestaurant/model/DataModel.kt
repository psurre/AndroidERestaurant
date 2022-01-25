package fr.isen.surre.androiderestaurant.model

import java.io.Serializable

data class DataModel(val data: List<DishCategoryModel> = listOf()): Serializable

data class  DishPrices(
    val id: String,
    val id_pizza: String,
    val id_size: String,
    val price: String,
    val create_date: String,
    val update_date: String,
    val size: String
): Serializable

data class  DishIngredients(
    val id: String,
    val id_shop: String,
    val name_fr: String,
    val name_en: String,
    val create_date: String,
    val update_date: String,
    val id_pizza: String
): Serializable

data class DishModel(
    val id: String,
    val name_fr: String,
    val name_en: String,
    val id_category: String,
    val categ_name_fr: String,
    val categ_name_en: String,
    val images: List<String>,
    val ingredients: List<DishIngredients>,
    val prices: List<DishPrices>
): Serializable

data class  DishCategoryModel(
    val name_fr: String,
    val name_en: String,
    val items: List<DishModel>
): Serializable


