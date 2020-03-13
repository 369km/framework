package org.example.common.utils;

import org.example.common.exception.BaseException;
import org.example.common.exception.server.ExceptionInternalServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtil.class);

    public LoggerUtil() {
    }

    public static void logFilter(Exception e) {
        LOGGER.error(e.getClass().getSimpleName() + " " + e.getMessage(), e);
    }

    public static void logFilter(BaseException e) {
        if (isServerException(e)) {
            LOGGER.error(e.getClass().getSimpleName() + " " + e.getMessage(), e);
        } else {
            LOGGER.warn(e.getClass().getSimpleName() + " " + e.getMessage());
        }

    }


    public static void logFilter(BaseException e, String desc, Object... args) {
        if (isServerException(e)) {
            LOGGER.error(printStr(desc, args), e);
        } else {
            LOGGER.warn(printStr(desc, args));
        }

    }

    private static boolean isServerException(BaseException e) {
        return e instanceof ExceptionInternalServer;
    }


    public static void logFilter(Exception e, String desc, Object... args) {
        if (e instanceof BaseException) {
            logFilter((BaseException) e, desc, args);
        } else {
            LOGGER.error(printStr(desc, args), e);
        }

    }

    public static void logFilter(Object e, String desc, Object... args) {
        if (e instanceof Throwable) {
            logFilter((Exception) ((Throwable) e).getCause(), desc, args);
        } else {
            LOGGER.error(printStr(desc, args), e);
        }

    }

    private static String printStr(String desc, Object... args) {
        String[] arr = desc.split("\\{}");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arr.length; ++i) {
            sb.append(arr[i]);
            if (args.length != 0 && args.length >= i) {
                sb.append(args[i]);
            }
        }

        return sb.toString();
    }
}
