package bot

import com.pengrad.telegrambot.model.Update

interface BotImpl {
    fun listen ()
    fun process(update: Update)
}