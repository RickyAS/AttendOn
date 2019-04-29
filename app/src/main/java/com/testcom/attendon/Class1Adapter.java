package com.testcom.attendon;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class Class1Adapter extends RecyclerView.Adapter<Class1Adapter.MahasiswaViewHolder> {


    private ArrayList<EnterData> dataList;

    public Class1Adapter(ArrayList<EnterData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MahasiswaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_owned_class, parent, false);
        return new MahasiswaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MahasiswaViewHolder holder, final int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtNpm.setText(dataList.get(position).getNpm());
        holder.txtNoHp.setText(dataList.get(position).getNohp());

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Class1Absence.class);
                intent.putExtra("lol",dataList.get(position).getNama());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;

    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtNpm, txtNoHp;
        private LinearLayout cvMain;
        public MahasiswaViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.row_oc_name);
            txtNpm = (TextView) itemView.findViewById(R.id.row_oc_time);
            txtNoHp = (TextView) itemView.findViewById(R.id.row_oc_date);
            cvMain = (LinearLayout) itemView.findViewById(R.id.row_oc_layout);



        }

    }
}
