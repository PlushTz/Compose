package com.example.compose.ui.uiutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
 * 状态栏保护组件：在顶部添加一个半透明渐变，防止内容与状态栏图标颜色冲突
 *
 * @param modifier
 * @param color
 */
@Composable
fun StatusBarProtection(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
) {
    val density = LocalDensity.current
    val statusBarHeightPx = WindowInsets.statusBars.getTop(density)

    if (statusBarHeightPx > 0) {
        val heightDp = with(density) { statusBarHeightPx.toDp() }
        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .height(heightDp * 1.5f) // 渐变区域稍大于状态栏高度
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.6f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}