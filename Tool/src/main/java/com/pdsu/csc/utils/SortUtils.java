package com.pdsu.csc.utils;


import com.pdsu.csc.bean.BlobInformation;

import java.util.Comparator;

/**
 * @author 半梦
 * @create 2020-09-21 20:33
 */
public final class SortUtils {

    public static Comparator<BlobInformation> getBlobComparator() {
        return (o1, o2) -> {
            Integer hint1 = o1.getCollection()*50000 + o1.getThumbs()*30000 + o1.getVisit()*10000
                    - (int) DateUtils.getSimpleDateDifference(o1.getWeb().getSubTime(), DateUtils.getSimpleDateSecond());
            Integer hint2 = o2.getCollection()*50000 + o2.getThumbs()*30000 + o2.getVisit()*10000
                    - (int) DateUtils.getSimpleDateDifference(o2.getWeb().getSubTime(), DateUtils.getSimpleDateSecond());
            if(hint1 > hint2) {
                return -1;
            } else if(hint1.equals(hint2))
                return 0;
            return 1;
        };
    }
}
