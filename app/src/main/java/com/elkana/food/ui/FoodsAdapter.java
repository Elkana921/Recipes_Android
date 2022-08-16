package com.elkana.food.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.elkana.food.R;
import com.elkana.food.Singleton;
import com.elkana.food.models.recipes.Recipes;
import com.elkana.food.models.results.Results;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {

    public interface ClickListener {
        void clicked(Results foods);
    }
    public interface RoomClickListener {
        void clicked(Recipes foods);
    }

    //Properties:
    private List<Results> results;
    private List<Recipes> recipes;

    private ClickListener clickListener;
    private RoomClickListener roomClickListener;
    //Constructor:
    public FoodsAdapter(@Nullable List<Recipes> recipes,
                        @Nullable List<Results> results,
                        @Nullable RoomClickListener roomClickListener ) {
        this.recipes = recipes;
        this.results = results;
        this.roomClickListener = roomClickListener;
    }

    public FoodsAdapter(@Nullable List<Results> results, ClickListener clickListener) {
        this(null,results,null);
        this.clickListener = clickListener;

    }

    //Getter:
    public List<Results> getResults() {
        return results;
    }
    public List<Recipes> getRecipes() {
        return recipes;
    }

    //ViewHolder:
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Properties:
        ImageView ivImage;
        TextView tvTitle;
        TextView tvTimeInMin;
        TextView tvLikes;
        CardView cardViewTime;
        CardView cardViewLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTimeInMin = itemView.findViewById(R.id.tvTimeInMin);
            tvLikes = itemView.findViewById(R.id.tvLikes);

            cardViewTime = itemView.findViewById(R.id.cardViewTime);
            cardViewLikes = itemView.findViewById(R.id.cardViewLikes);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.food_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (recipes != null) {

            Recipes recipe = recipes.get(position);

            holder.tvTitle.setText(recipe.getTitle());

            String minute = Singleton.getInstance().minutesFormat(recipe.getReadyInMinutes());

            holder.tvTimeInMin.setText(minute + " ");

            String likesString = numberFormat(recipe.getLikes());
            likesString = likesString;
            holder.tvLikes.setText(likesString + " ");

            Picasso.get().
                    load(recipe.getUrlImage()).
                    placeholder(R.drawable.default_background_image).
                    into(holder.ivImage);
        }else{

            Results result = results.get(position);
            holder.tvTitle.setText(result.getTitle());

            holder.cardViewTime.setVisibility(View.GONE);
            holder.cardViewLikes.setVisibility(View.GONE);


            Picasso.get().
                    load(result.getUrlImage()).
                    placeholder(R.drawable.default_background_image).
                    into(holder.ivImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (recipes != null) {

                    Recipes recipe = recipes.get(position);

                    Bundle b = new Bundle();
                    b.putString("TITLE", recipe.getTitle());
                    b.putString("IMAGE", recipe.getUrlImage());
                    b.putString("URL", recipe.getGoToTheWeb());
                    b.putString("LIKES", numberFormat(recipe.getLikes()));
                    b.putInt("TIME", recipe.getReadyInMinutes());
                    b.putString("SUM", recipe.getSummary());

                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_detailsFragment, b);

                }else if(clickListener != null) {
                    clickListener.clicked(results.get(position));
                }

                if(roomClickListener!=null && recipes!=null) {
                    roomClickListener.clicked(recipes.get(position));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        } else {
            return results.size();
        }
    }

    //----- Methods: ------
    //Number Format:
    private static final NavigableMap<Long, String> suffixed = new TreeMap<>();

    static{
        suffixed.put(1_000L, "K");
        suffixed.put(1_000_000L, "M");
    }

    public static String numberFormat(long number) {

        if (number == Long.MIN_VALUE) return numberFormat(Long.MIN_VALUE + 1);
        if (number < 0) return "-" + numberFormat(-number);
        if (number < 1000) return Long.toString(number);

        Map.Entry<Long, String> e = suffixed.floorEntry(number);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = number / (divideBy / 10);
        boolean hadDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);

        return hadDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
