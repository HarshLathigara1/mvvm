package lathigara.harsh.andriodarch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddnewNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "lathigara.harsh.andriodarch.EXTRA_TITLE ";
    public static final String EXTRA_DESC = "lathigara.harsh.andriodarch.EXTRA_DESC ";
    public static final String EXTRA_PRY = "lathigara.harsh.andriodarch.EXTRA_PRY ";

    private EditText edttitle;
    private EditText edtDesc;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_note);
        edttitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        numberPicker = findViewById(R.id.numberPicknewActivity);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_black_24dp);
        setTitle("Add Note");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnSave:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void saveNote() {
        String title =edttitle.getText().toString();
        String desc = edtDesc.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty()|| desc.trim().isEmpty()){
            Toast.makeText(this,"Plese insert a title and ddesc",Toast.LENGTH_LONG).show();
            return; // it will stop it here
        }

        Intent data  =new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESC,desc);
        data.putExtra(EXTRA_PRY,priority);
        setResult(RESULT_OK, data);
        finish();


    }
}
