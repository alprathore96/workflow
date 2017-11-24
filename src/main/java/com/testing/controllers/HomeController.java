package com.testing.controllers;

import com.testing.services.HomeService;
import com.testing.workflow.exceptions.InvalidMethodArgumentException;
import com.testing.workflow.exceptions.InvalidOperationPathException;
import com.testing.workflow.manager.WorkflowFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    HomeService homeService;
    @Autowired
    WorkflowFactory workflowFactory;

    @RequestMapping
    public String home() {
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/get")
    public String get() {
        return homeService.get();
    }

    @ResponseBody
    @RequestMapping(value = "/trigger/{workflow_id}")
    public String trigger(@ModelAttribute("requestId") String requestId, @PathVariable("workflow_id") String workflow_id) throws InvalidMethodArgumentException, InvalidOperationPathException {
        Map<String, Object> workflowAttributes = new HashMap<>();
        workflowAttributes.put("hotelId", "1");
        workflowAttributes.put("another", "some value");
        workflowFactory.triggerWorkflow(workflow_id, requestId.toString(), workflowAttributes);
        return "triggered";
    }
}
