package com.rockspur.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.rockspur.flowlayoutlibrary.FlowLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flowlayout);

        flowLayout.setGravity(FlowLayout.CENTER);
        flowLayout.setRowSpacing(
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()) + .5f)
        );
        flowLayout.setRowSpacing(
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()) + .5f)
        );
    }
}
