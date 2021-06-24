package io.github.thebesteric.framework.mocker.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CollectionUtils
 *
 * @author Eric Joe
 * @version 1.0
 * @date 2021-06-25 01:22
 * @since 1.0
 */
public class CollectionUtils {

    public static <T> List<T> arrayToList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

}
