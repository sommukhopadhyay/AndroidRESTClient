package com.somitsolutions.training.android.restclient;

public interface CallBack {
	public void onProgress();
	public void onResult(String result);
	public void onCancel();
}
