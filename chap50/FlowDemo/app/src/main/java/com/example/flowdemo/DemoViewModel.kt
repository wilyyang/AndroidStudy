package com.example.flowdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {
    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val sharedFlow = _sharedFlow.asSharedFlow()
    fun startSharedFlow(){
        viewModelScope.launch {
            for(i in 1..5){
                _sharedFlow.emit(i)
                delay(2000)
            }
        }
    }
    val subCount = _sharedFlow.subscriptionCount

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun increaseValue(){
        _stateFlow.value += 1
    }

    val myFlow: Flow<Int> = flow {
        for(i in 1..5){
            delay(1000)
            emit(i)
            Log.e("Crane", "myFlow : $i")
        }
    }

    val hotFlow = myFlow.shareIn(viewModelScope, replay = 1, started = SharingStarted.WhileSubscribed())

    fun doubleIt(value: Int) = flow {
        emit(value)
        Log.e("Crane", "doubleIt x : $value")
        delay(1000)
        emit(value + value)
        Log.e("Crane", "doubleIt x x x : $value + value")
    }

    val newFlow = myFlow.map {
        "Current value = $it"
    }

    // val newFlow = myFlow.filter{
    //     it % 2 == 0
    // }.map {
    //     "Current value = $it"
    // }

    // val newFlow = myFlow.transform {
    //     emit("Value = $it")
    //     delay(1000)
    //     val doubled = it * 2
    //     emit("Value doubled = $doubled")
    // }
}