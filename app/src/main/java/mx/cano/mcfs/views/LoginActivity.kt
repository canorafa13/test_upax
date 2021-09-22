package mx.cano.mcfs.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import mx.cano.mcfs.conf.GoogleClientApp
import mx.cano.mcfs.databinding.ActivityLoginBinding
import mx.cano.mcfs.storage.StorageSession

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (StorageSession.EMAIL.get(this) != ""){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }else{
            binding.login.setOnClickListener {
                loginGoogle()
            }
        }
    }

    private fun loginGoogle(){
        val googleClient = GoogleClientApp(this).getGoogleClient()
        googleClient.signOut()

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val account = task.getResult(ApiException::class.java)
                    StorageSession.ID.save(this, account.id)
                    StorageSession.NAME.save(this, account.displayName)
                    StorageSession.EMAIL.save(this, account.email)
                    if (account.photoUrl != null) {
                        StorageSession.PHOTO_URL.save(this, account.photoUrl.toString())
                    } else {
                        StorageSession.PHOTO_URL.save(this, "")
                    }
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }catch (e: ApiException){
                    e.printStackTrace()
                    showError()
                }
            }else{
                showError()
            }
        }.launch(googleClient.signInIntent)
    }


    private fun showError(){
        GoogleClientApp(this).getGoogleClient().signOut()
        Toast.makeText(this, "No se ha iniciado sesión", Toast.LENGTH_LONG).show()
    }
}