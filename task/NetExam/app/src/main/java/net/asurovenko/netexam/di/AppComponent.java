package net.asurovenko.netexam.di;


import net.asurovenko.netexam.ui.base.BaseActivity;
import net.asurovenko.netexam.ui.base.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BaseActivity activity);

    void inject(BaseFragment fragment);

}
