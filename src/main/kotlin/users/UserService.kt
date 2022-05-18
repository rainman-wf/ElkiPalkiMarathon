package users

import bot.BotService

object UserService {

    private val users = mutableMapOf<Long, User>()

    fun getUser (userID: Long) = users[userID]

    fun addUser (userID: Long) = users.put(userID, User(userID, if (BotService.dayLevel != 0) -1 else 0))

    fun levelUp(userID: Long) {
        val user = users[userID]
        val level = user?.level ?: return
        val newUser = user.copy(level = level + 1)
        users[userID] = newUser
    }

    fun removeUser (userID: Long) {
        users.remove(userID)
    }

    fun getAll () = users
}