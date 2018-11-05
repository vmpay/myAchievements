package me.vmpay.myachievements.repo

import kotlinx.coroutines.*

class Repository private constructor(private val userDao: UserDao, private val achievementDao: AchievementDao) {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: Repository? = null

        fun getInstance(userDao: UserDao, achievementDao: AchievementDao): Repository? {
            return instance ?: synchronized(this) {
                instance ?: Repository(userDao, achievementDao).also { instance = it }
            }
        }
    }

    fun insertUser(user: User): Job {
        return GlobalScope.launch(Dispatchers.IO) { userDao.insert(user) }
    }

    fun updateUser(user: User): Job {
        return GlobalScope.launch(Dispatchers.IO) { userDao.update(user) }
    }

    fun deleteUser(user: User): Job {
        return GlobalScope.launch(Dispatchers.IO) { userDao.delete(user) }
    }

    suspend fun getUserByEmail(email: String): User? {
        return GlobalScope.async {
            userDao.getByEmail(email)
        }.await()
    }

    suspend fun getUsersAchievement(): List<UserAchievements> {
        return GlobalScope.async {
            userDao.getUsersAchievement()
        }.await()
    }

    suspend fun getUsersAchievement(email: String): List<UserAchievements> {
        return GlobalScope.async {
            userDao.getUsersAchievement(email)
        }.await()
    }

    fun insertAchievement(achievement: Achievement): Job {
        return GlobalScope.launch(Dispatchers.IO) { achievementDao.insert(achievement) }
    }

    suspend fun getAchievementByEmail(email: String): List<Achievement> {
        return GlobalScope.async {
            achievementDao.getByEmail(email)
        }.await()
    }
}