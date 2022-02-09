package fr.isen.surre.androiderestaurant
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.databinding.FragmentDishBinding


class DishFragment: Fragment() {
    private lateinit var bindingDishFragment : FragmentDishBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
        bindingDishFragment = FragmentDishBinding.inflate(inflater, container, false)
        return bindingDishFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Afficher la photo
        arguments?.getString("picture_url")?.let{ pictureURL ->
            Picasso.get()
                .load(pictureURL)
                .placeholder(R.drawable.android_cooking)
                .into(bindingDishFragment.imgDishFrag)
        } ?: R.drawable.android_cooking.toString()
    }

    companion object{
        // companion object = Permet de référencer une fonction statique (sans instancier la classe à laquelle elle appartient)
        @JvmStatic
        fun newInstance(pictureURL: String) =
            DishFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureURL)
                }
            }
    }
}