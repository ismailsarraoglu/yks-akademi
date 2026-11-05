package com.yksakademi.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Profile::class,
        Subject::class,
        Topic::class,
        QuestionLog::class,
        StudySession::class,
        Exam::class,
        ExamResult::class,
        DailyTask::class,
        WeeklyPlanItem::class,
        ReviewItem::class,
        UniversityChoice::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun statsDao(): StatsDao
    abstract fun topicDao(): TopicDao
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yks_akademi.db"
                ).build().also { instance = it }
            }
    }
}
