package top.wuhaojie.week.entities;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by wuhaojie on 2016/11/30 9:28.
 */

public class TaskDetailEntity extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private int dayOfWeek;
    private String title;
    private String content;
    private String icon;
    private long timeStamp;
    private int state;

    public TaskDetailEntity() {
    }

    public TaskDetailEntity(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TaskDetailEntity{" +
                "dayOfWeek=" + dayOfWeek +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", icon=" + icon +
                ", timeStamp=" + timeStamp +
                ", state=" + state +
                '}';
    }
}
