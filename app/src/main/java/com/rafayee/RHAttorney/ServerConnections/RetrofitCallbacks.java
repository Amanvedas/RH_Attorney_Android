package com.rafayee.RHAttorney.ServerConnections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rafayee.RHAttorney.ServerConnections.ServerApiCollection.BASE_URL;


public class RetrofitCallbacks {
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static RetrofitCallbacks retrofitCallbacks;
    private static Retrofit retrofit = null;
    ServerResponseInterface serverResponseInterface;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitCallbacks getInstace() {
        if (retrofitCallbacks == null) {
            retrofitCallbacks = new RetrofitCallbacks();
        }
        return retrofitCallbacks;
    }

    public void fillcontext(Context context) {
        this.context = context;
    }

    public void ApiCallbacksGetAvailableTimings(final Context context, String EndUrl,JsonObject jsonobj, final String from) {

        Log.e("sas",from);
        this.context = context;
        ServerApiCollection apiCollection = getClient().create(ServerApiCollection.class);
        Call<ResponseBody> call = apiCollection.PostDataFromServer(EndUrl,jsonobj);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    String bodyString = null;
                    JSONObject jsonObject = null;
                    try {
                        bodyString = new String(response.body().bytes());
                        jsonObject = new JSONObject(bodyString);
                        if (jsonObject.getString("response").equals("3")) {
                            serverResponseInterface.successCallBack(bodyString,from);
                        } else {
                            Log.e("response ", "messsage");
                            serverResponseInterface.failureCallBack(jsonObject.getString("message"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("nullll","jjddjnj"+response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                serverResponseInterface.failureCallBack(t.getMessage());
            }
        });
    }



    public void ApiCallbacksSlotBooking(final Context context, String EndUrl,JsonObject jsonobj, final String from) {

        Log.e("sas",from);
        this.context = context;
        ServerApiCollection apiCollection = getClient().create(ServerApiCollection.class);
        Call<ResponseBody> call = apiCollection.PostDataFromServer(EndUrl,jsonobj);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    String bodyString = null;
                    JSONObject jsonObject = null;
                    try {
                        bodyString = new String(response.body().bytes());
                        jsonObject = new JSONObject(bodyString);
                        if (jsonObject.getString("response").equals("3")) {
                            serverResponseInterface.successCallBack(bodyString,from);
                        } else {
                            Log.e("response ", "messsage");
                            serverResponseInterface.failureCallBack(jsonObject.getString("message"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("nullll","jjddjnj"+response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                serverResponseInterface.failureCallBack(t.getMessage());
            }
        });
    }

    public void apiCallBacks(final Context context, String EndUrl,JsonObject jsonobj,String strFrom){
        this.context = context;
        ServerApiCollection apiCollection = getClient().create(ServerApiCollection.class);
        Call<ResponseBody> call = apiCollection.PostDataFromServer(EndUrl,jsonobj);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    String bodyString = null;
                    JSONObject jsonObject = null;
                    try {
                        bodyString = new String(response.body().bytes());
                        jsonObject = new JSONObject(bodyString);
                        if (jsonObject.getString("response").equals("3")) {
                            serverResponseInterface.successCallBack(bodyString,strFrom);
                        } else {
                            Log.e("response ", "messsage ::: "+jsonObject.getString("message"));
                            serverResponseInterface.failureCallBack(jsonObject.getString("message"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("nullll","jjddjnj"+response.code());
                }
                /*try {
                    String responseData = null;
                    if (response.body() != null) {
                        responseData = new String(response.body().bytes());
                    }
                    Log.e("response","rerr"+strFrom);

                    serverResponseInterface.successCallBack(responseData,strFrom);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("response","ee "+t.getMessage());
                serverResponseInterface.failureCallBack(t.getMessage());
            }
        });

    }



   /* public void OTPApiCall(Context context,JsonObject jsonObject){

        this.context = context;
        ServerApiCollection loginConection = getClient().create(ServerApiCollection.class);
        Call<ResponseBody> call = loginConection.LoginApi(jsonObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("response","re"+response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("response","ee "+t.getMessage());

            }
        });

    }
*/





    public void ApiCallbacksGet(final Context context, String EndUrl, final String from) {
        Log.e("sas",from);
        this.context = context;
        ServerApiCollection apiCollection = getClient().create(ServerApiCollection.class);
        Call<ResponseBody> call = null;
        if (from.equals("fetchAttorneyList")){
            call = apiCollection.fetchAttorneyList(EndUrl);
        }else {
            call = apiCollection.fetchCompanyInfo(EndUrl);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    String bodyString = null;
                    JSONObject jsonObject = null;
                    try {
                        bodyString = new String(response.body().bytes());
                        jsonObject = new JSONObject(bodyString);
                        if (jsonObject.getString("response").equals("3")) {
                            serverResponseInterface.successCallBack(bodyString,from);
                        } else {
                            Log.e("response ", "messsage");
                            serverResponseInterface.failureCallBack(jsonObject.getString("message"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e("nullll","jjddjnj"+response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                serverResponseInterface.failureCallBack(t.getMessage());
            }
        });
    }


    public void initializeServerInterface(ServerResponseInterface responseInterface) {
        serverResponseInterface = responseInterface;
    }

    public interface ServerResponseInterface {

        void successCallBack(String body, String from);
        void failureCallBack(String failureMsg);

    }
}
