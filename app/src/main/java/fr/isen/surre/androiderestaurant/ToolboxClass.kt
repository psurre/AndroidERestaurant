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

fun saveUserPrefs (context: Context, basketItems: Int): Int{
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    var editor = UserPrefs.edit()
    var actualBasket: Int = 0
    actualBasket = UserPrefs.getInt("basketquantity", actualBasket)
    editor.putInt("basketquantity", actualBasket + basketItems)
    editor.commit()
    actualBasket = UserPrefs.getInt("basketquantity", actualBasket)
    return actualBasket
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
    UserPrefs.getString("user_id", user_id)
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