package com.company.ProcessComparators;

import com.company.Process;

import java.util.Comparator;

/**
 * Created by Ethan on 12/9/2017.
 */
public class ProcessArrivalComparator implements Comparator<Process> {
    @Override
    public int compare(Process a, Process b) {
        if (a.getArrivalTime() == b.getArrivalTime())
            return 0;
        return a.getArrivalTime() > b.getArrivalTime() ? 1 : -1;
    }
}
