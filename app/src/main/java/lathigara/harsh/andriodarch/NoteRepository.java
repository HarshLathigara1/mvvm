package lathigara.harsh.andriodarch;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    // repository provides abstraction between data source and other part of app
    // repository is a mediator between your app other part like viewmodle and view to data source
    private NoteDao noteDao;
    private LiveData<List<Notes>> allNotes;
    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao  =database.noteDao();
        allNotes = noteDao.getAllNotes(); // livedata
    }
    // databse operation methods
    // this methods are api repository will excecute to outside classes
    // simply we are doing this all work in bacckgorund
    //that way our viwemodel have reference to our repository so we just have to call this metohd in viewmodel
    public void insert(Notes notes){
        new InsertNoteAsyncTask(noteDao).execute(notes);

    }

    public void update(Notes notes){
        new UpdateNoteAsyncTask(noteDao).execute(notes);

    }
    public void delete(Notes notes){
        new DeleteNoteAsyncTask(noteDao).execute(notes);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Notes,Void,Void>{
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class  DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.delelteAllNodes();
            return null;
        }
    }
}
