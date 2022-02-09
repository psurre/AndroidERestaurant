package fr.isen.surre.androiderestaurant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.surre.androiderestaurant.databinding.ActivityAccountBinding

class AccountActivity : OptionsMenuActivity() {

    private lateinit var bindingAccountActivity: ActivityAccountBinding
    val loginFragment = LoginFragment()
    val registerFragment = RegisterFragment()
    val fragmentManager : FragmentManager = supportFragmentManager
    val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAccountActivity = ActivityAccountBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_account)
        fragmentTransaction.replace(R.id.frameLogin, loginFragment).commit()
    }

    fun gotoRegister (){
        val fragmentRegisterTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentRegisterTransaction.replace(R.id.frameLogin, registerFragment).commit()
    }
    fun gotoLogin (){
        val fragmentRegisterTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentRegisterTransaction.replace(R.id.frameLogin, loginFragment).commit()
    }

    override fun onDestroy() {
        // Fonction d'ajout de logs dans le onDestroy
        super.onDestroy()
        invalidateOptionsMenu()
        Log.i("AccountActivity", "*****************  MainActivity -> Destroyed  *******************")
    }
}