package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.surre.androiderestaurant.databinding.ActivityMainBinding

// Constantes
public const val KEYDETAILTXT = "key.detail.txt"

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainAct : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "*****************  MainActivity -> created  *********************")
        bindingMainAct = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMainAct.root
        setContentView(view)
        bindingMainAct.txtStarter.setOnClickListener {
            dishesDetails(bindingMainAct.txtStarter.text.toString())
        }
        bindingMainAct.txtMainCourse.setOnClickListener {
            dishesDetails(bindingMainAct.txtMainCourse.text.toString())
        }
        bindingMainAct.txtDessert.setOnClickListener {
            dishesDetails(bindingMainAct.txtDessert.text.toString())
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "*****************  MainActivity -> Destroyed  *******************")
    }

    private fun dishesDetails( dishType : String) {
        val changePage = Intent(this, DetailActivity::class.java)
        changePage.putExtra(KEYDETAILTXT, dishType)
        startActivity(changePage)
    }
}
