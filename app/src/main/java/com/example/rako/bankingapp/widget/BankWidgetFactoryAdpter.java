package com.example.rako.bankingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.rako.bankingapp.R;
import com.example.rako.bankingapp.model.Ingredient;
import com.google.android.exoplayer2.upstream.BandwidthMeter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Kozar on 04/12/2017.
 */

public class BankWidgetFactoryAdpter implements RemoteViewsService.RemoteViewsFactory{
    private Context context;
    private List<Ingredient> ingredientList;

    public BankWidgetFactoryAdpter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("Teste", "teste", 5));
        ingredientList.add(new Ingredient("Teste", "teste", 5));
        ingredientList.add(new Ingredient("Teste", "teste", 5));
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
        view.setTextViewText(R.id.item_name_igredient, ingredientList.get(i).getIngredient());
        view.setTextViewText(R.id.item_measure, ingredientList.get(i).getIngredient());
        view.setTextViewText(R.id.item_quantity, String.valueOf(ingredientList.get(i).getQuantity()));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
