package com.testing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/hotel")
public class HotelController {

    @ResponseBody
    @RequestMapping
    public String get() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(new ExampleResponse());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "empty";
    }

    @ResponseBody
    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public String put() {
        return "put";
    }

    public static class ExampleResponse {
        private int a;
        private String name;
        private Map<Integer, String> mapping1;

        public ExampleResponse() {
            a = 10;
            name = "ten name";
            mapping1 = new HashMap<>();
            mapping1.put(1, "one");
            mapping1.put(2, "two");
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<Integer, String> getMapping1() {
            return mapping1;
        }

        public void setMapping1(Map<Integer, String> mapping1) {
            this.mapping1 = mapping1;
        }
    }
}
