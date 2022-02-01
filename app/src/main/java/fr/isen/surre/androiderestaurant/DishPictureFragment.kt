package fr.isen.surre.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.surre.androiderestaurant.databinding.FragmentDishPictureBinding


class DishPictureFragment : Fragment() {
    private lateinit var binding: FragmentDishPictureBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Faire le binding
        binding = FragmentDishPictureBinding.inflate(inflater,container, false )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Interactions avec la vue
        super.onViewCreated(view, savedInstanceState)
        // Afficher la photo
        arguments?.getString("picture_url")?.let{ pictureURL ->
            Picasso.get()
                .load(pictureURL)
                .placeholder(R.drawable.android_cooking)
                .into(binding.imgDetailDishPicture)
        }
    }

    companion object{
        // companion object = Permet de référencer une fonction statique (sans instancier la classe à laquelle elle appartient)
        @JvmStatic
        fun newInstance(pictureURL: String) =
            DishPictureFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureURL)
                }
            }
    }
}