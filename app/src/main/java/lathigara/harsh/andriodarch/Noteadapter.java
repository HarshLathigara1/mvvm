package lathigara.harsh.andriodarch;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Noteadapter extends RecyclerView.Adapter<Noteadapter.ViewHolder> {

    private List<Notes> notesList = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes currentNotes = notesList.get(position);
        holder.textViewTitle.setText(currentNotes.getTitle());
        holder.textViewdescription.setText(currentNotes.getDesc());
        holder.textViewPriority.setText(String.valueOf(currentNotes.getPriority()));



    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setNotes(List<Notes>notes){
        this.notesList  =notes;
        notifyDataSetChanged();
    }

    public Notes getNoteAt(int position){
        return notesList.get(position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle,textViewdescription,textViewPriority;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.txtViewTitle);
            textViewdescription = itemView.findViewById(R.id.textViewdesc);
            textViewPriority=  itemView.findViewById(R.id.txtViewPriority);
        }
    }
}
