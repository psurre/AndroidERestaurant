package fr.isen.surre.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DishPictureAdapter(activity: AppCompatActivity, private val pictures: List<String>): FragmentStateAdapter (activity){
    override fun getItemCount(): Int = pictures.size


    override fun createFragment(position: Int): Fragment {
        return DishPictureFragment.newInstance(pictures[position])
    }
}