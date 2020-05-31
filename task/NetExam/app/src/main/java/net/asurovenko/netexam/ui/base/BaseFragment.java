package net.asurovenko.netexam.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.squareup.otto.Bus;

import net.asurovenko.netexam.app.App;
import net.asurovenko.netexam.app.Preferences;
import net.asurovenko.netexam.network.NetExamApi;

import javax.inject.Inject;

import butterknife.ButterKnife;
import icepick.Icepick;

public abstract class BaseFragment extends Fragment {

    @Inject
    Bus bus;

    @Inject
    Preferences preferences;

    @Inject
    NetExamApi netExamApi;
    private ProgressDialog progressDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        App.getApp(this).getAppComponent().inject(this);

        if (savedInstanceState != null) {
            Icepick.restoreInstanceState(this, savedInstanceState);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Icepick.saveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public Bus getBus() {
        return bus;
    }

    protected Preferences getPreferences() {
        return preferences;
    }

    protected NetExamApi getNetExamApi() {
        return netExamApi;
    }

    protected void showProgressBar(String textProgressBar, int style) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(style);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(textProgressBar);
        progressDialog.show();
    }

    protected void hideProgressBar() {
        progressDialog.dismiss();
    }

    protected void showSnackBar(int messageId) {
        View view = getView();
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, getString(messageId), Snackbar.LENGTH_LONG);
            snackbar.getView().setPadding(0, 0, 0, 0);
            snackbar.show();
        }
    }

}