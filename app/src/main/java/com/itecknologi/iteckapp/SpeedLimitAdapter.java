package com.itecknologi.iteckapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpeedLimitAdapter extends RecyclerView.Adapter<SpeedLimitAdapter.VH> {

    private List<SpeedLimitModel> speedLimitModels;

    public SpeedLimitAdapter(List<SpeedLimitModel> driverListModelList) {
        this.speedLimitModels = driverListModelList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.speed_limit_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.setData(speedLimitModels.get(position).getDay(), speedLimitModels.get(position).getLocation(),
                speedLimitModels.get(position).getSpeed());
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static public class VH extends RecyclerView.ViewHolder {

        private TextView txtDate;
        private TextView txtLocation;
        private TextView txtHours;

        public VH(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDay);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtHours = itemView.findViewById(R.id.txtSpeed);

        }

        private void setData(final String date, final String location, final String speed) {
            this.txtDate.setText(date);
            this.txtLocation.setText(location);
            this.txtHours.setText(speed);
        }


    }
}
