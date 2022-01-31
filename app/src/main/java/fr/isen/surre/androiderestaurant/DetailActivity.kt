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


// Constantes
public const val KEYDISHTXT = "key.dish.txt"

class DetailActivity : AppCompatActivity() {
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

        // Récupération de l'intent
        val message = intent.getStringExtra(KEYDETAILTXT)
        // Affectation du texte récupéré via l'intent dans le composant TxtDetail
        bindingDetAct.TxtDetail.setText(message.toString())

        // Gestion du recycleView
        linearLayoutManager = LinearLayoutManager(this)

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
                // On met en pause 2s juste pour voir le loader tourner :)
                Thread.sleep(2000L)
                // Le résultat est parsé et envoyé dans la classe de DataModel
                dishModel = Gson().fromJson(response.toString(), DataModel::class.java)
                showDishes(message.toString(), dishModel)
                // Cacher la ProgressBar
                bindingDetAct.progressLoader.visibility = View.GONE
            },{error->
                bindingDetAct.TxtLog.text = error.message
            }
        )
        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun onListItemClick(dish: String) {
        // Fonction de gestion du clic dans la recycleview
        val changePage = Intent(this, DishActivity::class.java)
        changePage.putExtra(KEYDISHTXT, dish)
        startActivity(changePage)
    }

    private fun showDishes(message: String, dishes: DataModel){
        var dishesReturn: ArrayList<String> = arrayListOf()
        val recyclerView: RecyclerView = bindingDetAct.rcvMenu
        for (category in dishes.data){
            if (category.name_fr == message ){
                for (dish in category.items) {
                    dishesReturn.add(dish.id)
                }
            }
        }
        //val adapter = MenuAdapter(dishesReturn, { position -> onListItemClick(position) })
        val adapterCardHolder = CatCardHolder (dishesReturn, { position -> onListItemClick(position) }, dishes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = adapter
        recyclerView.adapter = adapterCardHolder
    }
}