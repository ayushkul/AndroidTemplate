package com.ayushkul.models;

import android.content.Context;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class EventModel {
    public Object requestObject;
    public Object responseObject;
    public String notificationName;
    public Context context;

    public EventModel(Object requestObject, Object responseObject, String notificationName, Context context) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
        this.notificationName = notificationName;
        this.context = context;
    }

    public EventModel() {

    }
}
