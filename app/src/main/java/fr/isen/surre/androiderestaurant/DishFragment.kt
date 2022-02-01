package fr.isen.surre.androiderestaurant
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import fr.isen.surre.androiderestaurant.databinding.FragmentDishBinding


class DishFragment (val img: String) : Fragment() {
    private lateinit var bindingDishFragment : FragmentDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDishFragment = FragmentDishBinding.inflate(layoutInflater)
        bindingDishFragment.imgDish1.setImageURI(img.toUri())
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View = inflater.inflate(R.layout.fragment_dish, container, false)
}