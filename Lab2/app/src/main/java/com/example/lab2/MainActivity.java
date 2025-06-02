package com.example.lab2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements InputFragment.OnFormSubmitListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInputFragment();
    }

    private void showInputFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new InputFragment())
                .commit();
    }

    private void showResultFragment(String result) {
        ResultFragment resultFragment = ResultFragment.newInstance(result);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, resultFragment)
                .commit();
    }

    @Override
    public void onFormSubmitted(String result) {
        showResultFragment(result);
    }

    public void onCancel() {
        showInputFragment();
    }
}
