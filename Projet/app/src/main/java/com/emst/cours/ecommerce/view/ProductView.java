package com.emst.cours.ecommerce.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emst.cours.ecommerce.R;
import com.emst.cours.ecommerce.interfaces.ItemClickListner;


public class ProductView extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice, txtCurrency;
    public ImageView imageView;
    public ItemClickListner listner;
    public LinearLayout linearLayout;


    public ProductView(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtCurrency = (TextView) itemView.findViewById(R.id.curency);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.mylayout);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}