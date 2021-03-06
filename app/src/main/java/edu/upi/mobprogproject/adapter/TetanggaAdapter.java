package edu.upi.mobprogproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.upi.mobprogproject.R;
import edu.upi.mobprogproject.activity.DetailTetanggaActivity;
import edu.upi.mobprogproject.adapter.data.TetanggaList;
import edu.upi.mobprogproject.helper.DbUsers;

/**
 * Created by amaceh on 16/12/17.
 */

public class TetanggaAdapter extends RecyclerView.Adapter<TetanggaAdapter.ViewHolder> {
    private List<TetanggaList> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context ctx;
    private DbUsers dbU;

    // data is passed into the constructor
    public TetanggaAdapter(Context context, List<TetanggaList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        ctx = context;

        dbU = new DbUsers(context);
        dbU.open();
    }

    // inflates the row layout from xml when needed
    @Override
    public TetanggaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitems_tetangga, parent, false);
        TetanggaAdapter.ViewHolder viewHolder = new TetanggaAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(TetanggaAdapter.ViewHolder holder, int position) {
        final TetanggaList name = mData.get(position);
        //Log.i("stop", "onBindViewHolder: ");
        holder.nama.setText(name.getNama());
        holder.alamat.setText(name.getAlamat());
        holder.kontak.setText(name.getKontak());
        holder.jabatan.setText(name.getJabatan());
        holder.btDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EXTRA_MESSAGE = "edu.upi.mobproject.maps.MESSAGE";
                Intent i = new Intent(ctx, DetailTetanggaActivity.class);
                i.putExtra(EXTRA_MESSAGE, name.getUsername());
                ctx.startActivity(i);
            }
        });
        Glide.with(ctx).asBitmap().apply(RequestOptions.circleCropTransform()).load(dbU.getUser(name.getUsername()).getProfile_image()).into(holder.foto);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, alamat, kontak, jabatan;
        public ImageView foto;
        public Button btDetail;
        public ViewHolder(View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.imageView4);
            nama = itemView.findViewById(R.id.tvLTNama);
            alamat = itemView.findViewById(R.id.tvLTalamat);
            kontak = itemView.findViewById(R.id.tvLTkontak);
            jabatan = itemView.findViewById(R.id.tvLeader);
            btDetail = itemView.findViewById(R.id.btLTlengkap);
        }
    }

    // convenience method for getting data at click position
    public TetanggaList getItem(int id) {
        return mData.get(id);
    }

    public void filterList(ArrayList<TetanggaList> filterdNames) {
        this.mData = filterdNames;
        notifyDataSetChanged();
    }
}
