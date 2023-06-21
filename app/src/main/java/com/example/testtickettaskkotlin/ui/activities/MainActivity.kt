package com.example.testtickettaskkotlin.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testtickettaskkotlin.mvvm.MainState
import com.example.testtickettaskkotlin.mvvm.MainViewModel
import com.example.testtickettaskkotlin.ui.theme.TestTicketTaskKotlinTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val errorState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.mainState.collect {state ->
                        when(state) {
                            MainState.DataError -> errorState.value = true
                            MainState.Ready -> errorState.value = false
                        }
                    }
                }
            }
        }

        setContent {
            TestTicketTaskKotlinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InputScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InputScreen() {

        val nameState = remember { mutableStateOf("") }

        val priceState = remember { mutableStateOf("") }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (errorState.value) {
                Text(
                    text = "There has been an issue processing data, please check if data provided is valid",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }

            TextField(
                value = nameState.value,
                onValueChange = {input ->
                    nameState.value = input
                },
                label = { Text(text = "Please Enter Name") },
                maxLines = 1,
                modifier = Modifier.padding(8.dp),
                isError = errorState.value
            )

            TextField(
                value = priceState.value,
                onValueChange = {input ->
                    priceState.value = input
                },
                label = { Text(text = "Please Enter Price") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = Modifier.padding(8.dp),
                isError = errorState.value
            )

            Button(onClick = {
                errorState.value = false
                viewModel.saveTicket(nameState.value, priceState.value)
                nameState.value = ""
                priceState.value = ""
            }) {
                Text(text = "Submit")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TestTicketTaskKotlinTheme {
            InputScreen()
        }
    }
}