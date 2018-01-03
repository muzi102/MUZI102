package com.nxm.muzi102.https.httpUtils;
/**
 * 用于缓存处理
 */
public interface RequestEnd extends RequestListener{
	void onRequest(String url);
}
