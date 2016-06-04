package kr.ac.ssu.onecard_ssu.Activity;

public interface Request {
    void onSuccess(String receiveData);
    void onFail(String url, String error);
}