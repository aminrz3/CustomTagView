package amin.rz3.example

import amin.rz3.customtagview.CustomTagViewBuilder
import amin.rz3.customtagview.CustomTagViewOnClick
import amin.rz3.customtagview.TextCustomTagView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var seperator = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textCustomTagViewRed.setCustomTagViewBuilder(object : CustomTagViewBuilder(){
            override fun getTagView(inflater: LayoutInflater?, data: String?): View? {
                val view = inflater?.inflate(R.layout.view_custom_text_tag_red,null)
                val tv = view?.findViewById<TextView>(R.id.tvCustomTextTagRed)
                tv?.text = data
                return view
            }
        })


        textCustomTagViewRed.setSeparator(seperator)
        textCustomTagViewRed.createCustomTagView()

        btnSubmitSeparator.setOnClickListener {
            val sp = etSeparator.text.toString()
            seperator = if(sp.isEmpty())
                " "
            else
                sp

            textCustomTagView.setSeparator(seperator)
            Toast.makeText(applicationContext,"Separator submit successfully",Toast.LENGTH_LONG).show()
        }



        etCustomTagView.setCustomTagViewBuilder(object : CustomTagViewBuilder(){
            override fun getTagView(inflater: LayoutInflater?, data: String?): View? {
                val view = inflater?.inflate(R.layout.view_custom_text_tag_orange,null)
                val tv = view?.findViewById<TextView>(R.id.tvTagOrange)
                tv?.text = data
                return view
            }
        })
        etCustomTagView.setSeparator(seperator)

        textCustomTagView.setCustomTagViewBuilder(object : CustomTagViewBuilder(){
            override fun getTagView(inflater: LayoutInflater?, data: String?): View? {
                val view = inflater?.inflate(R.layout.view_custom_text_tag_orange,null)
                val tv = view?.findViewById<TextView>(R.id.tvTagOrange)
                tv?.text = data
                return view
            }
        })
        textCustomTagView.setSeparator(seperator)

        btnConvertText.setOnClickListener {
            textCustomTagView.text = etText.text.toString()
            textCustomTagView.createCustomTagView()
        }

        textCustomTagView.setCustomTagViewOnClick(object : CustomTagViewOnClick{
            override fun onClick(text: String, startPos: Int, endPost: Int) {
                Toast.makeText(applicationContext, "Clicked $text",Toast.LENGTH_LONG).show()
            }
        })

        etCustomTagView.setCustomTagViewOnClick(object : CustomTagViewOnClick{
            override fun onClick(text: String, startPos: Int, endPost: Int) {
                Toast.makeText(applicationContext, "Clicked $text",Toast.LENGTH_LONG).show()
            }
        })
    }
}