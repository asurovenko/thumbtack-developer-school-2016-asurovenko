package net.asurovenko.netexam.ui.exam_screen;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.app.Preferences;
import net.asurovenko.netexam.events.OpenMainActivityEvent;
import net.asurovenko.netexam.ui.MainActivity;
import net.asurovenko.netexam.ui.base.BaseActivity;

public class ExamActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        if (savedInstanceState == null) {
            addFragment(ExamFragment.newInstance(
                    getIntent().getIntExtra(Preferences.STUDENT_EXAM_ID, 0),
                    getIntent().getStringExtra(Preferences.EXAM_TITLE),
                    getIntent().getStringExtra(Preferences.TOKEN)));
        }
    }

    @Subscribe
    public void openMainActivity(OpenMainActivityEvent event) {
        startActivity(
                new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra(OpenMainActivityEvent.CURRENT_TAB, event.getCurrentTab())
        );
    }
}
