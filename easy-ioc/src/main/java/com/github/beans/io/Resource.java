package com.github.beans.io;

import java.io.InputStream;

/**
 * Resource是spring内部定位资源的接口。
 *
 * @author plum-wine
 */
public interface Resource {

    InputStream getInputStream();

}
