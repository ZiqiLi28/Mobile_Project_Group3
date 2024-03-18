package com.example.accounting.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp


@Composable
fun MyComposable() {
    val context = LocalContext.current
    val (showNumPad, setShowNumPad) = remember { mutableStateOf(false) }
    val (enteredText, setEnteredText) = remember { mutableStateOf("") }

    val onNumPadButtonClick: (String) -> Unit = { text ->
        when (text) {
            "CE" -> setEnteredText("")
            "⌫" -> {
                if (enteredText.isNotEmpty()) {
                    // Remove the last character from the entered text
                    setEnteredText(enteredText.dropLast(1))
                }
            }
            else -> setEnteredText("$enteredText$text")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().clickable {
            // When clicked, hide the numeric keypad
            if (showNumPad) {
                setShowNumPad(false)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = enteredText, modifier = Modifier.padding(8.dp))
            }
            if (showNumPad) {
                NumPad(callback = onNumPadButtonClick)
            }
        }

        ButtonToShowOrHideNumPad(setShowNumPad)
    }
}



@Composable
fun NumPad(
    callback: (text: String) -> Any
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        NumPadRow(
            listOf("7", "8", "9", "0"),
            listOf(0.25f, 0.25f, 0.25f, 0.25f),
            callback
        )
        NumPadRow(
            listOf("4", "5", "6", "."),
            listOf(0.25f, 0.25f, 0.25f, 0.25f),
            callback
        )
        NumPadRow(
            listOf("1", "2", "3", "\u232b"),
            listOf(0.25f, 0.25f, 0.25f, 0.25f),
            callback
        )
        NumPadRow(
            listOf("Add", "CE"),
            listOf(0.5f, 0.5f),
            callback
        )
    }
}
@Composable
fun NumPadRow(
    texts: List<String>,
    weights: List<Float>,
    callback: (text: String) -> Any
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in texts.indices) {
            NumPadButton(
                text = texts[i],
                modifier = Modifier.weight(weights[i]),
                callback = callback
            )
        }
    }
}

@Composable
fun NumPadButton(
    text: String,
    callback: (text: String) -> Any,
    modifier: Modifier = Modifier
) {
    val hapticFeedback = LocalHapticFeedback.current
    Button(
        modifier = modifier
            .padding(4.dp),
        onClick = {
            hapticFeedback.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
            callback(text)
        }
    ) {
        Text(text)
    }
}

@Composable
fun ButtonToShowOrHideNumPad(setShowNumPad: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add padding to center the button
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 56.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Button(
                onClick = { setShowNumPad(true) },
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text("Show NumPad", color = MaterialTheme.colorScheme.surface)
            }
        }
    }
}
//now there is a button to show keyboard,
// (instead of "adding record" which would be created later).
// Add button is not ready


