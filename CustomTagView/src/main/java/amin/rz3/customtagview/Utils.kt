package amin.rz3.customtagview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View

class Utils(val context:Context,val customTagViewBuilder:CustomTagViewBuilder?, val inflater: LayoutInflater?,val customTagViewOnClick: CustomTagViewOnClick?=null) {
    fun buildCustomTagView(tag:String, builder: SpannableStringBuilder, index:Int, pos:Int):Int{
        customTagViewBuilder?.setPosition(pos)
        val view = customTagViewBuilder?.getTagView(inflater, tag)
        view?.let {
            val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            it.measure(spec, spec)
            it.layout(0, 0, it.measuredWidth, it.measuredHeight)
            val bmp = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            canvas.translate(-it.scrollX.toFloat(), -it.scrollY.toFloat())
            it.draw(canvas)
            val bitmap = getBitmapFromView(it)
            val drawable = BitmapDrawable(context.resources, bitmap)
            drawable.setBounds(0, 0, it.width, it.height)
            builder.setSpan(
                ImageSpan(drawable),
                index,
                index + tag.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.setSpan(Click(callback),
                index,
                index + tag.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return index + tag.length + 1
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private val callback = object : ClickCallback{
        override fun onClick(text: String, startPos: Int, endPost: Int) {
            customTagViewOnClick?.onClick(text, startPos, endPost)
        }
    }
}