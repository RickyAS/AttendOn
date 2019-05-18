package com.testcom.attendon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testcom.attendon.model.YourClassModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class Class1Adapter extends RecyclerView.Adapter<Class1Adapter.MahasiswaView> {

    private Context context;
    private ArrayList<YourClassModel> listYourClass;

    public ArrayList<YourClassModel> getListYourClass() {
        return listYourClass;
    }

    public void setListYourClass(ArrayList<YourClassModel> listYourClass) {
        this.listYourClass = listYourClass;
    }

    public Class1Adapter(Context context) {
        this.context = context;
    }


    @Override
    public Class1Adapter.MahasiswaView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_your_class, parent, false);
        return new Class1Adapter.MahasiswaView(view);

    }

    @Override
    public void onBindViewHolder(final Class1Adapter.MahasiswaView holder, final int position) {
        holder.name.setText(listYourClass.get(position).getName());
        holder.time.setText(listYourClass.get(position).getTime());
        holder.date.setText(listYourClass.get(position).getDate());
        holder.desc.setText(listYourClass.get(position).getDesc());

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Class1Absence.class);
                intent.putExtra("class_title",listYourClass.get(position).getName());
                intent.putExtra("class_code", listYourClass.get(position).getCode());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return getListYourClass().size();

    }

    public class MahasiswaView extends RecyclerView.ViewHolder{
        private TextView name, time, date, desc;
        private LinearLayout cvMain;
        public MahasiswaView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.row_yc_name);
            time = (TextView) itemView.findViewById(R.id.row_yc_time);
            date = (TextView) itemView.findViewById(R.id.row_yc_date);
            desc = (TextView) itemView.findViewById(R.id.row_yc_desc);
            cvMain = (LinearLayout) itemView.findViewById(R.id.row_yc_layout);


        }

    }
}