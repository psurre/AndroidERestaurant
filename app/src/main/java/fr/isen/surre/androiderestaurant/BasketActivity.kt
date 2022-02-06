package fr.isen.surre.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.surre.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

class BasketActivity : AppCompatActivity() {
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
                // TODO implémenter le passage de commande
            }else{
                val changePage = Intent(this, AccountActivity::class.java)
                startActivity(changePage)
            }
        }
    }

    private fun onListItemClick(basketItem: DataBasketItem) {
        // Fonction de gestion du clic dans la recycleview
        // Retrait du plat du panier
        basket.data.remove(basketItem)
        // Mise à jour du fichier de sauvegarde JSON
        deleteDishInBasket(basketItem)
        //Rechargement de la page
        basketInit()
        val message: String = "Article "+ basketItem.dish.name_fr.toString()+" supprimé du panier"
        showSnackbar(message, bindingBasketActivity.root)
    }

    private fun basketInit(){
        // Initialisation du recyclerView
        val recyclerView: RecyclerView = bindingBasketActivity.rcvBasket
        // Initialisation de l'adapteur
        val basketAdapter = BasketAdapter(basket, { position -> onListItemClick(position) })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = basketAdapter
    }

    private fun deleteDishInBasket (basketItem: DataBasketItem){
        var basket: DataBasket = loadBasketJSON(this)
        basket.data.remove(basketItem)
        saveBasketJSON(bindingBasketActivity.root,basket, this)
        saveUserPrefs(this, -basketItem.quantity)
    }
}