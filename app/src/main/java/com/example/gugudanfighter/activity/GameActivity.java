package com.example.gugudanfighter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.gugudanfighter.R;

import java.util.Timer;
import java.util.TimerTask;


public class GameActivity extends AppCompatActivity {

    private static final int TIME_LIMIT = 5;
    private Timer timer = new Timer();

    private int correctAnswer; // 정답
    private int getCorrectAnswer = 3; // 정답 수
    private int questionCount = 1; // 출제 문제 갯수

    private TextView tvLastTime;
    private TextView tvScore;

    private TextView rndQuesLeftNum;
    private TextView rndQuesRightNum;

    private int[] answerBtnIds = {
            R.id.button_0_0, R.id.button_0_1, R.id.button_0_2,
            R.id.button_1_0, R.id.button_1_1, R.id.button_1_2,
            R.id.button_2_0, R.id.button_2_1, R.id.button_2_2,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // View 찾기
        tvLastTime = findViewById(R.id.textViewLastTime);
        tvScore = findViewById(R.id.textViewScore);

        // ui 초기화(여기가 메인 쓰레드임!)
        updateLastTime(TIME_LIMIT);
        updateScore(getCorrectAnswer, questionCount);

        // 타이머 시작 - 1초 뒤부터 1초마다 run이 계속 실행됨
        timer.schedule(new GamePlayTimerTask(), 1000, 1000);

        rndQuesSetting();
        rndAnswSetting();

    }

    private int randomize(int from, int to) {
        return (int) (Math.random() * to) + from;
    }

    private void rndQuesSetting() {
        rndQuesLeftNum = findViewById(R.id.textViewLeftOperand);
        rndQuesRightNum = findViewById(R.id.textViewRightOperand);

        rndQuesLeftNum.setText("" + randomize(2, 9));
        rndQuesRightNum.setText("" + randomize(1, 9));
    }

    private void rndAnswSetting() {
        for (int i = 0; i < answerBtnIds.length; i++) {

            answerBtnIds[i] = randomize(2, 9) * randomize(1, 9);

            for (int j = 0; j <= 2; j++) {
                for (int k = 0; k <= 2; k++) {
                    String rndBtns = "button_" + j + "_" + k;
                    int resId = getResources().getIdentifier(rndBtns, "id", this.getPackageName());
                    Button view = ((Button) findViewById(resId));
                    view.setText("" + answerBtnIds[i]);
                }
            }
        }
    }

    private void updateScore(int getCorrectAnswer, int questionCount) {

        // 문제 출제 될때마다 questionCount++ 해주는 if문 작성
        // 맞는 버튼(답)을 누를 때에만 getCorrectAnswer++ 해주는 if문도 작성

        tvScore.setText(getCorrectAnswer + "/" + questionCount);
    }

    private void updateLastTime(int lastTime) {
        tvLastTime.setText("" + lastTime);
    }

    private class GamePlayTimerTask extends TimerTask {
        private int seconds = 0;

        @Override
        public void run() {
            if (seconds >= TIME_LIMIT) {
                // 게임 종료 단계:

                // 1. time stop
                timer.cancel();

                // 2. result activity 이동
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                startActivity(intent);

                // 3. activiti 종료
                finish();
                return;
            }
            seconds++;

            // ui 변경은 메인 쓰레드에서 해야 함.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateLastTime(TIME_LIMIT - seconds);
                }
            });
        }
    }
}
