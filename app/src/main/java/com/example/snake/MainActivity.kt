package com.example.snake

import android.content.res.Resources
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayout
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var gridLayout: GridLayout

    val UP = 0
    val RIGHT = 1
    val DOWN = 2
    val LEFT = 3
    val CHANGEHEAD = 4
    val MOVESNAKE = 5

    var speedOfSnake : Long = 200

    var nextCellOfHead = -1

    var newCell = -1

    var myTask : MyTask? = null

    var directionOfSnake = RIGHT

    var bodySnake: ArrayList<Int> = ArrayList()
    var lengthOfSnakeIndex : Int = 2

    val arrayOfGameCells = arrayOfNulls<ImageView>(300)
    var map: HashMap<Int, ImageView> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        gridLayout = findViewById(R.id.glGameSpace)
        inflateGridLayout()
        onClickChange()




    }

    override fun onDestroy() {
        super.onDestroy()
        myTask?.cancel(false)
    }

    fun onClick(view: View) {
        inflateGridLayout()
    }

    fun onClickChange() {
        bodySnake.add(2)
        bodySnake.add(1)
        bodySnake.add(0)
        arrayOfGameCells.get(0)?.setImageResource(R.drawable.rectangle_visible)
        arrayOfGameCells.get(1)?.setImageResource(R.drawable.rectangle_visible)
        arrayOfGameCells.get(2)?.setImageResource(R.drawable.rectangle_middle)
    }

    fun inflateGridLayout() {

        var dimensionInDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, Resources.getSystem().displayMetrics)
        var layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(dimensionInDp.toInt(), dimensionInDp.toInt())


        for (i in 0..299) {


            var imageView = ImageView(this)
            imageView.layoutParams = layoutParams
            imageView.setImageResource(R.drawable.rectangle_invisible)

            gridLayout.addView(imageView)
            arrayOfGameCells.set(i, imageView)


        }


    }




    inner class MyTask() : AsyncTask<Void, Int, Void>() {

        private fun addNewCell() {
            newCell = Random.nextInt(0, 299)
            if (bodySnake.contains(newCell)) {
                addNewCell()
            } else {
                arrayOfGameCells.get(newCell)?.setImageResource(R.drawable.rectangle_visible)
            }
        }


        override fun doInBackground(vararg params: Void?): Void {

            while (true) {
                Thread.sleep(speedOfSnake/2)
                publishProgress(CHANGEHEAD)
                Thread.sleep(speedOfSnake/2)
                when (directionOfSnake) {
                    UP -> {

                    }
                    DOWN -> {

                    }
                    LEFT -> {

                    }
                    RIGHT -> {

                    }
                }
                publishProgress(MOVESNAKE)

            }

        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            if (newCell < 0) {
                addNewCell()
            }
            if (values[0] == CHANGEHEAD) {

                arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_middle)
                arrayOfGameCells.get(newCell)?.setImageResource(R.drawable.rectangle_middle)

            } else if (values[0] == MOVESNAKE) {

                arrayOfGameCells.get(newCell)?.setImageResource(R.drawable.rectangle_visible)
                arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_visible)

                lengthOfSnakeIndex = bodySnake.size - 1

                if (directionOfSnake == UP) {

                    nextCellOfHead = if (bodySnake[0] - 15 > 0) bodySnake[0] - 15 else bodySnake[0] + 285
                    if (nextCellOfHead == newCell) {
                        bodySnake.add(0, nextCellOfHead)
                        arrayOfGameCells.get(bodySnake[1])?.setImageResource(R.drawable.rectangle_visible)
                        addNewCell()
                    } else if (bodySnake.contains(nextCellOfHead)) {
                        myTask?.cancel(false)
                    } else {
                        arrayOfGameCells.get(bodySnake[lengthOfSnakeIndex])?.setImageResource(R.drawable.rectangle_invisible)
                        moveCellsOfSnake()
                        bodySnake[0] = nextCellOfHead
                        arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_visible)
                    }



/////////////////////////////////////////////
//                    nextCellOfHead = if (bodySnake[0] - 15 > 0) bodySnake[0] - 15 else bodySnake[0] + 285
//                    if (nextCellOfHead == newCell) {
//                        bodySnake.add(0, newCell)
//                    } else {
//                        arrayOfGameCells.get(bodySnake[lengthOfSnakeIndex])?.setImageResource(R.drawable.rectangle_invisible)
//                    }
//                    for (i in bodySnake.size - 1 downTo 1) {
//                        bodySnake[i] = bodySnake[i - 1]
//                    }
//                    bodySnake[0] = nextCellOfHead //if (bodySnake[0] - 15 > 0) bodySnake[0] - 15 else bodySnake[0] + 285
//                    arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_visible)

                } else if (directionOfSnake == RIGHT && bodySnake[0] + 1 != bodySnake[1]) {
                    arrayOfGameCells.get(bodySnake[lengthOfSnakeIndex])?.setImageResource(R.drawable.rectangle_invisible)
                    for (i in lengthOfSnakeIndex downTo 1) {
                        bodySnake[i] = bodySnake[i - 1]
                    }
                    bodySnake[0] = if ((bodySnake[0] + 1) % 15 != 0) bodySnake[0] + 1 else bodySnake[0] - 14
                    arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_visible)


                } else if (directionOfSnake == DOWN && bodySnake[0] + 15 != bodySnake[1]) {
                    arrayOfGameCells.get(bodySnake[lengthOfSnakeIndex])?.setImageResource(R.drawable.rectangle_invisible)
                    for (i in lengthOfSnakeIndex downTo 1) {
                        bodySnake[i] = bodySnake[i - 1]
                    }
                    bodySnake[0] = if (bodySnake[0] + 15 < 299) bodySnake[0] + 15 else bodySnake[0] - 285
                    arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_visible)

                } else if (directionOfSnake == LEFT && bodySnake[0] - 1 != bodySnake[1]) {
                    arrayOfGameCells.get(bodySnake[lengthOfSnakeIndex])?.setImageResource(R.drawable.rectangle_invisible)
                    for (i in lengthOfSnakeIndex downTo 1) {
                        bodySnake[i] = bodySnake[i - 1]
                    }
                    bodySnake[0] = if ((bodySnake[0]) % 15 != 0) bodySnake[0] - 1 else bodySnake[0] + 14
                    arrayOfGameCells.get(bodySnake[0])?.setImageResource(R.drawable.rectangle_visible)

                }

            }

        }

        private fun moveCellsOfSnake() {
            for (i in bodySnake.size - 1 downTo 1) {
                bodySnake[i] = bodySnake[i - 1]
            }
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }
    }

    fun onClickPlay(view: View) {

        myTask = MyTask()
        myTask!!.execute()

    }

    fun onClickChangeDirection(view: View) {
        if (view.id == R.id.btnUp) {
            if (directionOfSnake != DOWN) directionOfSnake = UP
        } else if (view.id == R.id.btnLeft) {
            if (directionOfSnake != RIGHT) directionOfSnake = LEFT
        } else if (view.id == R.id.btnRight) {
            if (directionOfSnake != LEFT) directionOfSnake = RIGHT
        } else if (view.id == R.id.btnDown) {
            if (directionOfSnake != UP) directionOfSnake = DOWN
        }
    }

    fun onClickStop(view: View) {
        myTask?.cancel(false)
    }



}

