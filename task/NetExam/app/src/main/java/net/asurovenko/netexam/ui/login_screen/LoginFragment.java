package net.asurovenko.netexam.ui.login_screen;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.events.OpenRegisterFragmentEvent;
import net.asurovenko.netexam.network.models.Login;
import net.asurovenko.netexam.network.models.User;
import net.asurovenko.netexam.ui.MainActivity;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.utils.ServerUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends BaseFragment {
    @Bind(R.id.login_field)
    TextView login;

    @Bind(R.id.pass_field)
    TextView pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @OnClick(R.id.register_btn)
    public void registerBtnClick() {
        getBus().post(new OpenRegisterFragmentEvent());
    }

    @OnClick(R.id.login_btn)
    public void loginClick() {
        if (login.getText().toString().isEmpty()) {
            login.setError(getString(R.string.cannot_be_empty_field));
            login.performClick();
            return;
        }
        if (pass.getText().length() < 8) {
            pass.setError(getString(R.string.min_eight_characters));
            pass.performClick();
            return;
        }
        showProgressBar(getString(R.string.wait_with_dots), ProgressDialog.STYLE_SPINNER);
        getNetExamApi()
                .login(new Login(login.getText().toString(), pass.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLogin, this::errorLogin);
    }

    private void completeLogin(User user) {
        ((MainActivity) getActivity()).setupUser(user);
        hideProgressBar();
    }

    private void errorLogin(Throwable error) {
        hideProgressBar();
        showSnackBar(ServerUtils.getMsgId(error));
    }
}

