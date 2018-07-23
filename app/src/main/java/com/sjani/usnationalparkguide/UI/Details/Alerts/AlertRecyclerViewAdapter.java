package com.sjani.usnationalparkguide.UI.Details.Alerts;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjani.usnationalparkguide.Data.AlertContract;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertRecyclerViewAdapter extends RecyclerView.Adapter<AlertRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor cursor;

    public AlertRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alert, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);
            String title = cursor.getString(cursor.getColumnIndex(AlertContract.AlertEntry.COLUMN_ALERT_NAME));
            String description = cursor.getString(cursor.getColumnIndex(AlertContract.AlertEntry.COLUMN_ALERT_DESCRIPTION));
            String category = cursor.getString(cursor.getColumnIndex(AlertContract.AlertEntry.COLUMN_ALERT_CATEGORY));
            holder.alertTitleTv.setText(title);
            holder.alertDescriptionTv.setText(description);
            holder.alertCategoryTv.setText(category);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        @BindView(R.id.alert_title)
        TextView alertTitleTv;
        @BindView(R.id.alert_description)
        TextView alertDescriptionTv;
        @BindView(R.id.alert_category)
        TextView alertCategoryTv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, itemView);
        }

    }
}
