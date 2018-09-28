package com.hgeson.flowtaobaosearch;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hgeson.flowtaobaosearch.utils.StorageUtil;
import com.hgeson.flowtaobaosearch.view.FlowView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe：
 * @Date：2018/9/21
 * @Author：hgeson
 */

public class SearchActivity extends Activity implements View.OnClickListener {
    private EditText editSearch;
    private TextView tvSearch;
    private ImageView tvClear;
    private FlowView flowView;
    private List<String> data = new ArrayList<>();
    private LayoutInflater mInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null){
            getActionBar().hide();
        }
        setContentView(R.layout.activity_search);

        flowView = (FlowView) findViewById(R.id.flow_view);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        tvClear = (ImageView) findViewById(R.id.clear_search);
        editSearch = (EditText) findViewById(R.id.edit_search);
        mInflater = LayoutInflater.from(this);
        tvSearch.setOnClickListener(this);
        tvClear.setOnClickListener(this);

        data = StorageUtil.get(this);
        setFlow();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search) {
            if (editSearch.getText().length() == 0){
                if (editSearch.getHint().length() != 0){
                    data.add(editSearch.getHint().toString().trim());
                }else{
                    return;
                }
            }else {
                data.add(editSearch.getText().toString().trim());
            }
            editSearch.setText("");
            editSearch.setHint("");
            flowView.removeAllViews();
            setFlow();
        }else{
            editSearch.setHint("iphone xs");
            flowView.removeAllViews();
            data.clear();
            StorageUtil.remove(this,"",true);
        }
    }

    public void setFlow(){
        for (int i = 0; i < data.size(); i++) {
            final TextView textView = (TextView) mInflater.inflate(R.layout.item_tv, flowView, false);
            textView.setText(data.get(i));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //自定义点击事件，这里我写出点击item执行删除操作
                    for (int j = 0; j < data.size(); j++) {
                        if (data.get(j).equals(textView.getText().toString())) {
                            data.remove(j);
                            flowView.removeView(v);
                            StorageUtil.remove(SearchActivity.this,String.valueOf(j),false);
                            break;
                        }
                    }
                }
            });
            flowView.addView(textView);
        }
    }

    private long firstime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (System.currentTimeMillis() - firstime > 2000) {
                firstime = System.currentTimeMillis();
                Toast.makeText(this, "再点击一次退出程序~", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StorageUtil.share(this,data);
    }
}
