package com.alexG.payload.response;

public class Response<T> {
	public T payload;
	public boolean isError;
	public String errorMessage;

	public Response(T payload, String errorMessage, boolean isError) {
		this.payload = payload;
		this.errorMessage = errorMessage;
		this.isError = isError;

	}
}
