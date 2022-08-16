package com.elkana.food.home;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elkana.food.R;
import com.elkana.food.databinding.FragmentHomeBinding;
import com.elkana.food.models.api.RecipesApiManager;
import com.elkana.food.models.results.Results;
import com.elkana.food.room.RoomCallback;
import com.elkana.food.ui.FoodsAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //onResponse
        mViewModel.getRecipesLiveData().observe(getViewLifecycleOwner(), response -> {
            if(response.status == 402) {
              openDialog();
              return;
            }

            RecyclerView rvRecipes = binding.getRoot().findViewById(R.id.rvFoods);

            rvRecipes.setLayoutManager(new GridLayoutManager(getContext(), 1));

            rvRecipes.setAdapter(new FoodsAdapter(response.payload, null, foods ->
                    mViewModel.saveId(foods.getId(), data ->
                            getActivity().runOnUiThread(() ->
                                    Toast.makeText(getActivity().getApplicationContext(),data,Toast.LENGTH_LONG).show()))));

            binding.swipeRefresh.setRefreshing(false);
        });

        //swipeRefresh:
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshRecipes();
            }
        });

        return binding.getRoot();
    }

    //Methods:
    public void openDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Error Code: 402")
                .setMessage("Your daily points limit of 150 has been reached.\n\nTry again later!")
                .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}