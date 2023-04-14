package com.baidu.travel.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author pfk
 * @creatTime 2021/07/13下午 05:15
 * @describe
 */
public interface SpeechService {
    String find(String result, HttpServletRequest request) throws IOException;

}
