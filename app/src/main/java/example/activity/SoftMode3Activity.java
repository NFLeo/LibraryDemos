package example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.style.base.BaseRecyclerViewAdapter;
import com.style.bean.Friend;
import com.style.framework.R;
import com.style.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import example.adapter.StringAdapter;
import example.home.FriendAdapter;

public class SoftMode3Activity extends AppCompatActivity {
    private String TAG = "SoftMode3Activity";

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    RecyclerView recyclerView;
    private ArrayList<String> dataList;
    private LinearLayoutManager layoutManager;
    private StringAdapter adapter;
    private View layoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_mode_3);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutRoot = findViewById(R.id.layout_root);

    }

    @Override
    protected void onStart() {
        super.onStart();
        dataList = new ArrayList<>();
        adapter = new StringAdapter(this, dataList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {

            }
        });
        getData();

        //获取屏幕高度
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        //添加layout大小发生改变监听器,前提是windowSoftInputMode="adjustResize" 并且布局确实会发生大小变化
        layoutRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e(TAG, "监听到软键盘弹起");
                    layoutManager.scrollToPosition(adapter.getItemCount() - 1);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e(TAG, "监听到软件盘关闭");
                    //如果是切换到表情面板而隐藏流量输入法，需要延迟判断表情面板是否显示，如果表情面板是关闭的，操作栏也关闭
                    /**
                     * Convenience method to scroll to a certain position.
                     *
                     * RecyclerView does not implement scrolling logic, rather forwards the call to
                     * {@link android.support.v7.widget.RecyclerView.LayoutManager#scrollToPosition(int)}
                     * @param position Scroll to this adapter position
                     * @see android.support.v7.widget.RecyclerView.LayoutManager#scrollToPosition(int)
                     */
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //recyclerView.scrollToPosition(19);
        //layoutManager.scrollToPosition(19);

    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            dataList.add(i + "");
        }
        adapter.notifyDataSetChanged();

    }

}
