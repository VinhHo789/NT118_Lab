package com.example.lab2_gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {

    public ThumbnailAdapter(@NonNull Context context, @NonNull List<Thumbnail> thumbnails) {
        super(context, 0, thumbnails);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_thumbnail, parent, false);
        }

        Thumbnail currentThumbnail = getItem(position);

        ImageView selectedThumbnailImageView = view.findViewById(R.id.selectedThumbnailImageView);
        TextView selectedThumbnailNameTextView = view.findViewById(R.id.selectedThumbnailNameTextView);

        selectedThumbnailImageView.setImageResource(currentThumbnail.getImageResource());
        selectedThumbnailNameTextView.setText(currentThumbnail.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_thumbnail, parent, false);
        }

        Thumbnail currentThumbnail = getItem(position);

        ImageView thumbnailImageView = view.findViewById(R.id.thumbnailImageView);
        TextView thumbnailNameTextView = view.findViewById(R.id.thumbnailNameTextView);

        thumbnailImageView.setImageResource(currentThumbnail.getImageResource());
        thumbnailNameTextView.setText(currentThumbnail.getName());

        return view;
    }
}
