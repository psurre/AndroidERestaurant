package fr.isen.surre.androiderestaurant

import android.accounts.Account
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.FragmentRegisterBinding
import fr.isen.surre.androiderestaurant.model.DataRegister
import org.json.JSONObject


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
        bindingRegisterFragment.btnRegister.setOnClickListener {
            if (formValidation(view)) {
                // Enregistrement via webservices
                requestRegister(view)
            }
        }
        bindingRegisterFragment.imgCloseRegister.setOnClickListener {
            (activity as AccountActivity).onBackPressed()
        }
    }

    private fun formValidation(view: View): Boolean {
        // Fonction de validation du formulaire
        // Variables
        var resultFormVal: Boolean = true
        // Vérification que les champs soient remplis
        if (bindingRegisterFragment.edtRegName.text.isNullOrEmpty()){
            showSnackbar(view,"Champ Nom vide ! Veuillez le compléter.")
            resultFormVal = false
        }
        if (bindingRegisterFragment.edtRegSurname.text.isNullOrEmpty()) {
            showSnackbar(view,"Champ Prénom vide ! Veuillez le compléter.")
            resultFormVal = false
        }
        if (bindingRegisterFragment.edtRegEmail.text.isNullOrEmpty()){
            showSnackbar(view,"Champ Email vide ! Veuillez le compléter.")
            resultFormVal = false
        }
        if (bindingRegisterFragment.edtRegAdress.text.isNullOrEmpty()){
            showSnackbar(view,"Champ Adresse vide ! Veuillez le compléter.")
            resultFormVal = false
        }
        if (bindingRegisterFragment.edtRegPass.text.isNullOrEmpty()){
            showSnackbar(view,"Champ Mot de passe vide ! Veuillez le compléter.")
            resultFormVal = false
        }
        return resultFormVal
    }

    private fun showSnackbar(view: View, message: String){
        val snackbar = Snackbar
            .make(view, message, Snackbar.LENGTH_LONG)
        // Show
        snackbar.show()
    }

    private fun requestRegister (view: View){
        var idReg: DataRegister = DataRegister()
        val urlRegister: String = "http://test.api.catering.bluecodegames.com/user/register"
        var jsonReqObject = JSONObject()
        jsonReqObject.put("id_shop", "1")
        jsonReqObject.put("firstname", bindingRegisterFragment.edtRegSurname.text.toString())
        jsonReqObject.put("lastname", bindingRegisterFragment.edtRegName.text.toString())
        jsonReqObject.put("address", bindingRegisterFragment.edtRegAdress.text.toString())
        jsonReqObject.put("email", bindingRegisterFragment.edtRegEmail.text.toString())
        jsonReqObject.put("password", bindingRegisterFragment.edtRegPass.text.toString())
        val stringRequest = JsonObjectRequest(
            Request.Method.POST, urlRegister, jsonReqObject,
            { response ->
                idReg = Gson().fromJson(response.toString(), DataRegister::class.java)
                if (idReg.code.toString() == "200"){
                    showSnackbar(view, "Compte créé avec l'id : "+idReg.data.id+" !")
                    context?.let { savePrefsIdUser(it, idReg.data.id) }
                    // Redirection
                    (activity as AccountActivity).gotoLogin()
                }else if (idReg.code.toString() == "111"){
                    showSnackbar(view, "Erreur : le compte existe déjà")
                }else if (idReg.code.toString() == "113"){
                    showSnackbar(view, "Erreur : le mot de passe est trop court !")
                }else if (idReg.code.toString() == "112") {
                    showSnackbar(view, "Erreur : l'email est invalide !")
                }
            },{ error ->
                // Gérer l'erreur
                showSnackbar(view, "Serveur indisponible : réessayez plus tard")
            }
        )
        VolleySingleton.getInstance((activity as AccountActivity).applicationContext)
            .addToRequestQueue(stringRequest)
    }

}