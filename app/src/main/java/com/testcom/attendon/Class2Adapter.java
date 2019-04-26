package com.testcom.attendon;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.testcom.attendon.model.TampilMahasiswaModel;
import java.util.ArrayList;


public class Class2Adapter extends RecyclerView.Adapter<Class2Adapter.MahasiswaViewHolder> {

    private Context context;
    private ArrayList<TampilMahasiswaModel> listTampilMahasiswa;
    private ArrayList<TampilMahasiswaModel> getListTampilMahasiswa() {
        return listTampilMahasiswa;
    }
    public void setListTampilMahasiswa(ArrayList<TampilMahasiswaModel> listTampilMahasiswa) {
        this.listTampilMahasiswa = listTampilMahasiswa;
    }
    public Class2Adapter(Context context) {
        this.context = context;
    }


    @Override
    public MahasiswaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_class1, parent, false);
        return new MahasiswaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MahasiswaViewHolder holder, final int position) {
        holder.txtNama.setText(listTampilMahasiswa.get(position).getNim());
        holder.txtNpm.setText(listTampilMahasiswa.get(position).getNama());
        holder.txtNoHp.setText(listTampilMahasiswa.get(position).getNama_fakultas());

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Class2Students.class);
                intent.putExtra("lol",listTampilMahasiswa.get(position).getNama());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return getListTampilMahasiswa().size();

    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtNpm, txtNoHp;
        private LinearLayout cvMain;
        public MahasiswaViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_student);
            txtNpm = (TextView) itemView.findViewById(R.id.txt_npm_mahasiswa);
            txtNoHp = (TextView) itemView.findViewById(R.id.txt_nohp_mahasiswa);
            cvMain = (LinearLayout) itemView.findViewById(R.id.dog);


        }

    }
}
