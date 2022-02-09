package fr.isen.surre.androiderestaurant

import android.os.Bundle
import android.util.Log
import fr.isen.surre.androiderestaurant.databinding.ActivityDishBinding
import fr.isen.surre.androiderestaurant.model.DishModel
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

// Déclaration des Constantes
const val MAXQTY = 50 // Nombre d'articles maximum dans le panier
const val MINQTY = 1 // Nombre minimum à afficher dans la page de commande d'un plat
const val BASKET = "basket.activity"

class DishActivity : OptionsMenuActivity() {
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
        initDishActivity(dish)

        bindingDishAct.imgbtnPlus.setOnClickListener {
            addQuantity()
            updatePrice(dish.prices[0].price.toFloat(), bindingDishAct.etnQuantity.text.toString().toInt())
        }

        bindingDishAct.imgbtnMinus.setOnClickListener {
            subQuantity()
            updatePrice(dish.prices[0].price.toFloat(), bindingDishAct.etnQuantity.text.toString().toInt())
        }

        bindingDishAct.btnCart.setOnClickListener {
            var basketItem: DataBasketItem = DataBasketItem()
            basketItem.dish = dish
            basketItem.quantity = bindingDishAct.etnQuantity.text.toString().toInt()
            // Recherche si on a pas déjà un plat du même type
            for (item in basket.data){
                if (item.dish.id == basketItem.dish.id){
                    // On a trouvé un plat identique
                    var qtyOld = item.quantity.toInt()
                    basketItem.quantity+=qtyOld
                    basket.data.remove(item)
                }
            }
            basket.data.add(basketItem)
            saveBasketJSON(view, basket, this)
            basketItemQty = basket.getCountItemsInBasket()
            savePrefsQty(this, basketItemQty)
            invalidateOptionsMenu()
        }
    }

    override fun onResume() {
        super.onResume()
        initDishActivity(dish)
        invalidateOptionsMenu()
    }
    private fun initDishActivity(dish: DishModel){
        bindingDishAct.etnQuantity.setText("1")
        // On a trouvé notre plat
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
        basket = loadBasketJSON(this)
        basketItemQty = basket.getCountItemsInBasket()
    }

    private fun addQuantity (){
        // Augmenter la quantité
        var previousQty: Int = bindingDishAct.etnQuantity.text.toString().toInt()
        if (previousQty < MAXQTY){
            previousQty++
        }
        bindingDishAct.etnQuantity.setText(previousQty.toString())
    }
    private fun subQuantity (){
        // Soustraire la quantité
        var previousQty: Int = bindingDishAct.etnQuantity.text.toString().toInt()
        if (previousQty > MINQTY){
            previousQty--
        }
        bindingDishAct.etnQuantity.setText(previousQty.toString())
    }
    private fun updatePrice (basePrice: Float, quantity: Int){
        var newPrice: Float
        newPrice = quantity * basePrice
        bindingDishAct.txtPrice.setText(newPrice.toString())
    }
    override fun onDestroy() {
        // Fonction d'ajout de logs dans le onDestroy
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("DishActivity", "*****************  MainActivity -> Destroyed  *******************")
    }
}