package fr.isen.surre.androiderestaurant

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

class BasketAdapter(private val basket: DataBasket, private val onItemClicked: (DataBasketItem) -> Unit) : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var dishName: TextView
        var dishQty: TextView
        var dishTotal: TextView
        var dishImage: ImageView
        var dishTrash: ImageView
        init {
            dishName = view.findViewById(R.id.dishName)
            dishQty = view.findViewById(R.id.qtyDish)
            dishTotal = view.findViewById(R.id.totalPrice)
            dishImage = view.findViewById(R.id.dishImage)
            dishTrash = view.findViewById(R.id.imgTrash)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_basket, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picasso = Picasso.get()
        var floatTotal: Float = 0F

        for (basketItem in basket.data){
                if (basketItem.dish.id == basket.data[position].dish.id){
                    holder.dishName.text = basketItem.dish.name_fr
                    holder.dishQty.text = basketItem.quantity.toString()
                    floatTotal = basketItem.quantity.toString().toFloat() * basketItem.dish.prices[0].price.toString().toFloat()
                    holder.dishTotal.text = floatTotal.toString()
                    val firstImage = basketItem.dish.getFirstPicture(basketItem.dish)
                    if (firstImage != ""){
                        picasso.load(basketItem.dish.images[0]).into(holder.dishImage)
                    }
                }
        }
        holder.dishTrash.setOnClickListener {
            onItemClicked(basket.data[position])
        }
    }

    override fun getItemCount(): Int = basket.data.size

}