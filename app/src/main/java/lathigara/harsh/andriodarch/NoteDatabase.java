package lathigara.harsh.andriodarch;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// table name versio number
@Database(entities = {Notes.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {
   // we will user same insatnce in our app it will be singleton
    private static NoteDatabase instance;

    public abstract  NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        // only one thread will access this
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note.db")
                    .fallbackToDestructiveMigration()
                   // then here we are adding callback
                    .addCallback(roomCallback)
                    .build();
        }
       return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // wehn insatance will be created it will execute this method and it will populate our database
            //we want to excecute this in oncrate
            new PolulateDbAsyncTask(instance).execute();
        }


        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    // beacause we are doing all process in backgorund we are creating async mnethod
    private static class PolulateDbAsyncTask extends AsyncTask<Void ,Void ,Void>{
        private NoteDao noteDao;

        public PolulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Notes("title1","descriptio 1",1));
            noteDao.insert(new Notes("title2","descriptio 2",2));
            noteDao.insert(new Notes("title3","descriptio 3",3));
            return null;
        }
    }
}
