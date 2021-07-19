package com.podiatry.podiatrylogin.controlPanel;


import android.os.Bundle;

import androidx.annotation.NonNull;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.podiatry.podiatrylogin.R;
import com.podiatry.podiatrylogin.adapters.ProductAdapter;
import com.podiatry.podiatrylogin.controlPanel.viewModel.AllProductsViewModel;
import com.podiatry.podiatrylogin.data.model.Product;

import java.util.List;

public class AllProductsFragment extends Fragment {
    private AllProductsViewModel viewModel;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    public AllProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_all_products, container, false);
        viewModel= new ViewModelProvider(this).get(AllProductsViewModel.class);
        viewModel.getFromAPI();
        //set adapter into recycler view
        recyclerView= root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));



        Observer<List<Product>> observer= products -> {
            products.stream().forEach(System.out::println);
            productAdapter= new ProductAdapter(root.getContext(),products);
            recyclerView.setAdapter(productAdapter);
        };
        viewModel.getAllProducts().observe(getViewLifecycleOwner(),observer);


        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem=menu.findItem(R.id.nav_search);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("----> "+newText);
                Log.i("query",newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}