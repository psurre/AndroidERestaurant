package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Récupération de l'intent
        val message = intent.getStringExtra(KEYDETAILTXT)

        // Affectation du texte récupéré via l'intent dans le composant TxtDetail
        val textView = findViewById<TextView>(R.id.TxtDetail).apply {
            text = message
        }
    }
}