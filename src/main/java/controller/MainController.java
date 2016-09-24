package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clike.qe.vo.CurrentPage;

import repository.FileSqlConfig;
import repository.SqlConfigRepository;
import service.SqlExecutor;
import entity.SqlConfig;

@Controller
public class MainController{

    @Autowired
    private FileSqlConfig fileSqlConfig;

    @Autowired
    private SqlExecutor sqlExecutor;
    
    @RequestMapping(value = "/sqlConfig", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<SqlConfig> greeting(Model model){
        return fileSqlConfig.getSqlConfigs().values();
    }
    
    @RequestMapping(value = "/sqlConfig/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SqlConfig greetingId(@PathVariable String id,Model model){
        return fileSqlConfig.getSqlConfigs().get(id);
    }
    
    @RequestMapping(value = "/executeSql", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> executeSql(@RequestParam("sql") String sql, 
                                                @RequestParam(value = "params", required = false) List<String> params) {
        if(params == null) {
            params = new LinkedList<String>();
        }
        return sqlExecutor.executeSql(sql, params.toArray());
    }
    
    @RequestMapping(value = "/executeSqlPageable", method = RequestMethod.GET)
    @ResponseBody
    public CurrentPage<Map<String, Object>> executeSql(@RequestParam("sql") String sql, 
                                                @RequestParam(value = "params", required = false) List<String> params,
                                                @RequestParam(value = "pageNo", defaultValue="1") int pageNo) {
        if(params == null) {
            params = new LinkedList<String>();
        }
        return sqlExecutor.executeSql(sql, params.toArray(), pageNo, 10);
    }
}