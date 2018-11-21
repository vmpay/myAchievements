package me.vmpay.myachievements.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import me.vmpay.myachievements.getDistinct

@Dao
abstract class AchievementDao : BaseDao<Achievement> {
    @Query("SELECT * FROM achievements WHERE owner = :email")
    abstract fun getByEmail(email: String): LiveData<List<Achievement>>
}

@Dao
abstract class UserDao : BaseDao<User> {
    @Query("SELECT * FROM users WHERE email = :email")
    abstract fun getByEmail(email: String): LiveData<User>

    @Query("SELECT email, display_name, photo_url FROM users WHERE email = :email")
    abstract fun getMinimalByEmail(email: String): LiveData<UserMinimal>

    @Transaction
    @Query("SELECT * FROM users")
    protected abstract fun getUsersAchievement(): LiveData<List<UserAchievements>>

    @Transaction
    @Query("SELECT * FROM users WHERE email = :email ")
    abstract fun getUsersAchievement(email: String): LiveData<List<UserAchievements>>

    fun getDistinctUsersAchievement(): LiveData<List<UserAchievements>> =
        getUsersAchievement().getDistinct()

    @Transaction
    open fun updateUsers(users: List<User>) {
        deleteAll()
        insertAll(users)
    }

    @Query("DELETE FROM users")
    abstract fun deleteAll()
}