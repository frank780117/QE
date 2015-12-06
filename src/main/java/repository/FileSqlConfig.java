package repository;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import entity.SqlConfig;

@Component
@ConfigurationProperties(prefix = "SqlQueryFunction")
public class FileSqlConfig {
	

	private Map<String, SqlConfig> sqlConfigs;

	public Map<String, SqlConfig> getSqlConfigs() {
		return sqlConfigs;
	}

	public void setSqlConfigs(Map<String, SqlConfig> sqlConfigs) {
		this.sqlConfigs = sqlConfigs;
	}

	public void refresh() {
		sqlConfigs.entrySet().forEach((e) -> e.getValue().setId(e.getKey()));
	}
	
}
