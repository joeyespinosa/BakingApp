package com.axelia.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;


import com.axelia.bakingapp.R;
import com.axelia.bakingapp.ui.recipelist.MainActivity;
import com.axelia.bakingapp.util.Constants;

import static android.content.Context.MODE_PRIVATE;


public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredients);

        remoteViews.setRemoteAdapter(R.id.listview_widget, intent);
        remoteViews.setEmptyView(R.id.listview_widget, R.id.empty_view);

        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        String recipeName = prefs.getString(context.getString(R.string.recipe_name), "No Recipes Added");

        remoteViews.setTextViewText(R.id.textview_widget_recipe_name, recipeName);
        Intent recipeIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, recipeIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_list_container, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

