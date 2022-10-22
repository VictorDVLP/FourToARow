package com.example.fourtoarow

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class DrawableTest {

    object DrawableMatcher {

        fun withDrawable(@DrawableRes resourceId: Int): Matcher<View> {
            return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
                override fun describeTo(description: Description?) {
                    description!!.appendText("has drawable resource $resourceId")
                }

                override fun matchesSafely(imageView: ImageView): Boolean {
                    return isSameBitmap(imageView, imageView.drawable, resourceId)
                }
            }
        }

        private fun isSameBitmap(item: View, drawable: Drawable?, expectedResId: Int): Boolean {
            val image = item as ImageView
            if (expectedResId < 0) {
                return image.drawable == null
            }
            val expectedDrawable: Drawable? = ContextCompat.getDrawable(item.context, expectedResId)
            if (drawable == null || expectedDrawable == null) {
                return false
            }
            // Make tint consistent just in case they differ
            val bitmap = getBitmap(drawable)
            val expectedBitmap = getBitmap(expectedDrawable)
            return bitmap.sameAs(expectedBitmap)
        }

        /**
         * Convert vector drawable to bitmap
         * @param drawable vector drawable
         */
        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}