package fr.isen.surre.androiderestaurant

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.surre.androiderestaurant.model.DataBasket

open class OptionsMenuActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main,menu)
        val menuBasket: MenuItem? = menu?.findItem(R.id.itemBasket)
        val menuActionView: View? = menuBasket?.actionView
        val itemInBasket = menuActionView?.findViewById<TextView>(R.id.txtMenuBasket)
        val layoutCircle = menuActionView?.findViewById<FrameLayout>(R.id.view_alert_red_circle)
        itemInBasket?.text = getBasketQty(this)
        if (itemInBasket?.text.toString().toInt() > 0){
            layoutCircle?.visibility=View.VISIBLE
        }else{
            layoutCircle?.visibility=View.GONE
        }
        if (menuActionView != null) {
            menuActionView.setOnClickListener {
                onOptionsItemSelected(menuBasket as MenuItem)
            }
        }
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
            R.id.itemBasket ->
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
            R.id.itemAbout ->
            {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            else -> {
                val message: String = "Action inconnue"
                showToast(message, this)
            }
        }
        return true
    }
}