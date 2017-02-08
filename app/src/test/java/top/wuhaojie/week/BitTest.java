package top.wuhaojie.week;

import org.junit.Test;

/**
 * Created by wuhaojie on 17-2-9.
 */

public class BitTest {

    static int FLAG = 0x01;
    private long mOptions;

    private interface OPTIONS {
        int OPTION_CENTER_CROP = FLAG << 1;
        int OPTION_CIRCLE_CROP = FLAG << 2;
    }

    private final boolean hasOption(int option) {
        // ( 0011 & 0001 ) ^ 0001 == 0 ok
        // ( 0011 & 1000 ) ^ 1000 != 0 not ok
        // 0 & 0 = 0 ; 00 ^ 10 != 0
        return ((mOptions & option) ^ option) == 0;
    }

    @Test
    public void testBit() {
        System.out.println(hasOption(OPTIONS.OPTION_CENTER_CROP));
        System.out.println(hasOption(OPTIONS.OPTION_CIRCLE_CROP));
    }

}
