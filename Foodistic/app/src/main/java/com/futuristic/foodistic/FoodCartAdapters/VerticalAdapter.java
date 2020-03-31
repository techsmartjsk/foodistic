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

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.activity.Details;
import com.futuristic.foodistic.model.GeneralFood;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.futuristic.foodistic.activity.MainActivity.cartFoods;
import static com.futuristic.foodistic.activity.MainActivity.cartUpdate;



public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {

    private List<GeneralFood> regularFoods;
    private Context context;

    public VerticalAdapter(List<GeneralFood> regularFoods, Context applicationContext) {

    }


    public static class VerticalViewHolder extends RecyclerView.ViewHolder{

        LinearLayout verticalLayout;
        TextView regularTitle;
        TextView regularPrice;
        ImageView regularImage;
        ImageButton regularPlus;

        public VerticalViewHolder(View itemView) {
            super(itemView);

            verticalLayout = itemView.findViewById(R.id.vertical_parent_layout);
            regularTitle = itemView.findViewById(R.id.regular_food_title);
            regularImage = itemView.findViewById(R.id.regular_food_pc);
            regularPrice = itemView.findViewById(R.id.regular_food_price);
            regularPlus = itemView.findViewById(R.id.regular_food_plus);

        }
    }

    public VerticalAdapter(List<GeneralFood> regularFoods, int vertical_recyclerview, Context context){
        this.context = context;
        this.regularFoods = regularFoods;
    }

    @NonNull
    @Override
    public VerticalAdapter.VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_vertical, parent, false);
        return new VerticalAdapter.VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VerticalAdapter.VerticalViewHolder holder, final int position) {
        holder.regularTitle.setText(regularFoods.get(position).getTitle());
        holder.regularPrice.setText("₹" + (Double.toString((regularFoods.get(position).getPrice()))));
        Picasso.get().load(regularFoods.get(position).getImage()).fit().into(holder.regularImage);

        holder.regularPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartFoods.add(regularFoods.get(position));
                cartUpdate();


            }});

        holder.verticalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("foodTitle", regularFoods.get(position).getTitle());
                intent.putExtra("foodPrice", regularFoods.get(position).getPrice());
                intent.putExtra("foodCalories", regularFoods.get(position).getCalory());
                intent.putExtra("foodDescription", regularFoods.get(position).getDescription());
                intent.putExtra("foodFat", regularFoods.get(position).getFat());
                intent.putExtra("foodSodium", regularFoods.get(position).getSodium());
                intent.putExtra("foodPotassium", regularFoods.get(position).getProtein());
                intent.putExtra("foodImage",regularFoods.get(position).getImage());
                context.startActivity(intent);
            }
        });}



    @Override
    public int getItemCount() {
        return regularFoods.size();
    }
}
