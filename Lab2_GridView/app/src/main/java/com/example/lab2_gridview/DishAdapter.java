package com.example.lab2_gridview;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.lab2_gridview.R;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {
    private Activity context;

    public DishAdapter(Activity context, List<Dish> dishes) {
        super(context, 0, dishes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_dish, null);
        }

        Dish dish = getItem(position);

        ImageView thumbnailImageView = view.findViewById(R.id.dish_thumbnail);
        TextView dishNameTextView = view.findViewById(R.id.dish_name);
        ImageView promotionIconImageView = view.findViewById(R.id.promotion_icon);

        thumbnailImageView.setImageResource(dish.getThumbnail());
        dishNameTextView.setText(dish.getDishName());

        if (dish.hasPromotion()) {
            promotionIconImageView.setVisibility(View.VISIBLE);
        } else {
            promotionIconImageView.setVisibility(View.GONE);
        }

        return view;
    }
}
