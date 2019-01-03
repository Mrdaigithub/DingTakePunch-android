package com.goldze.mvvmhabit.ui.login;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.goldze.mvvmhabit.entity.SpinnerItemData;
import com.goldze.mvvmhabit.ui.main.DemoActivity;
import com.goldze.mvvmhabit.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;

import static com.goldze.mvvmhabit.utils.Constants.DEVICE_ID_LIST;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {
    private SPUtils mSPUtils = SPUtils.getInstance();
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
        Observable.fromArray(DEVICE_ID_LIST)
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String deviceID) {
                        deviceIDList.add(new SpinnerItemData(deviceID, deviceID));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        deviceID.set(mSPUtils.getString("deviceID", DEVICE_ID_LIST[0]));
                    }
                });
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
                            mSPUtils.put("deviceID", deviceID.get());
                        }
                    })
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            //进入DemoActivity页面
                            startActivity(DemoActivity.class);
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
