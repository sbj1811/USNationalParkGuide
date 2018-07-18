package com.example.android.usnationalparkguide.UI.Details.Trails;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.usnationalparkguide.Data.TrailContract;
import com.example.android.usnationalparkguide.R;
import com.example.android.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailRecyclerViewAdapter extends RecyclerView.Adapter<TrailRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = TrailRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private final OnListFragmentInteractionListener mListener;
    private Cursor cursor;

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
        cursor.moveToPosition(position);
        String title = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_NAME));
        String imageUrl = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_MED));
        holder.trailTitleView.setText(title);
        if (imageUrl.equals("")){
            Glide.with(holder.trailImageView.getContext())
                    .load(R.drawable.empty_detail)
                    .fitCenter()
                    .into(holder.trailImageView);
        } else {
            Glide.with(holder.trailImageView.getContext())
                    .load(imageUrl)
                    .fitCenter()
                    .into(holder.trailImageView);
        }
    }

    @Override
    public int getItemCount() {
        if(cursor == null){
            return 0;
        }
        return cursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (cursor == c) {
            return null;
        }

        Cursor temp = cursor;
        this.cursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
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
            cursor.moveToPosition(position);
            String trailId = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_ID));
            Log.e(TAG, "onClick: "+trailId+" "+position);
            mListener.onListFragmentInteraction(trailId,position);
        }
    }
}
