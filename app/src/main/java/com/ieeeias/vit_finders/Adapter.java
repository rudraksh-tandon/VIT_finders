package com.ieeeias.vit_finders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private List<ModelClasslost> itemList;

    public Adapter(List<ModelClasslost> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lostitem,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            int resource=itemList.get(position).getImageview1();
            String t1=itemList.get(position).getTextview1();
        String t2=itemList.get(position).getTextview2();

        holder.setData(resource,t1,t2);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv1;
        private TextView tv2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            img=itemView.findViewById(R.id.imageView1);
            tv1=itemView.findViewById(R.id.textView1);
            tv2=itemView.findViewById(R.id.textView2);
        }

        public void setData(int resource, String t1, String t2) {
           img.setImageResource(resource);
           tv1.setText(t1);
           tv2.setText(t2);
        }
    }
}
