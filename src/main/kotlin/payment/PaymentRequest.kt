package payment;

import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    val amount: Amount,
    val capture: Boolean,
    val payment_method_data: PaymentMethodData,
    val confirmation: Confirmation,
    val description: String
)

@Serializable
data class Amount(val value: String, val currency: String)

@Serializable
data class Confirmation(val type: String, val return_url: String)

@Serializable
data class PaymentMethodData (val type: String)