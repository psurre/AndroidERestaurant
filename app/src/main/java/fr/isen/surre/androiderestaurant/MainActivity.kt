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

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainAct : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "*****************  MainActivity -> created  *********************")
        // Binding
        bindingMainAct = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMainAct.root
        setContentView(view)

        // Actions sur les types de plats
        bindingMainAct.txtStarter.setOnClickListener {
            dishesDetails(bindingMainAct.txtStarter.text.toString())
        }
        bindingMainAct.txtMainCourse.setOnClickListener {
            dishesDetails(bindingMainAct.txtMainCourse.text.toString())
        }
        bindingMainAct.txtDessert.setOnClickListener {
            dishesDetails(bindingMainAct.txtDessert.text.toString())
        }

    }
    override fun onDestroy() {
        // Fonction d'ajout de logs dans le onDestroy
        super.onDestroy()
        Log.i("MainActivity", "*****************  MainActivity -> Destroyed  *******************")
    }

    private fun dishesDetails( dishType : String) {
        // Fonction qui transmet le type de plats à la fenêtre de détails
        val changePage = Intent(this, DetailActivity::class.java)
        changePage.putExtra(KEYDETAILTXT, dishType)
        startActivity(changePage)
    }

}


