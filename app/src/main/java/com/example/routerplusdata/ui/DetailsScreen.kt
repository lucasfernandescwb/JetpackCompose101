package com.example.routerplusdata.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailsScreen(
    onBackClick: () -> Unit
) {

    var counter by remember { mutableIntStateOf(0) }

    var kotlinList by remember {
        mutableStateOf(listOf<String>())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details Screen\nwith a simple counter",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )

        Text(
            text = "List",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )

        Button(
            onClick = {
                kotlinList = kotlinList + "Item ${kotlinList.size + 1}"
            }
        ) {
            Text("Add")
        }

        Button(
            onClick = {
                if (kotlinList.isNotEmpty()) {
                    kotlinList = kotlinList.dropLast(1)
                }
            },
            enabled = kotlinList.isNotEmpty()
        ) {
            Text("Remove")
        }

        Text(
            text = "Quantity: ${kotlinList.size}"
        )

        kotlinList.forEach { item ->
            Text(item)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    counter--
                },
                enabled = counter > 0
            ) {
                Text("-")
            }

            Spacer(Modifier.width(20.dp))

            Text("$counter")

            Spacer(Modifier.width(20.dp))

            Button(
                onClick = {
                    counter++
                }
            ) {
                Text("+")
            }
        }

        Button(
            onClick = onBackClick
        ) {
            Text("Go back")
        }
    }
}