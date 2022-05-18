package payment

import bot.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*


object Client {

    fun sendPaymentRequest(description: String?): Payment {
        val uuid = UUID.randomUUID().toString()
        val connection: HttpURLConnection = PAYMENT_API_URL.openConnection() as HttpURLConnection
        connection.apply {
            readTimeout = 3000
            connectTimeout = 3000
            requestMethod = "POST"
            setRequestProperty("Idempotence-Key", uuid)
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            doOutput = true
        }

        val paymentRequest = PaymentRequest(
            amount = Amount("5.00", "RUB"),
            capture = true,
            payment_method_data = PaymentMethodData("bank_card"),
            confirmation = Confirmation("redirect", "https://t.me/elkipalki_marathon_bot/"),
            description = description!!
        )

        DataOutputStream(connection.outputStream).use {
            val input: ByteArray = Json.encodeToString(paymentRequest).toByteArray(StandardCharsets.UTF_8)
            it.write(input, 0, input.size)
        }

        val stream: InputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
        val builder = StringBuilder()
        reader.lines().forEach { l: String -> builder.append(l.trim { it <= ' ' }) }
        var jo = Json.parseToJsonElement(builder.toString()).jsonObject
        val orderId = jo["id"].toString()
        val confirmation = jo["confirmation"].toString()
        jo = Json.parseToJsonElement(confirmation).jsonObject
        val confirmationUrl = jo["confirmation_url"].toString().replace("\"", "")
        println(confirmationUrl)
        connection.disconnect()
        return Payment(orderId, confirmationUrl)
    }

    fun getStatus(orderID: String): String {
        val url = URL(PAYMENT_API_URL.toString() + orderID)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")
        val stream: InputStream = connection.inputStream
        val reader = BufferedReader(InputStreamReader(stream))
        val builder = StringBuilder()
        var line: String
        while (reader.readLine().also { line = it } != null) builder.append(line.trim { it <= ' ' })
        val jo = Json.parseToJsonElement(builder.toString()).jsonObject
        connection.disconnect()
        return jo["status"].toString()
    }

}