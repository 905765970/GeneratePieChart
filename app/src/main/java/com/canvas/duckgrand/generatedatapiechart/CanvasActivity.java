package com.canvas.duckgrand.generatedatapiechart;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canvas.duckgrand.generatedatapiechart.myView.MyDataView;

import java.util.HashMap;

public class CanvasActivity extends AppCompatActivity {


    private MyDataView myDataView;
    private HashMap<String , Float> dataDegee = new HashMap<>();
    private HashMap<String , String> dataColor = new HashMap<>();

    private LinearLayout mLny;
    LinearLayout.LayoutParams params;

    private EditText etCanyin, etYule, etCHuxing, etShenghuo, etFushi;
    private Button btnUpdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        initView();
    }

    private void initView() {
        mLny = findViewById(R.id.lnyOut);
        etCanyin = findViewById(R.id.etCanyin);
        etYule = findViewById(R.id.etWenjiao);
        etCHuxing = findViewById(R.id.etChuxing);
        etFushi = findViewById(R.id.etFushi);
        etShenghuo = findViewById(R.id.etShenghuo);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uodatePieChart();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        myDataView = new MyDataView(this, DataDegree(), DataColor());
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mLny.addView(myDataView, params);
    }

    private HashMap<String , Float> DataDegree(){
        dataDegee.put("餐饮消费", 32f);
        dataDegee.put("文教娱乐", 42f);
        dataDegee.put("服饰美容", 52f);
        dataDegee.put("出行交通", 92f);
        dataDegee.put("其它",142f);
        return dataDegee;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private HashMap<String , String> DataColor(){

        dataColor.put("餐饮消费", "#6600ff");
        dataColor.put("文教娱乐", "#cc00ff");
        dataColor.put("服饰美容","#9933ff");
        dataColor.put("出行交通", "#ff9900");
        dataColor.put("其它","#9999ff");
        return dataColor;
    }

    private void uodatePieChart(){

        float canyin = getFloat(etCanyin);
        float yule = getFloat(etYule);
        float shenghuo = getFloat(etShenghuo);
        float chuxing = getFloat(etCHuxing);
        float fushi = getFloat(etFushi);

        float total = canyin +yule +fushi +shenghuo +chuxing;

        if(total>0){

            dataDegee.put("餐饮消费", getDegree(canyin,total));
            dataDegee.put("服饰美容", getDegree(fushi,total));
            dataDegee.put("其它", getDegree(shenghuo,total));
            dataDegee.put("出行交通", getDegree(chuxing,total));
            dataDegee.put("文教娱乐", getDegree(yule,total));
            mLny.removeView(myDataView);
            mLny.addView(myDataView, params);
            Log.i("test draw", "remove view ; add view");
            Toast.makeText(this,"哇，你居然在"+getMaxinumType()+"上花了这么多",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this,"输入您的消费额度",Toast.LENGTH_SHORT).show();
        }
    }

    private float getDegree(float number, float total){
        return  number/total * 360;
    }

    private float getFloat(EditText editText){
        float mdata = 0f;
        try {
            if(editText.getText().toString().equals("")){
                mdata = (float) Double.parseDouble(editText.getHint().toString());
            }else {
                mdata = (float) Double.parseDouble(editText.getText().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"输入格式错误",Toast.LENGTH_SHORT).show();
        }finally {
            return mdata;
        }
    }

    private String getMaxinumType(){
        String type="";
        float money = Float.MIN_VALUE;
        for(String key : dataDegee.keySet()){
            if(dataDegee.get(key) > money){
                type = key;
                money = dataDegee.get(key);
            }
        }
        return type;
    }
}
