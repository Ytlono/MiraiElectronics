package com.example.MiraiElectronics.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
public class TemplateSortService {

    public <T> List<T> sort(List<T> items, java.util.Comparator<T> comparator){
        if (items == null || comparator == null) {
            throw new IllegalArgumentException("Список или компаратор не может быть null.");
        }
        Collections.sort(items, comparator);
        return items;
    }
}
