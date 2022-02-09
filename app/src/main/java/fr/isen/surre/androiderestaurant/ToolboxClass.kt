package fr.isen.surre.androiderestaurant

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.surre.androiderestaurant.model.DataBasket
import java.io.File

fun saveBasketJSON(view: View, basket: DataBasket, context: Context){
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    val jsonBasket: String = gsonPretty.toJson(basket)
    val message: String = "Panier sauvegardé"
    File(context.codeCacheDir, "basket.json").writeText(jsonBasket)
    showSnackbar (message, view)
}
fun loadBasketJSON(context: Context): DataBasket{
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    var basket: DataBasket = DataBasket()
    lateinit var jsonString: String
    if (File (context.codeCacheDir, "basket.json").exists()){
        jsonString = File(context.codeCacheDir,"basket.json").readText()
        basket = gsonPretty.fromJson(jsonString, DataBasket::class.java)
    }
    return basket
}

fun savePrefsIdUser (context: Context, idUser: String){
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    var editor = UserPrefs.edit()
    editor.putString("user_id", idUser)
    editor.commit()
}

fun savePrefsQty (context: Context, basketItems: Int): Int{
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    var editor = UserPrefs.edit()
    editor.putInt("basketquantity", basketItems)
    editor.commit()
    return UserPrefs.getInt("basketquantity", basketItems)
}

fun showToast(message: String, context: Context) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.show()
}

fun showSnackbar (message: String, view: View){
    val snackbar = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
    // Show
    snackbar.show()
}

fun checkConn(context: Context): Boolean{
    var stateEnd: Boolean = true
    // Controle des parametres utilisateurs si on a un user_id
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    var user_id: String = ""
    user_id = UserPrefs.getString("user_id", user_id).toString()
    if (user_id.isEmpty()){
        // L'utilisateur n'est pas connecté
        stateEnd = false
    }
    return stateEnd
}

fun getUserBasket(context: Context): DataBasket{
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    var basket: DataBasket = DataBasket()
    lateinit var jsonString: String
    if (File (context.codeCacheDir, "basket.json").exists()){
        jsonString = File(context.codeCacheDir,"basket.json").readText()
        basket = gsonPretty.fromJson(jsonString, DataBasket::class.java)
    }
    return basket
}

fun getUserId(context: Context): String{
    var userId: String = ""
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    userId = UserPrefs.getString("user_id", userId).toString()
    return userId
}
fun removeUserId(context: Context): Boolean{
    var returnRemove = true
    val UserPrefs = context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    UserPrefs.edit().remove("user_id").commit()
    return returnRemove
}
fun getBasketQty(context: Context): String{
    var basketQty = ""
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    basketQty = UserPrefs.getInt("basketquantity", 0).toString()
    return basketQty
}

fun deleteBasketPersistent(context: Context){
    if (File (context.codeCacheDir, "basket.json").exists()){
        File(context.codeCacheDir, "basket.json").delete()
    }
    savePrefsQty(context, 0)
}

fun initBasket(context: Context){
    var itemInBasket = 0
    if (File (context.codeCacheDir, "basket.json").exists()){
        val basket = loadBasketJSON(context)
        for (item in basket.data){
            itemInBasket+=item.quantity
        }
    }
    savePrefsQty(context, itemInBasket)
}