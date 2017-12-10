package com.company;

import java.util.Comparator;

/**
 * Created by Ethan on 12/9/2017.
 */
public class ProcessFinishComparator implements Comparator<Process>{
    @Override
    public int compare(Process a, Process b) {
        if (a.getFinishTime() == b.getFinishTime())
            return 0;
        return a.getFinishTime() > b.getFinishTime() ? 1 : -1;
    }
}
