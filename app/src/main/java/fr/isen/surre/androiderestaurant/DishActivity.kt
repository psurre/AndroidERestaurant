package fr.isen.surre.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.isen.surre.androiderestaurant.databinding.ActivityDishBinding
import fr.isen.surre.androiderestaurant.model.DishModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem
import java.io.File

// Déclaration des Constantes
const val MAXQTY = 50 // Nombre d'articles maximum dans le panier
const val MINQTY = 1 // Nombre minimum à afficher dans la page de commande d'un plat
const val BASKET = "basket.activity"

class DishActivity : AppCompatActivity() {
    private lateinit var bindingDishAct : ActivityDishBinding
    private var basket: DataBasket = DataBasket()
    var listImages: MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDishAct = ActivityDishBinding.inflate(layoutInflater)
        val view = bindingDishAct.root
        setContentView(view)
        // Dish contient le DishModel
        val dish = intent.getSerializableExtra (KEYDISHES) as DishModel
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
            basket = loadBasketJSON(this)
            basket.data.add(basketItem)
            saveBasketJSON(view, basket, this)
            basketInfo(saveUserPrefs (this, bindingDishAct.etnQuantity.text.toString().toInt()))
        }
        bindingDishAct.imgCart.setOnClickListener {
            // Ouvrir la page du panier
            val changePage = Intent(this, BasketActivity::class.java)
            var basketJSON = loadBasketJSON(this)
            changePage.putExtra(BASKET, basketJSON)
            startActivity(changePage)
        }
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
            listImages.add(image)
        }
        if (listImages.isEmpty()){
            listImages.add("")
        }
        // TODO A modifier pour utiliser le nouveau caroussel
        bindingDishAct.viewPager.adapter = DishDetailAdapter(supportFragmentManager, listImages)
        //bindingDishAct.viewPager.adapter = DishPictureAdapter(activity as DishActivity(), listImages)
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
    private fun basketInfo (basketItems: Int){
        // Récupération des infos sur le nombre d'articles dans le panier
        if (basketItems > 0){
            bindingDishAct.btnInfoBasket.visibility = View.VISIBLE
            bindingDishAct.btnInfoBasket.text = basketItems.toString()
            if (basketItems > 10){
                bindingDishAct.btnInfoBasket.textSize = "7".toFloat()
            }else {
                bindingDishAct.btnInfoBasket.textSize = "12".toFloat()
            }
        }else{
            bindingDishAct.btnInfoBasket.visibility = View.GONE
            bindingDishAct.btnInfoBasket.text = "0"
            bindingDishAct.btnInfoBasket.textSize = "12".toFloat()
        }
    }
}