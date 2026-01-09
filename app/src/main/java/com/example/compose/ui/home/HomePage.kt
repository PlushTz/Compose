package com.example.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.router.AppRoute
import com.example.compose.theme.Blue01
import com.example.compose.theme.White

/**
 * Desc:
 * @author lijt
 * Created on 2026/1/9
 * Email: lijt@eetrust.com
 */
@Composable
fun ColumnsLayout(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Blue01),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                navController.navigate(AppRoute.ROUTE_HORIZONTAL_PAGE)
            }) {
            Text(text = "HorizontalPager", color = White)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Blue01),
        ) { Text(text = "VerticalPager", color = White) }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue01),
        ) {
            Text(text = "Modifier", color = White)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                navController.navigate(AppRoute.ROUTE_LAZY_COLUMN)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue01),
        ) {
            Text(text = "LazyColum", color = White)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                navController.navigate(AppRoute.ROUTE_LAZY_VERTICAL_GRID)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue01),
        ) {
            Text(text = "LazyVerticalGrid", color = White)
        }
    }
}