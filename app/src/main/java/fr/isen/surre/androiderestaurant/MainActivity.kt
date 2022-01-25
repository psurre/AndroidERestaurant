package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.ActivityMainBinding
import org.json.JSONObject
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.annotations.SerializedName
import fr.isen.surre.androiderestaurant.model.DataModel
import fr.isen.surre.androiderestaurant.model.DishCategoryModel
import kotlinx.serialization.Serializable


// Constantes
const val KEYDETAILTXT = "key.detail.txt"
const val KEYDATAMODEL = "key.datamodel"

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainAct : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "*****************  MainActivity -> created  *********************")
        // Binding
        bindingMainAct = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMainAct.root
        setContentView(view)

        // Récupération des éléments via un webservice
        val urlWebService = "http://test.api.catering.bluecodegames.com/menu"
        val paramsWebService = HashMap<String, String>()
        paramsWebService["id_shop"] = "1"
        val jsonObject = JSONObject(paramsWebService as Map<*, *>?)
        var dishModel: DataModel
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlWebService, jsonObject,
            { response ->
                // Le résultat est parsé et envoyé dans la classe de DataModel
                dishModel = Gson().fromJson(response.toString(), DataModel::class.java)
                // display the response string on text view
                //bindingMainAct.txtInfo.text = response.toString()

            },{error->
                bindingMainAct.txtInfo.text = error.message
            }
        )
        // Actions sur les types de plats
        bindingMainAct.txtStarter.setOnClickListener {
            //dishesDetails(bindingMainAct.txtStarter.text.toString())
            VolleySingleton.getInstance(applicationContext)
                .addToRequestQueue(stringRequest)
            TODO("Faire un loader sur la page DetailActivity et lancer la requete à ce moment là.")
            for (category in dishModel?.data){
                if (category.name_fr == bindingMainAct.txtStarter.text.toString()){
                    dishesDetails(category.name_fr, dishModel)
                }
            }
        }
        bindingMainAct.txtMainCourse.setOnClickListener {
            dishesDetails(bindingMainAct.txtMainCourse.text.toString(), dishModel)
        }
        bindingMainAct.txtDessert.setOnClickListener {
            dishesDetails(bindingMainAct.txtDessert.text.toString(), dishModel)
        }

    }
    override fun onDestroy() {
        // Fonction d'ajout de logs dans le onDestroy
        super.onDestroy()
        Log.i("MainActivity", "*****************  MainActivity -> Destroyed  *******************")
    }

    private fun dishesDetails( dishType : String, dataModel: DataModel) {
        // Fonction qui transmet le type de plats à la fenêtre de détails
        val changePage = Intent(this, DetailActivity::class.java)
        changePage.putExtra(KEYDETAILTXT, dishType)
        changePage.putExtra(KEYDATAMODEL, dataModel)
        startActivity(changePage)
    }

}


