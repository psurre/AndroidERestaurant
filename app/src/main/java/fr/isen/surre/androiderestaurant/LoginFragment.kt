package fr.isen.surre.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.FragmentLoginBinding
import fr.isen.surre.androiderestaurant.model.DataRegister
import org.json.JSONObject

/**
 * Fragment de gestion du login pour l'activité AccountActivity
 *
 * @author Patrick Surre
 */

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
        // Action OnClick pour s'enregistrer
        bindingLoginFragment.txtRegister.setOnClickListener {
            /**
             * Appel de la fonction [gotoRegister] pour afficher le fragment d'enregistrement d'un compte
             */
            (activity as AccountActivity?)?.gotoRegister()
        }
        // Action OnClick pour se connecter
        bindingLoginFragment.btnConn.setOnClickListener {
            /**
             * Appel de la fonction [connectUser]
             */
            connectUser(view)
        }
        // Action OnClick sur bouton Close
        bindingLoginFragment.imgCloseLogin.setOnClickListener {
            // Retour à la page du compte
            (activity as AccountActivity).onBackPressed()
        }
    }

    private fun connectUser (view: View){
        /**
         * Fonction permettant de connecter un utilisateur
         *
         * @param view Vue pour l'affichage des snackbar
         */
        var userData: DataRegister = DataRegister()
        val urlRegister: String = URLLOGIN
        var jsonReqObject = JSONObject()
        jsonReqObject.put("id_shop", "1")
        jsonReqObject.put("email", bindingLoginFragment.edtLogin.text.toString())
        jsonReqObject.put("password", bindingLoginFragment.edtMdp.text.toString())
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlRegister, jsonReqObject,
            { response ->
                userData = Gson().fromJson(response.toString(), DataRegister::class.java)
                if (userData.code == "200"){
                    /**
                     * Appel de la fonction [showSnackbar]
                     */
                    showSnackbar("Connecté avec l'id : "+userData.data.id+" !", view)
                    /**
                     * Appel de la fonction [savePrefsIdUser]
                     */
                    context?.let { savePrefsIdUser (it, userData.data.id) }
                    // Retour vers la page ayant appelé la page de login
                    (activity as AccountActivity).onBackPressed()
                }else{
                    showSnackbar("Erreur de connexion: " + userData.code, view)
                }
            },{ error ->
                // Gérer l'erreur
                /**
                 * Appel de la fonction [showSnackbar]
                 */
                showSnackbar("Serveur indisponible : réessayez plus tard", view)
            }
        )
        VolleySingleton.getInstance((activity as AccountActivity).applicationContext)
            .addToRequestQueue(stringRequest)
    }
}