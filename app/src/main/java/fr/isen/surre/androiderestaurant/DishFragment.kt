package fr.isen.surre.androiderestaurant
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.databinding.FragmentDishBinding

/**
 * Fragment de gestion du carroussel d'images pour l'activité DishActivity
 *
 * @author Patrick Surre
 */

class DishFragment: Fragment() {
    // Binding
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
                .placeholder(R.drawable.android_default)
                .into(bindingDishFragment.imgDishFrag)
        } ?: R.drawable.android_default.toString()
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