package fr.isen.surre.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Classe de type Adapter pour la gestion du menu
 *
 * @param menuReadings Panier de l'utilisateur.
 * @param onItemClicked Plat sur lequel l'utilisateur a cliqué.
 * @return Renvoie un RecyclerViewAdapter<MenuAdapter.ViewHolder>().
 *
 * @author Patrick Surre
 */

class MenuAdapter (private val menuReadings: ArrayList<String>, private val onItemClicked: (String) -> Unit) : RecyclerView.Adapter<MenuAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_items, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set element from menu data [using position] to TextView
        holder.menuTxtBasketInfo.text = menuReadings[position]
        holder.itemView.setOnClickListener{
            onItemClicked(menuReadings[position])
        }
    }
    override fun getItemCount(): Int {
        return menuReadings.size
    }
    //Implementation of the ViewHolder Class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val menuTxtBasketInfo: TextView
        val imgMenuCart: ImageView
        init {
            menuTxtBasketInfo = view.findViewById(R.id.txtMenuBasket)
            imgMenuCart = view.findViewById(R.id.imgMenuCart)
        }
    }
}