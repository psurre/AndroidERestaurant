package fr.isen.surre.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.surre.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

/**
 * Classe utilisée pour l'activité de gestion du panier.
 * Cette activité affiche le panier.
 * Fonctionnalités offertes à l'utilisateur :
 *  - supprimer un article du panier
 *  - payer et passer la commande
 *
 * Hérite de OptionsMenuActivity pour afficher le menu dans l'activité.
 * @constructor Non implémenté.
 * @author Patrick Surre
 *
 */

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

        // Action pour le clic sur le bouton Payer
        bindingBasketActivity.btnPay.setOnClickListener {
            // Vérification si l'utilisateur est connecté
            if (checkConn(this)){
                // Utilisateur connecté
                    // Affichage du loader pour le traitement de la commande
                bindingBasketActivity.pgbPay.visibility = View.VISIBLE
                // Tempo pour qu'on voit un peu le loader
                Thread.sleep(500L)
                // Passage de la commande
                /**
                 * Bascule vers l'activité [ProcessOrderActivity]
                 */
                val changePage = Intent (this, ProcessOrderActivity::class.java)
                changePage.putExtra(BASKETKEY, basket)
                startActivity(changePage)
            }else{
                // Utilisateur non connecté, renvoie vers la page de connexion
                /**
                 * Bascule vers l'activité [AccountActivity]
                 */
                val changePage = Intent(this, AccountActivity::class.java)
                startActivity(changePage)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Rechargement du panier
        basketInit()
    }

    private fun onListItemClick(basketItem: DataBasketItem) {
        // Fonction de gestion du clic dans la recycleview
        // Mise à jour du fichier de sauvegarde JSON
        deleteDishInBasket(basketItem)
        //Rechargement de la page
        basketInit()
        val message: String = "Article "+ basketItem.dish.name_fr.toString()+" supprimé du panier"
        // Rechargement du menu
        invalidateOptionsMenu()
        showSnackbar(message, bindingBasketActivity.root)
    }


    private fun basketInit(){
        /**
         * Fonction qui initialise la page
         *
         */
        // Initialisation du recyclerView
        val recyclerView: RecyclerView = bindingBasketActivity.rcvBasket
        // Initialisation de l'adapteur
        val basketAdapter = BasketAdapter(basket, { position -> onListItemClick(position) })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = basketAdapter
        // Si le panier est vide, on renvoie vers la page d'accueil
        /**
         * Appel de la fonction [getBasketQty]
         */
        if (getBasketQty(this).toInt() == 0){
            /**
             * Bascule vers l'activité [MainActivity]
             */
            startActivity(Intent(this, MainActivity::class.java))
        }
        // Cacher la progressBar
        bindingBasketActivity.pgbPay.visibility = View.GONE
    }

    private fun deleteDishInBasket (basketItem: DataBasketItem){
        /**
         * Fonction qui supprime un plat du panier
         *
         * @param basketItem Plat à supprimer du panier.
         *
         * Appel de la fonction [loadBasketJSON]
         */
        var basketJSON: DataBasket = loadBasketJSON(this)
        basketJSON.data.remove(basketItem)
        /**
         * Appel de la fonction [savePrefsQty]
         */
        savePrefsQty(this, basket.getCountItemsInBasket()-basketItem.quantity)
        basket.data.remove(basketItem)
        /**
         * Appel de la fonction [saveBasketJSON]
         */
        saveBasketJSON(bindingBasketActivity.root, basketJSON, this)
    }
    override fun onDestroy() {
        /**
         * Surcharge du onDestroy pour ajouter un log dans le onDestroy.
         */
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("BasketActivity", "*****************  BasketActivity -> Destroyed  *******************")
    }
}