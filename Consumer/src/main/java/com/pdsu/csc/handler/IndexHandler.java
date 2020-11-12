package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 半梦
 * @create 2020-11-10 17:59
 */
@RestController
public class IndexHandler {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/index")
    public Result index() {
        return providerService.index();
    }

}
