package com.yksakademi.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yksakademi.app.data.QuestionLog
import com.yksakademi.app.data.Subject
import com.yksakademi.app.ui.AppCard
import com.yksakademi.app.ui.BadColor
import com.yksakademi.app.ui.GoodColor
import com.yksakademi.app.ui.MiniBarChart
import com.yksakademi.app.ui.NeutralColor
import com.yksakademi.app.ui.QuestionsViewModel
import com.yksakademi.app.ui.StackedBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val Exams = listOf("TYT", "AYT", "YDT")
private val RangeLabels = listOf("Bugün", "7 Gün", "30 Gün", "Toplam")

private fun fmt(v: Double): String = String.format("%.2f", v)

@Composable
fun QuestionsScreen(vm: QuestionsViewModel, onBack: () -> Unit) {
    var tab by remember { mutableIntStateOf(0) }
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(horizontal = 8.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Geri")
            }
            Text("Soru Takibi", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = tab == 0, onClick = { tab = 0 }, label = { Text("Giriş") })
            FilterChip(selected = tab == 1, onClick = { tab = 1 }, label = { Text("İstatistik") })
        }
        if (tab == 0) EntryTab(vm) else StatsTab(vm)
    }
}

// ------------------------------------------------------------------ Giriş

@Composable
private fun EntryTab(vm: QuestionsViewModel) {
    val exam by vm.exam.collectAsStateWithLifecycle()
    val day by vm.day.collectAsStateWithLifecycle()
    val subjects by vm.subjects.collectAsStateWithLifecycle()
    val logs by vm.logs.collectAsStateWithLifecycle()
    val coef by vm.coefficient.collectAsStateWithLifecycle()
    var bulkFor by remember { mutableStateOf<Subject?>(null) }
    val byId = logs.associateBy { it.subjectId }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Exams.forEach { e ->
                    FilterChip(selected = exam == e, onClick = { vm.setExam(e) }, label = { Text(e) })
                }
            }
        }
        item {
            val label = if (day == vm.today) "Bugün"
            else LocalDate.ofEpochDay(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { vm.shiftDay(-1) }) { Icon(Icons.Rounded.ChevronLeft, contentDescription = "Önceki gün") }
                Text(label, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = { vm.shiftDay(1) }, enabled = day < vm.today) {
                    Icon(Icons.Rounded.ChevronRight, contentDescription = "Sonraki gün")
                }
            }
        }
        if (subjects.isEmpty()) {
            item {
                Text("Ders listesi hazırlanıyor...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        items(subjects, key = { it.id }) { s ->
            SubjectEntryCard(
                subject = s,
                log = byId[s.id],
                coef = coef,
                onAdjust = { field, delta -> vm.adjust(s.id, field, delta) },
                onBulk = { bulkFor = s }
            )
        }
        item {
            Text(
                "Net = Doğru − Yanlış × ${coef}. Katsayıyı Ayarlar sekmesinden değiştirebilirsin.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    bulkFor?.let { s ->
        BulkDialog(
            title = s.name,
            onDismiss = { bulkFor = null },
            onConfirm = { c, w, e ->
                vm.addBulk(s.id, c, w, e)
                bulkFor = null
            }
        )
    }
}

@Composable
private fun SubjectEntryCard(
    subject: Subject,
    log: QuestionLog?,
    coef: Double,
    onAdjust: (Int, Int) -> Unit,
    onBulk: () -> Unit
) {
    val c = log?.correct ?: 0
    val w = log?.wrong ?: 0
    val e = log?.empty ?: 0
    val total = c + w + e
    val net = c - w * coef

    AppCard(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(subject.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text("Net ${fmt(net)}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Text("Toplam $total soru", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Counter("Doğru", c, GoodColor, { onAdjust(0, -1) }, { onAdjust(0, 1) })
            Counter("Yanlış", w, BadColor, { onAdjust(1, -1) }, { onAdjust(1, 1) })
            Counter("Boş", e, NeutralColor, { onAdjust(2, -1) }, { onAdjust(2, 1) })
        }
        Spacer(Modifier.height(4.dp))
        TextButton(onClick = onBulk) { Text("Toplu giriş") }
    }
}

@Composable
private fun Counter(label: String, value: Int, color: Color, onMinus: () -> Unit, onPlus: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = color, fontWeight = FontWeight.SemiBold)
        Text("$value", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            FilledTonalIconButton(onClick = onMinus, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Rounded.Remove, contentDescription = "$label azalt")
            }
            Spacer(Modifier.width(8.dp))
            FilledTonalIconButton(onClick = onPlus, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Rounded.Add, contentDescription = "$label artır")
            }
        }
    }
}

@Composable
private fun BulkDialog(title: String, onDismiss: () -> Unit, onConfirm: (Int, Int, Int) -> Unit) {
    var c by remember { mutableStateOf("") }
    var w by remember { mutableStateOf("") }
    var e by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Toplu giriş · $title") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Girdiğin sayılar seçili günün mevcut değerlerine eklenir.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                NumField("Doğru", c) { c = it }
                NumField("Yanlış", w) { w = it }
                NumField("Boş", e) { e = it }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(c.toIntOrNull() ?: 0, w.toIntOrNull() ?: 0, e.toIntOrNull() ?: 0) }) { Text("Ekle") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Vazgeç") } }
    )
}

@Composable
private fun NumField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it.filter(Char::isDigit).take(5)) },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

// ------------------------------------------------------------------ İstatistik

@Composable
private fun StatsTab(vm: QuestionsViewModel) {
    val range by vm.range.collectAsStateWithLifecycle()
    val totals by vm.totalsList.collectAsStateWithLifecycle()
    val bySubject by vm.bySubject.collectAsStateWithLifecycle()
    val allSubjects by vm.allSubjects.collectAsStateWithLifecycle()
    val last14 by vm.last14.collectAsStateWithLifecycle()
    val coef by vm.coefficient.collectAsStateWithLifecycle()

    val names = allSubjects.associate { it.id to "${it.name} (${it.exam})" }
    val sel = totals[range]
    val total = sel.correct + sel.wrong + sel.empty
    val pc = if (total == 0) 0 else sel.correct * 100 / total
    val pw = if (total == 0) 0 else sel.wrong * 100 / total
    val pe = if (total == 0) 0 else sel.empty * 100 / total
    val net = sel.correct - sel.wrong * coef
    val rows = bySubject
        .filter { it.correct + it.wrong + it.empty > 0 }
        .sortedByDescending { it.correct + it.wrong + it.empty }
    val today = LocalDate.now()
    val dayLabels = (0..13).map { today.minusDays((13 - it).toLong()).dayOfMonth.toString() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RangeLabels.forEachIndexed { i, label ->
                    val t = totals[i]
                    AppCard(
                        modifier = Modifier.weight(1f),
                        onClick = { vm.setRange(i) },
                        containerColor = if (range == i) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surface,
                        contentPadding = 10.dp
                    ) {
                        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        Text("${t.correct + t.wrong + t.empty}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Text("${RangeLabels[range]} · Başarı Oranı", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                StackedBar(sel.correct, sel.wrong, sel.empty)
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Doğru %$pc", color = GoodColor, style = MaterialTheme.typography.labelLarge)
                    Text("Yanlış %$pw", color = BadColor, style = MaterialTheme.typography.labelLarge)
                    Text("Boş %$pe", color = NeutralColor, style = MaterialTheme.typography.labelLarge)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    "Toplam $total soru · ${sel.correct} D / ${sel.wrong} Y / ${sel.empty} B · Net ${fmt(net)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            AppCard(Modifier.fillMaxWidth()) {
                Text("Son 14 Gün (Günlük Soru)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(12.dp))
                MiniBarChart(values = last14, labels = dayLabels)
            }
        }
        item {
            Text("Ders Bazında", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        if (rows.isEmpty()) {
            item {
                Text(
                    "Bu aralıkta soru kaydı yok.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        items(rows, key = { it.subjectId }) { r ->
            val t = r.correct + r.wrong + r.empty
            val n = r.correct - r.wrong * coef
            val p = if (t == 0) 0 else r.correct * 100 / t
            AppCard(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(names[r.subjectId] ?: "Ders", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Text("Net ${fmt(n)}", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                StackedBar(r.correct, r.wrong, r.empty)
                Spacer(Modifier.height(6.dp))
                Text(
                    "$t soru · ${r.correct} D / ${r.wrong} Y / ${r.empty} B · Doğru %$p",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
