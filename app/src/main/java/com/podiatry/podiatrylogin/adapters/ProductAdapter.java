package com.podiatry.podiatrylogin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.podiatry.podiatrylogin.R;
import com.podiatry.podiatrylogin.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    private Context context;
    private List<Product> allProducts;
    private Filter itemFiltered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();
            if(constraint==null || constraint.length() == 0){
                filteredList.addAll(allProducts);
            }else{
                String filterPattern= constraint.toString().toLowerCase().trim();
                filteredList.add(allProducts.stream().filter(item -> item.getName().toLowerCase().contains(filterPattern)).findFirst().get());
            }
            FilterResults results= new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            allProducts.clear();
            allProducts.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
    public ProductAdapter(Context context, List<Product> allProducts) {
        this.context = context;
        this.allProducts = allProducts;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.item_product,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = allProducts.get(position);
        holder.item_name.setText(product.getName());
        holder.item_description.setText(String.format("Descripción de %s",product.getName()));
        holder.item_price.setText(String.format("$%1$,.2f",product.getPrice()));
        holder.item_total.setText(String.format("Stock: %s",product.getTotal()));
        byte[] bytes= Base64.decode(product.getImg(),Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.img_product.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }

    @Override
    public Filter getFilter() {
        return itemFiltered;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView item_name, item_description, item_total,item_price;
        private Button btn_add;
        private ImageView img_product;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=itemView.findViewById(R.id.item_name);
            item_price=itemView.findViewById(R.id.item_price);
            item_description=itemView.findViewById(R.id.item_description);
            item_total=itemView.findViewById(R.id.item_total);
            img_product=itemView.findViewById(R.id.img_product);
        }
    }
}
