package me.vmpay.myachievements.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [User::class, Achievement::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun achievementDao(): AchievementDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "sample.db"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // moving to a new thread
                        GlobalScope.launch(Dispatchers.IO) {
                            getInstance(context).apply {
                                userDao().insertAll(PREPOPULATE_USERS_DATA)
                                achievementDao().insertAll(PREPOPULATE_ACHIEVEMENTS_DATA)
                            }
                        }
                    }
                })
                .build()
        }

        private val PREPOPULATE_USERS_DATA: List<User> = listOf(
            User(
                "j.smith@yahoo.com", "John Smith", "https://avatars2.githubusercontent.com/u/4482555?s=400&v=4",
                1540376651, 1540376651, UserAddress("Poland", "Warsaw", "00-150", "Nowolipki", "37", "12")
            ),
            User(
                "t.johns@yahoo.com", "Tommy Johns", "https://avatars2.githubusercontent.com/u/4482555?s=400&v=4",
                1540376651, 1540376651, UserAddress("Poland", "Warsaw", "00-150", "Nowolipki", "37", "12")
            )
        )

        private val PREPOPULATE_ACHIEVEMENTS_DATA: List<Achievement> = listOf(
            Achievement(
                UUID.randomUUID().toString(),
                "j.smith@yahoo.com",
                "Discover Room",
                "Read about Room persistence library",
                "Completed",
                1540377150,
                1540388150,
                1540399150,
                1540399150
            ),
            Achievement(
                UUID.randomUUID().toString(),
                "j.smith@yahoo.com",
                "Use Room",
                "Creat an app using Room",
                "Completed",
                1540499150,
                1540499150,
                1540501150,
                1540501150
            ),
            Achievement(
                UUID.randomUUID().toString(),
                "t.johns@yahoo.com",
                "Discover Room",
                "Ask John about Room",
                "Completed",
                1540377150,
                1540399150,
                1540499150,
                1540499150
            )
        )
    }
}