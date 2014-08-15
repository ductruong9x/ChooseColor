package com.gamevn.choosecolor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameActivity;

public class GameOver extends BaseGameActivity {
    private Button btnPlay, btnRank, btnshare, btnhowto,btnrate;
    private Typeface banhmi;
    private TextView tvscore, tvhighscore,tvappname;
    private int score = 0;
    private int high_score = 0;
    private AdView adView;
    private String UNIT_ID = "ca-app-pub-1857950562418699/8897141167";
    private InterstitialAd interstitialAd;
    private boolean isSound;
    private ImageButton btnSound;
    private SharedPreferences sharedPreferences;
    private ImageButton btnthanhtich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setRequestedClients(BaseGameActivity.CLIENT_GAMES |
//                BaseGameActivity.CLIENT_APPSTATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(UNIT_ID);
        interstitialAd.loadAd(new AdRequest.Builder().build());
        banhmi = Typeface.createFromAsset(getAssets(), "fonts/banhmi.TTF");
        btnPlay = (Button) findViewById(R.id.btnplay);
        btnRank = (Button) findViewById(R.id.btnrank);
        btnSound=(ImageButton)findViewById(R.id.btnSound);
        btnhowto = (Button) findViewById(R.id.btnhowto);
        tvhighscore = (TextView) findViewById(R.id.tvhight_score);
        btnthanhtich=(ImageButton)findViewById(R.id.btnthanhtich);
        tvscore = (TextView) findViewById(R.id.tvscore);
        tvappname=(TextView)findViewById(R.id.tvappname);
        btnrate=(Button)findViewById(R.id.btnrate);
        tvhighscore.setTypeface(banhmi);
        tvscore.setTypeface(banhmi);
        btnPlay.setTypeface(banhmi);
        btnRank.setTypeface(banhmi);


        btnhowto.setTypeface(banhmi);
        tvappname.setTypeface(banhmi);
        btnrate.setTypeface(banhmi);
        sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        high_score = sharedPreferences.getInt("high_score", 0);
        final Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        checkSound();

        tvscore.setText(getString(R.string.score, score));
        tvhighscore.setText(getString(R.string.high_score, high_score));

        if (isSignedIn()){

        }

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSound){
                    editor.putBoolean("sound",false);
                    editor.commit();
                    checkSound();
                }else{
                    editor.putBoolean("sound",true);
                    editor.commit();
                    checkSound();
                }
            }
        });

        btnhowto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GameOver.this, HowToActivity.class);
                startActivity(intent1);

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(GameOver.this, MyActivity.class);
                startActivity(intent);
            }
        });
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.leaderboard_choose_color)), 1);
            }
        });
        btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("market://details?id="
                                + getPackageName()));
                startActivity(goToMarket);
            }
        });
        btnthanhtich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), 1);
            }
        });
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(sharingIntent, getResources()
                .getString(R.string.share_via)));
    }

    private void checkSound(){
        isSound=sharedPreferences.getBoolean("sound",true);
        if (isSound==true){
            btnSound.setBackgroundResource(R.drawable.sound);
        }else {
            btnSound.setBackgroundResource(R.drawable.mute);
        }
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {


        long high = (long) high_score;
        Games.Leaderboards.submitScore(getApiClient(), getString(R.string.leaderboard_choose_color), high);

        if (high_score>=5 && high_score<10){
            Games.Achievements.unlock(getApiClient(),getString(R.string.achievement_great));
        }else if(high_score>=10 && high_score<20) {
            Games.Achievements.unlock(getApiClient(),getString(R.string.achievement_good));

        }else if (high_score>=20 &&high_score<50){
            Games.Achievements.unlock(getApiClient(),getString(R.string.achievement_very_good));
        }else if(high_score>=50 && high_score<100){
            Games.Achievements.unlock(getApiClient(),getString(R.string.achievement_super_man));
        }else if (high_score>=100 && high_score<200 ){
            Games.Achievements.unlock(getApiClient(),getString(R.string.achievement_crazy));
        }else if (high_score>=200){
            Games.Achievements.unlock(getApiClient(),getString(R.string.achievement_are_you_cheat_));
        }

    //    loadScoreOfLeaderBoard();
    }

    private void loadScoreOfLeaderBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_choose_color), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (isScoreResultValid(scoreResult)) {
                    // here you can get the score like this
                    long mPoints = scoreResult.getScore().getRawScore();
                    high_score = (int) mPoints;
                    SharedPreferences sharedPreferences = getSharedPreferences("score", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("high_score", high_score);
                    editor.commit();
                    tvhighscore.setText(getString(R.string.high_score,high_score));
                }
            }
        });
    }

    private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
        return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adView!=null){
            adView.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        interstitialAd.show();
        super.onBackPressed();
    }
}
