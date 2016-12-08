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

    public static List<String> createBgImgs() {
        String[] ids = new String[]{"asset:///bg_autumn_tree-min.jpg", "asset:///bg_kites-min.png", "asset:///bg_lake-min.jpg", "asset:///bg_leaves-min.jpg",
                "asset:///bg_magnolia_trees-min.jpg", "asset:///bg_solda-min.jpg", "asset:///bg_tree-min.jpg", "asset:///bg_tulip-min.jpg"};
        return Arrays.asList(ids);
    }


    public static int[] createPriorityIcons() {
        return new int[]{R.drawable.ic_priority_blue_1};
    }

}
