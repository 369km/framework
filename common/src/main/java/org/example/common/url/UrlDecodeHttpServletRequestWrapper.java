package org.example.common.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecodeHttpServletRequestWrapper extends BaseHttpServletRequestWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlDecodeHttpServletRequestWrapper.class);

    public UrlDecodeHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected String dealWithParameter(String param) {
        try {
            param = URLDecoder.decode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode param error : {}", param);
        }
        return param;
    }

}