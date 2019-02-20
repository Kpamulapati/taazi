package com.example.taazi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taazi.entity.News;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<News> news;
    Context context;

    public RecyclerViewAdapter(List<News> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final News newsObject = this.news.get(i);

        myViewHolder.titleView.setText(newsObject.getTitle());
        myViewHolder.sourceView.setText(newsObject.getSource());
        myViewHolder.dateView.setText(newsObject.getSource());

       Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .cornerRadiusDp(20)
                .oval(false)
                .build();

        Picasso.with(context)
                .load(newsObject.getUrlToImage())
                .transform(transformation)
                .into(myViewHolder.imageView);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra("url", newsObject.getUrl());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(news == null ) return 0;
         return news.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView sourceView;
        TextView dateView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =(ImageView) itemView.findViewById(R.id.item_image);
            titleView = (TextView) itemView.findViewById(R.id.item_title);
            sourceView = (TextView) itemView.findViewById(R.id.item_source);
            dateView = (TextView) itemView.findViewById(R.id.item_date);

        }
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
