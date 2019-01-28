package com.sjani.usnationalparkguide.UI.MainList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sjani.usnationalparkguide.Data.ParkEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.Listeners.GridItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListAdapterViewHolder> {

    private static final String TAG = ListAdapter.class.getSimpleName();
    final GridItemClickListener listener;
    private Context context;
    private List<ParkEntity> parkEntities;

    public ListAdapter(GridItemClickListener mListener, Context mContext) {
        this.listener = mListener;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_article, parent, false);
        ListAdapterViewHolder holder = new ListAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterViewHolder holder, int position) {

        ParkEntity parkEntity = parkEntities.get(position);
        String title = parkEntity.getPark_name();
        String imageUrl = parkEntity.getImage();
        holder.parkTitle.setText(title);
        if (imageUrl.equals("")) {
            Glide.with(holder.parkThumbnail.getContext())
                    .load(R.drawable.empty_detail)
                    .into(holder.parkThumbnail);
        } else {
            Glide.with(holder.parkThumbnail.getContext())
                    .load(imageUrl)
                    .into(holder.parkThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (parkEntities == null) {
            return 0;
        }
        return parkEntities.size();
    }

    public void swapParks(List<ParkEntity> parkEntityList) {
        if (parkEntities == parkEntityList) {
            return;
        }
        List<ParkEntity> temp = parkEntityList;
        this.parkEntities = parkEntityList;
        if (parkEntityList != null) {
            this.notifyDataSetChanged();
        }
        return;
    }

    public class ListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.park_title)
        TextView parkTitle;

        @BindView(R.id.park_thumbnail)
        SimpleDraweeView parkThumbnail;

        public ListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ParkEntity parkEntity = parkEntities.get(position);
            String parkId = parkEntity.getPark_id();
            String parkCode = parkEntity.getParkCode();
            String latlong = parkEntity.getLatLong();
            listener.onItemClick(parkId, latlong, position, parkCode);
        }
    }


}
