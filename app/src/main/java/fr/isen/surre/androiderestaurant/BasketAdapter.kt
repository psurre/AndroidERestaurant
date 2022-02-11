package fr.isen.surre.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.model.DataBasket
import fr.isen.surre.androiderestaurant.model.DataBasketItem

/**
 * Classe de type Adapter pour la gestion du recyclerView de l'activité de gestion du panier
 *
 * @param basket Panier de l'utilisateur.
 * @param onItemClicked Plat sur lequel l'utilisateur a cliqué.
 * @return Renvoie un RecyclerViewAdapter<BasketAdapter.ViewHolder>().
 *
 * @author Patrick Surre
 */


class BasketAdapter(private val basket: DataBasket, private val onItemClicked: (DataBasketItem) -> Unit) : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        /**
         * Classe de gestion de l'affichage de la vue.
         *
         * @param view La vue en cours.
         * @return RecyclerView.ViewHolder(view).
         *
         */
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
        /**
         * Surcharge de la fonction onCreateViewHolder (création de la vue).
         *
         * @param parent Récupération du context du parent de la vue.
         * @param viewType Variable non surchargée.
         * @return ViewHolder.
         *
         */
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_basket, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /**
         * Surcharge de la fonction onBindViewHolder pour lier les données à la vue.
         *
         * @param holder Manager des éléments de la vue liés aux données.
         * @param position Position du clic dans le recyclerView.
         * @return ViewHolder.
         *
         */
        // Picasso pour l'affichage des images
        val picasso = Picasso.get()
        var floatTotal: Float = 0F

        for (basketItem in basket.data){
                if (basketItem.dish.id == basket.data[position].dish.id){
                    holder.dishName.text = basketItem.dish.name_fr
                    holder.dishQty.text = basketItem.quantity.toString()
                    floatTotal = basketItem.quantity.toString().toFloat() * basketItem.dish.prices[0].price.toString().toFloat()
                    holder.dishTotal.text = floatTotal.toString()
                    val firstImage = basketItem.dish.getFirstPicture(basketItem.dish)
                    // Cas où l'URL de l'image est vide.
                    if (firstImage != ""){
                        picasso.load(basketItem.dish.images[0]).into(holder.dishImage)
                    }
                }
        }
        // Gestion du clic sur le bouton de suppression d'un plat
        holder.dishTrash.setOnClickListener {
            onItemClicked(basket.data[position])
        }
    }

    override fun getItemCount(): Int = basket.data.size
    /**
     * Surcharge de la fonction getItemCount.
     *
     * @return Nombre de plats dans le panier.
     *
     */

}