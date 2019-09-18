package com.example.myapplication;

import android.annotation.SuppressLint;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class EntryObjectAdapter extends RecyclerView.Adapter<EntryObjectAdapter.DiaryEntryHolder> {
    private OnItemClickListener mainListener;
    private ArrayList<EntryObject> entryList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mainListener = listener;
    }

    public static class DiaryEntryHolder extends RecyclerView.ViewHolder{
        public TextView nkiView;
        public TextView dateView;

        public DiaryEntryHolder(View entryView, final OnItemClickListener Action) {
            super(entryView);
            nkiView = entryView.findViewById(R.id.NKIView);
            dateView = entryView.findViewById(R.id.DateView);

            entryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Action != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            Action.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }


    @Override
    public DiaryEntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        DiaryEntryHolder Diaryentryholder = new DiaryEntryHolder(view, mainListener);
        return Diaryentryholder;
    }

    public EntryObjectAdapter(ArrayList<EntryObject> entryList){
        this.entryList = entryList;
    }
    @Override
    public int getItemCount() {
        return entryList.size();
    }
    @Override
    public void onBindViewHolder(DiaryEntryHolder holder, int position) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat FF = new SimpleDateFormat("EEE d MMM yyyy HH:mm");
        EntryObject currentEntry = entryList.get(position);

        holder.dateView.setText("  "+ FF.format(currentEntry.getDate()));
        holder.nkiView.setText("  " +currentEntry.getNKI()+"");
    }
}

//CODE FROM "Code in Flow" for recylcerView