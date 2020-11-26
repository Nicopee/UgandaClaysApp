package com.emst.cours.ecommerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emst.cours.ecommerce.model.Product;
import com.emst.cours.ecommerce.view.ProductView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class CategorySpecActivity extends AppCompatActivity {

    private String category;
    private RecyclerView category_list;
    private ImageView ferm;
    private TextView cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_spec);

        ferm = (ImageView) findViewById(R.id.ferm);
        cate = (TextView) findViewById(R.id.category);

        ferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySpecActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        category = getIntent().getExtras().get("category").toString();
        if(category.trim().equals("Computers")){
            cate.setText("Bricks");
        }else if(category.trim().equals("Smartphones")){
            cate.setText("Decoration Grills");
        }else if(category.trim().equals("Robes")){
            cate.setText("Ventilators");
        }else if(category.trim().equals("Shoes")){
            cate.setText("Roofing Tiles");
        }

        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(CategorySpecActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference.orderByChild("category").startAt(category).endAt(category) , Product.class).build();


        FirebaseRecyclerAdapter<Product, ProductView> adapter = new FirebaseRecyclerAdapter<Product, ProductView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductView holder, int position, @NonNull final Product model) {
                String[] products = new String[]{"Asus", "JUANITA GUERRERO HOYOS", "Nike","HUAYCO","SILLAO", "MERCADO","Scarpin","VINO BORGONA", "chuno","Robe","Iphone X","CEBOLLA","cocoa","Marta Paz Cumapa","aaa","cabello de angel","LECHE ANCHOR","Coutume","MORALES","Lenovo","I don't now","arroz","ji","aceite primor","CASILLERO DE HUEVO","Coutume","Costume","Homero","TARAPOTO","filete de atun","Balenciaga","Pudeure","AZUCAR","HUEQUITO","Ana cecilia grandes","hhh","Talon haute","Iphone XS","VENDEDOR1","Thiarakh","ajino men","Huawei","Samsung","VENDEDORA HUEQUITO","Asus","HP","Mac book","HUEQUITO"};
                String[] prices = new String[]{"3","29","1","250","345","47","1250","320","20","128","11"};
                // Convert String Array to List
                List<String> list = Arrays.asList(products);

                List<String> myPrice = Arrays.asList(prices);

                if(list.contains(model.getPname())){
                    holder.txtProductName.setVisibility(View.GONE);
                    holder.txtProductDescription.setVisibility(View.GONE);
                    holder.txtProductPrice.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.txtCurrency.setVisibility(View.GONE);
                    holder.linearLayout.setVisibility(View.GONE);
                }else if(myPrice.contains(model.getPrice())) {
                    holder.txtProductName.setVisibility(View.GONE);
                    holder.txtProductDescription.setVisibility(View.GONE);
                    holder.txtProductPrice.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.txtCurrency.setVisibility(View.GONE);
                    holder.linearLayout.setVisibility(View.GONE);
                }else {
                    holder.txtProductName.setText(model.getPname());
                    holder.txtProductDescription.setText(model.getDescription());
                    holder.txtProductPrice.setText(model.getPrice());
                    Picasso.get().load(model.getImage()).into(holder.imageView);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategorySpecActivity.this,ProductDetailsActivity.class);
                        intent.putExtra("pid",model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_layout, viewGroup, false);
                ProductView holder = new ProductView(view);
                return holder;
            }
        };

        category_list.setAdapter(adapter);
        adapter.startListening();
    }
}
