package com.epam.testapp;

import com.epam.testapp.dao.AuthorDao;
import com.epam.testapp.dao.CommentDao;
import com.epam.testapp.dao.NewsDao;
import com.epam.testapp.dao.TagDao;
import com.epam.testapp.service.Service;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public abstract class DBUnitImplTest {
    protected static AbstractApplicationContext context;
    protected static DataSource dataSource;
    protected static NewsDao newsDao;
    protected static CommentDao commentDao;
    protected static AuthorDao authorDao;
    protected static TagDao tagDao;
    protected static Service service;

    @Before
    public  void setUpOperation() throws Exception {
        context = new ClassPathXmlApplicationContext("spring/applicationContext_test.xml");
        newsDao = (NewsDao) context.getBean("newsDaoImpl");
        commentDao = (CommentDao) context.getBean("commentDaoImpl");
        authorDao = (AuthorDao) context.getBean("authorDaoImpl");
        tagDao = (TagDao) context.getBean("tagDaoImpl");
        dataSource = (DataSource) context.getBean("dataSource");
        service = (Service) context.getBean("serviceImpl");

        Connection con = dataSource.getConnection();
        try {
            DatabaseMetaData databaseMetaData = con.getMetaData();
            // oracle schema name is the user name
            IDatabaseConnection conn = new DatabaseConnection(con,
                    databaseMetaData.getUserName().toUpperCase());
            DatabaseConfig config = conn.getConfig();
            // oracle 10g
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                    new Oracle10DataTypeFactory());

            IDataSet data = getDataSet();
            DatabaseOperation.CLEAN_INSERT.execute(conn, data);
        } finally {
            con.close();
        }
    }

    public abstract IDataSet getDataSet() throws Exception ;

    @AfterClass
    public static void afterClass() {
        if (context != null) {
            context.close();
            context = null;
        }
    }
}
