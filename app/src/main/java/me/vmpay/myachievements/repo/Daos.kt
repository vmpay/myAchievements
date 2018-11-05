package me.vmpay.myachievements.repo

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class AchievementDao : BaseDao<Achievement> {
    @Query("SELECT * FROM achievements WHERE owner = :email")
    abstract fun getByEmail(email: String): List<Achievement>
}

@Dao
abstract class UserDao : BaseDao<User> {
    @Query("SELECT * FROM users WHERE email = :email")
    abstract fun getByEmail(email: String): User

    @Transaction
    @Query("SELECT * FROM users")
    abstract fun getUsersAchievement(): List<UserAchievements>

    @Transaction
    @Query("SELECT * FROM users WHERE email = :email ")
    abstract fun getUsersAchievement(email: String): List<UserAchievements>
}