package fr.isen.surre.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val BtnEntry = findViewById(R.id.BtnEntry) as Button
        // set on-click listener
        BtnEntry.setOnClickListener {
            Toast.makeText(this@MainActivity, "Entrées", Toast.LENGTH_SHORT).show()
        }
        val BtnMain = findViewById(R.id.BtnMain) as Button
        // set on-click listener
        BtnMain.setOnClickListener {
            Toast.makeText(this@MainActivity, "Plats", Toast.LENGTH_SHORT).show()
        }
        val BtnDesserts = findViewById(R.id.BtnDesserts) as Button
        // set on-click listener
        BtnDesserts.setOnClickListener {
            Toast.makeText(this@MainActivity, "Desserts", Toast.LENGTH_SHORT).show()
        }
    }
}