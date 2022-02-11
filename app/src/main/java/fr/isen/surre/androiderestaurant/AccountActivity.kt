package fr.isen.surre.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.surre.androiderestaurant.databinding.ActivityAccountBinding

/**
 * Classe utilisée pour l'activité de gestion de compte.
 * Cette activité utilise deux fragments, l'un pour le login, et l'autre pour enregistrer un compte.
 *
 * @constructor Non implémenté.
 * @author Patrick Surre
 *
 */

class AccountActivity : OptionsMenuActivity() {

    // Définition des variables
    private lateinit var bindingAccountActivity: ActivityAccountBinding
    val loginFragment = LoginFragment()
    val registerFragment = RegisterFragment()
    val fragmentManager : FragmentManager = supportFragmentManager
    val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAccountActivity = ActivityAccountBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_account)

        // A la création, affichage du fragment de login
        fragmentTransaction.replace(R.id.frameLogin, loginFragment).commit()
    }

    fun gotoRegister (){
        /**
         * Fonction qui permet de changer le fragment actuel pour afficher le fragment d'enregistrement.
         */
        val fragmentRegisterTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentRegisterTransaction.replace(R.id.frameLogin, registerFragment).commit()
    }

    override fun onDestroy() {
        /**
         * Surcharge du onDestroy pour ajouter un log dans le onDestroy.
         */
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("AccountActivity", "*****************  MainActivity -> Destroyed  *******************")
    }
}