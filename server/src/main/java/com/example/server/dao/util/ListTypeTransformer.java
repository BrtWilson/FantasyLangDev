package com.example.server.dao.util;

import java.util.ArrayList;
import java.util.List;

public class ListTypeTransformer {
    public static <T, F> List<T> transform(List<F> values, Class<T> newTypeClass) {
        List<T> newList = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            newList.add( (T) values.get(i) );
        }
        return newList;
    }
}
