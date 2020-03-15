package org.example.common.url;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

public class BaseHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest orgRequest = null;

    public BaseHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof BaseHttpServletRequestWrapper) {
            return ((BaseHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(orgRequest.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(dealWithParameter(line));
        }
        return new BaseServletInputStream(new ByteArrayInputStream(result.toString().getBytes()));
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = dealWithParameter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = dealWithParameter(arr[i]);
            }
        }
        return arr;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Iterator entries = map.entrySet().iterator();
        Map.Entry<String, String[]> entry;
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            String[] values = entry.getValue();
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = dealWithParameter(values[i]);
                }
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = dealWithParameter(value);
        }
        return value;
    }

    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    protected String dealWithParameter(String param) {
        return param;
    }
}
