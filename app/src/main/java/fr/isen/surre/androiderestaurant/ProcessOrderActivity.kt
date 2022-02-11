package fr.isen.surre.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.ActivityProcessOrderBinding
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataOrderResponse
import org.json.JSONObject

/**
 * Classe utilisée pour gérer l'envoi de la commande
 * Cette activité affiche le statut d'envoi de la commande
 * Fonctionnalités offertes à l'utilisateur :
 *  - Voir si la commande a bien été transmise
 *
 * @constructor Non implémenté.
 * @author Patrick Surre
 *
 */

class ProcessOrderActivity : AppCompatActivity() {

    // Binding
    private lateinit var bindingProcessOrderActivity: ActivityProcessOrderBinding
    private var basket:DataBasket = DataBasket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingProcessOrderActivity = ActivityProcessOrderBinding.inflate(layoutInflater)
        val view = bindingProcessOrderActivity.root
        setContentView(view)
        // Fonction d'initialisation de l'activité
        /**
         * Appel de la fonction [initProcessOrderActivity]
         */
        initProcessOrderActivity()
        bindingProcessOrderActivity.btnBackHome.setOnClickListener {
            /**
             * Vers [MainActivity]
             */
            val changePage = Intent(this, MainActivity::class.java)
            startActivity(changePage)
        }
    }

    override fun onStart() {
        super.onStart()
        if (processOrder()){
            // Changer l'image, le texte et afficher le bouton
            bindingProcessOrderActivity.txtOrderStatus.text=getText(R.string.project_orderFinish)
            bindingProcessOrderActivity.btnBackHome.visibility=View.VISIBLE
            /**
             * Appel de la fonction [deleteBasket]
             */
            deleteBasket()
        }
    }

    private fun initProcessOrderActivity(){
        /**
         * Fonction permettant d'initialiser la page
         */
        // Visibilité des éléments
        bindingProcessOrderActivity.pgbOrder.visibility=View.INVISIBLE
        bindingProcessOrderActivity.btnBackHome.visibility=View.INVISIBLE
        bindingProcessOrderActivity.imgOrderFinish.visibility=View.INVISIBLE

        // Recupérer le panier
        basket = intent.getSerializableExtra(BASKETKEY) as DataBasket
    }

    private fun processOrder (): Boolean{
        /**
         * Fonction de transmission de la commande.
         *
         * @return Retourne vrai si la commande a bien été passée.
         */
        var resultProcessOrder: Boolean = true

        // Afficher la progressBar
        bindingProcessOrderActivity.pgbOrder.visibility=View.VISIBLE
        //Envoie vers le webservice
        // Paramètres attendus : id_shop, id_user et msg
        val userId = getUserId(this)
        val urlOrder: String = URLORDER
        val jsonOrder: String = Gson().toJson(basket)
        var responseOrder: DataOrderResponse = DataOrderResponse()
        var jsonReqObject = JSONObject()
        jsonReqObject.put("id_shop", "1")
        jsonReqObject.put("id_user", userId)
        jsonReqObject.put("msg", jsonOrder)
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlOrder, jsonReqObject,
            { response ->
                responseOrder = Gson().fromJson(response.toString(), DataOrderResponse::class.java)
                if (responseOrder.code == "NOK"){
                       showSnackbar("La commande n'a pas pu être traitée", bindingProcessOrderActivity.root)
                        resultProcessOrder = false
                }
            },{ error ->
                // Gérer l'erreur
                showSnackbar("Serveur indisponible : réessayez plus tard", bindingProcessOrderActivity.root)
                resultProcessOrder = false
            }
        )
        VolleySingleton.getInstance((this).applicationContext)
            .addToRequestQueue(stringRequest)
        // Pause pour afficher un peu la progressBar si ça va trop vite
        Thread.sleep(500L)
        // Cacher la progressBar
        bindingProcessOrderActivity.pgbOrder.visibility=View.GONE
        return resultProcessOrder
    }

    private fun deleteBasket (){
        /**
         * Fonction de suppression du panier
         */
        basket = DataBasket()
        /**
         * Appel à la fonction [deleteBasketPersistent]
         */
        deleteBasketPersistent(this)
    }
}