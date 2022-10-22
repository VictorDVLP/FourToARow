package com.example.fourtoarow

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _turn: MutableLiveData<Int> = MutableLiveData(1)
    val turn: LiveData<Int> = _turn

    private var _markerRed: MutableLiveData<Int> = MutableLiveData(0)
    val markerRed: LiveData<Int> = _markerRed

    private var _markerYellow: MutableLiveData<Int> = MutableLiveData(0)
    val markerYellow: LiveData<Int> = _markerYellow

    private var _booleanAction: MutableLiveData<Boolean> = MutableLiveData(false)
    val booleanAction: LiveData<Boolean> = _booleanAction

    var arrayArrows = arrayOf<ImageView>()
    var arrayBoard = arrayOf<Array<Gap>>()

    private var MAX_X = 6
    private var MAX_Y = 5
    private var MIN_XY = 0

    fun setArrayArrows(arrayArrow: Array<ImageView>): Array<ImageView> {
        arrayArrows = arrayArrow
        return arrayArrows
    }

    fun setArrayBoards(arrayBoards: Array<Array<Gap>>): Array<Array<Gap>> {
        arrayBoard = arrayBoards
        return arrayBoard
    }


    fun ckeckWin(x: Int, y: Int, color: Int) {

        var countHorizontal = 1
        var countVertical = 1
        var countDiagonal1 = 1
        var countDiagonal2 = 1

        if (x - 1 >= MIN_XY && arrayBoard[x - 1][y].color == color) {
            countHorizontal++
            if (x - 2 >= MIN_XY && arrayBoard[x - 2][y].color == color) {
                countHorizontal++
                if (x - 3 >= MIN_XY && arrayBoard[x - 3][y].color == color) countHorizontal++
            }
        }

        if (x + 1 <= MAX_X && arrayBoard[x + 1][y].color == color) {
            countHorizontal++
            if (x + 2 <= MAX_X && arrayBoard[x + 2][y].color == color) {
                countHorizontal++
                if (x + 3 <= MAX_X && arrayBoard[x + 3][y].color == color) countHorizontal++
            }
        }

        if (y - 1 >= MIN_XY && arrayBoard[x][y - 1].color == color) {
            countVertical++
            if (y - 2 >= MIN_XY && arrayBoard[x][y - 2].color == color) {
                countVertical++
                if (y - 3 >= MIN_XY && arrayBoard[x][y - 3].color == color) countVertical++
            }
        }

        if (y + 1 <= MAX_Y && arrayBoard[x][y + 1].color == color) {
            countVertical++
            if (y + 2 <= MAX_Y && arrayBoard[x][y + 2].color == color) {
                countVertical++
                if (y + 3 <= MAX_Y && arrayBoard[x][y + 3].color == color) countVertical++
            }
        }

        if (x - 1 >= MIN_XY && y - 1 >= MIN_XY && arrayBoard[x - 1][y - 1].color == color) {
            countDiagonal1++
            if (x - 2 >= MIN_XY && y - 2 >= MIN_XY && arrayBoard[x - 2][y - 2].color == color) {
                countDiagonal1++
                if (x - 3 >= MIN_XY && y - 3 >= MIN_XY && arrayBoard[x - 3][y - 3].color == color) countDiagonal1++
            }
        }

        if (x + 1 <= MAX_X && y - 1 >= MIN_XY && arrayBoard[x + 1][y - 1].color == color) {
            countDiagonal2++
            if (x + 2 <= MAX_X && y - 2 >= MIN_XY && arrayBoard[x + 2][y - 2].color == color) {
                countDiagonal2++
                if (x + 3 <= MAX_X && y - 3 >= MIN_XY && arrayBoard[x + 3][y - 3].color == color) countDiagonal2++
            }
        }

        if (x + 1 <= MAX_X && y + 1 <= MAX_Y && arrayBoard[x + 1][y + 1].color == color) {
            countDiagonal1++
            if (x + 2 <= MAX_X && y + 2 <= MAX_Y && arrayBoard[x + 2][y + 2].color == color) {
                countDiagonal1++
                if (x + 3 <= MAX_X && y + 3 <= MAX_Y && arrayBoard[x + 3][y + 3].color == color) countDiagonal1++
            }
        }

        if (x - 1 >= MIN_XY && y + 1 <= MAX_Y && arrayBoard[x - 1][y + 1].color == color) {
            countDiagonal2++
            if (x - 2 >= MIN_XY && y + 2 <= MAX_Y && arrayBoard[x - 2][y + 2].color == color) {
                countDiagonal2++
                if (x - 3 >= MIN_XY && y + 3 <= MAX_Y && arrayBoard[x - 3][y + 3].color == color) countDiagonal2++
            }
        }

        if (countHorizontal >= 4 || countVertical >= 4 || countDiagonal1 >= 4 || countDiagonal2 >= 4) {
            when (color) {
                1 -> _markerRed.value.let { a ->
                    if (a != null) {
                        _markerRed.value = a + 1
                    }
                }
                2 -> _markerYellow.value.let { a ->
                    if (a != null) {
                        _markerYellow.value = a + 1
                    }
                }
            }
            _booleanAction.value = true

        }
    }

    fun clickArrow(clickable: Array<ImageView>, arrayBoards: Array<Array<Gap>>) {
        for (x in 0..6) {
            clickable[x].setOnClickListener {
                when {
                    arrayBoards[x][5].color == 0 -> setPiece(x, 5, _turn.value!!)
                    arrayBoards[x][4].color == 0 -> setPiece(x, 4, _turn.value!!)
                    arrayBoards[x][3].color == 0 -> setPiece(x, 3, _turn.value!!)
                    arrayBoards[x][2].color == 0 -> setPiece(x, 2, _turn.value!!)
                    arrayBoards[x][1].color == 0 -> setPiece(x, 1, _turn.value!!)
                    arrayBoards[x][0].color == 0 -> setPiece(x, 0, _turn.value!!)
                }
            }
        }
    }

    private fun setPiece(x: Int, y: Int, color: Int) {
        arrayBoard[x][y].color = color
        when (color) {
            1 -> arrayBoard[x][y].gap.setImageResource(R.drawable.red_piece)
            2 -> arrayBoard[x][y].gap.setImageResource(R.drawable.yellow_piece)
        }
        _turn.value = if (_turn.value == 1) {
            2
        } else {
            1
        }

        ckeckWin(x, y, color)
    }

    fun resetMarket() {
        _markerRed.value = 0
        _markerYellow.value = 0
    }

    fun resetGame() {
        arrayBoard.forEach { array ->
            array.forEach {
                it.gap.setImageResource(R.drawable.empty_piece)
                it.color = 0
            }
        }
    }
}
