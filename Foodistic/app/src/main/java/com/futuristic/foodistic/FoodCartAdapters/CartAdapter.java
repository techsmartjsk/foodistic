package com.futuristic.foodistic.FoodCartAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futuristic.foodistic.R;
import com.futuristic.foodistic.activity.MainActivity;
import com.futuristic.foodistic.model.GeneralFood;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.futuristic.foodistic.activity.CartActivity.grandTotal;
import static com.futuristic.foodistic.activity.CartActivity.priceAdjust;
import static com.futuristic.foodistic.activity.MainActivity.cartFoods;
import static com.futuristic.foodistic.activity.MainActivity.cartUpdate;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private CartAdapter mCartAdapter;
    public int i = 1;


    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        holder.cartTitle.setText(cartFoods.get(position).getTitle());
        holder.cartPrice.setText("₹" + (Double.toString((cartFoods.get(position).getPrice()))));
        Picasso.get().load(cartFoods.get(position).getImage()).fit().into(holder.cartPicture);


        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralFood item = cartFoods.get(position);
                cartFoods.remove(item);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartFoods.size());

                grandTotal(cartFoods);
                priceAdjust();

                cartUpdate();

            }
        });
        holder.NumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++i;
                holder.NumberBtn.setText(String.valueOf(i));
            }
        });
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
        Button NumberBtn;

        public CartViewHolder(View itemView) {
            super(itemView);

            cartPicture = itemView.findViewById(R.id.cart_food_pic);
            cartTitle = itemView.findViewById(R.id.cart_food_title);
            cartPrice = itemView.findViewById(R.id.cart_food_price);
            cartParentLayout = itemView.findViewById(R.id.cart_parent_layout);
            cartDelete = itemView.findViewById(R.id.cart_food_delete);
            NumberBtn = itemView.findViewById(R.id.NumberBtn);
        }
    }

    public CartAdapter(List<GeneralFood> cartFoods, int recyclerview_cart, Context context){
        this.context = context;
        MainActivity.cartFoods = cartFoods;

    }
}
