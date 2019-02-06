package com.ayushkul.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.ayushkul.R;
import com.ayushkul.constants.AppConstants;
import com.ayushkul.managers.preferences.SharedPreferenceManager;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class AppUtility {

    private static Toast toast;
    private static Snackbar snackbar;
    private final static AtomicInteger smallNotification = new AtomicInteger(0);

    public static void showSnackBarAtBottom(Context context, String message) {
        if (message == null)
            return;

        try {
            snackbar.getView().isShown();
            snackbar.setText(message);
        } catch (Exception e) {
            snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, BaseTransientBottomBar.LENGTH_LONG);
        }
        if (snackbar != null)
            snackbar.show();
    }

    @SuppressLint("ShowToast")
    public static void showToastAtBottom(Context context, int message) {

        try {
            toast.getView().isShown();     // true if visible
            toast.setText(message);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (toast != null)
            toast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToastAtBottom(Context context, String message) {

        try {
            toast.getView().isShown();     // true if visible
            toast.setText(message);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (toast != null)
            toast.show();
    }


    public static void hideSoftKeyboard(Context context) {
        toggleSoftKeyboard(context, null, false);
    }

    public static void hideSoftKeyboard(Context context, EditText editText) {
        toggleSoftKeyboard(context, editText, false);
    }

    public static void showSoftKeyboard(Context context, EditText editText) {
        toggleSoftKeyboard(context, editText, true);
    }

    private static void toggleSoftKeyboard(Context context, View view, boolean show) {
        if (context == null)
            return;

        if (view == null)
            view = ((Activity) context).getCurrentFocus();

        if (view == null)
            return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null)
            return;

        if (show) {
            view.requestFocus();
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            IBinder iBinder = view.getWindowToken();
            if (iBinder == null)
                return;

            inputMethodManager.hideSoftInputFromWindow(iBinder, 0);
            view.clearFocus();
        }
    }

    public static int[] getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dpHeight = displayMetrics.heightPixels;
        int dpWidth = displayMetrics.widthPixels;
        return new int[]{dpHeight, dpWidth};
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        return displaymetrics.widthPixels;
    }

    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.trim().length() == 0;
    }


    public static void showUnderDevelopmentAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
        builder.setMessage(context.getString(R.string.under_development));
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void showNoInternetToast(Context context) {
        if (context == null)
            return;
        showToastAtBottom(context, context.getString(R.string.no_internet_available));
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceUniqueId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && !activeNetworkInfo.isConnected())
                showNoInternetToast(context);  //if no network available, it shows No internet toast-
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


    public static void showAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static String changeDateFormat(Date date, String outputDateFormat) {
        String dateString;

        SimpleDateFormat outputSource = new SimpleDateFormat(outputDateFormat, Locale.getDefault());
//        if (setUTC)
//            outputSource.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateString = outputSource.format(date);

        return dateString;
    }

    /**
     * Convert the date string from one format to other.
     *
     * @param dateString       String
     * @param inputDateFormat  String
     * @param outputDateFormat String
     * @return dateString
     */
    public static String changeDateFormat(String dateString, String inputDateFormat, String outputDateFormat) {
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat(inputDateFormat, Locale.US);
            Date date = sdfSource.parse(dateString);

            SimpleDateFormat outputSource = new SimpleDateFormat(outputDateFormat, Locale.US);
            dateString = outputSource.format(date);

        } catch (Exception e) {
            Log.d("Exception", String.valueOf(e));
        }
        return dateString;

    }

    public static String getDateFromEpoch(long epoch, String outputDateFormat) {
        String dateString;
        Date date = new Date(epoch);
        dateString = changeDateFormat(date, outputDateFormat);
        return dateString;
    }

    public static String getDateFromEpochForView(long epoch) {
        Date date = new Date(epoch);
        String dateString = changeDateFormat(date, AppConstants.DD_MMMM_YYYY);
        Date currentDate = new Date();
        String currentDateString = changeDateFormat(currentDate, AppConstants.DD_MMMM_YYYY);
        if (dateString.equals(currentDateString)) {
            dateString = changeDateFormat(date, AppConstants.HH_MM);
            return dateString + ", Today";
        } else {
            dateString = changeDateFormat(date, AppConstants.HH_MM_DD_MMM_YYYY);
            return dateString;
        }
    }

    public static String getDateFromEpochForTransaction(long epoch) {
        Date date = new Date(epoch);
        String dateString = changeDateFormat(date, AppConstants.DD_MMMM_YYYY);
        Date currentDate = new Date();
        String currentDateString = changeDateFormat(currentDate, AppConstants.DD_MMMM_YYYY);
        if (dateString.equals(currentDateString)) {
            long difference = currentDate.getTime() - date.getTime();
            long diffSeconds = difference / 1000 % 60;
            long diffMinutes = difference / (60 * 1000) % 60;
            long diffHours = difference / (60 * 60 * 1000) % 24;
            if (diffHours == 0 && diffMinutes == 0)
                return " just now";
            else if (diffHours == 0)
                return String.valueOf(diffMinutes) + " mins ago";
            else
                return String.valueOf(diffHours) + " hrs ago";
        } else {
            dateString = changeDateFormat(date, AppConstants.DD_MMM);
            return " on " + dateString;
        }
    }

    public static Date getDateFromString(String dateString, String inputDateFormat) {
        Date date = null;
        try {
            SimpleDateFormat sdfSource = new SimpleDateFormat(inputDateFormat, Locale.getDefault());
            sdfSource.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = sdfSource.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static void showAlertDialog(final Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    public static void showAlertDialog(final Context context, String message, String title, final boolean isFinish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (isFinish)
                    ((Activity) context).finish();
//                else
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    /**
     * Convert the date format
     *
     * @param dateString       is date
     * @param inputDateFormat  is input format
     * @param outputDateFormat is output format
     * @return date string
     */
    public static String convertDateInFormat(String dateString, String inputDateFormat, String outputDateFormat) {
        try {
            // create SimpleDateFormat object with source string
            // date format
            SimpleDateFormat sdfSource = new SimpleDateFormat(inputDateFormat, Locale.US);

            // parse the string into Date object
            Date date = sdfSource.parse(dateString);

            // create SimpleDateFormat object with desired date
            // format
            SimpleDateFormat sdfDestination = new SimpleDateFormat(outputDateFormat, Locale.US);

            // parse the date into another format
            dateString = sdfDestination.format(date);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return dateString;

    }

    public static int getMonthNumber(String monthName) {
        if (monthName.equals("Jan"))
            return 1;
        if (monthName.equals("Feb"))
            return 2;
        if (monthName.equals("Mar"))
            return 3;
        if (monthName.equals("Apr"))
            return 4;
        if (monthName.equals("May"))
            return 5;
        if (monthName.equals("Jun"))
            return 6;
        if (monthName.equals("Jul"))
            return 7;
        if (monthName.equals("Aug"))
            return 8;
        if (monthName.equals("Sep"))
            return 9;
        if (monthName.equals("Oct"))
            return 10;
        if (monthName.equals("Nov"))
            return 11;

        return 12;
    }

    public static int getOffsetX(int webPixel, Activity activity) {
        int offset;
        int halfWidth = AppUtility.getScreenWidth(activity) / 2;
        offset = halfWidth - webPixel / 2;
        return offset;
    }

    public static float getWebInitialScale(int webPixel) {// width = 320
//        int width = display.getWidth();
        Double val = (double) webPixel / (double) 320;
        val = val * 100.0D;
        return val.floatValue();
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = AppConstants.EMAIL_PATTERN;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean passwordValidator(String password) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = AppConstants.PASSWORD_PATTERN;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkPermissionByPermissionName(final Context context, String permission, String message, boolean isCancelable) {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                if (SharedPreferenceManager.getInstance().getNeverAskAgainStatus(permission)) {
                    Drawable drawableResource = getDrawableIcon(context, permission);

                    if (isCancelable)
                        showPermissionAlert(context, drawableResource, message);
                    else
                        showNonCancelablePermissionAlert(context, drawableResource, message);

                } else {
                    switch (permission) {
                        case Manifest.permission.CAMERA:
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, AppConstants.PERMISSIONS_REQUEST_CAMERA);
                            break;
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    AppConstants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                            break;
                        case Manifest.permission.READ_PHONE_STATE:
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, AppConstants.PERMISSIONS_REQUEST_READ_PHONE_STATE);
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, AppConstants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            break;
                    }
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void showPermissionAlert(Context context, Drawable resId, String message) {
        AppAlertDialog AppAlertDialog = new AppAlertDialog((AppAlertDialog.AppAlertDialogClickListener) context);
        AppAlertDialog.showPermissionDialog(context, Html.fromHtml("<font color='#FF7F27 '>Permission Necessary</font>"), message, context.getString(R.string.settings), context.getString(android.R.string.no), resId);
    }

    public static void showNonCancelablePermissionAlert(Context context, Drawable resId, String message) {
        AppAlertDialog AppAlertDialog = new AppAlertDialog((AppAlertDialog.AppAlertDialogClickListener) context);
        AppAlertDialog.showPermissionDialog(context, Html.fromHtml("<font color='#FF7F27 '>Permission Necessary</font>"), message, context.getString(R.string.settings), resId);
    }

    @Nullable
    public static Drawable getDrawableIcon(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        PermissionInfo info = null;
        Drawable drawableResource = null;
        try {
            info = pm.getPermissionInfo(permission, 0);
            PermissionGroupInfo groupInfo = pm.getPermissionGroupInfo(info.group, 0);
            drawableResource = pm.getResourcesForApplication(context.getPackageName()).getDrawable(groupInfo.icon);
            int color = Color.parseColor("#c14c54");
            drawableResource.setColorFilter(color, PorterDuff.Mode.SRC_ATOP); //Important for changing the color of drawable
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return drawableResource;
    }

    public static String getPathFromURI(Uri contentUri, Context context) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        return res;
    }

    public static void showDialog(Context context, android.app.ProgressDialog progress) {
        if (context == null)
            return;
        progress = new android.app.ProgressDialog(context);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
    }

    public static void hideDialog(ProgressDialog progress) {
        if (progress != null && progress.isShowing())
            progress.dismiss();
    }


    public static void setSwipeRefreshLayoutColorScheme(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                R.color.colorAccent,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public static int getUniqueNotificationID() {
        return smallNotification.incrementAndGet();
    }

    public static String getNetworkIP(Context context) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            NetworkInterface ni;
            while (nis.hasMoreElements()) {
                ni = nis.nextElement();
                if (!ni.isLoopback() && ni.isUp()) {
                    for (InterfaceAddress ia : ni.getInterfaceAddresses()) { //filter for ipv4/ipv6
                        if (ia.getAddress().getAddress().length == 4)  //4 for ipv4, 16 for ipv6
                            return ia.getAddress().getHostAddress();
                    }
                }
            }
            if (context != null) {
                WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
                return Formatter.formatIpAddress(wm != null ? wm.getConnectionInfo().getIpAddress() : 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}