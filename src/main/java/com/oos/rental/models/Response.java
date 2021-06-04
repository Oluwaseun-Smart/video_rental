package com.oos.rental.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<E> implements Serializable {

    private Boolean status;

    private Integer code;

    private String message;

    private E data;

    private Response(Boolean status, Integer code, String message, E data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Response(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static class Builder<E> {


        private Boolean status = false;

        private Integer code = Code.UNKNOWN_ERROR;

        private String message = "Unknow Error";

        private E data = null;


        public Builder() {
        }

        public Builder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Builder setCode(Integer code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setData(E e) {
            this.data = e;
            return this;
        }

        public Response build() {
            return new Response(this.status, this.code, this.message, this.data);
        }

        public Response buildSuccess(String message, E e) {
            return new Response(true, Code.SUCCESS, message, e);
        }

        public Response buildSuccess(String message) {
            return new Response(true, Code.SUCCESS, message);
        }
    }

    public static class Code {
        //General Error 100 - 110
        public static final Integer SUCCESS = 100;
        public static final Integer NOT_FOUND = 101;
        public static final Integer UNKNOWN_ERROR = 110;
        public static final Integer NOT_ALLOWED = 120;
    }
}

