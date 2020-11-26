package com.emst.cours.ecommerce.interfaces;

import android.view.View;


// This interface is implemented on every click of the product
public interface ItemClickListner
{
    void onClick(View view, int position, boolean isLongClick);
}