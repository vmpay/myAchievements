package me.vmpay.myachievements

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.vmpay.myachievements.repo.AppDatabase
import me.vmpay.myachievements.repo.Repository
import me.vmpay.myachievements.repo.User
import me.vmpay.myachievements.repo.UserAddress

class LoginActivity : AppCompatActivity() {

    private var repo: Repository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getInstance(applicationContext)
        repo = Repository.getInstance(db.userDao(), db.achievementDao())

        val user = User(
            "t.johns2@yahoo.com", "Tommy Johns", "https://avatars2.githubusercontent.com/u/4482555?s=400&v=4",
            1540376651, 1540376651, UserAddress("Poland", "Warsaw", "00-150", "Nowolipki", "37", "12")
        )
        val updatedUser = User(
            "t.johns2@yahoo.com", "Tommy Lee Johns", "https://avatars2.githubusercontent.com/u/4482555?s=400&v=4",
            1540376651, 1540376651, UserAddress("Poland", "Warsaw", "00-150", "Nowolipki", "37", "12")
        )
        findViewById<Button>(R.id.btn_insert).setOnClickListener { repo?.insertUser(user) }
        findViewById<Button>(R.id.btn_select).setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val userAchievementList = repo?.getUsersAchievement()
                if (userAchievementList != null) {
                    for (item in userAchievementList) {
                        Log.d("TAG", item.toString())
                    }
                }
            }
        }
        findViewById<Button>(R.id.btn_update).setOnClickListener { repo?.updateUser(updatedUser) }
        findViewById<Button>(R.id.btn_delete).setOnClickListener { repo?.deleteUser(user) }
    }
}
