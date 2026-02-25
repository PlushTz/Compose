package com.plush.soothe.ui.uiutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

/**
 * Desc:
 * @author lijt
 * Created on 2026/2/25
 * Email: lijt@eetrust.com
 */

/**
 * 导航栏保护组件：在底部添加一个半透明渐变
 *
 * @param modifier
 * @param color
 */
@Composable
fun NavigationBarProtection(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
) {
    val density = LocalDensity.current
    val navBarHeightPx = WindowInsets.navigationBars.getBottom(density)

    if (navBarHeightPx > 0) {
        val heightDp = with(density) { navBarHeightPx.toDp() }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .height(heightDp * 1.5f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                color.copy(alpha = 0.4f)
                            )
                        )
                    )
            )
        }
    }
}