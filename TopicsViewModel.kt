package com.yksakademi.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

data class Totals(val correct: Int, val wrong: Int, val empty: Int)

data class SubjectTotals(val subjectId: Long, val correct: Int, val wrong: Int, val empty: Int)

@Dao
interface TopicDao {
    @Query("SELECT COUNT(*) FROM subject")
    suspend fun subjectCount(): Int

    @Insert
    suspend fun insertSubject(subject: Subject): Long

    @Insert
    suspend fun insertTopics(topics: List<Topic>)

    @Insert
    suspend fun insertTopic(topic: Topic): Long

    @Update
    suspend fun updateTopic(topic: Topic)

    @Update
    suspend fun updateSubject(subject: Subject)

    @Delete
    suspend fun deleteTopic(topic: Topic)

    @Query("DELETE FROM review_item WHERE topicId = :topicId")
    suspend fun deleteReviewsOfTopic(topicId: Long)

    @Query("DELETE FROM review_item WHERE topicId IN (SELECT id FROM topic WHERE subjectId = :subjectId)")
    suspend fun deleteReviewsOfSubject(subjectId: Long)

    @Query("DELETE FROM topic WHERE subjectId = :subjectId")
    suspend fun deleteTopicsOfSubject(subjectId: Long)

    @Query("DELETE FROM question_log WHERE subjectId = :subjectId")
    suspend fun deleteLogsOfSubject(subjectId: Long)

    @Query("DELETE FROM subject WHERE id = :subjectId")
    suspend fun deleteSubjectById(subjectId: Long)

    @Query("SELECT * FROM subject WHERE exam = :exam ORDER BY orderIndex, id")
    fun subjects(exam: String): Flow<List<Subject>>

    @Query("SELECT t.* FROM topic t INNER JOIN subject s ON t.subjectId = s.id WHERE s.exam = :exam ORDER BY t.id")
    fun topics(exam: String): Flow<List<Topic>>

    @Query("SELECT COALESCE(MAX(orderIndex), 0) FROM subject WHERE exam = :exam")
    suspend fun maxOrder(exam: String): Int
}

@Dao
interface QuestionDao {
    @Query("SELECT * FROM question_log WHERE day = :day")
    fun logsOn(day: Long): Flow<List<QuestionLog>>

    @Query("SELECT * FROM question_log WHERE subjectId = :subjectId AND day = :day LIMIT 1")
    suspend fun find(subjectId: Long, day: Long): QuestionLog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(log: QuestionLog)

    @Query("SELECT COALESCE(SUM(correct), 0) AS correct, COALESCE(SUM(wrong), 0) AS wrong, COALESCE(SUM(empty), 0) AS empty FROM question_log WHERE day BETWEEN :from AND :to")
    fun totals(from: Long, to: Long): Flow<Totals>

    @Query("SELECT subjectId AS subjectId, SUM(correct) AS correct, SUM(wrong) AS wrong, SUM(empty) AS empty FROM question_log WHERE day BETWEEN :from AND :to GROUP BY subjectId")
    fun bySubject(from: Long, to: Long): Flow<List<SubjectTotals>>

    @Query("SELECT day AS day, SUM(correct + wrong + empty) AS total FROM question_log WHERE day BETWEEN :from AND :to GROUP BY day")
    fun byDay(from: Long, to: Long): Flow<List<DayTotal>>

    @Query("SELECT * FROM subject ORDER BY exam, orderIndex, id")
    fun allSubjects(): Flow<List<Subject>>
}
