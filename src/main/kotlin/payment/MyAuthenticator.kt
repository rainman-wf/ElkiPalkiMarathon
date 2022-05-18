package payment;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

object MyAuthenticator :Authenticator() {

    val SHOP_ID = "849431";
    val API_KEY = "live_MFyS3bG3pU3fjSwdtZSl0DgJ8vWuqk5S6jgB-nTJgM0";

    override fun  getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(SHOP_ID, API_KEY.toCharArray());
    }
}