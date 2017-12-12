package me.hoyuo.firstapplication;

import android.animation.Animator;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    Button btn;
    LottieAnimationView animationView;
    ImageView view;
    CircularProgressBar bar;

    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = 10;
        view = findViewById(R.id.test_view);
        bar = findViewById(R.id.progress);
        btn = findViewById(R.id.start_btn);
        animationView = findViewById(R.id.animation_view);

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
                countTask.execute((int)animationView.getDuration());
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
