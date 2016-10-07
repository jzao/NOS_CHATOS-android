package chatos.camp.noschatos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by joaozao on 07/10/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.get(this).inject(this);
    }
}