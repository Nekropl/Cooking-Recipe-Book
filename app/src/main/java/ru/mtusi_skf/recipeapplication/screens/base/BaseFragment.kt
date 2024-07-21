package ru.mtusi_skf.recipeapplication.screens.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ru.mtusi_skf.recipeapplication.MainActivity

abstract class BaseLayoutFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId)

abstract class CustomBaseFragment(contentLayoutId: Int) : BaseLayoutFragment(contentLayoutId) {

    protected open var bottomNavigationViewVisibility = View.VISIBLE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Установка слушателя для изменений WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            if (isKeyboardVisible) {
                setBottomNavigationVisibility(View.GONE)
            } else {
                setBottomNavigationVisibility(bottomNavigationViewVisibility)
            }
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        setBottomNavigationVisibility(bottomNavigationViewVisibility)
    }

    override fun onResume() {
        super.onResume()
        setBottomNavigationVisibility(bottomNavigationViewVisibility)
    }

    private fun setBottomNavigationVisibility(visibility: Int) {
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(visibility)
        }
    }
}
