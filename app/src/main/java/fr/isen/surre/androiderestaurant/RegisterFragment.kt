package fr.isen.surre.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import fr.isen.surre.androiderestaurant.databinding.FragmentRegisterBinding
import fr.isen.surre.androiderestaurant.model.DataRegister
import org.json.JSONObject

/**
 * Fragment de gestion de l'enregistrement d'un utilisateur pour l'activité AccountActivity
 *
 * @author Patrick Surre
 */

class RegisterFragment : Fragment() {
    // Binding
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
        /**
         * Appel de la fonction [initReg]
         */
        initReg()
        bindingRegisterFragment.btnRegister.setOnClickListener {
            /**
             * Appel de la fonction [formValidation]
             */
            if (formValidation(view)) {
                /**
                 * Appel de la fonction [requestRegister]
                 */
                // Enregistrement via webservices
                requestRegister(view)
            }
        }
        // Clic sur l'icone en forme de croix
        bindingRegisterFragment.imgCloseRegister.setOnClickListener {
            (activity as AccountActivity).onBackPressed()
        }
    }

    override fun onResume() {
        /**
         * Appel de la fonction [initReg]
         */
        initReg()
        super.onResume()
    }

    private fun initReg (){
        /**
         * Fonction d'initialisation des champs du formulaire
         */
        bindingRegisterFragment.edtRegName.setText("")
        bindingRegisterFragment.edtRegSurname.setText("")
        bindingRegisterFragment.edtRegEmail.setText("")
        bindingRegisterFragment.edtRegAdress.setText("")
        bindingRegisterFragment.edtRegPass.setText("")
    }
    private fun formValidation(view: View): Boolean {
        /**
         * Fonction de validation des champs du formulaire.
         *
         * @param view Accès à la vue pour affichage des snackbar
         * @return Retourne vrai si tous les champs du formulaire sont valides
         */
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
        /**
         * Fonction de validation d'un champ.
         * Vérification de la taille et du contenu.
         * Vérification complémentaire du formatage pour le champ Email.
         *
         * @param txtEditText Champ du formulaire à tester
         * @param txtInputLayout Récupération du label du champ.
         * @return Retourne un message d'erreur ou la valeur de la constante TRUERETURN
         */
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
        /**
         * Fonction de soumission d'une demande d'enregistrement d'un nouvel utilisateur.
         *
         * @param view Accès à la vue pour affichage des snackbar.
         */
        var idReg: DataRegister = DataRegister()
        val urlRegister: String = URLREGISTER
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
                // Test de tous les codes possibles renvoyés par le webservice
                if (idReg.code == "200"){
                    showSnackbar("Compte créé avec l'id : "+idReg.data.id+" !", view)
                    /**
                     * Appel de la fonction [savePrefsIdUser]
                     */
                    context?.let { savePrefsIdUser(it, idReg.data.id) }
                    Thread.sleep(1000L)
                    /**
                     * Vers [AccountActivity]
                     */
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