package com.gamevn.choosecolor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gamevn.choosecolor.view.RoundImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MyActivity extends Activity implements View.OnClickListener {
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private TextView tvcolor, tvscore;
    private String[] arrayNameColor;
    private int[] arrayColor;
    private int[] arrayImage;
    private int random_name_color = 0;
    private int random_color = 0;
    private Typeface banhmi;
    private RoundImageView btn1, btn2, btn3, btn4, btn5, btn6;
    private ArrayList<Integer> listbutton = new ArrayList<Integer>();
    private boolean game = true;
    private int score;
    private boolean fist_choose = true;
    private SoundPool soundtrue;
    private SoundPool soundfalse;
    private int idTrue, idFasle;
    private boolean isSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);
        score = 0;
        arrayNameColor = new String[]{getString(R.string.red)
                , getString(R.string.black)
                , getString(R.string.brown)
                , getString(R.string.gray)
                , getString(R.string.white)
                , getString(R.string.yellow)
                , getString(R.string.orange)
                , getString(R.string.pink)
                , getString(R.string.purple)
                , getString(R.string.blue)
                , getString(R.string.green)};
        arrayColor = new int[]{getResources().getColor(R.color.red)
                , getResources().getColor(R.color.black)
                , getResources().getColor(R.color.brown)
                , getResources().getColor(R.color.gray)
                , getResources().getColor(R.color.white)
                , getResources().getColor(R.color.yellow)
                , getResources().getColor(R.color.orange)
                , getResources().getColor(R.color.pink)
                , getResources().getColor(R.color.purple)
                , getResources().getColor(R.color.blue)
                , getResources().getColor(R.color.green)};

        arrayImage = new int[]{R.drawable.red
                , R.drawable.black
                , R.drawable.brown
                , R.drawable.gray
                , R.drawable.white
                , R.drawable.yellow
                , R.drawable.orange
                , R.drawable.pink
                , R.drawable.purple
                , R.drawable.blue
                , R.drawable.green};

        banhmi = Typeface.createFromAsset(getAssets(), "fonts/banhmi.TTF");
        btn1 = (RoundImageView) findViewById(R.id.btn1);
        btn2 = (RoundImageView) findViewById(R.id.btn2);
        btn3 = (RoundImageView) findViewById(R.id.btn3);
        btn4 = (RoundImageView) findViewById(R.id.btn4);
        btn5 = (RoundImageView) findViewById(R.id.btn5);
        btn6 = (RoundImageView) findViewById(R.id.btn6);
        soundfalse = new SoundPool(30, AudioManager.STREAM_MUSIC, 0);
        soundtrue = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        idTrue = soundtrue.load(this, R.raw.truemusic, 1);
        idFasle = soundfalse.load(this, R.raw.falsemusic, 1);
        SharedPreferences sharedPreferences=getSharedPreferences("score",MODE_PRIVATE);
        isSound=sharedPreferences.getBoolean("sound",true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(1000);
        progressBar.setProgress(1000);
        tvcolor = (TextView) findViewById(R.id.tvcolor);
        tvscore = (TextView) findViewById(R.id.tvscore);

        tvscore.setTypeface(banhmi);
        tvcolor.setTypeface(banhmi);
        tvscore.setText(getString(R.string.score, score));

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        countDownTimer = new CountDownTimer(1000, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) millisUntilFinished;
                progressBar.setProgress(progress);
                game = false;

            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);

                if (game) {

                } else {
                    countDownTimer.cancel();
                    hideButton();
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    int high_score = sharedPreferences.getInt("high_score", 0);
                    if (score > high_score) {
                        editor.putInt("high_score", score);
                        editor.commit();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            Intent intent = new Intent(MyActivity.this, GameOver.class);
                            intent.putExtra("score", score);
                            startActivity(intent);
                        }
                    }, 1000);

                }
            }
        };
        randomColor();


    }

    public void randomColor() {
        Random random_name = new Random();
        Random random_values_color = new Random();
        random_name_color = random_name.nextInt(arrayNameColor.length);
        random_color = random_values_color.nextInt(arrayColor.length);
        tvcolor.setText(arrayNameColor[random_name_color]);
        tvcolor.setTextColor(arrayColor[random_color]);
        Random number = new Random();
        listbutton.add(random_name_color);
        Log.e("CHOOSE_COLOR", random_name_color + "");
        while (listbutton.size() < 6) {
            int color_pos = number.nextInt(arrayColor.length);
            if (!listbutton.contains(color_pos)) {
                listbutton.add(color_pos);
            }

        }

        Collections.shuffle(listbutton);

        btn1.setImageResource(arrayImage[listbutton.get(0)]);
        btn2.setImageResource(arrayImage[listbutton.get(1)]);
        btn3.setImageResource(arrayImage[listbutton.get(2)]);
        btn4.setImageResource(arrayImage[listbutton.get(3)]);
        btn5.setImageResource(arrayImage[listbutton.get(4)]);
        btn6.setImageResource(arrayImage[listbutton.get(5)]);


    }

    @Override
    public void onClick(View v) {
        int check = 0;


        switch (v.getId()) {
            case R.id.btn1:
                check = listbutton.get(0);
                if (check == random_name_color) {
                    //chon dung
                    if (isSound) {
                        soundtrue.play(idTrue, 1, 1, 0, 0, 1);
                    }
                    score++;
                    tvscore.setText(getString(R.string.score, score));
                    game = true;
                    fist_choose = false;
                    listbutton.clear();
                    randomColor();
                    countDownTimer.cancel();
                    countDownTimer.start();
                } else {
                    //chon sai
                    hideButton();
                    game = false;
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    if (fist_choose) {
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int high_score = sharedPreferences.getInt("high_score", 0);
                        if (score > high_score) {
                            editor.putInt("high_score", score);
                            editor.commit();
                        }
                        listbutton.clear();
                        Intent intent = new Intent(MyActivity.this, GameOver.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    } else {
                        listbutton.clear();

                        //    countDownTimer.onFinish();
                        //   countDownTimer.cancel();

                    }

                }
                break;
            case R.id.btn2:
                check = listbutton.get(1);
                if (check == random_name_color) {
                    //chon dung
                    if (isSound) {
                        soundtrue.play(idTrue, 1, 1, 0, 0, 1);
                    }
                    score++;
                    tvscore.setText(getString(R.string.score, score));
                    game = true;
                    fist_choose = false;
                    listbutton.clear();
                    randomColor();
                    countDownTimer.cancel();
                    countDownTimer.start();

                } else {
                    //chon sai
                    hideButton();
                    game = false;
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    if (fist_choose) {
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int high_score = sharedPreferences.getInt("high_score", 0);
                        if (score > high_score) {
                            editor.putInt("high_score", score);
                            editor.commit();
                        }
                        listbutton.clear();
                        Intent intent = new Intent(MyActivity.this, GameOver.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    } else {
                        listbutton.clear();

                        //  countDownTimer.onFinish();
                        //  countDownTimer.cancel();
                    }
                }
                break;
            case R.id.btn3:
                check = listbutton.get(2);
                if (check == random_name_color) {
                    //chon dung
                    if (isSound) {
                        soundtrue.play(idTrue, 1, 1, 0, 0, 1);
                    }
                    score++;
                    tvscore.setText(getString(R.string.score, score));
                    game = true;
                    fist_choose = false;
                    listbutton.clear();
                    randomColor();
                    countDownTimer.cancel();
                    countDownTimer.start();
                } else {
                    //chon sai
                    hideButton();
                    game = false;
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    if (fist_choose) {
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int high_score = sharedPreferences.getInt("high_score", 0);
                        if (score > high_score) {
                            editor.putInt("high_score", score);
                            editor.commit();
                        }
                        listbutton.clear();
                        Intent intent = new Intent(MyActivity.this, GameOver.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    } else {
                        listbutton.clear();

                        //  countDownTimer.onFinish();
                        // countDownTimer.cancel();
                    }

                }
                break;
            case R.id.btn4:
                check = listbutton.get(3);
                if (check == random_name_color) {
                    //chon dung
                    if (isSound) {
                        soundtrue.play(idTrue, 1, 1, 0, 0, 1);
                    }
                    score++;
                    tvscore.setText(getString(R.string.score, score));
                    game = true;
                    fist_choose = false;
                    listbutton.clear();
                    randomColor();
                    countDownTimer.cancel();
                    countDownTimer.start();
                } else {
                    //chon sai
                    hideButton();
                    game = false;
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    if (fist_choose) {
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int high_score = sharedPreferences.getInt("high_score", 0);
                        if (score > high_score) {
                            editor.putInt("high_score", score);
                            editor.commit();
                        }
                        listbutton.clear();
                        Intent intent = new Intent(MyActivity.this, GameOver.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    } else {

                        listbutton.clear();

                        //  countDownTimer.onFinish();
                        //  countDownTimer.cancel();
                    }


                }
                break;
            case R.id.btn5:
                check = listbutton.get(4);
                if (check == random_name_color) {
                    //chon dung
                    if (isSound) {
                        soundtrue.play(idTrue, 1, 1, 0, 0, 1);
                    }
                    score++;
                    tvscore.setText(getString(R.string.score, score));
                    game = true;
                    fist_choose = false;
                    listbutton.clear();
                    randomColor();
                    countDownTimer.cancel();
                    countDownTimer.start();
                } else {
                    //chon sai
                    hideButton();
                    game = false;
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    if (fist_choose) {
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int high_score = sharedPreferences.getInt("high_score", 0);
                        if (score > high_score) {
                            editor.putInt("high_score", score);
                            editor.commit();
                        }
                        listbutton.clear();
                        Intent intent = new Intent(MyActivity.this, GameOver.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    } else {
                        listbutton.clear();

                        //  countDownTimer.onFinish();
                        //  countDownTimer.cancel();
                    }

                }
                break;
            case R.id.btn6:
                check = listbutton.get(5);
                if (check == random_name_color) {
                    //chon dung
                    if (isSound) {
                        soundtrue.play(idTrue, 1, 1, 0, 0, 1);
                    }
                    score++;
                    tvscore.setText(getString(R.string.score, score));
                    game = true;
                    fist_choose = false;
                    listbutton.clear();
                    randomColor();
                    countDownTimer.cancel();
                    countDownTimer.start();
                } else {
                    //chon sai
                    hideButton();
                    game = false;
                    if (isSound) {
                        soundfalse.play(idFasle, 1, 1, 0, 0, 1);
                    }
                    if (fist_choose) {
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int high_score = sharedPreferences.getInt("high_score", 0);
                        if (score > high_score) {
                            editor.putInt("high_score", score);
                            editor.commit();
                        }
                        listbutton.clear();
                        Intent intent = new Intent(MyActivity.this, GameOver.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                    } else {
                        listbutton.clear();

                        // countDownTimer.onFinish();
                        // countDownTimer.cancel();
                    }
                }
                break;

        }
    }

    private void hideButton() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
