package com.augmentis.ayp.beatbox;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Wilailux on 8/8/2016.
 */
public class BeatBoxFragment extends Fragment {

        public static BeatBoxFragment newInstance() {

        Bundle args = new Bundle();

        BeatBoxFragment fragment = new BeatBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    BeatBox beatBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box,container,false);

        RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycle_view);

        recycleView.setLayoutManager(new GridLayoutManager(getActivity(),3));// 3 is columns
        recycleView.setAdapter(new SoundAdaptor(beatBox.getSounds()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beatBox.release();
    }

    public class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button button;
        private Sound sound;

        SoundHolder(LayoutInflater layoutInflater,ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.list_item_sound,viewGroup,false));

            button = (Button) itemView.findViewById(R.id.list_item_sound_button);
            button.setOnClickListener(this);
        }

        public void bindSound(Sound sound) {
            this.sound = sound;
            button.setText(sound.getName());
        }

        @Override
        public void onClick(View view) {
            beatBox.play(sound);
        }
    }

    private class SoundAdaptor extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> soundList;

        SoundAdaptor(List<Sound> sounds) {
            soundList = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = soundList.get(position);
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return soundList.size();
        }
    }
}
