package br.com.rodrigoscorza.artederua.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import br.com.rodrigoscorza.artederua.R


abstract class SwipeHandler(context: Context) : ItemTouchHelper.Callback() {
    var cx = context
    private val background = ColorDrawable()

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        var intrinsicWidth = 0
        var intrinsicHeight = 0

        var iconTop = 0
        var iconMargin = 0
        var iconLeft = 0
        var iconRight = 0
        var iconBottom = 0

        var icone: Drawable


        if (dX > 0.0) {
            background.color = ContextCompat.getColor(cx, android.R.color.holo_blue_dark)
            background.setBounds(itemView.left - dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            icone = ContextCompat.getDrawable(cx, R.drawable.ic_edit)!!
        } else {
            background.color = ContextCompat.getColor(cx, android.R.color.holo_red_dark)
            background.setBounds(itemView.left, itemView.top, itemView.right - dX.toInt(), itemView.bottom)
            icone = ContextCompat.getDrawable(cx, R.drawable.ic_delete)!!
        }
        background.draw(c)

        intrinsicWidth = icone.intrinsicWidth
        intrinsicHeight = icone.intrinsicHeight

        iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        iconMargin = (itemHeight - intrinsicHeight) / 2
        iconBottom = iconTop + intrinsicHeight


        if (dX > 0.0) {
            iconLeft = itemView.left + iconMargin
            iconRight = itemView.left + iconMargin + intrinsicWidth
        } else {
            iconLeft = itemView.right - iconMargin - intrinsicWidth
            iconRight = itemView.right - iconMargin
        }
        icone.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icone.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}