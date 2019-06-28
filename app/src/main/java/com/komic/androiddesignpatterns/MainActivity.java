package com.komic.androiddesignpatterns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.komic.androiddesignpatterns.mvc.MVCActivity;
import com.komic.androiddesignpatterns.mvp.MVPActivity;
import com.komic.androiddesignpatterns.mvvm.MVVMActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void onMVC(View view) {
        startActivity(MVCActivity.getIntent(this));
    }
    public void onMVP(View view) {
        startActivity(MVPActivity.getIntent(this));
    }
    public void onMVVM(View view) {
        startActivity(MVVMActivity.getIntent(this));
    }
}
