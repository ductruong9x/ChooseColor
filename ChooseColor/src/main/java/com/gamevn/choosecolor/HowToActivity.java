package com.gamevn.choosecolor;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HowToActivity extends Activity {
    private TextView tvHowto,tvContent;
    private Button btnclose;
    private Typeface banhmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_how_to);
        banhmi = Typeface.createFromAsset(getAssets(), "fonts/banhmi.TTF");
        tvContent=(TextView)findViewById(R.id.tvcontent);
        tvHowto=(TextView)findViewById(R.id.howto);
        btnclose=(Button)findViewById(R.id.btnclose);
        tvContent.setTypeface(banhmi);
        tvHowto.setTypeface(banhmi);
        btnclose.setTypeface(banhmi);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
