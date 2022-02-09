package fr.isen.surre.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.surre.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

const val BASKETKEY="basket"

class BasketActivity : OptionsMenuActivity() {
    // Définition des variables
    private lateinit var bindingBasketActivity : ActivityBasketBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var basket: DataBasket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        // Bindings
        bindingBasketActivity = ActivityBasketBinding.inflate(layoutInflater)
        val view = bindingBasketActivity.root
        setContentView(view)

        // Récupération du panier
        basket = intent.getSerializableExtra(BASKET) as DataBasket

        // LinerLayout du Recyclerview
        linearLayoutManager = LinearLayoutManager(this)

        // Initialisation de la page
        basketInit()

        bindingBasketActivity.btnPay.setOnClickListener {
            if (checkConn(this)){
                // L'utilisateur est connecté -> on peut passer la commande
                val changePage = Intent (this, ProcessOrderActivity::class.java)
                changePage.putExtra(BASKETKEY, basket)
                startActivity(changePage)
            }else{
                val changePage = Intent(this, AccountActivity::class.java)
                startActivity(changePage)
            }
        }
    }

    private fun onListItemClick(basketItem: DataBasketItem) {
        // Fonction de gestion du clic dans la recycleview
        // Mise à jour du fichier de sauvegarde JSON
        deleteDishInBasket(basketItem)
        //Rechargement de la page
        basketInit()
        val message: String = "Article "+ basketItem.dish.name_fr.toString()+" supprimé du panier"
        invalidateOptionsMenu()
        showSnackbar(message, bindingBasketActivity.root)
    }

    private fun basketInit(){
        // Initialisation du recyclerView
        val recyclerView: RecyclerView = bindingBasketActivity.rcvBasket
        // Initialisation de l'adapteur
        val basketAdapter = BasketAdapter(basket, { position -> onListItemClick(position) })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = basketAdapter
        if (getBasketQty(this).toInt() == 0){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun deleteDishInBasket (basketItem: DataBasketItem){
        var basketJSON: DataBasket = loadBasketJSON(this)
        basketJSON.data.remove(basketItem)
        savePrefsQty(this, basket.getCountItemsInBasket()-basketItem.quantity)
        basket.data.remove(basketItem)
        saveBasketJSON(bindingBasketActivity.root, basketJSON, this)
    }
    override fun onDestroy() {
        // Fonction d'ajout de logs dans le onDestroy
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("BasketActivity", "*****************  MainActivity -> Destroyed  *******************")
    }
}