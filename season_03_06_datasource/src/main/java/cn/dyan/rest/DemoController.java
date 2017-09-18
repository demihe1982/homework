package cn.dyan.rest;

import cn.dyan.service.DemoService;
import cn.dyan.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by demi on 2017/9/5.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @Autowired
    private OrgService orgService;

    @GetMapping("/{uname}")
    public ResponseEntity<Object> sayHello(@PathVariable String uname){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("code",1);
        result.put("message","Success");
        try {
            demoService.sayHello(uname);
//            orgService.insertOrg(UUID.randomUUID().toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<Object>(result,HttpStatus.OK);
    }
}
