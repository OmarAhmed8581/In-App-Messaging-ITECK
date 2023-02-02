package com.itecknologi.iteckapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterFreqPlaces extends RecyclerView.Adapter<AdapterFreqPlaces.VH> {

    private List<ModelFreqPlaces> modelFreqPlaces;
    private ClickListener clickListener;
    private Context context;


    public AdapterFreqPlaces(List<ModelFreqPlaces> tripModelList, ClickListener clickListener, Context context) {
        this.modelFreqPlaces = tripModelList;
        this.clickListener = clickListener;
        this.context = context;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.freq_list_rv_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.setData(modelFreqPlaces.get(position).getTxtNoTimes(), modelFreqPlaces.get(position).getTxtLocation());

    }

    @Override
    public int getItemCount() {
        return modelFreqPlaces.size();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNotimes;
        private TextView txtLocation;


        public VH(@NonNull View itemView) {
            super(itemView);

            txtNotimes = itemView.findViewById(R.id.txtNoTimes);
            txtLocation = itemView.findViewById(R.id.listTitle);

        }

        private void setData(final String txtNotimes,
                             final String txtLocation) {
            this.txtNotimes.setText(txtNotimes);
            this.txtLocation.setText(txtLocation);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            clickListener.onItemClicked(getAdapterPosition());
        }
    }
}
