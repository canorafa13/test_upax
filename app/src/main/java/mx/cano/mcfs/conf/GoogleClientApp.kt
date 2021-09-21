package mx.cano.mcfs.conf

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import mx.cano.mcfs.BuildConfig

class GoogleClientApp(val context: Context) {
    private val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.DEFAULT_WEB_CLIENT_ID)
        .requestId()
        .requestEmail()
        .requestProfile()
        .build()

    fun getGoogleClient(): GoogleSignInClient{
        return GoogleSignIn.getClient(context, googleConf)
    }
}