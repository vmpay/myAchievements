package me.vmpay.myachievements

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import me.vmpay.myachievements.repo.*

class LoginActivity : AppCompatActivity() {

    private var repo: Repository? = null

    private var userEntity: LiveData<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getInstance(applicationContext)
        repo = Repository.getInstance(db.userDao(), db.achievementDao())

        userEntity = repo?.getUserByEmail("t.johns2@yahoo.com")
        userEntity?.observe(this, Observer<User?> { Log.d("TAG", "user $it") })
        repo?.getUsersAchievement()?.observe(this, Observer<List<UserAchievements>> {
            for (item in it) {
                Log.d("TAG", "item $item")
            }
        })

        val user = User(
            "t.johns2@yahoo.com", "Tommy Johns", "https://avatars2.githubusercontent.com/u/4482555?s=400&v=4",
            1540376651, 1540376651, UserAddress("Poland", "Warsaw", "00-150", "Nowolipki", "37", "12")
        )
        val updatedUser = User(
            "t.johns2@yahoo.com", "Tommy Lee Johns", "https://avatars2.githubusercontent.com/u/4482555?s=400&v=4",
            1540376651, 1540376651, UserAddress("Poland", "Warsaw", "00-150", "Nowolipki", "37", "12")
        )
        findViewById<Button>(R.id.btn_insert).setOnClickListener { repo?.insertUser(user) }
        findViewById<Button>(R.id.btn_update).setOnClickListener { repo?.updateUser(updatedUser) }
        findViewById<Button>(R.id.btn_delete).setOnClickListener { repo?.deleteUser(user) }
    }
}
