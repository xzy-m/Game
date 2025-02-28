package com.example.module.tool;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.example.module.response.Response;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author XRS
 * @date 2024-10-10 下午 7:56
 */
@Component
public class SmsTool {

    String signName = "xzy略";
    String templateCode = "SMS_474150460";

    public Response sendMessage(String phoneNumbers, String conference, String address, String time) throws ExecutionException, InterruptedException {
        if (phoneNumbers == null || conference == null || address == null || time == null) {
            throw new RuntimeException("传入参数有空，请检查");
        }

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
                .accessKeyId("LTAI5tARCGpwvVeZATBDX5BD")
                .accessKeySecret("yBC3u87BJIEBDD6H1KIvqy4DqsG9ha")
                //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                .build());

        AsyncClient client = AsyncClient.builder()
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                ).build();

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phoneNumbers)
                .signName(signName)
                .templateCode(templateCode)
                .templateParam("{\"conference\":\"" + conference + "\",\"address\":\"" + address + "\",\"time\":\"" + time + "\"}")
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = response.get();
        client.close();
        return new Response("1001", new Gson().toJson(resp.getStatusCode()));
    }

}
