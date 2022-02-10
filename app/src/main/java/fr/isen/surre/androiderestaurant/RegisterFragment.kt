package fr.isen.surre.androiderestaurant

import android.accounts.Account
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.FragmentRegisterBinding
import fr.isen.surre.androiderestaurant.model.DataRegister
import org.json.JSONObject

const val MINSIZE = 4
const val MINMDPSIZE = 8
const val TRUERETURN = "NOERROR"

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
        initReg()
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

    override fun onResume() {
        initReg()
        super.onResume()
    }

    private fun initReg (){
        bindingRegisterFragment.edtRegName.setText("")
        bindingRegisterFragment.edtRegSurname.setText("")
        bindingRegisterFragment.edtRegEmail.setText("")
        bindingRegisterFragment.edtRegAdress.setText("")
        bindingRegisterFragment.edtRegPass.setText("")
    }
    private fun formValidation(view: View): Boolean {
        // Fonction de validation du formulaire
        // Champ Nom
        var message = formFieldsValidation(bindingRegisterFragment.edtRegName, bindingRegisterFragment.otxtRegName)
        if (message != TRUERETURN){
            showSnackbar(message,view)
            return false
        }
        // Champ Prénom
        message = formFieldsValidation(bindingRegisterFragment.edtRegSurname, bindingRegisterFragment.otxtRegSurname)
        if (message != TRUERETURN){
            showSnackbar(message,view)
            return false
        }
        // Champ Email
        message = formFieldsValidation(bindingRegisterFragment.edtRegEmail, bindingRegisterFragment.otxtRegEmail)
        if (message != TRUERETURN){
            showSnackbar(message,view)
            return false
        }
        // Champ Address
        message = formFieldsValidation(bindingRegisterFragment.edtRegAdress, bindingRegisterFragment.otxtRegAdress)
        if (message != TRUERETURN){
            showSnackbar(message,view)
            return false
        }
        // Champ password
        message = formFieldsValidation(bindingRegisterFragment.edtRegPass, bindingRegisterFragment.otxtRegPass)
        if (message != TRUERETURN){
            showSnackbar(message,view)
            return false
        }
        return true
    }

    private fun formFieldsValidation (txtEditText: TextInputEditText, txtInputLayout: TextInputLayout): String{
        // Champ vide
        if (txtEditText.text?.isEmpty() == true){
            return "Champ "+ txtInputLayout.hint + " vide ! Veuillez le compléter."
            // Taille du texte saisi <= MINSIZE
        }else if(txtEditText.text?.length!! <= MINSIZE){
                return "Champ "+ txtInputLayout.hint + " trop court (<= "+MINSIZE.toString()+")."
            }else if (txtInputLayout.hint == getString(R.string.project_email)){
                        // Formatage de l'email
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtEditText.text.toString()).matches()){
                            return "Champ "+ txtInputLayout.hint + " invalide."
                        }
                }else if (txtInputLayout.hint == getString(R.string.project_mdp)){
                            // Taille spécifique pour le mot de passe
                            if (txtEditText.text?.length!! <= MINMDPSIZE){
                                return "Champ "+ txtInputLayout.hint + " trop court (<= "+ MINMDPSIZE.toString()+")."
                            }
                }
        return TRUERETURN
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
                if (idReg.code == "200"){
                    showSnackbar("Compte créé avec l'id : "+idReg.data.id+" !", view)
                    context?.let { savePrefsIdUser(it, idReg.data.id) }
                    Thread.sleep(1000L)
                    // Redirection
                    (activity as AccountActivity).onBackPressed()
                }else if (idReg.code.toString() == "111"){
                    showSnackbar("Erreur : le compte existe déjà", view)
                }else if (idReg.code.toString() == "113"){
                    showSnackbar("Erreur : le mot de passe est trop court !", view)
                }else if (idReg.code.toString() == "112") {
                    showSnackbar("Erreur : l'email est invalide !", view)
                }
            },{ error ->
                // Gérer l'erreur
                showSnackbar("Serveur indisponible : réessayez plus tard", view)
                Log.i("********** VolleyError", "Erreur -> " + error)
                val errorDetails = onVolleyErrorResponse(error)
                if (errorDetails.isNotEmpty()) {
                    Log.i("********** VolleyError", "Status Code -> " + errorDetails[0])
                    if (errorDetails.size == 2) { Log.i("********** VolleyError", "Data -> " + errorDetails[1])}
                }
            }
        )
        VolleySingleton.getInstance((activity as AccountActivity).applicationContext)
            .addToRequestQueue(stringRequest)
    }

}