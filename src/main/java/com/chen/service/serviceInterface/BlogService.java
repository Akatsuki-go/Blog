package com.chen.service.serviceInterface;

import java.text.ParseException;
import java.util.Map;

public interface BlogService {
    Map<String, Object> BlogSide(Map<String, String> map) throws ParseException;
}
