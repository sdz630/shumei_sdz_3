package com.example.photoshow;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "|MainActivity";

    private RecyclerView.Adapter myAdapter;
    private RecyclerView mRecyclerView;
    private  Handler mHandler;
    private List<Result> resultList = new ArrayList<>();
    private List<String> Images ;
    private List<Integer> mImageHeights = new ArrayList<>();
    private int count = 1;
    TextView responseText;

    @SuppressWarnings("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText = (TextView)findViewById(R.id.response_text);
        Log.i("sdz", "Step1 OK!");

        InitUI();
        InitData();

        //给RecyclerView设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //创建适配器
        myAdapter = new RecyclerViewAdapter( Images,MainActivity.this);
        //给RecyclerView绑定适配器
        mRecyclerView.setAdapter(myAdapter);


        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String JsonStr = (String)msg.obj;
                Log.i("wk","Handler"+JsonStr);
                parseJSONWithGSON(JsonStr);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"加载到第"+count+"页",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //使用Okhttp进行网络请求
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
//                String ResultStr = Httphelper.GetHttpConnection("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
//                    showResponse(responseData);
                    Message msg = new Message();

                    msg.obj = responseData;

                    mHandler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判断是否滑动到底部，用于分页加载
                if (isSlideTOBottom(recyclerView)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/"+count++)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String responseData = response.body().string();
                                Message msg = new Message();
                                msg.obj = responseData;
                                mHandler.sendMessage(msg);
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {

                            }
                        }
                    }).start();
                }
            }



        });

    }


    private void parseJSONWithGSON(String jsonData) {

        Gson gson = new Gson();
        Httphelper httphelper = gson.fromJson(jsonData,Httphelper.class);
        resultList = httphelper.results;

        for (Result result : httphelper.results) {
            String url = result.getUrl();
            Images.add(url);
            Log.i(TAG,"URL is "+ url);
        }
    }
//    private void getImagesFromJson(String jsonStr){
//        try{
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            JSONArray jsonArray = jsonObject.getJSONArray("results");;
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONArray jb = jsonArray.getJSONArray(i);
//                Images.add(jb.getString(Integer.parseInt("url")));
//            }
//            Log.i("sdz", "Ok_4!");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }finally {
//        }
//    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }

    private void InitData() {
        Images = new ArrayList<String>();
    }

    private void InitUI(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    //判断是否滑倒底部
    public boolean isSlideTOBottom(RecyclerView recyclerView){
        if(recyclerView == null)return false;
        if(recyclerView.computeVerticalScrollExtent()+recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange()){
            return true;
        }
        return false;
    }




}
