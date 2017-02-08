package top.wuhaojie.week.data;

import java.util.Arrays;
import java.util.List;

import top.wuhaojie.week.R;

/**
 * Created by wuhaojie on 2016/12/6 19:33.
 */

public class ImageFactory {

    private ImageFactory() {
    }

    private static final String[] ids = new String[]{"bg_autumn_tree-min.jpg", "bg_kites-min.png", "bg_lake-min.jpg", "bg_leaves-min.jpg",
            "bg_magnolia_trees-min.jpg", "bg_solda-min.jpg", "bg_tree-min.jpg", "bg_tulip-min.jpg"};


    public static List<String> createBgImgs() {
        return Arrays.asList(ids);
    }


    private static final int[] priorityIcons = new int[]{
            R.drawable.ic_priority_1,
            R.drawable.ic_priority_2,
            R.drawable.ic_priority_3,
            R.drawable.ic_priority_4
    };

    public static int[] createPriorityIcons() {
        return priorityIcons;
    }

}
