package com.invall.investproject.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.invall.investproject.R;
import com.invall.investproject.model.ContentData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AllContentDataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    
    private List<ContentData> contentList;

    public AllContentDataRecyclerViewAdapter(Context context, List<ContentData> content) {
        this.mInflater = LayoutInflater.from(context);
        this.contentList = content;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ContentData contentData = contentList.get(position);

        Picasso.get().load(contentData.getCardimage()).into(viewHolder.cardImage);
        viewHolder.cardTitle.setText(contentData.getCardtitle());
        viewHolder.cardText.setText(contentData.getCardtext());
    }

    @Override
    public int getItemViewType(int position)
    {
        return (contentList.get(position) != null) ? 1 : 2;
    }


    public ContentData getItem(int position) {
        int viewType = getItemViewType(position);

        return  viewType == 1
                ? contentList.get(position)
                : null;
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardTitle;
        TextView cardText;
        ImageView cardImage;

        ViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.cardtitle);
            cardText = itemView.findViewById(R.id.cardtext);
            cardImage = itemView.findViewById(R.id.cardImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), getItem(getAdapterPosition()));
        }
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder{

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, ContentData content);
    }
}