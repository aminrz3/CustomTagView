package amin.rz3.customtagview

import android.view.LayoutInflater
import android.view.View

abstract class CustomTagViewBuilder {
    private var position = 0

    fun getViewTypeCount(): Int {
        return 0
    }

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    abstract fun getTagView(inflater: LayoutInflater?, data: String?): View?
}