package com.testcom.attendon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testcom.attendon.model.AbsenceModel;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Class1AbsenceAdapter extends RecyclerView.Adapter<Class1AbsenceAdapter.MahasiswaView>{

    private Context context;
    private ArrayList<AbsenceModel> listAbsence;

    public ArrayList<AbsenceModel> getListAbsence() {
        return listAbsence;
    }

    public void setListAbsence(ArrayList<AbsenceModel> listAbsence) {
        this.listAbsence = listAbsence;
    }

    public Class1AbsenceAdapter(Context context) {
        this.context = context;
    }


    @Override
    public Class1AbsenceAdapter.MahasiswaView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_absence, parent, false);
        return new Class1AbsenceAdapter.MahasiswaView(view);

    }

    @Override
    public void onBindViewHolder(final Class1AbsenceAdapter.MahasiswaView holder, final int position) {
        holder.date.setText(listAbsence.get(position).getDate());
        holder.record=listAbsence.get(position).getRecord();
        System.out.println("wooooooooooooo"+holder.record);
        if (holder.record.equals("1")){
            holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.textCorrect));
        }
        else {
            holder.cvMain.setCardBackgroundColor(context.getResources().getColor(R.color.textIncorrect));
        }

    }

    @Override
    public int getItemCount() {
        return getListAbsence().size();

    }

    public class MahasiswaView extends RecyclerView.ViewHolder{
        private TextView date;
        private CardView cvMain;
        private String record="";
        public MahasiswaView(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.absence_date);
           cvMain = (CardView) itemView.findViewById(R.id.absence_card);



        }

    }
}
