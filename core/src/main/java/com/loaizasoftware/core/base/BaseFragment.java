package com.loaizasoftware.core.base;

import androidx.fragment.app.Fragment;

import com.loaizasoftware.core.ui.LoaderView;

public class BaseFragment extends Fragment {

    public void showLoader() {
        LoaderView.getInstance(requireContext()).showLoader();
    }

    public void dismissLoader() {
        LoaderView.getInstance(requireContext()).dismissLoader();
    }

}
