package com.example.testdelcaimanv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaimanAdapter extends RecyclerView.Adapter<CaimanAdapter.CaimanViewHolder> {

    private List<Caimanes.Caiman> caimanes;
    private Context context;

    public CaimanAdapter(Context context, List<Caimanes.Caiman> caimanes) {
        this.context = context;
        this.caimanes = caimanes;
    }

    @Override
    public CaimanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_caiman, parent, false);
        return new CaimanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CaimanViewHolder holder, int position) {
        Caimanes.Caiman caiman = caimanes.get(position);
        holder.tvNombre.setText(caiman.getNombre());
        holder.tvRango.setText(caiman.getRango());
        holder.tvAciertos.setText(String.valueOf(caiman.getAciertos()));
    }

    @Override
    public int getItemCount() {
        return caimanes.size();
    }

    public static class CaimanViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvRango;
        TextView tvAciertos;

        public CaimanViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvRango = itemView.findViewById(R.id.tvRango);
            tvAciertos = itemView.findViewById(R.id.tvAciertos);
        }
    }
}
