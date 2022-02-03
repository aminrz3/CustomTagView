package amin.rz3.customtagview

import android.text.Selection
import android.text.Spannable
import android.text.method.ArrowKeyMovementMethod
import android.text.method.MovementMethod
import android.view.MotionEvent
import android.widget.TextView

class CustomTagViewMovement: ArrowKeyMovementMethod() {

    companion object{
        private var instance: CustomTagViewMovement? = null
        fun getInstance():MovementMethod{
            if(instance==null) instance = CustomTagViewMovement()
            return instance as CustomTagViewMovement
        }
    }

    override fun onTouchEvent(widget: TextView?, buffer: Spannable?, event: MotionEvent?): Boolean {
        val action = event!!.action
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            widget?.let {
                x -= it.totalPaddingLeft
                y -= it.totalPaddingTop
                x += it.scrollX
                y += it.scrollY
                val layout = it.layout
                val line = layout.getLineForVertical(y)
                val off = layout.getOffsetForHorizontal(line, x.toFloat())
                buffer?.let { bf->
                    val link: Array<Click> = bf.getSpans(off, off, Click::class.java)
                    if (link.isNotEmpty()) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget)
                            Selection.removeSelection(bf)
                        } else if (action == MotionEvent.ACTION_DOWN) Selection.setSelection(
                            bf, bf.getSpanStart(
                                link[0]
                            ), bf.getSpanEnd(link[0])
                        )
                        return true
                    }
                }

            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }
}