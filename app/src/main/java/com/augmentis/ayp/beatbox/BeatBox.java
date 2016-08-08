package com.augmentis.ayp.beatbox;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilailux on 8/8/2016.
 */
public class BeatBox {

    private static final String TAG = "BeatBox";

    private static final String SOUND_FOLDER = "sample_sounds";

    private static final int MAX_SOUND = 5;

    private AssetManager assets;
    private List<Sound> sounds = new ArrayList<>();
    private SoundPool soundPool;

    public BeatBox(Context context) {
        assets = context.getAssets();
        soundPool = new SoundPool(MAX_SOUND, AudioManager.STREAM_MUSIC,0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;

        try {
            soundNames = assets.list(SOUND_FOLDER);//หา folder ที่เก็บไฟล์เสียง

            Log.i(TAG,"found " + soundNames.length + "sounds");
        } catch (IOException ioe) {
            Log.e(TAG,"Could not list assets " + ioe);
            return;
        }

        //get list of file name
        for (String filename : soundNames) {
            try {
                String assetPath = SOUND_FOLDER + File.separator + filename;

                Sound sound = new Sound(assetPath);

                load(sound);

                //add sound to list
                sounds.add(sound);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = assets.openFd(sound.getAssetPath());
        int soundId = soundPool.load(afd,1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();

        if (sound == null) {
            return;
        }

        soundPool.play(soundId,1.0f,1.0f,1,0,1.0f);
    }

    public void release () {
        soundPool.release();
    }

}
