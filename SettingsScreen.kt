package com.yksakademi.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val GoodColor = Color(0xFF34C759)
val BadColor = Color(0xFFFF3B30)
val NeutralColor = Color(0xFF8E8E93)

object StatusStyle {
    private val labels = listOf("Başlanmadı", "Çalışılıyor", "Öğrenildi", "Tekrar edilmeli", "Tamamlandı")
    private val colors = listOf(
        Color(0xFF8E8E93), Color(0xFFFF9500), Color(0xFF007AFF), Color(0xFFAF52DE), Color(0xFF34C759)
    )

    val count: Int get() = labels.size
    fun label(status: Int): String = labels.getOrElse(status) { labels[0] }
    fun color(status: Int): Color = colors.getOrElse(status) { colors[0] }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val m = if (onClick != null) modifier.clip(shape).clickable(onClick = onClick) else modifier
    Card(
        modifier = m,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(contentPadding), content = content)
    }
}

/** Doğru / yanlış / boş oranını gösteren yatay çubuk. */
@Composable
fun StackedBar(correct: Int, wrong: Int, empty: Int, modifier: Modifier = Modifier) {
    val total = correct + wrong + empty
    Row(
        modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (total > 0) {
            if (correct > 0) Box(Modifier.weight(correct.toFloat()).fillMaxHeight().background(GoodColor))
            if (wrong > 0) Box(Modifier.weight(wrong.toFloat()).fillMaxHeight().background(BadColor))
            if (empty > 0) Box(Modifier.weight(empty.toFloat()).fillMaxHeight().background(NeutralColor))
        }
    }
}

/** Basit dikey çubuk grafik. */
@Composable
fun MiniBarChart(
    values: List<Int>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    barWidth: Dp = 14.dp
) {
    val maxV = (values.maxOrNull() ?: 0).coerceAtLeast(1)
    Row(
        modifier.fillMaxWidth().height(110.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEachIndexed { i, v ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                Box(
                    Modifier
                        .width(barWidth)
                        .height((70f * v / maxV).dp + 3.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (v > 0) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    labels.getOrElse(i) { "" },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
