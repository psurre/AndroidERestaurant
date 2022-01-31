package fr.isen.surre.androiderestaurant

import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.model.DataModel

class CatCardHolder (private val menuReadings: ArrayList<String>, private val onItemClicked: (String) -> Unit, private val dishes: DataModel) : RecyclerView.Adapter<CatCardHolder.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatCardHolder.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_cat_dishes, parent, false)
        return CatCardHolder.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picasso = Picasso.get()
        for (data in dishes.data){
            for (dish in data.items){
                if (dish.id == menuReadings[position]) {
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
        }
        //set element from menu data [using position] to TextView
        holder.itemView.setOnClickListener{
            onItemClicked(menuReadings[position])
        }
    }
    override fun getItemCount(): Int {
        return menuReadings.size
    }

    //Implementation of the ViewHolder Class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
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