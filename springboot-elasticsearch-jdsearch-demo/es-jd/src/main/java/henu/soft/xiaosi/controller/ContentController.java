package henu.soft.xiaosi.controller;


import henu.soft.xiaosi.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ContentController {

    @Autowired
    ContentService contentService;


    @GetMapping("/parse/{keywords}")
    public boolean parse(@PathVariable String keywords) throws IOException {
        return contentService.getDataToEsByKeywords(keywords);

    }

    @GetMapping("/search/{keywords}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> search(@PathVariable String keywords,@PathVariable int pageNo,@PathVariable int pageSize) throws IOException {
        List<Map<String, Object>> maps = contentService.searchDataFromEsHighLight(keywords, pageNo, pageSize);
        System.out.println(maps);
        return maps;


    }

}
