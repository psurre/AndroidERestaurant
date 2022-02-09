package fr.isen.surre.androiderestaurant
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DishDetailAdapter (activity: AppCompatActivity, private val dishImages: List<String>) : FragmentStateAdapter (activity) {
    override fun getItemCount(): Int {
        return dishImages.size;
    }
    override fun createFragment(position: Int): Fragment = DishFragment.newInstance (dishImages[position])
}
