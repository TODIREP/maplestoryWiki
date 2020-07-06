package app.jaebyoung.maplestorywiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    private val DELAY: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val slime = findViewById<ImageView>(R.id.splash_slime)
        val mushroom = findViewById<ImageView>(R.id.splash_mushroom)

        Glide.with(this).load(R.drawable.slime).into(slime)
        Glide.with(this).load(R.drawable.mushroom).into(mushroom)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, DELAY)
    }

    override fun onBackPressed() {

    }
}