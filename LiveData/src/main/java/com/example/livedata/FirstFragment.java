package com.example.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 演示Activity以及下面的Fragment数据同步
 */
public class FirstFragment extends Fragment {
  private static final String TAG = "LiveDataFragment";
  private NameViewModel mNameViewModel;
  @BindView(R.id.tv_name) TextView mTvName;

  public static FirstFragment getInstance() {
    return new FirstFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // 如果需要在Activity以及下面的所有Fragment数据同步,那么这里的of对应参数应该为Fragment所在Activity
    mNameViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NameViewModel.class);

    // 订阅LiveData中当前Name数据变化，以lambda形式定义Observer
    mNameViewModel.getCurrentName().observe(this, (String name) -> {
      mTvName.setText(name);
      Log.d(TAG, "currentName: " + name);
    });

    mNameViewModel.getNameWithExt().observe(this, (String name) -> {
      Log.d(TAG, "currentName: " + name);
    });

    // 订阅LiveData中Name列表数据变化，以lambda形式定义Observer
    mNameViewModel.getNameList().observe(this, (List<String> nameList) -> {
      if (nameList == null) {
        return;
      }
      for (String item : nameList) {
        Log.d(TAG, "name: " + item);
      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_first, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  List<String> nameList = new ArrayList<>();

  @OnClick({ R.id.btn_change_name, R.id.btn_update_list })
  void onClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_change_name:
        mNameViewModel.getCurrentName().setValue("FirstFragment中设置Name");
        break;
      case R.id.btn_update_list:
        for (int i = 0; i < 1; i++) {
          nameList.add("Jane<" + i + ">");
        }
        mNameViewModel.getNameList().setValue(nameList);

        break;
    }
  }
}