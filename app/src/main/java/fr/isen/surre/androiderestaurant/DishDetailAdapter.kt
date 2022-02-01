package fr.isen.surre.androiderestaurant
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class DishDetailAdapter(fm:FragmentManager, private val dishImages: MutableList<String>) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return dishImages.size;
    }

    override fun getItem(position: Int): Fragment = DishFragment(dishImages[position])
}
