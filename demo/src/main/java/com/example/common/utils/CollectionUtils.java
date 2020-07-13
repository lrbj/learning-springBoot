package com.example.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 集合处理工具
 */
@Slf4j
public class CollectionUtils {

  public static Collection convertElementToString(Collection<?> sourceCollection) {
    if (sourceCollection instanceof List) {
      return sourceCollection.stream().map(e -> ((Object) e).toString()).collect(Collectors.toList());
    }

    if (sourceCollection instanceof Set) {
      return sourceCollection.stream().map(e -> ((Object) e).toString()).collect(Collectors.toSet());
    }

    log.error("unsupported collection type convert");
    throw new RuntimeException();
  }

  public static List clearRedundancy(Collection<?> sourceCollection) {
    return sourceCollection.stream().filter(e -> e != null).distinct().collect(Collectors.toList());
  }


}
