package com.yksakademi.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yksakademi.app.data.Profile
import com.yksakademi.app.ui.AppCard
import com.yksakademi.app.ui.MainViewModel
import com.yksakademi.app.ui.ModuleInfo
import com.yksakademi.app.ui.quickAccessModules
import java.time.LocalDate
import java.time.temporal.ChronoUnit

private val DayLabels = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz")

@Composable
fun HomeScreen(vm: MainViewModel, onNavigate: (String) -> Unit) {
    val profile by vm.profile.collectAsStateWithLifecycle()
    val todayQ by vm.todayQuestions.collectAsStateWithLifecycle()
    val todaySec by vm.todayStudySeconds.collectAsStateWithLifecycle()
    val weekSec by vm.weekStudySeconds.collectAsStateWithLifecycle()
    val doneTopics by vm.completedTopics.collectAsStateWithLifecycle()
    val allTopics by vm.totalTopics.collectAsStateWithLifecycle()
    val lastNet by vm.lastExamNet.collectAsStateWithLifecycle()
    val dueToday by vm.reviewsDueToday.collectAsStateWithLifecycle()
    val overdue by vm.reviewsOverdue.collectAsStateWithLifecycle()
    val plan by vm.planProgress.collectAsStateWithLifecycle()
    val weekQ by vm.weekQuestions.collectAsStateWithLifecycle()

    val p = profile ?: Profile()
    val todayMin = todaySec / 60
    val planPercent = if (plan.second == 0) 0 else plan.first * 100 / plan.second

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Header(p.name) }
        item { HeroCard(p) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GoalCard(Modifier.weight(1f), "Bugünkü Soru", "$todayQ / ${p.dailyQuestionGoal}", todayQ, p.dailyQuestionGoal)
                GoalCard(Modifier.weight(1f), "Bugünkü Süre", "$todayMin / ${p.dailyStudyMinutesGoal} dk", todayMin, p.dailyStudyMinutesGoal)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatTile(Modifier.weight(1f), "Konu", "$doneTopics/$allTopics")
                StatTile(Modifier.weight(1f), "Günlük Plan", if (plan.second == 0) "-" else "%$planPercent")
                StatTile(Modifier.weight(1f), "Son Deneme", lastNet?.let { String.format("%.2f", it) } ?: "-")
            }
        }
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Text("Tekrar Merkezi", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(6.dp))
                Row {
                    Text("Bugün: $dueToday", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.width(20.dp))
                    Text(
                        "Geciken: $overdue",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (overdue > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        item { WeeklyCard(weekQ, weekSec / 60) }
        item {
            Text("Hızlı Erişim", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        quickAccessModules.chunked(3).forEach { rowItems ->
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowItems.forEach { m ->
                        QuickTile(Modifier.weight(1f), m) { onNavigate(m.route) }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(44.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.School, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text("YKS AKADEMİ", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
            Text(
                if (name.isBlank()) "Hoş geldin" else "Merhaba, $name",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HeroCard(p: Profile) {
    val examDate = runCatching { LocalDate.parse(p.examDate) }.getOrNull()
    val daysLeft = examDate?.let { ChronoUnit.DAYS.between(LocalDate.now(), it).coerceAtLeast(0) }
    val shape = RoundedCornerShape(24.dp)
    Box(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, Color(0xFF5856D6))))
            .padding(20.dp)
    ) {
        Column {
            Text("YKS ${p.examYear}", color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    daysLeft?.toString() ?: "-",
                    color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text("gün kaldı", color = Color.White.copy(alpha = 0.9f), modifier = Modifier.padding(bottom = 10.dp))
            }
            Spacer(Modifier.height(8.dp))
            val dept = p.targetDepartment.ifBlank { "Hedef bölüm belirlenmedi" }
            val uni = p.targetUniversity.ifBlank { "Hedef üniversite belirlenmedi" }
            Text(dept, color = Color.White, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium)
            Text(uni, color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.bodyMedium)
            if (p.name.isBlank() && p.targetUniversity.isBlank()) {
                Spacer(Modifier.height(8.dp))
                Text("Ayarlar sekmesinden profilini doldur.", color = Color.White.copy(alpha = 0.85f), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun GoalCard(modifier: Modifier, title: String, valueText: String, value: Int, goal: Int) {
    AppCard(modifier) {
        Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        Text(valueText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        val progress = if (goal <= 0) 0f else (value.toFloat() / goal).coerceIn(0f, 1f)
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun StatTile(modifier: Modifier, label: String, value: String) {
    AppCard(modifier) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun WeeklyCard(values: List<Int>, weekMinutes: Int) {
    val today = LocalDate.now()
    val maxV = (values.maxOrNull() ?: 0).coerceAtLeast(1)
    AppCard(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Son 7 Gün (Soru)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text("${weekMinutes / 60} sa ${weekMinutes % 60} dk", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth().height(120.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            values.forEachIndexed { i, v ->
                val date = today.minusDays((6 - i).toLong())
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                    Text("$v", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(4.dp))
                    Box(
                        Modifier
                            .width(22.dp)
                            .height((60f * v / maxV).dp + 4.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (v > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(DayLabels[date.dayOfWeek.value - 1], style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
private fun QuickTile(modifier: Modifier, module: ModuleInfo, onClick: () -> Unit) {
    AppCard(modifier.height(108.dp), onClick = onClick) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(module.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                module.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
