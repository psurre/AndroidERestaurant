package fr.isen.surre.androiderestaurant
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.GsonBuilder
import fr.isen.surre.androiderestaurant.databinding.ActivityMainBinding
import fr.isen.surre.androiderestaurant.model.DataBasket
import java.io.File

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
        val changePage = Intent(this, CategoryActivity::class.java)
        changePage.putExtra(KEYDETAILTXT, dishType)
        startActivity(changePage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item?.itemId)
        {
            R.id.itemLogin ->
            {
                if (checkConn(this)){
                    val message: String = "Vous êtes déjà connecté."
                    showToast(message, this)
                }else{
                    val changePage = Intent(this, AccountActivity::class.java)
                    startActivity(changePage)
                }
            }
            R.id.itemBasket  ->
            {
                // Récupération d'un éventuel panier
                var userBasket: DataBasket = getUserBasket(this)
                if (userBasket.data.isNotEmpty()) {
                    val changePage = Intent(this, BasketActivity::class.java)
                    changePage.putExtra(BASKET, userBasket)
                    startActivity(changePage)
                }else {
                    val message: String = "Votre panier est vide."
                    showToast(message, this)
                }
            }
            else -> {
                val message: String = "Action inconnue"
                showToast(message, this)
            }
        }
        return true
    }
}


