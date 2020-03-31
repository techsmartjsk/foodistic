package com.futuristic.foodistic.FoodCartAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.activity.MainActivity;
import com.futuristic.foodistic.model.GeneralFood;

import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.futuristic.foodistic.activity.MainActivity.cartFoods;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartViewHolder> {

    private Context context;
    private CartAdapter mCartAdapter;


    @NonNull
    @Override
    public OrderAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_order, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        holder.cartTitle.setText(cartFoods.get(position).getTitle());
        holder.cartPrice.setText("₹" + (Double.toString((cartFoods.get(position).getPrice()))));


    }

    @Override
    public int getItemCount() {
        return cartFoods.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartPicture;
        TextView cartTitle;
        TextView cartPrice;
        CardView cartParentLayout;
        ImageButton cartDelete;

        public CartViewHolder(View itemView) {
            super(itemView);

            cartPicture = itemView.findViewById(R.id.cart_food_pic);
            cartTitle = itemView.findViewById(R.id.cart_food_title);
            cartPrice = itemView.findViewById(R.id.cart_food_price);
            cartParentLayout = itemView.findViewById(R.id.cart_parent_layout);


        }
    }

    public OrderAdapter(List<GeneralFood> cartFoods, int recyclerview_order, Context context){
        this.context = context;
        MainActivity.cartFoods = cartFoods;

    }


}
