package com.example.trabwsmutantes.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabwsmutantes.Model.Mutant;
import com.example.trabwsmutantes.R;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public class AdapterMutantes extends RecyclerView.Adapter<AdapterMutantes.MyViewHolder> implements Serializable {
    private List<Mutant> mutanteList;
    static String url = "https://08b1-2804-7f4-378e-dc86-ed30-ec7c-e28e-1505.sa.ngrok.io/";
    public  AdapterMutantes(List<Mutant> list){
        mutanteList = list;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView idMutante;
        ImageView img;

        public MyViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.Name);
            idMutante = view.findViewById(R.id.idMutante);
            img = view.findViewById(R.id.imageView);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public static class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage=BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
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
        Mutant obj = mutanteList.get(position);
        holder.name.setText(obj.getName());
        holder.idMutante.setText(String.valueOf(obj.getId()));
        //holder.img.setImageResource();
        new DownloadImageFromInternet((ImageView) holder.img).execute(url+obj.getPhoto());
    }

    @Override
    public int getItemCount() {
        return mutanteList.size();
    }
}
