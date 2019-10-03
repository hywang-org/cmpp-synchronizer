package com.cmpp.service.dao;

import com.cmpp.common.db.HibernateDao;
import com.cmpp.common.db.SessionFactoryBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Component
public class SyncDao extends HibernateDao {

	@Value("${dataSource.url}")
	private String url;

	@Value("${dataSource.user}")
	private String user;

	@Value("${dataSource.password}")
	private String password;

	@Value("${dataSource.hbm2ddl:none}")
	private String hbm2ddl;

	@Value("${dataSource.show_sql:false}")
	private String show_sql;

	private static final Log LOGGER = LogFactory.getLog(SyncDao.class);

	@PostConstruct
	public void initSessionFactory() {
		if (StringUtils.isEmpty(url)) {
			LOGGER.info(getClass() + "由于没有找到配置参数,未装配完整");
			return;
		}
		SessionFactoryBuilder sfb = new SessionFactoryBuilder();
		sfb.setUrl(url);
		sfb.setUsername(user);
		sfb.setPassword(password);
		sfb.setPackagesToScan(new String[] { "com.cmpp.entity" });
		sfb.setHbm2ddl(hbm2ddl);
		sfb.setShowSql(show_sql);
		SessionFactory sf = sfb.buildSessionFactory();
		setSessionFactory(sf);
	}

}