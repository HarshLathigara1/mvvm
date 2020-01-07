package lathigara.harsh.andriodarch;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String desc;

    @ColumnInfo(name = "prioritycolumn")
    private int priority;


    public Notes( String title, String desc, int priority) {

        this.title = title;
        this.desc = desc;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getPriority() {
        return priority;
    }
}
