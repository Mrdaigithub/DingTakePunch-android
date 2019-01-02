package com.goldze.mvvmhabit.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.goldze.mvvmhabit.app.AppApplication;
import com.goldze.mvvmhabit.entity.SpinnerItemData;
import com.goldze.mvvmhabit.ui.main.DemoActivity;
import com.goldze.mvvmhabit.utils.Constants;
import com.goldze.mvvmhabit.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static com.goldze.mvvmhabit.utils.Constants.DEVICE_ID_LIST;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {
    //设备ID的绑定
    public ObservableField<String> deviceID = new ObservableField<>("");
    public List<IKeyAndValue> deviceIDList;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        deviceIDList = new ArrayList<>();
        for (String deviceID : DEVICE_ID_LIST) {
            deviceIDList.add(new SpinnerItemData(deviceID, deviceID));
        }

        deviceID.set(SharedPreferencesUtils.getInstance().get(getApplication().getBaseContext(), "deviceID", DEVICE_ID_LIST[0]));
    }

    //设备ID选择的监听
    public BindingCommand<IKeyAndValue> onDeviceIDSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            deviceID.set(iKeyAndValue.getValue());
        }
    });

    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Observable.just("")
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            SharedPreferencesUtils.getInstance().save("deviceID", deviceID.get());
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            //进入DemoActivity页面
                            startActivity(DemoActivity.class);
                            //关闭页面
                            finish();
                        }
                    });
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        deviceIDList.clear();
        deviceIDList = null;
    }
}
