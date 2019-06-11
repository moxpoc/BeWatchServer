package com.bewatches.server.Controller;

import com.bewatches.server.Model.Parent.Warning;
import com.bewatches.server.Service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warning")
public class WarningController {

    @Autowired
    WarningService warningService;

    @RequestMapping(method = RequestMethod.POST)
    public Warning saveWarning(@RequestBody Warning warning){
        return warningService.save(warning);
    }
}
