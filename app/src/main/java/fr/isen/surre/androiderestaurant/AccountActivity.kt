package fr.isen.surre.androiderestaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import fr.isen.surre.androiderestaurant.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

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
    fun savePrefsIdUser (idUser: String){
        val UserPrefs =  getSharedPreferences("ERESTOPARAMS", Context.MODE_PRIVATE)
        var editor = UserPrefs.edit()
        editor.putString("user_id", idUser)
        editor.commit()
    }

    fun gotoRegister (){
        val fragmentRegisterTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentRegisterTransaction.replace(R.id.frameLogin, registerFragment).commit()
    }
    fun gotoLogin (){
        val fragmentRegisterTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentRegisterTransaction.replace(R.id.frameLogin, loginFragment).commit()
    }
}