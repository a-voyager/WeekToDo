package top.wuhaojie.week.dagger;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import top.wuhaojie.week.data.DataDao;
import top.wuhaojie.week.data.PageFactory;
import top.wuhaojie.week.entities.MainPageItem;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/13 15:16
 * Version: 1.0
 */
@Module
public class DataModule {

    @Provides
    public List<MainPageItem> pages() {
        return PageFactory.createPages();
    }

    @Provides
    public DataDao dataDao() {
        return DataDao.getInstance();
    }

}
