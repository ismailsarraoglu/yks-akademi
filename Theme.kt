package com.yksakademi.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yksakademi.app.data.AppDatabase
import com.yksakademi.app.data.Profile
import com.yksakademi.app.data.Seeder
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.get(app)
    private val profileDao = db.profileDao()
    private val stats = db.statsDao()

    /** Uygulama açıldığındaki gün (gece yarısını geçerse uygulamayı yeniden açmak gerekir). */
    private val today: Long = LocalDate.now().toEpochDay()

    private fun <T> kotlinx.coroutines.flow.Flow<T>.state(initial: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initial)

    val profile: StateFlow<Profile?> = profileDao.observe().state(null)

    val todayQuestions: StateFlow<Int> = stats.questionsOn(today).state(0)
    val todayStudySeconds: StateFlow<Int> = stats.studySecondsOn(today).state(0)
    val weekStudySeconds: StateFlow<Int> = stats.studySecondsBetween(today - 6, today).state(0)
    val completedTopics: StateFlow<Int> = stats.completedTopics().state(0)
    val totalTopics: StateFlow<Int> = stats.totalTopics().state(0)
    val lastExamNet: StateFlow<Double?> = stats.lastExamNet().state(null)
    val reviewsDueToday: StateFlow<Int> = stats.reviewsDueOn(today).state(0)
    val reviewsOverdue: StateFlow<Int> = stats.reviewsOverdueBefore(today).state(0)

    /** (tamamlanan, toplam) */
    val planProgress: StateFlow<Pair<Int, Int>> =
        combine(stats.doneTasksOn(today), stats.tasksOn(today)) { done, total -> done to total }
            .state(0 to 0)

    /** Son 7 günün (bugün dahil) günlük soru sayıları, eskiden yeniye. */
    val weekQuestions: StateFlow<List<Int>> =
        stats.questionsByDay(today - 6, today).map { list ->
            val byDay = list.associate { it.day to it.total }
            (0..6).map { i -> byDay[today - 6 + i] ?: 0 }
        }.state(List(7) { 0 })

    init {
        viewModelScope.launch {
            if (profileDao.get() == null) profileDao.upsert(Profile())
            Seeder.seedIfEmpty(db)
        }
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch { profileDao.upsert(profile) }
    }
}
