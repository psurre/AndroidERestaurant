package fr.isen.surre.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.ActivityDishBinding
import fr.isen.surre.androiderestaurant.model.DataModel
import org.json.JSONObject


class DishActivity : AppCompatActivity() {
    private lateinit var bindingDishAct : ActivityDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDishAct = ActivityDishBinding.inflate(layoutInflater)
        val view = bindingDishAct.root
        setContentView(view)
        // Dish contient l'identifiant du plat
        val dish = intent.getStringExtra(KEYDISHTXT)
        getDataMenu(dish.toString())
    }
    private fun getDataMenu(idDish: String){
        // Récupération des éléments via un webservice
        val urlWebService = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        var dishDetail: DataModel
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlWebService, jsonObject,
            { response ->
                // Affichage de la ProgressBar
                bindingDishAct.pgbDish.visibility = View.VISIBLE
                // On met en pause 1s juste pour voir le loader tourner :)
                Thread.sleep(1000L)
                // Le résultat est parsé et envoyé dans la classe de DataModel
                dishDetail = Gson().fromJson(response.toString(), DataModel::class.java)
                showDishDetail (idDish, dishDetail)
                // Cacher la ProgressBar
                bindingDishAct.pgbDish.visibility = View.GONE
            },{error->
                Log.e("Recuperation du plat", "Le détail du plat n'a pas pu être récupéré")
            }
        )
        VolleySingleton.getInstance(applicationContext)
            .addToRequestQueue(stringRequest)
    }
    private fun showDishDetail (idDish: String, dishDetail: DataModel){
        loopCat@for (cat in dishDetail.data){
            for (dish in cat.items){
                if (dish.id == idDish){
                    // On a trouvé notre plat
                    bindingDishAct.txtPrice.text = dish.prices[0]?.price
                    bindingDishAct.txtDishName.text = dish.name_fr
                    //Ingredients
                    for (ingredient in dish.ingredients){
                        val previousText = bindingDishAct.txtIngredients.text
                        if (previousText.isNotEmpty()) {
                            bindingDishAct.txtIngredients.text =
                                previousText.toString()+", "+ingredient.name_fr
                        }else{
                            bindingDishAct.txtIngredients.text = ingredient.name_fr
                        }
                    }
                    break@loopCat
                }
            }
        }
    }
}