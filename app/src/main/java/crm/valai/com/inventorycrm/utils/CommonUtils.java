/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package crm.valai.com.inventorycrm.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.Formatter;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import crm.valai.com.inventorycrm.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Response;

import static android.content.Context.WIFI_SERVICE;
import static crm.valai.com.inventorycrm.utils.AppConstants.MULTIPART_FORM_DATA;

@SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
public final class CommonUtils {

    private static final String TAG = CommonUtils.class.getSimpleName();


    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static void runLayoutAnimation(final RecyclerView recyclerView, final AnimationItem item) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, item.getResourceId());

        recyclerView.setLayoutAnimation(controller);
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        recyclerView.scheduleLayoutAnimation();
    }

    @NonNull
    public static String getStringResponseBody(Response<ResponseBody> response) {
        StringBuilder sbAppend = new StringBuilder();
        BufferedSource source = response.body().source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        String responseString = buffer.clone().readString(Charset.forName("UTF-8"));
        return sbAppend.append("\"data\":").append(responseString).append("}").toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("MM/dd/yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat2(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd/MM/yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat3(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd MMM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateStringFormat4(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd EEE");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return splitDate(spf.format(newDate));
    }

    public static String splitTime(String dateTime) {
        String[] parts = dateTime.split("T");
        String part1 = parts[0];
        String part2 = parts[1];

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt = null;
        try {
            dt = sdf.parse(part2);
            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdfs.format(dt);
    }

    private static String splitDate(String date) {
        String[] parts = date.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];
        return part1 + "\n" + part2;
    }

    @NonNull
    public static Spanned fromHtml(@NonNull String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c);
    }

    public static String getCurrentDateNew() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(c);
    }

    public static String getCurrentDateNewOne() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        return df.format(c);
    }

    public static String getCurrentYear() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return df.format(c);
    }

    public static String getCurrentDateForOrder() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("ddmmyy");
        return df.format(c);
    }

    public static String getCurrentDateForGetOrder() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c);
    }

    public static String compareDates(String currentDate, String punchDate) {
        String compareTimeVal = "";
        try {
            String pattern = "MM/dd/yyyy";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date dateCurrent = formatter.parse(currentDate);
            Date datePunch = formatter.parse(punchDate);
            if (dateCurrent != null && datePunch != null) {
                if (dateCurrent.equals(datePunch)) {
                    Log.e("equal", "date");
                    //compareTimeVal = "4";
                    compareTimeVal = "equal";

                } else if (dateCurrent.before(datePunch)) {
                    Log.e("before", "before");
                    //compareTimeVal = "4";
                    compareTimeVal = "before";

                } else if (dateCurrent.after(datePunch)) {
                    Log.e("after", "after");
                    //compareTimeVal = compareTime();
                    compareTimeVal = "after";
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return compareTimeVal;
    }

    @NonNull
    private static String compareTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());

        int dtHour;
        int dtMin;
        int i_AM_PM;

        String strAMorPM = null;
        Date dtCurrentDate;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());

        try {
            Date TimeToCompare = sdf.parse("12:00");
            dtMin = cal.get(Calendar.MINUTE);
            dtHour = cal.get(Calendar.HOUR);
            i_AM_PM = cal.get(Calendar.AM_PM);

            if (i_AM_PM == 1) {
                strAMorPM = "PM";
            }

            if (i_AM_PM == 0) {
                strAMorPM = "AM";
            }

            dtCurrentDate = sdf.parse(dtHour + ":" + dtMin + " " + strAMorPM);

            if (dtCurrentDate.after(TimeToCompare)) {
                return "1";
            }

            if (dtCurrentDate.before(TimeToCompare)) {
                return "2";
            }

            if (dtCurrentDate.equals(TimeToCompare)) {
                return "3";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "4";
    }

    public static String getIpAddress(Context ctx) {
        WifiManager wm = (WifiManager) ctx.getApplicationContext().getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    /*
   * Java Method to calculate difference between two dates in Java
   * without using any third party library.
   */
    public static long daysBetween(String startDate, String endDate) {
        Date one = new Date(startDate);
        Date two = new Date(endDate);
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    // Prepare FilePart
    @NonNull
    public static MultipartBody.Part prepareFilePart(String name, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }

    // Prepare StringPart
    @NonNull
    public static MultipartBody.Part prepareStringPart(String name, String imagePath) {
        return MultipartBody.Part.createFormData(name, imagePath);
    }
}