package it.macgood.core.fragment

import android.R
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Fragment.themeTextColor(attr: Int) : Int {
    val typedValue = TypedValue()
    requireContext().theme.resolveAttribute(attr, typedValue, true)
    val color = typedValue.data
    return color
}

fun Fragment.makeToast(message: String?) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.makeSnackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .setAnchorView(view)
        .show()
}


fun Fragment.getDrawable(id: Int) : Drawable? {
    return ResourcesCompat.getDrawable(resources, id, requireActivity().theme)
}

fun Fragment.getColor(id: Int): Int {
    return resources.getColor(id, requireActivity().theme)
}
