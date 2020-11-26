package com.emst.cours.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emst.cours.ecommerce.model.Product;
import com.emst.cours.ecommerce.prevalent.Prevalent;
import com.emst.cours.ecommerce.view.ProductView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

//This Home Activity is one displaying the products on a recyclerview.
// A recyclerview is like an advanced listview and can display a good number of products
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Uganda Clays");
        setSupportActionBar(toolbar);


        ImageView fab = (ImageView) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CardActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        if(Prevalent.currentOnlineUser != null){
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(ProductsRef, Product.class)
                        .build();


        FirebaseRecyclerAdapter<Product, ProductView> adapter =
                new FirebaseRecyclerAdapter<Product, ProductView>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductView holder, int position, @NonNull final Product model)
                    {

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
                                Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);
                        ProductView holder = new ProductView(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(HomeActivity.this,CardActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_search)
        {
            Intent intent = new Intent(HomeActivity.this,SearchProductActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_laptop)
        {
            Intent intent = new Intent(HomeActivity.this,CategorySpecActivity.class);
            intent.putExtra("category", "Computers");
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_smart)
        {
            Intent intent = new Intent(HomeActivity.this,CategorySpecActivity.class);
            intent.putExtra("category", "Smartphones");
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_dress)
        {
            Intent intent = new Intent(HomeActivity.this,CategorySpecActivity.class);
            intent.putExtra("category", "Robes");
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_shoes)
        {
            Intent intent = new Intent(HomeActivity.this,CategorySpecActivity.class);
            intent.putExtra("category", "Shoes");
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}