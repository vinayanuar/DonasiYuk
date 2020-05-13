package com.undev.donkuy;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDonasi extends RecyclerView.Adapter<AdapterDonasi.MateriViewHolder> {

    ArrayList<ModelDonasi> arrayList;
    Context context;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public void setProfil(ArrayList<ModelDonasi> arrayList) {
        this.arrayList = arrayList;
    }

    public AdapterDonasi(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MateriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_donasi, parent, false);
        return new MateriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriViewHolder holder, final int position) {
        holder.textJudul.setText(arrayList.get(position).getJudul());
        holder.textSubJudul.setText(arrayList.get(position).getSubjudul());
        holder.textTerkumpul.setText(arrayList.get(position).getTerkumpul());
        holder.textSisaHari.setText("Sisa Hari " + arrayList.get(position).getSisahari());
        holder.imageView.setImageResource(arrayList.get(position).getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MateriViewHolder extends RecyclerView.ViewHolder {
        private TextView textJudul, textSubJudul, textTerkumpul, textSisaHari;
        private ImageView imageView;

        public MateriViewHolder(View itemView) {
            super(itemView);
            textJudul = itemView.findViewById(R.id.listJudul);
            textSubJudul = itemView.findViewById(R.id.listSubJudul);
            textTerkumpul = itemView.findViewById(R.id.listTerkumpul);
            textSisaHari = itemView.findViewById(R.id.listSisaHari);
            imageView = itemView.findViewById(R.id.listImage);
        }
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}
