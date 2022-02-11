package fr.isen.surre.androiderestaurant

import android.os.Bundle
import android.util.Log
import fr.isen.surre.androiderestaurant.databinding.ActivityDishBinding
import fr.isen.surre.androiderestaurant.model.DishModel
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

/**
* Classe utilisée pour l'activité de commande d'un plat.
* Cette activité affiche les détails d'un plat.
* Fonctionnalités offertes à l'utilisateur :
*  - Ajouter le plat au panier
*  - Modifier la quantité
*
* Hérite de OptionsMenuActivity pour afficher le menu dans l'activité.
* @constructor Non implémenté.
* @author Patrick Surre
*
*/

class DishActivity : OptionsMenuActivity() {
    // Définition des variables
    private lateinit var bindingDishAct : ActivityDishBinding
    private var basket: DataBasket = DataBasket()
    var listImages: MutableList<String> = arrayListOf()
    var basketItemQty: Int = 0
    private lateinit var dish: DishModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDishAct = ActivityDishBinding.inflate(layoutInflater)
        val view = bindingDishAct.root
        setContentView(view)
        // Dish contient le DishModel
        dish = intent.getSerializableExtra (KEYDISHES) as DishModel
        /**
         * Appel de la fonction [initDishActivity]
         */
        initDishActivity(dish)

        // Action pour le clic sur le bouton +1
        bindingDishAct.imgbtnPlus.setOnClickListener {
            /**
             * Appel des fonctions [addQuantity] et [updatePrice]
             */
            addQuantity()
            updatePrice(dish.prices[0].price.toFloat(), bindingDishAct.etnQuantity.text.toString().toInt())
        }

        // Action pour le clic sur le bouton -1
        bindingDishAct.imgbtnMinus.setOnClickListener {
            /**
             * Appel des fonctions [subQuantity] et [updatePrice]
             */
            subQuantity()
            updatePrice(dish.prices[0].price.toFloat(), bindingDishAct.etnQuantity.text.toString().toInt())
        }

        // Action pour le clic sur le bouton d'ajout d'un plat au panier
        bindingDishAct.btnCart.setOnClickListener {
            val basketItem = DataBasketItem()
            basketItem.dish = dish
            basketItem.quantity = bindingDishAct.etnQuantity.text.toString().toInt()
            // Recherche si on a pas déjà un plat du même type
            for (item in basket.data){
                if (item.dish.id == basketItem.dish.id){
                    // On a trouvé un plat identique
                    val qtyOld = item.quantity
                    basketItem.quantity+=qtyOld
                    basket.data.remove(item)
                }
            }
            basket.data.add(basketItem)
            /**
             * Appel de la fonction [saveBasketJSON]
             */
            saveBasketJSON(view, basket, this)
            basketItemQty = basket.getCountItemsInBasket()
            /**
             * Appel de la fonction [savePrefsQty]
             */
            savePrefsQty(this, basketItemQty)
            // Mise à jour du menu
            invalidateOptionsMenu()
        }
    }

    override fun onResume() {
        super.onResume()
        /**
         * Appel de la fonction [initDishActivity]
         */
        initDishActivity(dish)
        // Mise à jour du menu
        invalidateOptionsMenu()
    }
    private fun initDishActivity(dish: DishModel){
        /**
         * Fonction pour initialiser la page
         *
         * @param dish Plat.
         */
        bindingDishAct.etnQuantity.setText("1")
        bindingDishAct.txtPrice.text = dish.prices[0].price ?: ""
        bindingDishAct.txtDishName.text = dish.name_fr ?: ""
        //Ingredients
        bindingDishAct.txtIngredients.text = dish.ingredients.joinToString (", "){ it.name_fr }
        // Création d'une liste avec les images
        for (image in dish.images) {
            if (image == "") {
                listImages.add("http://No_URL")
            }else{
                listImages.add(image)
            }
        }
        bindingDishAct.dishPicturesPager.adapter = DishDetailAdapter(this, listImages)
        /**
         * Appel de la fonction [loadBasketJSON]
         */
        basket = loadBasketJSON(this)
        /**
         * Appel de la fonction [basket.getCountItemsInBasket]
         */
        basketItemQty = basket.getCountItemsInBasket()
    }

    private fun addQuantity (){
        /**
         * Fonction pour augmenter la quantité d'un plat dans le panier
         */
        var previousQty: Int = bindingDishAct.etnQuantity.text.toString().toInt()
        if (previousQty < MAXQTY){
            previousQty++
        }
        bindingDishAct.etnQuantity.setText(previousQty.toString())
    }
    private fun subQuantity (){
        /**
         * Fonction pour réduire la quantité d'un plat dans le panier
         */
        var previousQty: Int = bindingDishAct.etnQuantity.text.toString().toInt()
        if (previousQty > MINQTY){
            previousQty--
        }
        bindingDishAct.etnQuantity.setText(previousQty.toString())
    }
    private fun updatePrice (basePrice: Float, quantity: Int){
        /**
         * Fonction de mise à jour du prix total
         *
         * @param basePrice Prix unitaire d'un plat
         * @param quantity Quantité de plats à ajouter
         */
        val newPrice: Float = quantity * basePrice
        bindingDishAct.txtPrice.text = newPrice.toString()
    }
    override fun onDestroy() {
        /**
         * Surcharge du onDestroy pour ajouter un log dans le onDestroy.
         */
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("DishActivity", "*****************  DishActivity -> Destroyed  *******************")
    }
}