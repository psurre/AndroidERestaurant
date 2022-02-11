package fr.isen.surre.androiderestaurant
import android.os.Bundle
import android.content.Intent
import android.util.Log
import fr.isen.surre.androiderestaurant.databinding.ActivityMainBinding

/**
 * Classe principale de l'application.
 *
 * Hérite de OptionsMenuActivity pour afficher le menu dans l'activité.
 * @constructor Non implémenté.
 * @author Patrick Surre
 */

class MainActivity : OptionsMenuActivity() {
    private lateinit var bindingMainAct : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ajout d'un log
        Log.i("MainActivity", "*****************  MainActivity -> created  *********************")
        // Binding
        bindingMainAct = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMainAct.root
        setContentView(view)
        /**
         * Appel de la fonction [initMainActivity]
         */
        initMainActivity()
        // Actions Clic sur les types de plats
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
        // Mise à jour du menu
        invalidateOptionsMenu()
    }

    private fun initMainActivity(){
        /**
         * Fonction qui permet d'initialiser la page
         *
         * Appel de la fonction [initBasket]
         */
        initBasket(this)
    }
    override fun onDestroy() {
        /**
         * Surcharge du onDestroy pour ajouter un log dans le onDestroy.
         */
        super.onDestroy()
        Log.i("MainActivity", "*****************  MainActivity -> Destroyed  *******************")
    }

    private fun dishesDetails( dishType : String) {
        /**
         * Fonction qui permet de changer de page vers la page des plats de la catégorie choisie.
         *
         * @param dishType Catégorie des plats.
         *
         * Vers l'activité [CategoryActivity]
         */
        val changePage = Intent(this, CategoryActivity::class.java)
        changePage.putExtra(KEYDETAILTXT, dishType)
        startActivity(changePage)
    }
}


