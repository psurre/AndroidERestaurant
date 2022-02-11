package fr.isen.surre.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.surre.androiderestaurant.databinding.ActivityAboutBinding

/**
 * Classe utilisée pour l'activité "A propos".
 * Dans cette activité, on affiche une page avec des informations sur l'application,
 * notamment la réponse à la question "Que signifie : Pinguis, id est vita ?"
 *
 * @constructor Non implémenté.
 * @author Patrick Surre
 *
 */

class AboutActivity : AppCompatActivity() {
    // Binding
    private lateinit var bindingAboutActivity: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAboutActivity = ActivityAboutBinding.inflate(layoutInflater)
        val view = bindingAboutActivity.root
        setContentView(view)

        // Action pour le clic sur l'image en forme de croix
        bindingAboutActivity.imgCloseAbout.setOnClickListener {
            this.onBackPressed()
        }

    }


}