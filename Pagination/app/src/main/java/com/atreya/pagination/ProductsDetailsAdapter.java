package com.atreya.pagination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by atreya on 16/3/17.
 */

public class ProductsDetailsAdapter extends RecyclerView.Adapter<ProductsDetailsAdapter.MyViewHolder> {

    Context context;
    public static ArrayList<ProduceOnSaleData> productList = new ArrayList<>();
    MyViewHolder holder;
    ArrayList<ProduceOnSaleData> duplicate = null;

    public ProductsDetailsAdapter(Context context, ArrayList<ProduceOnSaleData> productList) {
        this.context = context;
        this.productList = productList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.buyer_product_list_items, parent, false);

//        ViewHolder viewHolder1 = new ViewHolder(view1);
        // work here if you need to control height of your items
        // keep in mind that parent is RecyclerView in this case
        int height = parent.getMeasuredHeight() / 4;
        view1.setMinimumHeight(height);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder1, final int position) {
        holder = holder1;
        DecimalFormat form = new DecimalFormat("0.00");
        final ProduceOnSaleData obj = productList.get(position);

        holder.itemView.setTag(position);
        holder.productName.setText(obj.getproductName());

        Double rate = Double.parseDouble(obj.getrate());//to show rate value in 0.00 format
        holder.rateVal.setText(form.format(rate));

        holder.unit.setText(obj.r_unit);

        holder.Quality.setText(obj.getquality());

        Double availableQuantityVal = Double.parseDouble(obj.getAvailable_quantity());//to show rate value in 0.00 format
        holder.productAvailableQuantity.setText(form.format(availableQuantityVal));

        holder.aqunit.setText(obj.getAq_unit());


        if (!obj.getimgUrl().isEmpty()) {
            Picasso.with(context).load(obj.getimgUrl()).resize(100, 100).centerCrop().placeholder(R.drawable.loadingimg)
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.no_pic_placeholder_with_border);
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rateVal, Quality, productName, productLocation, unit, productAvailableQuantity, aqunit;
        ImageView productImage;
        Button contact_btn, buy_btn;
        View itemView;
        public MyViewHolder(View itemView) {
            super(itemView);

            this.itemView =itemView;
            productName = (TextView) itemView.findViewById(R.id.productName);//name

            rateVal = (TextView) itemView.findViewById(R.id.productQuantity);//rate

            unit = (TextView) itemView.findViewById(R.id.unit);//rate unit

            Quality = (TextView) itemView.findViewById(R.id.productQuality);//quality

            productAvailableQuantity = (TextView) itemView.findViewById(R.id.productAvailableQuantity);//available q

            aqunit = (TextView) itemView.findViewById(R.id.aqunit);//available q unit


            productImage = (ImageView) itemView.findViewById(R.id.product_img);//img

            contact_btn = (Button) itemView.findViewById(R.id.contact_btn);//contact btn

            productLocation = (TextView) itemView.findViewById(R.id.productLocation);

            buy_btn = (Button) itemView.findViewById(R.id.buy_btn);//buy btn



        }


    }
    public void clear() {
        productList.clear();
        notifyDataSetChanged();
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<ProduceOnSaleData> results = new ArrayList<ProduceOnSaleData>();
                if (duplicate == null)
                    duplicate = productList;
                if (constraint != null) {
                    if (duplicate != null & duplicate.size() > 0) {
                        for ( final ProduceOnSaleData g :duplicate) {
                            if (g.getproductName().toLowerCase().contains(constraint.toString()))results.add(g);
                        }
                    }
                    oReturn.values = results;
                    Log.e("Searc res", String.valueOf(oReturn.values));
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productList = (ArrayList<ProduceOnSaleData>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}

