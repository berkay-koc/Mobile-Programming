package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Song> songs;
    Song song;
    public SongsAdapter(List<Song> songs){
        this.songs = songs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.song_row_item, parent, false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        song = songs.get(position);
        SongViewHolder viewHolder = (SongViewHolder) holder;

        viewHolder.durationHolder.setText(String.valueOf(song.getTrackDuration()));
        viewHolder.numberHolder.setText(String.valueOf(position+1));
        viewHolder.titleHolder.setText(song.getTrackName());
        viewHolder.artistHolder.setText(song.getArtist());

        Uri albumCover = song.getAlbumartUri();
        if(albumCover != null){
            viewHolder.albumcoverHolder.setImageURI(albumCover);

            if (viewHolder.albumcoverHolder.getDrawable() == null){
                viewHolder.albumcoverHolder.setImageResource(R.drawable.album_cover);
            }
        }
        else {
            viewHolder.albumcoverHolder.setImageResource(R.drawable.album_cover);
        }

        viewHolder.rowItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = song.songUri;
                Intent intent = new Intent(view.getContext(), MediaPlayerActivity.class);
                intent.putExtra("trackURI", uri.toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout rowItemLayout;
        ImageView albumcoverHolder;
        TextView numberHolder, titleHolder, durationHolder, artistHolder;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            rowItemLayout = itemView.findViewById(R.id.rowItemLayout);
            albumcoverHolder = itemView.findViewById(R.id.album_cover);
            numberHolder = itemView.findViewById(R.id.number);
            titleHolder = itemView.findViewById(R.id.title);
            artistHolder = itemView.findViewById(R.id.artist);
            durationHolder = itemView.findViewById(R.id.duration);

        }
    }

}
