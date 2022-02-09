package fr.isen.surre.androiderestaurant
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import fr.isen.surre.androiderestaurant.databinding.ActivityMainBinding
import fr.isen.surre.androiderestaurant.model.DataBasket


// Constantes
const val KEYDETAILTXT = "key.detail.txt"

class MainActivity : OptionsMenuActivity() {
    private lateinit var bindingMainAct : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "*****************  MainActivity -> created  *********************")
        // Binding
        bindingMainAct = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMainAct.root
        setContentView(view)
        initMainActivity()
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

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    private fun initMainActivity(){
        initBasket(this)
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
}


