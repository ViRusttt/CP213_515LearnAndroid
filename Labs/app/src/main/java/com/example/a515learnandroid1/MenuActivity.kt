package com.example.a515learnandroid1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ดึง View ออกมาใช้เป็น Base สำหรับ Scale / Clip Animation
            val view = LocalView.current 
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Custom Animation: Fade In/Out
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, Part1AnimationActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 1 (Fade In/Out)")
                }

                // 2. Custom Animation: Slide In Left / Slide Out Right
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.slide_out_right
                    )
                    startActivity(Intent(this@MenuActivity, Part2Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 2 (Slide Left/Right)")
                }

                // 3. Scale Up Animation
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeScaleUpAnimation(
                        view, 0, 0, view.width, view.height
                    )
                    startActivity(Intent(this@MenuActivity, Part3Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 3 (Scale Up)")
                }

                // 4. Clip Reveal Animation
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeClipRevealAnimation(
                        view, view.width / 2, view.height / 2, view.width, view.height
                    )
                    startActivity(Intent(this@MenuActivity, Part4Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 4 (Clip Reveal Center)")
                }

                // 5. Basic Default Transition
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeBasic()
                    startActivity(Intent(this@MenuActivity, Part5Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 5 (Basic Default)")
                }

                // 6. No Animation (0 for enter and exit resources)
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, 0, 0
                    )
                    startActivity(Intent(this@MenuActivity, Part6Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 6 (No Animation)")
                }

                // 6.5 Part 8: Responsive Design layout
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, Part8Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 8 (Responsive UI)")
                }

                // 6.6 Part 9: Collapsing Toolbar
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, Part9Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 9 (Collapsing Toolbar)")
                }

                // 6.7 Part 10: App Widget Concept
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, Part10Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 10 (App Widget Concept)")
                }

                // 6.8 Part 11: Skeleton Loading
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, Part11Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 11 (Skeleton Loading)")
                }

                // 6.9 Part 12: Dialog & Bottom Sheet
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, Part12Activity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Part 12 (Dialog & BottomSheet)")
                }

                // 7. Scale Up จากตำแหน่งกึ่งกลาง 
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeScaleUpAnimation(
                        view, view.width / 2, view.height / 2, 100, 100
                    )
                    startActivity(Intent(this@MenuActivity, RPGCardActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("RPG Card (Scale Up Center)")
                }

                // 8. Clip Reveal จากมุมซ้ายบน
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeClipRevealAnimation(
                        view, 0, 0, 100, 100
                    )
                    startActivity(Intent(this@MenuActivity, PokedexActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Pokedex (Clip Reveal Top-Left)")
                }

                // 9. Fade In เฉพาะขาเข้า
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, 0
                    )
                    startActivity(Intent(this@MenuActivity, LifeCycleComposeActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("LifeCycle (Fade In Only)")
                }

                // 10. Fade Out เฉพาะขากลับ (ขาเข้าไม่ต้องมี Effect)
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, 0, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Prefs (Fade Out Only)")
                }

                // 11. Custom Animation (Fade In + Slide Out Right combination)
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.fade_in, android.R.anim.slide_out_right
                    )
                    startActivity(Intent(this@MenuActivity, GalleryActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Gallery (Fade In / Slide Out)")
                }

                // 12. Slide In Left + Fade Out combination
                Button(onClick = {
                    val options = ActivityOptionsCompat.makeCustomAnimation(
                        this@MenuActivity, android.R.anim.slide_in_left, android.R.anim.fade_out
                    )
                    startActivity(Intent(this@MenuActivity, SensorActivity::class.java), options.toBundle())
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Sensor (Slide In / Fade Out)")
                }
            }
        }
    }
}
