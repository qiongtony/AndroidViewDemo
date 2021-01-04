package com.example.autoscrolllistdemo.reposity;

import com.example.autoscrolllistdemo.CommentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommentReposity {
    private static List<CommentBean> mCommentList = new ArrayList<>();
    private static String[] nickNameArr = new String[]{"安琪拉", "瑶妹恐惧症", "Hex", "天若有情"};
    private static String[] commentArr = new String[]{"不管鸟的翅膀多么完美，如果不凭借空气，鸟就永远飞不到高空",
                        "想象力是翅膀，客观实际是空气，只有两方面紧密结合，才能取得显着成绩。",
            "想停下来深情地沉湎一番，怎奈行驶的船却没有铁锚；想回过头去重温旧梦，怎奈身后早已没有了归途",
            "真正痛苦的人，却在笑脸的背后，流着别人无法知道的眼泪，生活中我们笑得比谁都开心，可是当所有的人潮散去的时候，我们比谁都落寂。",
            "温暖是飘飘洒洒的春雨"};
    // 测试初始没数据的情况
    private static boolean test = true;
    public static List<CommentBean> getCommentList(){
        if (test){
            return mCommentList;
        }
        if (mCommentList.size() > 0){
            return mCommentList;
        }
        for (int i = 0; i < 5; i++){
            mCommentList.add(getComment(i));
        }
        return mCommentList;
    }

    public static CommentBean getComment(int pos){
        return new CommentBean(nickNameArr[(int) (Math.random() * nickNameArr.length)],
                commentArr[new Random().nextInt(commentArr.length)]
//                commentArr[pos]
                , "");
    }
}
