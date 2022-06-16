package com.ieeeias.vit_finders;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListItemAdapter extends ArrayAdapter<ListItem> {
//    private List<ListItem> itemList;

//    public ListItemAdapter(List<ListItem> itemList) {
//        this.itemList = itemList;
//    }

    public ListItemAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.image);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.name);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.location);

        ListItem item = getItem(position);

//        boolean isPhoto = message.getPhotoUrl() != null;
//        if (isPhoto) {
//            messageTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
//            Glide.with(photoImageView.getContext()).load(message.getPhotoUrl()).into(photoImageView);
//        } else {
//            messageTextView.setVisibility(View.VISIBLE);
//            photoImageView.setVisibility(View.GONE);
//            messageTextView.setText(message.getText());
//        }
//        authorTextView.setText(message.getName());

        return convertView;
    }

//    @Override
//    public ListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lostitem,parent,false);
//       return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ListItemAdapter.ViewHolder holder, int position) {
//            int resource=itemList.get(position).getImageView();
//            String t1=itemList.get(position).getTextView1();
//        String t2=itemList.get(position).getTextView2();
//
//        holder.setData(resource,t1,t2);
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView img;
//        private TextView tv1;
//        private TextView tv2;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//
//            img=itemView.findViewById(R.id.imageView1);
//            tv1=itemView.findViewById(R.id.textView1);
//            tv2=itemView.findViewById(R.id.textView2);
//        }
//
//        public void setData(int resource, String t1, String t2) {
//           img.setImageResource(resource);
//           tv1.setText(t1);
//           tv2.setText(t2);
//        }
//    }
}