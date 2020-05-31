package net.asurovenko.netexam.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.app.App;
import net.asurovenko.netexam.app.Preferences;

import javax.inject.Inject;

import icepick.Icepick;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Bus bus;

    @Inject
    Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(this).getAppComponent().inject(this);
        if (savedInstanceState != null) {
            Icepick.restoreInstanceState(this, savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Icepick.saveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, true, null);
    }

    protected void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(fragment, addToBackStack, null);
    }

    protected void replaceFragment(Fragment fragment, boolean addToBackStack, @Nullable String key) {
        FragmentTransaction replaceTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment);
        if (addToBackStack)
            replaceTransaction
                    .addToBackStack(key);

        replaceTransaction
                .commit();
    }

    protected void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    protected void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    protected void returnToBackStack(String stackKey, boolean inclusive) {
        //FragmentManager.POP_BACK_STACK_INCLUSIVE (1) - вытолкнуть из стэка и закрыть не только фрагменты выше
        //добавленного по ключу, но и сам фрагмент, добавленный по ключу
        //0 - вытолкнуть и закрыть только те, что выше
        getSupportFragmentManager()
                .popBackStackImmediate(stackKey, inclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);

    }

    public Bus getBus() {
        return bus;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}