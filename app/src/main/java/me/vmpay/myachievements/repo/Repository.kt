package me.vmpay.myachievements.repo

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    fun getUserByEmail(email: String): LiveData<User> {
        return userDao.getByEmail(email)
    }

    fun getUsersAchievement(): LiveData<List<UserAchievements>> {
        return userDao.getUsersAchievement()
    }

    fun getUsersAchievement(email: String): LiveData<List<UserAchievements>> {
        return userDao.getUsersAchievement(email)
    }

    fun insertAchievement(achievement: Achievement): Job {
        return GlobalScope.launch(Dispatchers.IO) { achievementDao.insert(achievement) }
    }

    fun getAchievementByEmail(email: String): LiveData<List<Achievement>> {
        return achievementDao.getByEmail(email)
    }
}