package com.yksakademi.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yksakademi.app.data.Profile
import com.yksakademi.app.ui.AppCard
import com.yksakademi.app.ui.MainViewModel
import java.time.LocalDate

@Composable
fun SettingsScreen(vm: MainViewModel) {
    val profile by vm.profile.collectAsStateWithLifecycle()
    val p = profile
    if (p == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        SettingsContent(p) { vm.saveProfile(it) }
    }
}

@Composable
private fun SettingsContent(p: Profile, onSave: (Profile) -> Unit) {
    var name by remember { mutableStateOf(p.name) }
    var uni by remember { mutableStateOf(p.targetUniversity) }
    var dept by remember { mutableStateOf(p.targetDepartment) }
    var year by remember { mutableStateOf(p.examYear.toString()) }
    var date by remember { mutableStateOf(p.examDate) }
    var dq by remember { mutableStateOf(p.dailyQuestionGoal.toString()) }
    var dm by remember { mutableStateOf(p.dailyStudyMinutesGoal.toString()) }
    var wq by remember { mutableStateOf(p.weeklyQuestionGoal.toString()) }
    var wm by remember { mutableStateOf(p.weeklyStudyMinutesGoal.toString()) }
    var coef by remember { mutableStateOf(p.wrongCoefficient.toString()) }
    var theme by remember { mutableIntStateOf(p.themeMode) }
    var message by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Ayarlar", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        AppCard(Modifier.fillMaxWidth()) {
            Text("Profil ve Hedef", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Field("Ad", name) { name = it }
            Field("Hedef üniversite", uni) { uni = it }
            Field("Hedef bölüm", dept) { dept = it }
            Field("Hedef sınav yılı", year, number = true) { year = it }
            Field("Sınav tarihi (yyyy-AA-gg)", date) { date = it }
            Text(
                "Varsayılan tarih tahminidir. ÖSYM'nin açıkladığı resmi tarihe göre düzenle.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AppCard(Modifier.fillMaxWidth()) {
            Text("Hedefler", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Field("Günlük soru hedefi", dq, number = true) { dq = it }
            Field("Günlük çalışma hedefi (dk)", dm, number = true) { dm = it }
            Field("Haftalık soru hedefi", wq, number = true) { wq = it }
            Field("Haftalık çalışma hedefi (dk)", wm, number = true) { wm = it }
            Field("Yanlış katsayısı (varsayılan 0.25)", coef) { coef = it }
        }

        AppCard(Modifier.fillMaxWidth()) {
            Text("Tema", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = theme == 0, onClick = { theme = 0 }, label = { Text("Sistem") })
                FilterChip(selected = theme == 1, onClick = { theme = 1 }, label = { Text("Açık") })
                FilterChip(selected = theme == 2, onClick = { theme = 2 }, label = { Text("Koyu") })
            }
        }

        Button(
            onClick = {
                val parsedDate = runCatching { LocalDate.parse(date.trim()) }.getOrNull()
                val parsedCoef = coef.replace(',', '.').toDoubleOrNull()
                if (parsedDate == null) {
                    message = "Sınav tarihi yyyy-AA-gg biçiminde olmalı (örn. 2027-06-19)."
                } else if (parsedCoef == null || parsedCoef < 0.0) {
                    message = "Yanlış katsayısı geçerli bir sayı olmalı."
                } else {
                    onSave(
                        p.copy(
                            name = name.trim(),
                            targetUniversity = uni.trim(),
                            targetDepartment = dept.trim(),
                            examYear = year.toIntOrNull() ?: p.examYear,
                            examDate = parsedDate.toString(),
                            dailyQuestionGoal = dq.toIntOrNull() ?: p.dailyQuestionGoal,
                            dailyStudyMinutesGoal = dm.toIntOrNull() ?: p.dailyStudyMinutesGoal,
                            weeklyQuestionGoal = wq.toIntOrNull() ?: p.weeklyQuestionGoal,
                            weeklyStudyMinutesGoal = wm.toIntOrNull() ?: p.weeklyStudyMinutesGoal,
                            wrongCoefficient = parsedCoef,
                            themeMode = theme
                        )
                    )
                    message = "Kaydedildi."
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Kaydet") }

        if (message.isNotEmpty()) {
            Text(message, color = MaterialTheme.colorScheme.primary)
        }

        Text(
            "YKS AKADEMİ 0.1.0 (Aşama 1) · Veriler yalnızca bu cihazda saklanır.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun Field(label: String, value: String, number: Boolean = false, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = if (number) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    )
}
