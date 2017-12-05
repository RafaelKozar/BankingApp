package com.example.rako.bankingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Rafael Kozar on 04/12/2017.
 */

public class BankWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BankWidgetFactoryAdpter(this);
    }
}
