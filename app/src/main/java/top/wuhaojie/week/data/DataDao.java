package top.wuhaojie.week.data;

import io.realm.Realm;
import io.realm.RealmResults;
import top.wuhaojie.week.entities.TaskDetailEntity;

/**
 * Created by wuhaojie on 2016/11/30 9:26.
 */

public class DataDao {

    private Realm mRealm;
    private static volatile DataDao mDataDao;

    private DataDao() {
        mRealm = Realm.getDefaultInstance();
    }


    public static DataDao getInstance() {
        if (mDataDao == null) {
            synchronized (DataDao.class) {
                if (mDataDao == null) {
                    mDataDao = new DataDao();
                }
            }
        }
        return mDataDao;
    }

    public void insertTask(TaskDetailEntity taskDetailEntity) {
        mRealm.executeTransaction(realm -> {
//            taskDetailEntity.setDayOfWeek(Calendar.THURSDAY);
//            taskDetailEntity.setTimeStamp(System.currentTimeMillis());
//            taskDetailEntity.setTitle("无标题");
//            taskDetailEntity.setContent("测试文本");
//            taskDetailEntity.setIcon(null);
//            taskDetailEntity.setState(TaskState.DEFAULT);
            realm.copyToRealm(taskDetailEntity);
        });
    }

    public RealmResults<TaskDetailEntity> findAllTask() {
        return mRealm.where(TaskDetailEntity.class)
                .findAllSorted("timeStamp");
    }


    public RealmResults<TaskDetailEntity> findAllTask(int dayOfWeek) {
        return mRealm.where(TaskDetailEntity.class)
                .equalTo("dayOfWeek", dayOfWeek)
                .findAllSorted("timeStamp");
    }


    public void editTask(TaskDetailEntity oldTask, TaskDetailEntity newTask) {
        mRealm.executeTransaction(realm -> oldTask.setTaskDetailEntity(newTask));
    }


    public void deleteTask(TaskDetailEntity entity) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskDetailEntity first = realm.where(TaskDetailEntity.class)
                        .equalTo("dayOfWeek", entity.getDayOfWeek())
                        .equalTo("title", entity.getTitle())
                        .equalTo("content", entity.getContent())
                        .equalTo("icon", entity.getIcon())
                        .equalTo("priority", entity.getPriority())
                        .equalTo("state", entity.getState())
                        .equalTo("timeStamp", entity.getTimeStamp())
                        .findFirst();
                if (first != null)
                    first.deleteFromRealm();
            }
        });


    }


}
