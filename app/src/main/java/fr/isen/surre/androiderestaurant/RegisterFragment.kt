package fr.isen.surre.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.surre.androiderestaurant.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var bindingRegisterFragment: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingRegisterFragment = FragmentRegisterBinding.inflate(inflater,container, false)
        return bindingRegisterFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Interactions avec la vue
        super.onViewCreated(view, savedInstanceState)
    }

    companion object{
        // companion object = Permet de référencer une fonction statique (sans instancier la classe à laquelle elle appartient)
        // TODO A corriger, pris en exemple
        @JvmStatic
        fun newInstance(pictureURL: String) =
            DishPictureFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureURL)
                }
            }
    }
}