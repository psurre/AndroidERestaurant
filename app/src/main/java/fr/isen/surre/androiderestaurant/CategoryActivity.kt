package fr.isen.surre.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.surre.androiderestaurant.model.DataModel
import org.json.JSONObject
import android.view.View
import fr.isen.surre.androiderestaurant.model.DishModel

/**
 * Classe utilisée pour l'activité d'affichage des plats d'une catégorie (Entrées, Plats, Desserts)
 * Cette activité affiche les plats sous forme de liste déroulante.
 * Fonctionnalités offertes à l'utilisateur :
 *  - Voir la liste des plats
 *  - Sélectionner un plat
 *
 * Hérite de OptionsMenuActivity pour afficher le menu dans l'activité.
 * @constructor Non implémenté.
 * @author Patrick Surre
 *
 */


class CategoryActivity : OptionsMenuActivity() {
    // Definition des variables
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var bindingDetAct : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ajout d'un log
        Log.i("CategoryActivity", "***************** CategoryActivity -> created ***************** ")
        // Binding
        bindingDetAct = ActivityDetailsBinding.inflate(layoutInflater)
        val view = bindingDetAct.root
        setContentView(view)

        // Récupération de l'intent -> Valeur de la catégorie
        val txtCategory = intent.getStringExtra(KEYDETAILTXT)
        // Affectation du texte récupéré via l'intent dans le composant TxtDetail
        bindingDetAct.TxtDetail.text = txtCategory.toString()

        // Gestion du recycleView
        linearLayoutManager = LinearLayoutManager(this)

        // Affichage des menus
        getDataMenu (txtCategory.toString())

    }

    override fun onResume() {
        super.onResume()
        // Mis à jour du menu
        invalidateOptionsMenu()
    }

    private fun onListItemClick(dish: DishModel) {
        // Fonction de gestion du clic dans la recycleview
        /**
         * Bascule vers l'activité [DishActivity]
         */
        val changePage = Intent(this, DishActivity::class.java)
        changePage.putExtra(KEYDISHES, dish)
        startActivity(changePage)
    }

    private fun showDishes(txtCategory: String, dishes: DataModel){
        /**
         * Fonction d'affichage des plats
         *
         * @param txtCategory Catégorie sélectionnée par l'utilisateur
         * @param dishes Liste de la carte des plats retournée par le webservice
         */
        // Variable contenant la liste des plats d'une catégorie
        val dishesReturn: ArrayList<DishModel> = arrayListOf()
        val recyclerView: RecyclerView = bindingDetAct.rcvMenu
        // Balayage des catégories pour trouver les plats qui nous intéressent
        for (category in dishes.data){
            if (category.name_fr == txtCategory ){
                for (dish in category.items) {
                    dishesReturn.add(dish)
                }
            }
        }
        val adapterCardHolder = CategoryAdapter (dishesReturn, { position -> onListItemClick(position) })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterCardHolder
    }

    private fun getDataMenu(txtCategory: String){
        /**
         * Fonction qui interroge le webservice pour récupèrer le menu
         *
         * @param txtCategory Catégorie sélectionnée par l'utilisateur
         *
         */
        // Récupération des éléments via un webservice
        val urlWebService = URLMENU
        val jsonObject = JSONObject()
        // Ajout des paramètres passés à l'URL
        jsonObject.put("id_shop", "1")
        var dishModel: DataModel
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlWebService, jsonObject,
            { response ->
                // Affichage de la ProgressBar
                bindingDetAct.progressLoader.visibility = View.VISIBLE
                // On met en pause 500ms juste pour voir le loader tourner :)
                Thread.sleep(500L)
                // Le résultat est parsé et envoyé dans la classe de DataModel
                dishModel = Gson().fromJson(response.toString(), DataModel::class.java)
                /**
                 * Appel de la fonction [showDishes]
                 */
                showDishes(txtCategory, dishModel)
                // Cacher la ProgressBar
                bindingDetAct.progressLoader.visibility = View.GONE
            },{error->
                bindingDetAct.TxtLog.text = error.message
            }
        )
        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }
    override fun onDestroy() {
        /**
         * Surcharge du onDestroy pour ajouter un log dans le onDestroy.
         */
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("CategoryActivity", "*****************  CategoryActivity -> Destroyed  *******************")
    }
}