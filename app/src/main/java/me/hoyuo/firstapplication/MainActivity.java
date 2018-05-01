package me.hoyuo.firstapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    Button btn;
    LottieAnimationView animationView;
    ImageView view;
    CircularProgressBar bar;
    LinearLayout linearLayout;
    TextView start;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = 100;
        view = findViewById(R.id.test_view);
        bar = findViewById(R.id.progress);
        btn = findViewById(R.id.start_btn);
        linearLayout = findViewById(R.id.total);
        animationView = findViewById(R.id.animation_view);
        start = findViewById(R.id.start);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                bar.setProgress(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.setAnimation("a.json");
                animationView.playAnimation();
                CountTask countTask = new CountTask();
                countTask.execute((int) animationView.getDuration());
                start.setVisibility(View.VISIBLE);
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(start, View.ALPHA, 0, 1).setDuration(5000);
                animatorSet.playTogether(alphaAnimation);
                animatorSet.start();
                Animation animator = new ScaleAnimation(
                        1f, 0.65f,
                        1f, 0.65f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f);

                animator.setFillAfter(true);
                animator.setDuration(5000);
                linearLayout.startAnimation(animator);
            }
        });
    }

    class CountTask extends AsyncTask<Integer, Integer, Void> {
        int num = 0;

        @Override
        protected Void doInBackground(Integer... params) {
            for (int i = 0; i < 1000000000; i++) {
                if (i % 50000000 == 0) {
                    publishProgress(new Integer(i));
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            num += 5;
            bar.setProgress(num);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
