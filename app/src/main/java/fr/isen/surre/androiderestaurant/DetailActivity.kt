package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

class DetailActivity : AppCompatActivity() {
    // Definition des variables
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("DetailActivity", "***************** DetailActivity -> created ***************** ")
        setContentView(R.layout.activity_details)
        linearLayoutManager = LinearLayoutManager(this)
        // recycleView.layoutManager = linearLayoutManager

        // Récupération de l'intent
        val message = intent.getStringExtra(KEYDETAILTXT)

        // Affectation du texte récupéré via l'intent dans le composant TxtDetail
        val textView = findViewById<TextView>(R.id.TxtDetail).apply {
            text = message
        }

    }
}