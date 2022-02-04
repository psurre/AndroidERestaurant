package fr.isen.surre.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.surre.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem
import fr.isen.surre.androiderestaurant.model.DishModel

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
            val changePage = Intent(this, AccountActivity::class.java)
            startActivity(changePage)
        }

    }

    private fun onListItemClick(basketItem: DataBasketItem) {
        // Fonction de gestion du clic dans la recycleview
        // TODO Toast pour vérifier le bon fonctionnement
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, "Article "+ basketItem.dish.name_fr.toString()+" supprimé du panier", duration)
        toast.show()
    }

    private fun basketInit(){
        // Initialisation du recyclerView
        val recyclerView: RecyclerView = bindingBasketActivity.rcvBasket
        // Initialisation de l'adapteur
        val basketAdapter = BasketAdapter(basket, { position -> onListItemClick(position) })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = basketAdapter
    }

}