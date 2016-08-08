package com.augmentis.ayp.beatbox;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return BeatBoxFragment.newInstance();
    }
}
