package com.elkana.food.details;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkana.food.MainActivity;
import com.elkana.food.R;
import com.elkana.food.Singleton;
import com.elkana.food.databinding.FragmentDetailsBinding;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {

    private DetailsViewModel mViewModel;
    private FragmentDetailsBinding binding;
    private String title;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        Bundle bag = getArguments();

        if (bag != null) {
            title = bag.get("TITLE").toString();
            String image = bag.get("IMAGE").toString();
            String url = bag.get("URL").toString();
            String likes = bag.get("LIKES").toString();
            String time = bag.get("TIME").toString();
            String sum = bag.get("SUM").toString();
            String sum2 = DetailsViewModel.html2text(sum);

            binding.tvDetails.setText(title);
            binding.tvSummary.setText(sum2);
            binding.tvLikesNumber.setText(likes + " ");

            String minutes = Singleton.getInstance().minutesFormat(Integer.parseInt(time));

            binding.tvTime.setText(minutes + " ");

            binding.floatBtnGoToWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = mViewModel.goToWeb(url);
                    startActivity(intent);
                }
            });

            Picasso.get().
                    load(image).
                    placeholder(R.drawable.default_background_image).
                    into(binding.ivImage);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).hideSearchIcon();
        ((MainActivity) getActivity()).setTitle(title);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

}