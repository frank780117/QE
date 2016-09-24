package service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.clike.qe.vo.CurrentPage;

@Service
public class SqlExecutor {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Map<String, Object>> executeSql(String sql, Object[] parameters) {
    return jdbcTemplate.queryForList(sql, parameters);
  }

  @SuppressWarnings("unchecked")
  public CurrentPage<Map<String, Object>> executeSql(String sql, Object[] parameters, int pageNo,
      int pageSize) {
    Assert.notNull(pageNo);
    Assert.notNull(pageSize);
    Assert.state(pageNo > 0 && pageSize > 0, "pageNo and pageSize must > 0");
    
    String sqlCountRows = String.format("select count(1) from (%s) AS T", sql);
    int rowCount = jdbcTemplate.queryForObject(sqlCountRows, parameters, Integer.class);
    int pageCount = rowCount / pageSize;
    if (rowCount > pageSize * pageCount) {  
      pageCount++;
    }
    
    CurrentPage<Map<String, Object>> page = new CurrentPage<Map<String, Object>>();  
    page.setPageNumber(pageNo);  
    page.setPagesAvailable(pageCount);
    int startRow = (pageNo - 1) * pageSize;
    jdbcTemplate.query(sql, parameters, new ResultSetExtractor() {
      public Object extractData(ResultSet rs) throws SQLException,  
              DataAccessException {  
          final List pageItems = page.getPageItems();  
          int currentRow = 0;  
          ResultSetMetaData md = rs.getMetaData();
          int columns = md.getColumnCount();
          
          while (rs.next() && currentRow < startRow + pageSize) {  
              if (currentRow >= startRow) {  
                Map<String, Object> row = new HashMap<String, Object>();
                for(int i=1; i<=columns; ++i){
                  row.put(md.getColumnName(i),rs.getObject(i));
                }
                pageItems.add(row);
              }  
              currentRow++;  
          }  
          return page;  
      }  
  });
  
  return page;
    
  }

}
