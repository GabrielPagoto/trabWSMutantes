package com.example.trabwsmutantes.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabwsmutantes.Model.Mutante;
import com.example.trabwsmutantes.R;

import java.io.Serializable;
import java.util.List;

public class AdapterMutantes extends RecyclerView.Adapter<AdapterMutantes.MyViewHolder> implements Serializable {
    private List<Mutante> mutanteList;
    public  AdapterMutantes(List<Mutante> list){
        mutanteList = list;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,habilidades;
        ImageView img;

        public MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.Name);
            img = view.findViewById(R.id.imageView);

        }
    }

    @NonNull
    @Override
    public AdapterMutantes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_mutantes,parent,false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMutantes.MyViewHolder holder, int position) {
        Mutante obj = mutanteList.get(position);
        holder.name.setText(obj.getNome());
        //.img.setImageResource(obj.getImg());
    }

    @Override
    public int getItemCount() {
        return mutanteList.size();
    }
}
