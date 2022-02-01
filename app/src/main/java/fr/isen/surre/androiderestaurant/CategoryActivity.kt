package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
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


// Constantes
const val KEYDISHES = "key.dishes"

class CategoryActivity : AppCompatActivity() {
    // Definition des variables
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var bindingDetAct : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ajout d'un log
        Log.i("DetailActivity", "***************** DetailActivity -> created ***************** ")
        // Binding
        bindingDetAct = ActivityDetailsBinding.inflate(layoutInflater)
        val view = bindingDetAct.root
        setContentView(view)

        // Récupération de l'intent -> Valeur de la catégorie
        val txtCategory = intent.getStringExtra(KEYDETAILTXT)
        // Affectation du texte récupéré via l'intent dans le composant TxtDetail
        bindingDetAct.TxtDetail.setText(txtCategory.toString())

        // Gestion du recycleView
        linearLayoutManager = LinearLayoutManager(this)

        // Affichage des menus
        getDataMenu (txtCategory.toString())

    }

    private fun onListItemClick(dish: DishModel) {
        // Fonction de gestion du clic dans la recycleview
        val changePage = Intent(this, DishActivity::class.java)
        changePage.putExtra(KEYDISHES, dish)
        startActivity(changePage)
    }

    private fun showDishes(txtCategory: String, dishes: DataModel){
        // Variable contenant la liste des plats d'une catégorie
        var dishesReturn: ArrayList<DishModel> = arrayListOf()
        val recyclerView: RecyclerView = bindingDetAct.rcvMenu
        for (category in dishes.data){
            if (category.name_fr == txtCategory ){
                for (dish in category.items) {
                    dishesReturn.add(dish)
                }
            }else{
                //TODO Faire le cas ou on ne trouve pas la catégorie
            }
        }
        val adapterCardHolder = CatCardHolder (dishesReturn, { position -> onListItemClick(position) })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterCardHolder
    }

    private fun getDataMenu(txtCetegory: String){
        // Récupération des éléments via un webservice
        val urlWebService = "http://test.api.catering.bluecodegames.com/menu"
        val paramsWebService = HashMap<String, String>()
        paramsWebService["id_shop"] = "1"
        val jsonObject = JSONObject(paramsWebService as Map<*, *>?)
        var dishModel: DataModel
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlWebService, jsonObject,
            { response ->
                // Affichage de la ProgressBar
                bindingDetAct.progressLoader.visibility = View.VISIBLE
                // On met en pause 1s juste pour voir le loader tourner :)
                Thread.sleep(1000L)
                // Le résultat est parsé et envoyé dans la classe de DataModel
                dishModel = Gson().fromJson(response.toString(), DataModel::class.java)
                showDishes(txtCetegory, dishModel)
                // Cacher la ProgressBar
                bindingDetAct.progressLoader.visibility = View.GONE
            },{error->
                bindingDetAct.TxtLog.text = error.message
            }
        )
        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }
}