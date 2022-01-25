package fr.isen.surre.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.surre.androiderestaurant.databinding.ActivityDishBinding


class DishActivity : AppCompatActivity() {
    private lateinit var bindingDishAct : ActivityDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDishAct = ActivityDishBinding.inflate(layoutInflater)
        val view = bindingDishAct.root
        setContentView(view)

        val dish = intent.getStringExtra(KEYDISHTXT)
        bindingDishAct.txtDishName.setText(dish)

    }
}