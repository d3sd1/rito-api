/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.configuration;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * The type Shutdown manager.
 * This handles application shutdown to gracefully stop scrapers from working.
 * Must be like this, also, static.
 *
 * @version 1.0.7
 */
@Component
public class ShutdownManager {


    /**
     * On app shutdown.
     *
     * @param event the event
     */
    @EventListener
    public void onAppShutdown(ContextClosedEvent event) {
        Constants.APP_BEING_CLOSED = true;
    }
}
