package com.testing.config;

import com.testing.services.operations.OperationFactory;
import com.testing.workflow.manager.WorkflowFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupListener {
    private static final Logger LOGGER = Logger.getLogger(StartupListener.class);

    @Autowired
    WorkflowFactory workflowFactory;
    @Autowired
    OperationFactory operationFactory;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.info("Initializing app...");
        workflowFactory.init();
        operationFactory.init();
    }
}
