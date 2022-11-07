package com.hiteshchopra.marketo.data.remote.retrofit

import com.hiteshchopra.marketo.BuildConfig
import com.hiteshchopra.marketo.data.Constants
import com.hiteshchopra.marketo.data.Constants.ASSET_ID
import com.hiteshchopra.marketo.data.Constants.DATA_SET_ID
import com.hiteshchopra.marketo.data.Constants.HOST
import com.hiteshchopra.marketo.data.Constants.REVISION_ID
import com.hiteshchopra.marketo.data.Constants.X_AMZ_DATE
import com.hiteshchopra.marketo.data.DateUtil
import com.hiteshchopra.marketo.data.remote.aws_signer.OkHttpAwsV4Signer
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AwsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var awsRequest: Request = request.newBuilder()
            .addHeader(HOST, request.url.host)
            .addHeader(X_AMZ_DATE, DateUtil.getAwsDate())
            .addHeader(DATA_SET_ID, BuildConfig.DATA_SET_ID)
            .addHeader(REVISION_ID, BuildConfig.REVISION_ID)
            .addHeader(ASSET_ID, BuildConfig.ASSET_ID).build()


        val okHttpAwsV4Signer = OkHttpAwsV4Signer(Constants.REGION, Constants.SERVICE_NAME)

        awsRequest = okHttpAwsV4Signer.sign(
            awsRequest, BuildConfig.ACCESS_KEY, BuildConfig.SECRET_KEY
        )

        awsRequest.newBuilder()
        return chain.proceed(awsRequest)
    }
}