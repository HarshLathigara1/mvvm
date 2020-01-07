package lathigara.harsh.andriodarch;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao()
public interface NoteDao {
    // per dao of per entity
    //dao must be interface or abstract class

    @Insert // room will  automatically generate neccessary code
    void insert(Notes note);

    @Update
    void update(Notes notes);
    @Delete
    void delete(Notes notes);

    @Query("DELETE FROM notes_table")
    void delelteAllNodes();

    @Query("select * from notes_table order by prioritycolumn DESC")
    // if i put live data here i can observe this note list and make this list observable
   // that way if any changes made in table  than live data will have eye on that and our activity wil be notified it wil change our data in ui
    LiveData<List<Notes>> getAllNotes();

}
