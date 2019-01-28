package com.sjani.usnationalparkguide.UI.Details.Alerts;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjani.usnationalparkguide.Data.AlertEntity;
import com.sjani.usnationalparkguide.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertRecyclerViewAdapter extends RecyclerView.Adapter<AlertRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private Cursor cursor;
    private List<AlertEntity> alertEntities;

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
        AlertEntity alertEntity = alertEntities.get(position);
        String title = alertEntity.getAlert_name();
        String description = alertEntity.getDescription();
        String category = alertEntity.getCategory();
        holder.alertTitleTv.setText(title);
        holder.alertDescriptionTv.setText(description);
        holder.alertCategoryTv.setText(category);
    }

    @Override
    public int getItemCount() {
        if (alertEntities == null) {
            return 0;
        }
        return alertEntities.size();
    }

    public void swapAlerts(List<AlertEntity> alertEntityList) {
        if (alertEntities == alertEntityList) {
            return;
        }

        List<AlertEntity> temp = alertEntityList;
        this.alertEntities = alertEntityList;

        if (alertEntityList != null) {
            this.notifyDataSetChanged();
        }
        return;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
