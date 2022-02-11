package fr.isen.surre.androiderestaurant

import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.model.DishModel

/**
 * Classe de type Adapter pour la gestion du recyclerView de l'activité d'affichage des plats d'une catégorie
 *
 * @param dishes List de plats.
 * @param onItemClicked Plat sur lequel l'utilisateur a cliqué.
 * @return Renvoie un RecyclerViewAdapter<CategoryAdapter.ViewHolder>().
 *
 * @author Patrick Surre
 */

class CategoryAdapter (private val dishes: ArrayList<DishModel>, private val onItemClicked: (DishModel) -> Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

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
            .inflate(R.layout.layout_cat_dishes, parent, false)
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
        for (dish in dishes){
            if (dish.id == dishes[position].id){
                // Nom du plat
                holder.dishTextView.text = dish.name_fr
                loopImg@for (image in dish.images){
                    if (Patterns.WEB_URL.matcher(image).matches()) {
                        // Image du plat
                        picasso.load(image).into(holder.dishImgView)
                        break@loopImg
                    }
                }
                loopPrice@for (price in dish.prices){
                    // Prix du plat
                    holder.dishTextViewPrice.text = price.price + "€"
                }
            }
        }
        //set element from menu data [using position] to TextView
        holder.itemView.setOnClickListener{
            onItemClicked(dishes[position])
        }
    }
    override fun getItemCount(): Int {
        /**
         * Surcharge de la fonction getItemCount.
         *
         * @return Nombre de plats.
         *
         */
        return dishes.size
    }

    //Implementation of the ViewHolder Class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        /**
         * Classe de gestion de l'affichage de la vue.
         *
         * @param view La vue en cours.
         * @return RecyclerView.ViewHolder(view).
         *
         */
        var dishTextView: TextView
        var dishImgView: ImageView
        var dishTextViewPrice: TextView
        init {
            dishTextView = view.findViewById(R.id.txtDish)
            dishImgView = view.findViewById(R.id.imgDish)
            dishTextViewPrice = view.findViewById(R.id.txtListPrice)
        }
    }

}