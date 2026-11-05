package com.yksakademi.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

data class DayTotal(val day: Long, val total: Int)

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE id = 1 LIMIT 1")
    fun observe(): Flow<Profile?>

    @Query("SELECT * FROM profile WHERE id = 1 LIMIT 1")
    suspend fun get(): Profile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: Profile)
}

/** Ana sayfa için salt okunur özet sorguları. */
@Dao
interface StatsDao {
    @Query("SELECT COALESCE(SUM(correct + wrong + empty), 0) FROM question_log WHERE day = :day")
    fun questionsOn(day: Long): Flow<Int>

    @Query("SELECT day AS day, SUM(correct + wrong + empty) AS total FROM question_log WHERE day BETWEEN :from AND :to GROUP BY day")
    fun questionsByDay(from: Long, to: Long): Flow<List<DayTotal>>

    @Query("SELECT COALESCE(SUM(durationSeconds), 0) FROM study_session WHERE day = :day")
    fun studySecondsOn(day: Long): Flow<Int>

    @Query("SELECT COALESCE(SUM(durationSeconds), 0) FROM study_session WHERE day BETWEEN :from AND :to")
    fun studySecondsBetween(from: Long, to: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM topic WHERE status = 4")
    fun completedTopics(): Flow<Int>

    @Query("SELECT COUNT(*) FROM topic")
    fun totalTopics(): Flow<Int>

    @Query("SELECT totalNet FROM exam ORDER BY day DESC, id DESC LIMIT 1")
    fun lastExamNet(): Flow<Double?>

    @Query("SELECT COUNT(*) FROM daily_task WHERE day = :day")
    fun tasksOn(day: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM daily_task WHERE day = :day AND done = 1")
    fun doneTasksOn(day: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM review_item WHERE done = 0 AND dueDay = :day")
    fun reviewsDueOn(day: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM review_item WHERE done = 0 AND dueDay < :day")
    fun reviewsOverdueBefore(day: Long): Flow<Int>
}
