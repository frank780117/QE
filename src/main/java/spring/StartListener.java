package spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import repository.FileSqlConfig;

@Component
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		FileSqlConfig f1 = event.getApplicationContext().getBean("fileSqlConfig", FileSqlConfig.class);
		FileSqlConfig f2 = event.getApplicationContext().getBean("SqlQueryFunction.CONFIGURATION_PROPERTIES", FileSqlConfig.class);
		f1.refresh();
		f2.refresh();
	}

}
