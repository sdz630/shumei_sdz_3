package com.example.photoshow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.Size;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mDatas;//urls
    private Random random;

    //构造器
    public RecyclerViewAdapter( List<String> data, Context mContext) {
        this.mLayoutInflater = mLayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mDatas = data;
        random = new Random();
    }

    //创建一个可复用的ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = mLayoutInflater.inflate(R.layout.item_single_textview, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    //数据和ViewHolder中的内容通过position绑定
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Uri uri  = Uri.parse(mDatas.get(position));

        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();

        //随机数生成ImageView的高度，动态设置给ImagView实现瀑布流效果
        params.height = random.nextInt(200)+300;
//        params.height = 500;
        holder.imageView.setLayoutParams(params);

        //Glide 加载图片

        Glide.with(mContext).load(uri).into(holder.imageView);

        //设置ImageView的点击事件
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
////            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
////            @Override
////            public void onClick(View v) {
////
////                Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
////                v.getContext().startActivity(
////                        new Intent(v.getContext(),DetailActivity.class),
////                        ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(), v, "sharedView").toBundle());
//////                Intent intent = new Intent(mContext, DetailActivity.class);
//////
//////                intent.putExtra("uri",mDatas.get(position));
//////
//////                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext
//////                        ,holder.imageView,"share").toBundle());
////
////            }
////        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                        Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("uri",mDatas.get(position));
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,holder.imageView,"sharedView").toBundle());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}

//通过继承ViewHolder这个抽象类实现View的封装
class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;

    public MyViewHolder(View itemView) {

        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
