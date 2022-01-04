package com.task.noteapp.components

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R

class MyRecyclerView : RecyclerView {
    private var extraHeight = 0
    private var itemDecor = ExtraSpace()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        decorateView(getOrientation(attrs), attrs)
//        addItemDecoration(itemDecor)

    }

    init {
        extraHeight = resources.getDimension(R.dimen.scroll_extra_height).toInt()
//        addItemDecoration(itemDecor)
    }

    fun setDividerMode(mode: DividerMode) {

    }

    override fun getItemDecorationCount(): Int {
        return super.getItemDecorationCount() - 1 // Ignore itemDecor
    }

    private fun getOrientation(attrs: AttributeSet?): Int {
        val b = context.theme.obtainStyledAttributes(
            attrs,
            intArrayOf(android.R.attr.orientation), 0, 0
        )
        val orientation = b.getInt(0, RecyclerView.VERTICAL)
        b.recycle()
        return orientation
    }

    private fun decorateView(
        orientation: Int,
        attrs: AttributeSet?
    ) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MyRecyclerView, 0, 0
        )

        // Divider and the mode
        if (typedArray.hasValue(R.styleable.MyRecyclerView_rvDividerDrawable)) {
            val dividerDrawable =
                typedArray.getDrawable(R.styleable.MyRecyclerView_rvDividerDrawable)
            val dividerMode = typedArray.getInt(
                R.styleable.MyRecyclerView_rvDividerMode,
                DividerMode.SHOW_DIVIDER_BEGINNING.or(
                    DividerMode.SHOW_DIVIDER_MIDDLE
                ).or(
                    DividerMode.SHOW_DIVIDER_END
                )
            )

            val isExtraSpaceActive = ExtraSpaceMode.EXTRA_SPACE_ON == typedArray.getInt(
                R.styleable.MyRecyclerView_rvExtraSpace, ExtraSpaceMode.EXTRA_SPACE_ON
            )
            val decoration = DrawableDividerDecoration(
                dividerDrawable = dividerDrawable!!,
                dividerMode = dividerMode
            )
            addItemDecoration(decoration)
            if (isExtraSpaceActive) {
                when (orientation) {
                    VERTICAL -> {
                        addItemDecoration(itemDecor)
                    }
                    HORIZONTAL -> {
                    }
                }
            }
        }

        // Preview list item
        if (isInEditMode) {
            // In XML, you could add attribute "app:rvPreviewLayout=grid" to
            // display the items in a grid
            val layout = typedArray.getInt(
                R.styleable.MyRecyclerView_rvPreviewLayout,
                PREVIEW_LAYOUT_AS_LIST
            )
            val layoutManager = when (layout) {
                PREVIEW_LAYOUT_AS_GRID -> GridLayoutManager(context, 3, orientation, false)
                else -> LinearLayoutManager(context, orientation, false)
            }

            this.layoutManager = layoutManager
        }

        typedArray.recycle()

    }

    object ExtraSpaceMode {

        @IntDef(
            flag = true,
            value = [EXTRA_SPACE_ON, EXTRA_SPACE_OFF]
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type

        /**
         * Don't show any dividers.
         */
        const val EXTRA_SPACE_ON = 0

        /**
         * Show a divider at the beginning of the group.
         */
        const val EXTRA_SPACE_OFF = 1


    }

    object DividerMode {

        @IntDef(
            flag = true,
            value = [SHOW_DIVIDER_NONE, SHOW_DIVIDER_BEGINNING, SHOW_DIVIDER_MIDDLE, SHOW_DIVIDER_END]
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type

        /**
         * Don't show any dividers.
         */
        const val SHOW_DIVIDER_NONE = 0

        /**
         * Show a divider at the beginning of the group.
         */
        const val SHOW_DIVIDER_BEGINNING = 1

        /**
         * Show dividers between each item in the group.
         */
        const val SHOW_DIVIDER_MIDDLE = 2

        /**
         * Show a divider at the end of the group.
         */
        const val SHOW_DIVIDER_END = 4
    }


    inner class ExtraSpace : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position: Int = parent.getChildAdapterPosition(view)
            val itemCount: Int = state.itemCount

            val bottomIndexOffset: Int
            if (layoutManager is GridLayoutManager) {
                var spanCount = (layoutManager as GridLayoutManager).spanCount
                if (itemCount % spanCount != 0) {
                    spanCount = itemCount % spanCount
                }
                var i = 0
                while (spanCount > 0) {
                    i++
                    spanCount -= (layoutManager as GridLayoutManager).spanSizeLookup.getSpanSize(
                        itemCount - i
                    )
                }
                bottomIndexOffset = i
            } else {
                bottomIndexOffset = 1
            }
            if (itemCount > 0 && position >= itemCount - bottomIndexOffset) {
                outRect.bottom = extraHeight
            }
        }
    }

    companion object {

        /**
         * To preview the item displayed in a list in XML previewer.
         */
        @JvmStatic
        val PREVIEW_LAYOUT_AS_LIST = 0

        /**
         * To preview the item displayed in a grid in XML previewer.
         */
        @JvmStatic
        val PREVIEW_LAYOUT_AS_GRID = 1
    }
}