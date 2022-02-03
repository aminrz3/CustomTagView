package amin.rz3.customtagview

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.method.MovementMethod
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater

class EditTextCustomTagView:androidx.appcompat.widget.AppCompatEditText {
    private var inflater: LayoutInflater? = null
    private var customTagViewBuilder:CustomTagViewBuilder? = null
    private var separator:String = " "
    private var customTagViewOnClick: CustomTagViewOnClick?=null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getDefaultMovementMethod(): MovementMethod {
        return CustomTagViewMovement.getInstance()
    }

    init{
        isFocusableInTouchMode = true
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun setCustomTagViewBuilder(customTagViewBuilder:CustomTagViewBuilder){
        this.customTagViewBuilder = customTagViewBuilder
    }

    fun setCustomTagViewOnClick(customTagViewOnClick: CustomTagViewOnClick){
        this.customTagViewOnClick = customTagViewOnClick
    }


    fun setSeparator(separator:String){
        this.separator = separator
    }

    fun getSeparator():String = separator


    private fun createCustomTagView(){
        val text = text.toString()
        if(text.contains(separator)){
            val tags = text.split(separator).toTypedArray()
            val builder = SpannableStringBuilder(getText().toString().replace(separator," "))
            var index = 0
            var pos = -1
            for (tag in tags){
                customTagViewBuilder?.let {
                    if(it.getViewTypeCount()==0){
                        index = Utils(context,customTagViewBuilder,inflater,customTagViewOnClick)
                            .buildCustomTagView(tag,builder,index,0)
                    }else{
                        pos = (pos + 1) % it.getViewTypeCount()
                        index = Utils(context,customTagViewBuilder,inflater,customTagViewOnClick)
                            .buildCustomTagView(tag,builder,index,pos)
                    }
                }

            }
            setText(builder)
            setSelection(text.length)
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        text?.let {
            if (lengthAfter > 0 && text[start].toString() == separator) createCustomTagView()
        }

    }
}