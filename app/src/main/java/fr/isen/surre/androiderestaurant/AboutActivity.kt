package fr.isen.surre.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.surre.androiderestaurant.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var bindingAboutActivity: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAboutActivity = ActivityAboutBinding.inflate(layoutInflater)
        val view = bindingAboutActivity.root
        setContentView(view)

        bindingAboutActivity.imgCloseAbout.setOnClickListener {
            this.onBackPressed()
        }

    }


}