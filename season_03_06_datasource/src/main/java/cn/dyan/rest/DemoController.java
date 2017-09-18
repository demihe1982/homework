package cn.dyan.rest;

import cn.dyan.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by demi on 2017/9/5.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    private static  final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private DemoService demoService;

    @GetMapping("/{uname}/{orgname}")
    public ResponseEntity<Object> sayHello(@PathVariable String uname,@PathVariable String orgname){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("code",0);
        result.put("message","Success");
        try {
            demoService.insertInfo(uname,orgname);
            return new ResponseEntity<Object>(result,HttpStatus.OK);
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(),ex);
            result.put("code",1);
            result.put("message","Fail");
            return new ResponseEntity<Object>(result,HttpStatus.EXPECTATION_FAILED);
        }

    }
}
