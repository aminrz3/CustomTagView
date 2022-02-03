package amin.rz3.customtagview

import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class Click(val clickCallback: ClickCallback):ClickableSpan() {
    override fun onClick(v: View) {
        val text = (v as TextView).text
        val s = text as Spanned
        clickCallback.onClick(text.subSequence(s.getSpanStart(this), s.getSpanEnd(this)).toString(), s.getSpanStart(this), s.getSpanEnd(this))
    }
}