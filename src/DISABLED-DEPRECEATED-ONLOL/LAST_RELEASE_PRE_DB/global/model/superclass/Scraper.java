/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.model.superclass;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Scraper {
    @Column
    private Long lastExecTime = 1000L; // With 0 it is not working
    @Column
    private boolean executing = false; // With 0 it is not working

    public Long getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(Long lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    public boolean isExecuting() {
        return executing;
    }

    public void setExecuting(boolean executing) {
        this.executing = executing;
    }
}
