package com.yksakademi.app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Assessment
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Quiz
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.Today
import androidx.compose.ui.graphics.vector.ImageVector

object Routes {
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val TOPICS = "topics"
    const val QUESTIONS = "questions"
    const val TIMER = "timer"
    const val EXAMS = "exams"
    const val DAILY = "daily"
    const val WEEKLY = "weekly"
    const val REVIEW = "review"
    const val CHOICES = "choices"
    const val STATS = "stats"
}

/** Henüz geliştirilmemiş modüller için yer tutucu ekran bilgisi. */
data class ModuleInfo(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val phase: String,
    val description: String
)

val quickAccessModules = listOf(
    ModuleInfo(Routes.TOPICS, "Konu Takibi", Icons.Rounded.MenuBook, "Aşama 2",
        "TYT, AYT ve YDT konu listeleri, durum işaretleme, kendi konunu ekleme ve ders bazlı tamamlanma yüzdesi."),
    ModuleInfo(Routes.QUESTIONS, "Soru Takibi", Icons.Rounded.Quiz, "Aşama 2",
        "Ders bazlı doğru/yanlış/boş girişi, artı-eksi butonları, toplu giriş ve net hesabı."),
    ModuleInfo(Routes.TIMER, "Süre Takibi", Icons.Rounded.Timer, "Aşama 3",
        "Kronometre, Pomodoro (25/5) ve 50/10 modları, çalışma oturumu kaydı."),
    ModuleInfo(Routes.EXAMS, "Deneme Takibi", Icons.Rounded.Assessment, "Aşama 4",
        "TYT/AYT/YDT deneme arşivi, ders bazlı netler ve net değişim grafikleri."),
    ModuleInfo(Routes.DAILY, "Günlük Program", Icons.Rounded.Today, "Aşama 3",
        "Ders, konu, hedef soru, hedef süre ve başlama saati olan günlük görevler."),
    ModuleInfo(Routes.WEEKLY, "Haftalık Program", Icons.Rounded.DateRange, "Aşama 3",
        "Pazartesi-Pazar planı, haftalık hedefler ve hafta sonu performans raporu."),
    ModuleInfo(Routes.REVIEW, "Tekrar Merkezi", Icons.Rounded.Repeat, "Aşama 3",
        "1-3-7-14-30 günlük aralıklı tekrar takvimi; bugünkü ve geciken tekrarlar."),
    ModuleInfo(Routes.CHOICES, "Tercih Listesi", Icons.Rounded.School, "Aşama 4",
        "Üniversite/bölüm tercihlerini manuel girerek sıralama. Veriler doğrulanmış gibi gösterilmez."),
    ModuleInfo(Routes.STATS, "İstatistikler", Icons.Rounded.BarChart, "Aşama 4",
        "Soru, süre, başarı oranı ve ilerleme grafikleri ile performans raporları.")
)

fun moduleFor(route: String): ModuleInfo? = quickAccessModules.firstOrNull { it.route == route }
