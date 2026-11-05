package com.yksakademi.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

object TopicStatus {
    const val NOT_STARTED = 0
    const val STUDYING = 1
    const val LEARNED = 2
    const val NEEDS_REVIEW = 3
    const val COMPLETED = 4
}

/** Tek satırlık öğrenci profili ve ayarlar (id her zaman 1). */
@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey val id: Int = 1,
    val name: String = "",
    val targetUniversity: String = "",
    val targetDepartment: String = "",
    val examYear: Int = 2027,
    /** yyyy-MM-dd. Tahmini değerdir; ÖSYM takvimine göre kullanıcı düzenlemelidir. */
    val examDate: String = "2027-06-19",
    val dailyQuestionGoal: Int = 100,
    val dailyStudyMinutesGoal: Int = 300,
    val weeklyQuestionGoal: Int = 700,
    val weeklyStudyMinutesGoal: Int = 2100,
    /** Net = doğru - yanlış * katsayı (varsayılan 1/4). */
    val wrongCoefficient: Double = 0.25,
    /** 0 = sistem, 1 = açık, 2 = koyu */
    val themeMode: Int = 0
)

/** exam: "TYT", "AYT" veya "YDT" */
@Entity(tableName = "subject")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val exam: String,
    val orderIndex: Int = 0,
    val isCustom: Boolean = false
)

@Entity(tableName = "topic")
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Long,
    val name: String,
    val status: Int = TopicStatus.NOT_STARTED,
    val lastStudiedDay: Long? = null,
    val solvedQuestions: Int = 0,
    val reviewCount: Int = 0,
    val notes: String = "",
    val isCustom: Boolean = false
)

@Entity(tableName = "question_log")
data class QuestionLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Long,
    /** LocalDate.toEpochDay() */
    val day: Long,
    val correct: Int = 0,
    val wrong: Int = 0,
    val empty: Int = 0
)

@Entity(tableName = "study_session")
data class StudySession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Long? = null,
    val topicId: Long? = null,
    val day: Long,
    val startMillis: Long,
    val durationSeconds: Int,
    /** "SERBEST", "POMODORO" veya "50-10" */
    val mode: String = "SERBEST"
)

@Entity(tableName = "exam")
data class Exam(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val publisher: String = "",
    val day: Long,
    val type: String,
    val notes: String = "",
    val totalNet: Double = 0.0
)

@Entity(tableName = "exam_result")
data class ExamResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val examId: Long,
    val subjectName: String,
    val correct: Int = 0,
    val wrong: Int = 0,
    val empty: Int = 0,
    val net: Double = 0.0
)

@Entity(tableName = "daily_task")
data class DailyTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val day: Long,
    val subjectName: String,
    val topicName: String = "",
    val targetQuestions: Int = 0,
    val targetMinutes: Int = 0,
    /** HH:mm */
    val startTime: String = "",
    val done: Boolean = false
)

@Entity(tableName = "weekly_plan_item")
data class WeeklyPlanItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** 1 = Pazartesi ... 7 = Pazar */
    val dayOfWeek: Int,
    val subjectName: String,
    val topicName: String = "",
    val targetQuestions: Int = 0,
    val targetMinutes: Int = 0
)

@Entity(tableName = "review_item")
data class ReviewItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topicId: Long,
    val dueDay: Long,
    val intervalDays: Int,
    val done: Boolean = false
)

/** Tüm alanlar kullanıcı tarafından manuel girilir; doğrulanmış veri iddiası yoktur. */
@Entity(tableName = "university_choice")
data class UniversityChoice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val university: String,
    val department: String,
    val city: String = "",
    val scoreType: String = "",
    val quota: String = "",
    val lastYearRank: String = "",
    val targetRank: String = "",
    val orderIndex: Int = 0
)
