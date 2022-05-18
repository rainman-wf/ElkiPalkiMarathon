package utils

import bot.*
import java.util.*
import users.UserService.getAll

object First : TimerTask() {
    override fun run() {
        BotService.dayLevel = 1
        getAll()
            .filter { it.value.level in 0 .. 4 }
            .forEach { firstMsg(it.key) }
    }
}

object Second : TimerTask() {

    override fun run() {
        BotService.dayLevel = 2
        getAll()
            .filter { it.value.level in 0 .. 4  }
            .forEach { secondMsg(it.key) }
    }
}

object Fourth : TimerTask() {
    override fun run() {
        BotService.dayLevel = 0
        getAll()
            .filter { it.value.level in 0 .. 4 }
            .forEach {
                fourthMsg(it.key)
            }
    }
}

object Fifth : TimerTask() {
    override fun run() {
        getAll()
            .filter { it.value.level == 5 }
            .forEach {
                fifthMsg(it.key)
            }
    }
}

object Sixth : TimerTask() {
    override fun run() {
        getAll()
            .filter { it.value.level == 5 }
            .forEach {
                sixthMsg(it.key)
            }
    }
}

object Seventh : TimerTask() {
    override fun run() {
        getAll()
            .filter { it.value.level == 5 }
            .forEach {
                seventhMsg(it.key)
            }
    }
}

object Eight : TimerTask() {
    override fun run() {
        getAll()
            .filter { it.value.level == 5 }
            .forEach {
                eighthMsg(it.key)
            }
    }
}
