package net.asurovenko.netexam.utils;


import com.google.gson.Gson;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.ErrorCodes;
import net.asurovenko.netexam.network.models.ServerError;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ServerUtils {

    public static int getMsgId(Throwable error) {
        if (error instanceof HttpException) {
            HttpException httpException = (HttpException) error;
            String errorResponse;
            try {
                errorResponse = httpException.response().errorBody().string();
            } catch (IOException e) {
                return R.string.read_error_server_response;
            }
            ServerError serverError = new Gson().fromJson(errorResponse, ServerError.class);
            return ServerUtils.getMessageIdFromError(serverError);
        }
        if (error.getMessage().contains("Failed to connect")) {
            return R.string.failed_to_connect_to_server;
        }
        return R.string.unknown_error;
    }

    private static int getMessageIdFromError(ServerError error) {
        for (ServerError.Error er : error.getErrors()) {
            for (ErrorCodes errorCode : ErrorCodes.values()) {
                if (er.getErrorCode().equalsIgnoreCase(errorCode.toString())) {
                    return errorCode.getMessageId();
                }
            }
        }
        return R.string.unknown_error;
    }
}
