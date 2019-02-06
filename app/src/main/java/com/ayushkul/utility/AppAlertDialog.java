package com.ayushkul.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;

import com.ayushkul.R;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class AppAlertDialog {

        private AppAlertDialogClickListener alertDialogClickListener;

        public AppAlertDialog(AppAlertDialogClickListener currentObject) {
            alertDialogClickListener = currentObject;
        }

        public void showLhtDialog(Context context, String title, String message, String okButtonText) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);

            builder.setMessage(message)
                    .setPositiveButton(okButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            alertDialogClickListener.dialogOkClicked(dialog);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        public void showLhtDialog(Context context, String title, String message, String okButtonText, String cancelButtonText, boolean... isCancelable) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);

            builder.setCancelable(isCancelable.length > 0 && isCancelable[0]);

            builder.setMessage(message)
                    .setPositiveButton(okButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            alertDialogClickListener.dialogOkClicked(dialog);
                        }
                    })
                    .setNegativeButton(cancelButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialogClickListener.dialogCancelClicked(dialog);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        public void showPermissionDialog(Context context, Spanned title, String message, String okButtonText, String cancelButtonText, Drawable resId) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);
            builder.setCancelable(false);
            builder.setIcon(resId);

            builder.setMessage(message)
                    .setPositiveButton(okButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (alertDialogClickListener != null)
                                alertDialogClickListener.dialogOkClicked(dialog);
                        }
                    })
                    .setNegativeButton(cancelButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (alertDialogClickListener != null)
                                alertDialogClickListener.dialogCancelClicked(dialog);
                        }
                    });

            builder.show();
        }

        public void showPermissionDialog(Context context, Spanned title, String message, String okButtonText, Drawable resId) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);
            builder.setCancelable(false);
            builder.setIcon(resId);

            builder.setMessage(message)
                    .setPositiveButton(okButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if (alertDialogClickListener != null)
                                alertDialogClickListener.dialogOkClicked(dialog);
                        }
                    });

            builder.show();
        }

        public interface AppAlertDialogClickListener {
            void dialogOkClicked(DialogInterface dialog);

            void dialogCancelClicked(DialogInterface dialog);
        }
    }