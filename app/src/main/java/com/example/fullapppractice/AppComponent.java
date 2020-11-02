package com.example.fullapppractice;

import com.example.fullapppractice.activity.LocaldbActivity;
import com.example.fullapppractice.activity.TableActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(LocaldbActivity localdbActivity);
    void inject(TableActivity tableActivity);
}
