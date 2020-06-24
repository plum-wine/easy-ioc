package com.github.mvc.type;

import lombok.Data;

import java.util.Objects;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:48
 * *****************
 * function:
 */
@Data
public class RequestPathInfo {

    private String httpMethod;

    private String httpPath;

    public RequestPathInfo(String httpMethod, String httpPath) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPathInfo that = (RequestPathInfo) o;
        return Objects.equals(httpMethod, that.httpMethod) &&
                Objects.equals(httpPath, that.httpPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, httpPath);
    }
}
