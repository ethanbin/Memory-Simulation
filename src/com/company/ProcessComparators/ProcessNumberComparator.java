package com.company.ProcessComparators;

import com.company.Process;

import java.util.Comparator;

/**
 * Created by Ethan on 12/10/2017.
 */
public class ProcessNumberComparator implements Comparator<Process> {
    @Override
    public int compare(Process a, Process b) {
        if (a.getProcessNumber() == b.getProcessNumber())
            return 0;
        return a.getProcessNumber() > b.getProcessNumber() ? 1 : -1;
    }
}
