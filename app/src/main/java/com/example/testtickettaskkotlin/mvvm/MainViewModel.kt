package com.example.testtickettaskkotlin.mvvm

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testticketaskkotlin.Ticket
import com.example.testticketaskkotlin.copy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import servermock.IServerHandler
import java.lang.Exception

class MainViewModel(private val serverHandler : IServerHandler) : ViewModel() {

    private val _mainState: MutableStateFlow<MainState> = MutableStateFlow(MainState.Ready)
    val mainState = _mainState.asStateFlow()

    fun saveTicket(name: String, priceValueString: String) {

       try {
           val priceValue = priceValueString.toDouble()

           viewModelScope.launch {
               val priceInCents = priceValue.times(100).toInt()
               serverHandler.sendTicketDataToServer(name, priceInCents)
               _mainState.value = MainState.Ready
           }
       } catch (e: Exception) {
           println("Entered data is invalid")
           _mainState.value = MainState.DataError
       }

       }

}

sealed class MainState {
    object Ready : MainState()

    object DataError : MainState()
}