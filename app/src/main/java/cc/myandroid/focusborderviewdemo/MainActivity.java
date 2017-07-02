package cc.myandroid.focusborderviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalFocusChangeListener{
    private FocusBorderView focusBorderView;
    private FrameLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        focusBorderView = (FocusBorderView) findViewById(R.id.focus_border_view);
        root = (FrameLayout) findViewById(R.id.root);
        root.getViewTreeObserver().addOnGlobalFocusChangeListener(this);
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        focusBorderView.runTranslateAnimation(newFocus,1.2f,1.2f);
    }
}
