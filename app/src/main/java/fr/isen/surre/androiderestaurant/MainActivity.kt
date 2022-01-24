package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import org.w3c.dom.Text

// Constantes
public const val KEYDETAILTXT = "key.detail.txt"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun gotoEntry (view: View){
        val txtClic = findViewById(R.id.BtnEntry) as Button
        val msgTxt = txtClic.text.toString()
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEYDETAILTXT, msgTxt)
        }
        startActivity(intent)
    }
    fun gotoMain (view: View){
        val txtClic = findViewById(R.id.BtnMain) as Button
        val msgTxt = txtClic.text.toString()
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEYDETAILTXT, msgTxt)
        }
        startActivity(intent)
    }
    fun gotoDesserts (view: View){
        val txtClic = findViewById(R.id.BtnDesserts) as Button
        val msgTxt = txtClic.text.toString()
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEYDETAILTXT, msgTxt)
        }
        startActivity(intent)
    }
}
