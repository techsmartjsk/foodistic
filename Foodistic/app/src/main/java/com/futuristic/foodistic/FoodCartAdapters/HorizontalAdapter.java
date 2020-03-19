package com.futuristic.foodistic.FoodCartAdapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.activity.CartActivity;
import com.futuristic.foodistic.activity.Details;
import com.futuristic.foodistic.model.GeneralFood;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.futuristic.foodistic.activity.MainActivity.cartFoods;
import static com.futuristic.foodistic.activity.MainActivity.cartUpdate;


public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>{

    private List<GeneralFood> popularFoods;
    private Context context;


    public static class HorizontalViewHolder extends RecyclerView.ViewHolder{

        LinearLayout horizontalLayout;
        ImageView popularImage;
        TextView popularTitle;
        TextView popularPrice;
        ImageButton popularPlus;

        public HorizontalViewHolder(View itemView) {
            super(itemView);

            horizontalLayout = itemView.findViewById(R.id.horizontal_parent_layout);
            popularImage = itemView.findViewById(R.id.popular_food_pc);
            popularPrice = itemView.findViewById(R.id.popular_food_price);
            popularTitle = itemView.findViewById(R.id.popular_food_title);
            popularPlus = itemView.findViewById(R.id.popular_food_plus);

        }
    }

    public HorizontalAdapter(List<GeneralFood> popularFoods, int horizontal_recyclerview, Context context){
        this.context = context;
        this.popularFoods = popularFoods;
    }

    @NonNull
    @Override
    public HorizontalAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_horizontal, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalAdapter.HorizontalViewHolder holder, final int position) {
        holder.popularTitle.setText(popularFoods.get(position).getTitle());
        holder.popularPrice.setText("₹" + (Double.toString((popularFoods.get(position).getPrice()))));
        Picasso.get().load(popularFoods.get(position).getImage()).fit().into(holder.popularImage);

        holder.popularPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartFoods.add(popularFoods.get(position));
                cartUpdate();
            }});

        holder.horizontalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("foodTitle", popularFoods.get(position).getTitle());
                intent.putExtra("foodPrice", popularFoods.get(position).getPrice());
                intent.putExtra("foodCalories", popularFoods.get(position).getCalory());
                intent.putExtra("foodDescription", popularFoods.get(position).getDescription());
                intent.putExtra("foodFat", popularFoods.get(position).getFat());
                intent.putExtra("foodSodium", popularFoods.get(position).getSodium());
                intent.putExtra("foodPotassium", popularFoods.get(position).getProtein());
                intent.putExtra("foodImage",popularFoods.get(position).getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularFoods.size();
    }
}
