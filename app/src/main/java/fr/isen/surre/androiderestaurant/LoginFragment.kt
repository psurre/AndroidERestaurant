package fr.isen.surre.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.surre.androiderestaurant.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var bindingLoginFragment: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingLoginFragment = FragmentLoginBinding.inflate(inflater,container, false )
        return bindingLoginFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Interactions avec la vue
        super.onViewCreated(view, savedInstanceState)
        bindingLoginFragment.txtRegister.setOnClickListener {
            (activity as AccountActivity?)?.gotoRegister()
        }
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