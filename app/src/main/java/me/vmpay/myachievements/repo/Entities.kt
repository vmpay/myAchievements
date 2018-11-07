package me.vmpay.myachievements.repo

import androidx.room.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
    @ColumnInfo(name = "reg_timestamp")
    val registrationTimestamp: Long,
    @ColumnInfo(name = "last_login_timestamp")
    val lastLoginTimestamp: Long,
    @Embedded
    val address: UserAddress
)

data class UserAddress(
    val country: String,
    val city: String,
    val index: String,
    val street: String,
    val house: String,
    val apartments: String
)

@Entity(
    tableName = "achievements",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("email"),
        childColumns = arrayOf("owner")
    )]
)
data class Achievement(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "owner")
    val owner: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "creation_timestamp")
    val creationTimestamp: Long,
    @ColumnInfo(name = "start_timestamp")
    val startTimestamp: Long,
    @ColumnInfo(name = "finish_timestamp")
    val finishTimestamp: Long,
    @ColumnInfo(name = "completion_timestamp")
    val completionTimestamp: Long
)

data class UserAchievements(
    @Embedded
    val user: User,
    @Relation(parentColumn = "email", entityColumn = "owner")
    val achievementList: List<Achievement>
)

data class UserMinimal(
    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String
)