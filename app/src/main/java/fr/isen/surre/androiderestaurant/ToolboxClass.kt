package fr.isen.surre.androiderestaurant

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.surre.androiderestaurant.model.DataBasket
import java.io.File
import com.android.volley.VolleyError
import java.io.UnsupportedEncodingException

/**
 * Fichier contenant toutes les fonctions utiles à l'ensemble des activités
 *
 * @author Patrick Surre
 *
 */

fun saveBasketJSON(view: View, basket: DataBasket, context: Context){
    /**
     * Fonction pour sauvegarder le panier dans un fichier JSON
     *
     * @param view La vue pour afficher les snackbar.
     * @param basket Le panier de type DataBasket.
     * @param context Le contexte pour récupérer le codeCacheDir.
     */
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    val jsonBasket: String = gsonPretty.toJson(basket)
    val message: String = "Panier sauvegardé"
    File(context.codeCacheDir, "basket.json").writeText(jsonBasket)
    showSnackbar (message, view)
}
fun loadBasketJSON(context: Context): DataBasket{
    /**
     * Fonction pour charger le panier depuis un fichier JSON
     *
     * @param context Le contexte pour récupérer le codeCacheDir.
     * @return Retourne un panier (classe DataBasket).
     */
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
    /**
     * Fonction pour sauvegarder l'Id d'un utilisateur dans les préférences de l'application
     *
     * @param context Le contexte pour récupérer le getSharedPreferences.
     * @param idUser Id de l'utilisateur à sauvegarder.
     */
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    var editor = UserPrefs.edit()
    editor.putString("user_id", idUser)
    editor.commit()
}

fun savePrefsQty (context: Context, basketItems: Int): Int{
    /**
     * Fonction pour sauvegarder le nombre de plats dans les préférences de l'application
     *
     * @param context Le contexte pour récupérer le getSharedPreferences.
     * @param basketItems Nombre de plats à sauvegarder.
     * @return Retourne la quantité de plats dans le panier.
     */
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    var editor = UserPrefs.edit()
    editor.putInt("basketquantity", basketItems)
    editor.commit()
    return UserPrefs.getInt("basketquantity", basketItems)
}

fun showToast(message: String, context: Context) {
    /**
     * Fonction pour afficher des Toast
     *
     * @param message Message à afficher dans le Toast.
     * @param context Le contexte pour la fonction makeText().
     *
     */
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.show()
}

fun showSnackbar (message: String, view: View){
    /**
     * Fonction pour afficher des Snackbar
     *
     * @param message Message à afficher dans le Toast.
     * @param view La vue à utiliser pour la fonction make().
     *
     */
    val snackbar = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
    snackbar.show()
}

fun checkConn(context: Context): Boolean{
    /**
     * Fonction pour tester si un utilisateur est connecté à l'application
     *
     * @param context Le contexte pour pour récupérer le getSharedPreferences.
     * @return Retourne vrai si l'utilisateur est connecté.
     */
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

fun getUserId(context: Context): String{
    /**
     * Fonction qui renvoie l'Id de l'utilisateur connecté
     *
     * @param context Le contexte pour pour récupérer le getSharedPreferences.
     * @return Retourne l'Id de l'utilisateur s'il est connecté, "" sinon.
     */
    var userId: String = ""
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    userId = UserPrefs.getString("user_id", userId).toString()
    return userId
}
fun removeUserId(context: Context): Boolean{
    /**
     * Fonction qui déconnecte l'utilisateur en supprimant son Id des préférences de l'application
     *
     * @param context Le contexte pour pour récupérer le getSharedPreferences.
     * @return Retourne vrai si la suppression s'est bien passée.
     */
    var returnRemove = true
    val UserPrefs = context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    returnRemove = UserPrefs.edit().remove("user_id").commit()
    return returnRemove
}
fun getBasketQty(context: Context): String{
    /**
     * Fonction qui récupère le nombre de plats dans le panier
     *
     * @param context Le contexte pour pour récupérer le getSharedPreferences.
     * @return Retourne le nombre de plats dans le panier.
     */
    var basketQty = ""
    val UserPrefs =  context.getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
    basketQty = UserPrefs.getInt("basketquantity", 0).toString()
    return basketQty
}

fun deleteBasketPersistent(context: Context){
    /**
     * Fonction pour supprimer le fichier basket.json et mettre à jour la qauntité dans les préférences.
     * Appelle la fonction [savePrefsQty].
     *
     * @param context Le contexte pour pour récupérer le codeCacheDir.
     *
     */
    if (File (context.codeCacheDir, "basket.json").exists()){
        File(context.codeCacheDir, "basket.json").delete()
    }
    savePrefsQty(context, 0)
}

fun initBasket(context: Context){
    /**
     * Fonction qui calcule le nombre de plats dans le fichier basket.json et met à jour les préférences avec cette valeur.
     * Appelle la fonction [savePrefsQty].
     *
     * @param context Le contexte pour pour récupérer le codeCacheDir.
     *
     */
    var itemInBasket = 0
    if (File (context.codeCacheDir, "basket.json").exists()){
        val basket = loadBasketJSON(context)
        for (item in basket.data){
            itemInBasket+=item.quantity
        }
    }
    savePrefsQty(context, itemInBasket)
}

fun onVolleyErrorResponse(error: VolleyError?): List<String> {
    /**
     * Fonction qui renvoie une liste d'erreurs générées par Volley.
     * Sont remontées les erreurs réseaux.
     *
     * @param error Les erreurs levées à l'exécution d'une requête par Volley.
     * @return Retourne une liste de détails sur l'erreur générée.
     *
     */
    if (error?.networkResponse == null) {
        return emptyList()
    }
    var returnList = arrayListOf<String>()
    returnList.add(error.networkResponse.statusCode.toString())
    //get response body and parse with appropriate encoding
    try {
        returnList.add(error.networkResponse.data.toString())
    } catch (e: UnsupportedEncodingException) {
        // exception
    }
    return returnList
}