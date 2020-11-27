package com.pdsu.csc.handler;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 半梦
 * @create 2020-11-10 18:01
 */
@RestController
public class SearchHandler {

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @CrossOrigin
    public Result searchByText(@RequestParam(value = "p")String text, HttpServletRequest request) {
        return providerService.searchByText(text);
    }

}
