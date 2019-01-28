package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjani.usnationalparkguide.Data.CampEntity;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CampgroundRecyclerViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<CampgroundRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = CampgroundRecyclerViewAdapter.class.getSimpleName();
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private Cursor cursor;
    private List<CampEntity> campEntities;

    public CampgroundRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context context) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_campgound, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CampEntity campEntity = campEntities.get(position);
        String title = campEntity.getCamp_name();
        holder.campTitleTv.setText(title);
    }

    @Override
    public int getItemCount() {
        if (campEntities == null) {
            return 0;
        }
        return campEntities.size();
    }

    public void swapCamps(List<CampEntity> campEntityList) {
        if (campEntities == campEntityList) {
            return;
        }

        List<CampEntity> temp = campEntityList;
        this.campEntities = campEntityList;

        if (campEntityList != null) {
            this.notifyDataSetChanged();
        }
        return;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        @BindView(R.id.camp_list_title)
        TextView campTitleTv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            CampEntity campEntity = campEntities.get(position);
            String campId = campEntity.getCamp_id();
            mListener.onListFragmentInteraction(campId, position);

        }
    }
}
