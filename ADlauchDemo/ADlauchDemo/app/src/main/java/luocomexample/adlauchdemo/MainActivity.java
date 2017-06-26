package luocomexample.adlauchdemo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout lvSkip;
    private TasksCompletedView mTasksView;
    private ImageView mImageView;
    private int displaySecond = 3;
    private int mCurrentSencond = -1;
    private boolean skip = true;

    private int[] imgList = {R.mipmap.login3,R.mipmap.login3, R.mipmap.login2, R.mipmap.login1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initUI();
        timeUI();
    }

    private void initUI() {
        lvSkip = (LinearLayout) findViewById(R.id.lvSkip);
        mTasksView = (TasksCompletedView) findViewById(R.id.mTasksiew);
        mImageView = (ImageView) findViewById(R.id.ivAd);

        lvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编写对应事件
                Toast.makeText(MainActivity.this, "点击了跳过，跳转到主界面", Toast.LENGTH_SHORT).show();
                skip = false;
//                finish();
            }
        });
    }


    private void timeUI() {
        //两线程同时运行，
        new Thread(new ProgressRunable()).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (skip) {
                    Toast.makeText(MainActivity.this, "广告界面结束，跳转到主界面", Toast.LENGTH_LONG).show();
//                    finish();
                }
            }
        }, displaySecond * 1000);
    }

    //计时
    private class ProgressRunable implements Runnable {
        @Override
        public void run() {
            mTasksView.setTotalProgress(displaySecond);
            while (mCurrentSencond < displaySecond) {
                try {
                    mCurrentSencond += 1;
                    //设置广告界面显示
                    Log.i("TAG","第 "+mCurrentSencond+"张图片");
                    mTasksView.setProgress(mCurrentSencond);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageResource(imgList[mCurrentSencond]);
                        }
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
