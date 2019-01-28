package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sjani.usnationalparkguide.Data.TrailEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailRecyclerViewAdapter extends RecyclerView.Adapter<TrailRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = TrailRecyclerViewAdapter.class.getSimpleName();
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private List<TrailEntity> trailEntities;

    public TrailRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context context) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TrailEntity trailEntity = trailEntities.get(position);
        String title = trailEntity.getTrail_name();
        String imageUrl = trailEntity.getImage_med();
        holder.trailTitleView.setText(title);
        if (imageUrl.equals("")) {
            Glide.with(holder.trailImageView.getContext())
                    .load(R.drawable.empty_detail)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(holder.trailImageView);
        } else {
            Glide.with(holder.trailImageView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(holder.trailImageView);
        }

    }

    @Override
    public int getItemCount() {
        if (trailEntities == null) {
            return 0;
        }
        return trailEntities.size();
    }

    public void swapTrails(List<TrailEntity> trailEntityList) {
        if (trailEntities == trailEntityList) {
            return;
        }
        List<TrailEntity> temp = trailEntityList;
        this.trailEntities = trailEntityList;
        if (trailEntityList != null) {
            this.notifyDataSetChanged();
        }
        return;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        @BindView(R.id.trail_title)
        TextView trailTitleView;
        @BindView(R.id.trail_image)
        ImageView trailImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TrailEntity trailEntity = trailEntities.get(position);
            String trailId = trailEntity.getTrail_id();
            mListener.onListFragmentInteraction(trailId, position);
        }
    }
}
