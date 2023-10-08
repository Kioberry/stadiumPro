package com.wwdw.easytimeapplication.base;

import com.wwdw.easytimeapplication.uitls.Config;

public abstract class BasePresenter {
    private Config config;

    public Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }
}
