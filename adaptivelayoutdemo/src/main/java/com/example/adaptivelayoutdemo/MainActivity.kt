package com.example.adaptivelayoutdemo

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var animator : ObjectAnimator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_reset.setOnClickListener {
            root.init()
        }

        btn_start.setOnClickListener {
            /*if (animator == null) {
                animator = ObjectAnimator.ofFloat(bg, "rotation", 0f, 360f)
                animator?.repeatCount = ValueAnimator.INFINITE
                animator?.duration = 5000L
                animator?.start()
            }else{
                animator?.resume()
            }*/
            bg.animate()
                    .setInterpolator(LinearInterpolator())
                    .rotationBy(36000f).setDuration(500000L)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            Log.w("WWS", "onAnimationEnd")
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                            Log.w("WWS", "onAnimationCancel")
                        }

                        override fun onAnimationStart(animation: Animator?) {
                        }

                    })
                    .start()
        }

        btn_cancel.setOnClickListener {
            bg.animate().cancel()
//            animator?.pause()
        }
    }
}
