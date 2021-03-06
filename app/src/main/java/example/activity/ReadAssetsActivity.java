package example.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.style.base.BaseToolBarActivity;
import com.style.framework.R;
import com.style.utils.AssetsUtil;
import com.style.utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

public class ReadAssetsActivity extends BaseToolBarActivity {

    private TextView tv_agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLayoutResID = R.layout.activity_user_agree;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        setToolbarTitle("用户协议");

        tv_agree = (TextView) findViewById(R.id.tv_useragree);
        readData();
    }

    public void readData() {
        try {

            tv_agree.setText(AssetsUtil.getAssetsText(this, "useragree.txt"));
        } catch (IOException e) {

        }
    }
}
