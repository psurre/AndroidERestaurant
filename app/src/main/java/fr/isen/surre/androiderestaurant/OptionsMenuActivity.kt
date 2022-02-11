package fr.isen.surre.androiderestaurant

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.surre.androiderestaurant.model.DataBasket

/**
 * Classe utilisée pour gérer le menu de l'application
 * Cette activité affiche le menu.
 * Fonctionnalités offertes à l'utilisateur :
 *  - icone de login : se connecter / se déconnecter
 *  - icone de panier : accéder au panier s'il n'est pas vide
 *  - icone point d'interrogation : accéder à la page "A propos"
 *
 * @constructor Non implémenté.
 * @author Patrick Surre
 *
 */

open class OptionsMenuActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main,menu)

        val menuBasket: MenuItem? = menu?.findItem(R.id.itemBasket)
        val menuActionView: View? = menuBasket?.actionView
        val itemInBasket = menuActionView?.findViewById<TextView>(R.id.txtMenuBasket)
        val layoutCircle = menuActionView?.findViewById<FrameLayout>(R.id.view_alert_red_circle)
        /**
         * Appel de la fonction [getBasketQty]
         */
        itemInBasket?.text = getBasketQty(this)
        // Gestion de l'affichage de la pastille rouge
        if (itemInBasket?.text.toString().toInt() > 0){
            layoutCircle?.visibility=View.VISIBLE
        }else{
            layoutCircle?.visibility=View.GONE
        }
        menuActionView?.setOnClickListener {
            onOptionsItemSelected(menuBasket as MenuItem)
        }

        // Gestion du cadenas pour le login - logout
        if (getUserId(this) != ""){
            menu?.findItem(R.id.itemLogin)?.setIcon(R.drawable.ic_lock_24)
        }else{
            menu?.findItem(R.id.itemLogin)?.setIcon(R.drawable.ic_lock_open_24)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        /**
         * Gestion des clics sur les icones
         */
        when (item?.itemId)
        {
            R.id.itemLogin ->
            {
                /**
                 * Appel de la fonction [checkConn]
                 */
                if (checkConn(this)){
                    /**
                     * Appel de la fonction [removeUserId]
                     */
                    if (removeUserId(this)){
                        val message: String = "Déconnexion réalisée avec succès !"
                        showToast(message, this)
                        // Mise à jour du menu
                        invalidateOptionsMenu()
                    }
                }else{
                    /**
                     * Vers [AccountActivity]
                     */
                    val changePage = Intent(this, AccountActivity::class.java)
                    startActivity(changePage)
                }
            }
            R.id.itemBasket ->
            {
                // Récupération d'un éventuel panier
                /**
                 * Appel de la fonction [loadBasketJSON]
                 */
                var userBasket: DataBasket = loadBasketJSON(this)
                if (userBasket.data.isNotEmpty()) {
                    /**
                     * Vers [BasketActivity]
                     */
                    val changePage = Intent(this, BasketActivity::class.java)
                    changePage.putExtra(BASKET, userBasket)
                    startActivity(changePage)
                }else {
                    val message: String = "Votre panier est vide."
                    showToast(message, this)
                }
            }
            R.id.itemAbout ->
            {
                /**
                 * Vers [AboutActivity]
                 */
                startActivity(Intent(this, AboutActivity::class.java))
            }
            else -> {
                val message: String = "Action non prise en charge"
                showToast(message, this)
            }
        }
        return true
    }
}