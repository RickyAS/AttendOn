package com.testcom.attendon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testcom.attendon.model.OwnedClassModel;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class Class2Adapter extends RecyclerView.Adapter<Class2Adapter.MahasiswaViewHolder> {

    private Context context;
    private ArrayList<OwnedClassModel> listOwnedClass;

    public ArrayList<OwnedClassModel> getListOwnedClass() {
        return listOwnedClass;
    }

    public void setListOwnedClass(ArrayList<OwnedClassModel> listOwnedClass) {
        this.listOwnedClass = listOwnedClass;
    }

    public Class2Adapter(Context context) {
        this.context = context;
    }


    @Override
    public MahasiswaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_owned_class, parent, false);
        return new MahasiswaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MahasiswaViewHolder holder, final int position) {
        holder.name.setText(listOwnedClass.get(position).getName());
        holder.time.setText(listOwnedClass.get(position).getTime());
        holder.date.setText(listOwnedClass.get(position).getDate());
        holder.desc.setText(listOwnedClass.get(position).getDesc());

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Class2Students.class);
                intent.putExtra("class_title",listOwnedClass.get(position).getName());
                intent.putExtra("class_code", listOwnedClass.get(position).getCode());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return getListOwnedClass().size();

    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder{
        private TextView name, time, date, desc;
        private LinearLayout cvMain;
        public MahasiswaViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.row_oc_name);
            time = (TextView) itemView.findViewById(R.id.row_oc_time);
            date = (TextView) itemView.findViewById(R.id.row_oc_date);
            desc = (TextView) itemView.findViewById(R.id.row_oc_desc);
            cvMain = (LinearLayout) itemView.findViewById(R.id.row_oc_layout);


        }

    }
}
