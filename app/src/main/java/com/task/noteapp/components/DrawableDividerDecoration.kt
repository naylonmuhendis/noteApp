package com.task.noteapp.components

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import kotlin.math.roundToInt
import com.task.noteapp.components.MyRecyclerView.DividerMode

class DrawableDividerDecoration(
    private val dividerDrawable: Drawable,
    @DividerMode.Type private val dividerMode: Int
) :
    RecyclerView.ItemDecoration() {

    private val bounds = Rect()

    override fun onDraw(
        canvas: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager: RecyclerView.LayoutManager? = parent.layoutManager



        layoutManager?.let {
            when (layoutManager) {
                is LinearLayoutManager -> {
                    when (layoutManager.orientation) {
                        RecyclerView.HORIZONTAL -> decorateHorizontally(canvas, parent)
                        RecyclerView.VERTICAL -> decorateVertically(canvas, parent)
                        else -> throw IllegalStateException("Unknown orientation")
                    }
                }
                is GridLayoutManager -> {
                    when (layoutManager.orientation) {
                        RecyclerView.HORIZONTAL -> decorateHorizontally(canvas, parent)
                        RecyclerView.VERTICAL -> decorateVertically(canvas, parent)
                        else -> throw IllegalStateException("Unknown orientation")
                    }
                }
                else -> {
                    throw IllegalStateException(
                        "Cannot support ${javaClass.simpleName} for ${layoutManager.javaClass.simpleName}"
                    )

                }
            }
        }
    }

    private fun decorateVertically(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        canvas.save()
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        canvas.clipRect(
            left, parent.paddingTop,
            right, parent.height - parent.paddingBottom
        )

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)

            // Beginning
            if (i == 0 && dividerMode.and(DividerMode.SHOW_DIVIDER_BEGINNING) == DividerMode.SHOW_DIVIDER_BEGINNING) {
                val top = bounds.top + child.translationY.roundToInt()
                val bottom = top + dividerDrawable.intrinsicHeight

                dividerDrawable.setBounds(left, top, right, bottom)
                dividerDrawable.draw(canvas)
            }

            // Middle
            if (i in 0..(childCount - 2) &&
                dividerMode.and(DividerMode.SHOW_DIVIDER_MIDDLE) == DividerMode.SHOW_DIVIDER_MIDDLE
            ) {
                val bottom = bounds.bottom + child.translationY.roundToInt()
                val top = bottom - dividerDrawable.intrinsicHeight

                dividerDrawable.setBounds(left, top, right, bottom)
                dividerDrawable.draw(canvas)
            }

            // End
            if (i == childCount - 1 &&
                dividerMode.and(DividerMode.SHOW_DIVIDER_END) == DividerMode.SHOW_DIVIDER_END
            ) {
                val bottom = bounds.bottom + child.translationY.roundToInt()
                val top = bottom - dividerDrawable.intrinsicHeight

                dividerDrawable.setBounds(
                    left,
                    top,
                    right,
                    bottom - parent.resources.getDimension(R.dimen.scroll_extra_height).toInt()
                )
                dividerDrawable.draw(canvas)
            }
        }

        canvas.restore()
    }

    private fun decorateHorizontally(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        canvas.save()

        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        canvas.clipRect(
            parent.paddingLeft, top,
            parent.width - parent.paddingRight, bottom
        )

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)

            // Beginning
            if (i == 0 &&
                dividerMode.and(DividerMode.SHOW_DIVIDER_BEGINNING) == DividerMode.SHOW_DIVIDER_BEGINNING
            ) {
                val left = bounds.left + Math.round(child.translationX)
                val right = left + dividerDrawable.intrinsicWidth

                dividerDrawable.setBounds(left, top, right, bottom)
                dividerDrawable.draw(canvas)
            }

            // Middle
            if (i in 0..(childCount - 2) &&
                dividerMode.and(DividerMode.SHOW_DIVIDER_MIDDLE) == DividerMode.SHOW_DIVIDER_MIDDLE
            ) {
                val right = bounds.right + child.translationX.roundToInt()
                val left = right - dividerDrawable.intrinsicWidth

                dividerDrawable.setBounds(left, top, right, bottom)
                dividerDrawable.draw(canvas)
            }

            // End
            if (i == childCount - 1 &&
                dividerMode.and(DividerMode.SHOW_DIVIDER_END) == DividerMode.SHOW_DIVIDER_END
            ) {
                val right = bounds.right + child.translationX.roundToInt()
                val left = right - dividerDrawable.intrinsicWidth

                dividerDrawable.setBounds(left, top, right, bottom)
                dividerDrawable.draw(canvas)
            }
        }

        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position = parent.getChildAdapterPosition(view)
        var top: Int
        var bottom: Int
        var left: Int
        var right: Int
        top = 0
        bottom = 0
        left = 0
        right = 0

        val layoutManager: RecyclerView.LayoutManager? = parent.layoutManager

        layoutManager?.let {
            when (layoutManager) {
                is LinearLayoutManager -> {
                    when (layoutManager.orientation) {
                        RecyclerView.HORIZONTAL -> {
                            if (position == 0 && dividerMode.and(DividerMode.SHOW_DIVIDER_BEGINNING) == DividerMode.SHOW_DIVIDER_BEGINNING) {
                                left = dividerDrawable.intrinsicWidth
                            }
                            right = dividerDrawable.intrinsicWidth
                        }
                        RecyclerView.VERTICAL -> {
                            if (position == 0 && dividerMode.and(DividerMode.SHOW_DIVIDER_BEGINNING) == DividerMode.SHOW_DIVIDER_BEGINNING) {
                                top = dividerDrawable.intrinsicHeight
                            }
                            bottom = dividerDrawable.intrinsicHeight
                        }
                        else -> {
                        }
                    }
                }
                is GridLayoutManager -> {
                    when (layoutManager.orientation) {
                        RecyclerView.HORIZONTAL -> {

                        }
                        RecyclerView.VERTICAL -> {
                            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                                outRect.right = dividerDrawable.intrinsicHeight / 2
                            } else {
                                outRect.left = dividerDrawable.intrinsicHeight / 2
                            }
                            outRect.bottom = dividerDrawable.intrinsicHeight
                        }
                        else -> {

                        }
                    }
                }
                else -> throw IllegalStateException(
                    "Cannot support ${javaClass.simpleName} for ${layoutManager.javaClass.simpleName}"
                )
            }
        }
        outRect[left, top, right] = bottom
    }
}