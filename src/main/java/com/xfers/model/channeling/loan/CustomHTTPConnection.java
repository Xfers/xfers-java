package com.xfers.model.channeling.loan;

import com.google.common.base.Strings;
import com.xfers.Xfers;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/** The other HTTP Client can't handle internal object as parameter, internal objects was parsed into string.
 * Use this one if the request body contains object inside object. */
class CustomHTTPConnection {
    private String baseURL;

    public CustomHTTPConnection() {
        this.baseURL = Xfers.getApiBase();
    }

    public String post(String url, String userApiToken, String params) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(baseURL + url);

        if (!Strings.isNullOrEmpty(userApiToken)) {
            post.addHeader("X-XFERS-USER-API-KEY", userApiToken);
            post.addHeader("accept", "application/json");
            post.addHeader("content-type", "application/json");
        }

        try {
            StringEntity stringEntity = new StringEntity(params);
            post.getRequestLine();
            post.setEntity(stringEntity);

            return handleResponse(client.execute(post));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String handleResponse(HttpResponse httpResponse)
            throws InvalidRequestException, AuthenticationException, APIException {
        Integer status = httpResponse.getStatusLine().getStatusCode();
        String body = null;
        try {
            body = EntityUtils.toString(httpResponse.getEntity());
        } catch(Exception e) {
            throw new APIException(e.getMessage(), status);
        }
        if (status == 200) {
            return body;
        } else if (status == 400) {
            throw new InvalidRequestException(body, status);
        } else if (status == 401) {
            throw new AuthenticationException(body, status);
        } else {
            throw new APIException(body, status);
        }
    }
}
