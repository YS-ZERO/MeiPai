package pri.ys.meipaiapi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.ys.base.BaseActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Random;

import pri.ys.meipaiapi.api.MeiPaiApi;
import pri.ys.meipaiapi.ui.InfoActivity;
import pri.ys.meipaiapi.vo.MeiPaiVo;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }


    private RecyclerView mRecyclerView;
    private List<MeiPaiVo> mDataList;


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        //recyclerview.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        //recyclerview.setLayoutManager(new GridLayoutManager(this, 4));//这里用线性宫格显示 类似于grid view
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流


        RequestParams params = new RequestParams(MeiPaiApi.MeiPaiUrl);
        params.addBodyParameter("id", "1");
        params.addBodyParameter("count", "30");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                mDataList = new Gson().fromJson(result, new TypeToken<List<MeiPaiVo>>() {
                }.getType());


                HomeAdapter hAdapter = new HomeAdapter(MainActivity.this, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(InfoActivity.class, false, "url", mDataList.get(position).getUrl());
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                mRecyclerView.setAdapter(hAdapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private Context mContext;
        private OnItemClickListener mOnItemClickListener;

        public HomeAdapter(Context context, OnItemClickListener onItemClickListener) {
            this.mContext = context;
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_item, parent, false));
            return holder;
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            x.image().bind(holder.iv, mDataList.get(position).getCover_pic());


            String text = mDataList.get(position).getCaption();
            if (text.length() > 35) {
                text = text.substring(0, 35);
            }
            holder.tv.setText(text + "...");

            /**随机图片高度，瀑布流效果*/
            Random r = new Random();
            int height = r.nextInt(150) + 200;
            ViewGroup.LayoutParams params = holder.iv.getLayoutParams();
            params.height = height;
            holder.iv.setLayoutParams(params);

            if (mOnItemClickListener != null) {
                holder.iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(v, position);
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView iv;
            private TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.adapter_item_iv);
                tv = (TextView) itemView.findViewById(R.id.adapter_item_tv);
            }
        }
    }


}
