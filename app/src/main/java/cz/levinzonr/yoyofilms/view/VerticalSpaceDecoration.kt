package cz.levinzonr.yoyofilms.view
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class VerticalSpaceDecoration : RecyclerView.ItemDecoration() {

    private val verticalSpaceHeight = 10

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}