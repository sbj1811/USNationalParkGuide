package com.example.android.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.usnationalparkguide.Data.CampContract;
import com.example.android.usnationalparkguide.R;
import com.example.android.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampgroundRecyclerViewAdapter extends RecyclerView.Adapter<CampgroundRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = CampgroundRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private final OnListFragmentInteractionListener mListener;
    private Cursor cursor;

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
        cursor.moveToPosition(position);
        String title = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_NAME));
        holder.campTitleTv.setText(title);
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
            cursor.moveToPosition(position);
            String campId = cursor.getString(cursor.getColumnIndex(CampContract.CampEntry.COLUMN_CAMP_ID));
            mListener.onListFragmentInteraction(campId,position);

        }
    }
}
