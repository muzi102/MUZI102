package com.nxm.muzi102.https.httpUtils;


import com.squareup.okhttp.Request;

public interface RequestListener {
	void onResponse(String response);
	void onError(Request request, Exception e) ;
}
