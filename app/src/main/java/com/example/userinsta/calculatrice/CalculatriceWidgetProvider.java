package com.example.userinsta.calculatrice;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by userinsta on 10/04/2017.
 */

public class CalculatriceWidgetProvider extends AppWidgetProvider {

    private static RemoteViews mRemoteViews;
    private static AppWidgetManager mWidgetManager;
    private static Intent mIntent;
    private static Context mContext;
    private static int[] mWidgetsIds;

    private static CalculateModel model;

    private void setupIntent(String action, int id){
        mIntent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0);
        mRemoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    private void initVariables(Context context){
        mContext= context;
        updateWidgetIds();
        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
        mWidgetManager = AppWidgetManager.getInstance(mContext);
        model = new CalculateModel();
    }

    private void updateWidget(){
        for(int widgetId: mWidgetsIds){
            mWidgetManager.updateAppWidget(widgetId, mRemoteViews);
        }
    }

    private void updateWidgetIds(){
        final ComponentName componentName= new ComponentName(mContext, CalculatriceWidgetProvider.class);
        mWidgetManager = AppWidgetManager.getInstance(mContext);
        mWidgetsIds = mWidgetManager.getAppWidgetIds(componentName);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        initVariables(context);
        mIntent = new Intent(context, CalculatriceWidgetProvider.class);

        setupIntent(Constant.OPERATOR_PLUS, R.id.buttonPLus);
        setupIntent(Constant.OPERATOR_MOINS, R.id.buttonMoins);
        setupIntent(Constant.OPERATOR_DIV, R.id.buttonDiviser);
        setupIntent(Constant.OPERATOR_MULT, R.id.buttonMultiplier);
        setupIntent(Constant.NUMBER_POINT, R.id.buttonPoint);
        setupIntent(Constant.ACTION_CLEAR, R.id.buttonClear);
        setupIntent(Constant.ACTION_EGALE, R.id.buttonEgale);
        for(int i=0; i<10; i++) {
            setupIntent(String.valueOf(i), context.getResources()
                    .getIdentifier("button" + i, "id", context.getPackageName()));
        }
        updateWidget();
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        switch (action) {
            case Constant.OPERATOR_PLUS:
            case Constant.OPERATOR_MOINS:
            case Constant.OPERATOR_DIV:
            case Constant.OPERATOR_MULT:
            case Constant.NUMBER_POINT:
            case Constant.ACTION_CLEAR:
            case Constant.ACTION_EGALE:
            case Constant.NUMBER_0:
            case Constant.NUMBER_1:
            case Constant.NUMBER_2:
            case Constant.NUMBER_3:
            case Constant.NUMBER_4:
            case Constant.NUMBER_5:
            case Constant.NUMBER_6:
            case Constant.NUMBER_7:
            case Constant.NUMBER_8:
            case Constant.NUMBER_9:
                doAction(action, context);
                break;
            default:
                super.onReceive(context, intent);
        }
    }

    private void doAction(String action, Context context){
        if(model == null || mRemoteViews == null || mWidgetManager == null || mContext ==null){
            initVariables(context);
        }
        switch (action){
            case Constant.OPERATOR_PLUS:
            case Constant.OPERATOR_MOINS:
            case Constant.OPERATOR_MULT:
            case Constant.OPERATOR_DIV:
                model.setOperator(action);
                break;
            case Constant.ACTION_EGALE:
                double result = model.calculate();
                mRemoteViews.setTextViewText(R.id.TextViewEcran, String.valueOf(result));
                model.clear();
                model.setFirstValue(String.valueOf(result));
                break;
            case Constant.ACTION_CLEAR:
                model.clear();
                mRemoteViews.setTextViewText(R.id.TextViewEcran, "0");
                break;
            default:
                if(model.getOperator()==""){
                    model.addFirstValueNumber(action);
                    mRemoteViews.setTextViewText(R.id.TextViewEcran, String.valueOf(model.getFirstValue()));
                }else{
                    model.addSecondValueNumber(action);
                    mRemoteViews.setTextViewText(R.id.TextViewEcran, String.valueOf(model.getSecondValue()));
                }
                break;
        }
        updateWidget();
    }
}
