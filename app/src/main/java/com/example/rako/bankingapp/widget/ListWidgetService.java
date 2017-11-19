package com.example.rako.bankingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Rafael Kozar on 19/11/2017.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}
