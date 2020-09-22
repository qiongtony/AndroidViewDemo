package com.example.customlayoutmanagerdemo;

import java.util.ArrayList;
import java.util.List;

public class AudioLinesRepos {
    private static List<AudioLinesItem> mAudioLinesRepos;
    private static String[] sLines = new String[]{"我多想拥抱你，可惜时光之里山南水北，可惜你我之间人来人往。",
    };
    public static List<AudioLinesItem> getDatas(){
        if (mAudioLinesRepos != null){
            return mAudioLinesRepos;
        }
        mAudioLinesRepos = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            AudioLinesItem item = new AudioLinesItem(i, sLines[0]);
            mAudioLinesRepos.add(item);
        }

        return mAudioLinesRepos;
    }
}
