package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.surre.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.surre.androiderestaurant.databinding.ActivityMainBinding

public const val KEYDISHTXT = "key.dish.txt"

class DetailActivity : AppCompatActivity() {
    // Definition des variables
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var bindingDetAct : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DetailActivity", "***************** DetailActivity -> created ***************** ")
        bindingDetAct = ActivityDetailsBinding.inflate(layoutInflater)
        val view = bindingDetAct.root
        setContentView(view)

        linearLayoutManager = LinearLayoutManager(this)
        // recycleView.layoutManager = linearLayoutManager

        // Récupération de l'intent
        val message = intent.getStringExtra(KEYDETAILTXT)

        // Affectation du texte récupéré via l'intent dans le composant TxtDetail
        bindingDetAct.TxtDetail.setText(message.toString())

        // List des éléments du menu
        val recyclerView: RecyclerView = bindingDetAct.rcvMenu
        var menuVal: ArrayList<String> = arrayListOf()

        for (iter in 0..5 step 1){
            menuVal.add("Plat" + iter.toString())
        }
        val adapter = MenuAdapter(menuVal, { position -> onListItemClick(position) })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun onListItemClick(dish: String) {
        val changePage = Intent(this, DishActivity::class.java)
        changePage.putExtra(KEYDISHTXT, dish)
        startActivity(changePage)
    }
}