package com.alejandrorios.peakstest.presentation

import android.os.Bundle
import android.util.Log
import android.view.DragEvent.ACTION_DRAG_ENDED
import android.view.DragEvent.ACTION_DRAG_ENTERED
import android.view.DragEvent.ACTION_DRAG_EXITED
import android.view.DragEvent.ACTION_DRAG_LOCATION
import android.view.DragEvent.ACTION_DRAG_STARTED
import android.view.DragEvent.ACTION_DROP
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.alejandrorios.peakstest.R
import com.alejandrorios.peakstest.data.utils.CallResult.Failure
import com.alejandrorios.peakstest.data.utils.CallResult.Loading
import com.alejandrorios.peakstest.data.utils.CallResult.Success
import com.alejandrorios.peakstest.databinding.ActivityMainBinding
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.presentation.view.RectangleView
import com.alejandrorios.peakstest.utils.showToast
import com.alejandrorios.peakstest.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var rectangleWithPos: List<RectangleWithPos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainViewModel.validateAndStart().observe(this) { result ->
            when (result) {
                is Failure -> Log.e("MainActivity", "There's some error")
                is Loading -> {}
                is Success -> {
                    rectangleWithPos = result.data
                    setupView()
                }
            }
        }
    }

    private fun setupView() {
        with(binding) {
            clContent.setOnDragListener(dragListener)

            rectangleWithPos.forEach { rectangle ->
                val rectangleView = RectangleView(this@MainActivity).apply {
                    buildView(rectangleData = rectangle, viewParent = clContent)
                }

                clContent.addView(rectangleView)
            }
        }

        showToast(R.string.tutorial_message)
    }

    private val dragListener = View.OnDragListener { view, dragEvent ->
        val draggableItem = dragEvent.localState as View

        return@OnDragListener when (dragEvent.action) {
            ACTION_DRAG_EXITED -> {
                // When the dragged rectangle exits drop-area without dropping set rectangle visibility to VISIBLE
                draggableItem.visibility = VISIBLE
                view.invalidate()
                true
            }
            ACTION_DROP -> {
                // Re-position the rectangle in the updated x, y co-ordinates
                // with rectangle center position pointing to new x,y co-ordinates
                draggableItem.x = dragEvent.x - (draggableItem.width / 2)
                draggableItem.y = dragEvent.y - (draggableItem.height / 2)

                // Search dragged rectangleData and update (lastPosX, lastPosY)
                (draggableItem as RectangleView).rectangleData.apply {
                    lastPosX = (dragEvent.x.toDouble() / binding.clContent.width)
                    lastPosY = (dragEvent.y.toDouble() / binding.clContent.height)
                }

                // On drop event remove the rectangle from parent viewGroup
                val parent = draggableItem.parent as ConstraintLayout
                parent.removeView(draggableItem)

                // Add the rectangle view to a new viewGroup where the rectangle was dropped
                val dropArea = view as ConstraintLayout
                dropArea.addView(draggableItem)

                // Save changes
                mainViewModel.saveChanges(rectangleWithPos)
                true
            }
            ACTION_DRAG_ENDED -> {
                draggableItem.visibility = VISIBLE
                view.invalidate()
                true
            }
            ACTION_DRAG_STARTED, ACTION_DRAG_LOCATION, ACTION_DRAG_ENTERED -> true
            else -> false
        }
    }
}
