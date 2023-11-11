package com.jacobo0312.backstage2.controller;


import com.jacobo0312.backstage2.model.TopTerm;
import com.jacobo0312.backstage2.service.BigQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
@Slf4j
public class BigQueryController {

    @Autowired
    BigQueryService bigQueryService;

    @GetMapping("/querydb")
    public List<TopTerm> querydb() {
        log.info("Starting querydb");
        try {
            return bigQueryService.basicQuery();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
