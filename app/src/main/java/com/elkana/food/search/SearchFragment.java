package com.elkana.food.search;

import static com.elkana.food.ui.FoodsAdapter.numberFormat;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.databinding.tool.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.elkana.food.MainActivity;
import com.elkana.food.R;
import com.elkana.food.databinding.FragmentSearchBinding;
import com.elkana.food.models.recipes.Recipes;
import com.elkana.food.models.api.RecipesApiManager;
import com.elkana.food.ui.FoodsAdapter;

import org.w3c.dom.Text;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        //onSuccess:
        mViewModel.getFoodsLiveData().observe(getViewLifecycleOwner(), results -> {

            RecyclerView rvRecipes = binding.getRoot().findViewById(R.id.rvFoodsSearch);

            rvRecipes.setLayoutManager(new GridLayoutManager(getContext(), 2));

            rvRecipes.setAdapter(new FoodsAdapter(results, clickedFood -> {

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Foods");
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                mViewModel.getInfo(String.valueOf(clickedFood.getTheId()), new RecipesApiManager.RecipeInfoCallback() {

                    @Override
                    public void onSuccess(Recipes recipe) {

                        progressDialog.dismiss();

                        Bundle b = new Bundle();
                        b.putString("TITLE", recipe.getTitle());
                        b.putString("IMAGE", recipe.getUrlImage());
                        b.putString("URL", recipe.getGoToTheWeb());
                        b.putString("LIKES", numberFormat(recipe.getLikes()));
                        b.putInt("TIME", recipe.getReadyInMinutes());
                        b.putString("SUM", recipe.getSummary());
                        NavHostFragment.findNavController(SearchFragment.this).
                                navigate(R.id.action_searchFragment_to_detailsFragment, b);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                });
            }));
        });

        //onFail:
        mViewModel.getFoodsExcData().observe(getViewLifecycleOwner(), exc -> {
            System.err.println("--- Error:\n" + exc);
            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
        });

        //SearchView:
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String textInput) {
                mViewModel.getFoods(textInput);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideSearchIcon();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setTitle("Search");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}