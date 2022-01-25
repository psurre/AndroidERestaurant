package fr.isen.surre.androiderestaurant

import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter (private val menuReadings: ArrayList<String>, private val onItemClicked: (String) -> Unit) : RecyclerView.Adapter<MenuAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_items, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set element from menu data [using position] to TextView
        holder.menuTextView.text = menuReadings[position]
        holder.itemView.setOnClickListener{
            onItemClicked(menuReadings[position])
        }
    }
    override fun getItemCount(): Int {
        return menuReadings.size
    }
    //Implementation of the ViewHolder Class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val menuTextView: TextView
        init {
            menuTextView = view.findViewById(R.id.txtMenuItem)
        }
    }
}