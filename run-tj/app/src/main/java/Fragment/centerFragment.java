package Fragment;

import activity.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.R;
import net.asyncCall;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link centerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class centerFragment extends Fragment implements View.OnClickListener{

    View CenterView;

    ImageView avatar;

    final Handler handle =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap bmp=(Bitmap)msg.obj;
                    avatar.setImageBitmap(bmp);
                    break;
            }
        };

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CenterView=inflater.inflate(R.layout.fragment_center, container, false);
        // 加载静态页面
        avatar = CenterView.findViewById(R.id.imageView7);
        // 在这个线程中设置头像
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncCall asyncCall = new asyncCall();
                ArrayList<String> idList = new ArrayList<>();
                // 要查询的id
                idList.add("10");
                // 返回response解析Json
                Response response = asyncCall.getAsync("/user",idList);
                try {
                    JSONObject object = new JSONObject(Objects.requireNonNull(response.body()).string());
                    // Json处理器
                    String UserIcon = object.getString("avator");
                    // 设置头像为用户头像
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = Utils.BitMap.returnBitMap(UserIcon);
                    handle.sendMessage(msg);
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 设置跳转按钮
        TextView runToRecord = CenterView.findViewById(R.id.jumpToRunRecord);
        runToRecord.setOnClickListener(this);
        // Inflate the layout for this fragment
        avatar.setOnClickListener(this);
        //取身体数据按钮，并设置跳转事件
        TextView physical = CenterView.findViewById(R.id.jumpToPhysicalData);
        physical.setOnClickListener(this);
        //取反馈信息按钮，并设置跳转事件
        TextView feedback = CenterView.findViewById(R.id.jumpToFeedback);
        feedback.setOnClickListener(this);
        //取关注的人按钮，并设置跳转事件
        TextView follow = CenterView.findViewById(R.id.jumpToFollow);
        follow.setOnClickListener(this);
        return CenterView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.jumpToRunRecord:{
                Intent intent = new Intent(getActivity(), RunRecordActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.imageView7:{
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.jumpToPhysicalData:{
                Intent intent = new Intent(getActivity(), physicalActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.jumpToFeedback:{
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.jumpToFollow:{
                Intent intent = new Intent(getActivity(), FollowActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

}