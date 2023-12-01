package com.mca.ecommerceapplicationfinal.vansh.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mca.ecommerceapplicationfinal.vansh.R;
import com.mca.ecommerceapplicationfinal.vansh.databinding.FragmentHomeBinding;
import com.mca.ecommerceapplicationfinal.vansh.ui.home.Featured_Product.Product;
import com.mca.ecommerceapplicationfinal.vansh.ui.home.Featured_Product.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // View Pager
        ViewPager2 viewPager = root.findViewById(R.id.viewPagerImages);

        // Fetch image URLs from Firebase Realtime Database
        fetchImageUrlsFromFirebase(viewPager);

        // RecyclerView for Featured Products
        RecyclerView recyclerView = root.findViewById(R.id.featuredProductsRecyclerView);

        // Fetch featured products from Firebase
        fetchFeaturedProductsFromFirebase(recyclerView);

        return root;
    }

    private void fetchImageUrlsFromFirebase(ViewPager2 viewPager) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Pager Sliding Image");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> imageUrls = new ArrayList<>();

                    // Iterate through child nodes (1, 2, 3, ...)
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String imageUrl = childSnapshot.getValue(String.class);
                        if (imageUrl != null) {
                            imageUrls.add(imageUrl);
                        }
                    }

                    // Create the ImageSliderAdapter
                    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(requireContext(), imageUrls);

                    // Set the adapter for the ViewPager2
                    viewPager.setAdapter(imageSliderAdapter);

                    // Optionally, set automatic sliding
                    setAutomaticSliding(viewPager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }

    private void fetchFeaturedProductsFromFirebase(RecyclerView recyclerView) {
        DatabaseReference featuredProductsReference = FirebaseDatabase.getInstance().getReference().child("Featured Products").child("Products");

        featuredProductsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Product> productList = new ArrayList<>();

                    // Iterate through child nodes (1, 2, 3, ...)
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Assuming your Product class has a constructor that takes name, price, and imageURL
                        Product product = childSnapshot.getValue(Product.class);
                        if (product != null) {
                            productList.add(product);
                        }
                    }

                    // Create the ProductAdapter
                    ProductAdapter productAdapter = new ProductAdapter(productList , requireContext());

                    // Set the adapter for the RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }


    private void setAutomaticSliding(ViewPager2 viewPager) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPager.getAdapter().getItemCount();
                int nextItem = (currentItem + 1) % totalItems;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000); // 3 seconds interval
            }
        };
        handler.postDelayed(runnable, 3000); // Initial delay
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
