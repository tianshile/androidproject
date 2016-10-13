package com.cn.yb.view.uploaddown;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;



public class TestRecyclerViewAndImageDownLoadAndUpLoad extends AppCompatActivity {
    static final int ACTION_DOWN = 0;
    static final int ACTION_PULL_DOWN = 1;
    static final int ACTION_PULL_UP = 2;
    static final int PAGE_SIZE = 10;
    static final String USER_NAME = "a";

    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mtvRefresh;
    RecyclerView mrvContact;
    ContactAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    ArrayList<UserAvatar> mContactList;

    int mPageId = 1;

    int mNewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setEnabled(true);
                mSwipeRefreshLayout.setRefreshing(true);
                mtvRefresh.setVisibility(View.VISIBLE);
                mPageId = 1;
                downloadContactList(ACTION_PULL_DOWN, mPageId);
            }
        });
    }

    private void setPullUpListener() {
        mrvContact.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //保存滚动状态
                mNewState = newState;
                //获取当前列表的索引
                lastPosition = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition >= mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    //滚动结且列表已到最底部且还有更多数据可加载
                    mPageId++;
                    //下载下一页的数据
                    downloadContactList(ACTION_PULL_UP, mPageId);
                }
                //停止拖拽，则通知系统回调Adapter.onBindViewHolder()，刷新RecyclerView
                if (newState != RecyclerView.SCROLL_STATE_DRAGGING) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData() {
        downloadContactList(ACTION_DOWN, mPageId);
    }

    private void downloadContactList(final int action, int pageId) {
        final OkHttpUtils<Result> utils = new OkHttpUtils<>();
        utils.url(I.SERVER_URL)
                .addParam(I.KEY_REQUEST, I.REQUEST_DOWNLOAD_CONTACT_PAGE_LIST)
                .addParam(I.Contact.USER_NAME, USER_NAME)
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, PAGE_SIZE + "")
                .targetClass(Result.class)
                .execute(new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        if (result.getRetCode() != 0) {
                            return;
                        }
                        String json = result.getRetData().toString();
                        Gson gson = new Gson();
                        Map map = gson.fromJson(json, Map.class);
                        json = map.get("pageData").toString();
                        UserAvatar[] users = gson.fromJson(json, UserAvatar[].class);
                        mAdapter.setMore(users != null && users.length > 0);
                        if (!mAdapter.isMore()) {
                            if (action == ACTION_PULL_UP) {
                                mAdapter.setFooter("没有更多可加载");
                            }
                            return;
                        }
                        ArrayList<UserAvatar> contactList = utils.array2List(users);
                        switch (action) {
                            case ACTION_DOWN:
                                mAdapter.initContactList(contactList);
                                mAdapter.setFooter("加载更多");
                                break;
                            case ACTION_PULL_DOWN:
                                mAdapter.initContactList(contactList);
                                mAdapter.setFooter("加载更多");
                                mSwipeRefreshLayout.setRefreshing(false);
                                mtvRefresh.setVisibility(View.GONE);
                                ImageLoader.release();
                                break;
                            case ACTION_PULL_UP:
                                mAdapter.addContactList(contactList);
                                break;
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sl);
        mtvRefresh = (TextView) findViewById(R.id.tvRefreshHint);

        mrvContact = (RecyclerView) findViewById(R.id.rvContact);
        mContactList = new ArrayList<>();
        mAdapter = new ContactAdapter(this, mContactList);
        mrvContact.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mrvContact.setLayoutManager(mLayoutManager);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvUserName;
        TextView tvNick;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ivThumb = (ImageView) itemView.findViewById(R.id.ivThumb);
            tvNick = (TextView) itemView.findViewById(R.id.tvNick);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        }
    }

    class FooterView extends RecyclerView.ViewHolder {
        TextView tvFooter;

        public FooterView(View itemView) {
            super(itemView);
            tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);
        }
    }

    class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        static final int TYPE_ITEM = 0;
        static final int TYPE_FOOTER = 1;

        Context context;
        ArrayList<UserAvatar> contactList;
        String footer;
        RecyclerView parent;
        boolean isMore;

        public ContactAdapter(Context context, ArrayList<UserAvatar> contactList) {
            this.context = context;
            this.contactList = contactList;
        }

        public boolean isMore() {
            return isMore;
        }

        public void setMore(boolean isMore) {
            this.isMore = isMore;
        }

        public void initContactList(ArrayList<UserAvatar> contactList) {
            this.contactList.clear();
            this.contactList.addAll(contactList);
            notifyDataSetChanged();
        }

        public void addContactList(ArrayList<UserAvatar> contactList) {
            this.contactList.addAll(contactList);
            notifyDataSetChanged();
        }

        public void setFooter(String footer) {
            this.footer = footer;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.parent = (RecyclerView) parent;
            RecyclerView.ViewHolder holder = null;
            View layout = null;
            switch (viewType) {
                case TYPE_FOOTER:
                    layout = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
                    holder = new FooterView(layout);
                    break;
                case TYPE_ITEM:
                    layout = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
                    holder = new ContactViewHolder(layout);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_FOOTER) {
                ((FooterView) holder).tvFooter.setText(footer);
                return;
            }
            ContactViewHolder contactHolder = (ContactViewHolder) holder;
            UserAvatar user = contactList.get(position);
            contactHolder.tvNick.setText("昵称:" + user.getMUserNick());
            contactHolder.tvUserName.setText("账号:" + user.getMUserName());
            ImageLoader.build()
                    .url(I.SERVER_URL)
                    .addParam(I.KEY_REQUEST, I.REQUEST_DOWNLOAD_AVATAR)
                    .addParam(I.NAME_OR_HXID, user.getMUserName())
                    .addParam(I.AVATAR_TYPE, "user_avatar")
                    .width(80)
                    .height(80)
                    .defaultPicture(R.drawable.default_face)
                    .imageView(contactHolder.ivThumb)
                    .listener(parent)
                    .setDragging(mNewState != RecyclerView.SCROLL_STATE_DRAGGING)
                    .showImage(context);
        }

        @Override
        public int getItemCount() {
            return contactList == null ? 0 : contactList.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }
    }

}
