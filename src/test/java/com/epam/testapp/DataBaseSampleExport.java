package com.epam.testapp;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DataBaseSampleExport
{
    public static void main(String[] args) throws Exception
    {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");

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

            QueryDataSet partialDataSet = new QueryDataSet(conn);
            partialDataSet.addTable("NEWS2");
            partialDataSet.addTable("AUTHOR");
            partialDataSet.addTable("NEWS_AUTHOR");
            partialDataSet.addTable("TAGS");
            partialDataSet.addTable("NEWS_TAG");
            partialDataSet.addTable("COMMENTS");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/data/news.xml"));

            partialDataSet = new QueryDataSet(conn);
            partialDataSet.addTable("COMMENTS");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/data/comments.xml"));

            partialDataSet = new QueryDataSet(conn);
            partialDataSet.addTable("TAGS");
            partialDataSet.addTable("NEWS_TAG");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/data/tags.xml"));

            partialDataSet = new QueryDataSet(conn);
            partialDataSet.addTable("AUTHOR");
            partialDataSet.addTable("NEWS_AUTHOR");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/data/author.xml"));

            partialDataSet = new QueryDataSet(conn);
            partialDataSet.addTable("NEWS2");
            partialDataSet.addTable("AUTHOR");
            partialDataSet.addTable("NEWS_AUTHOR");
            partialDataSet.addTable("TAGS");
            partialDataSet.addTable("NEWS_TAG");
            partialDataSet.addTable("COMMENTS");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/data/service.xml"));

        } finally {
            con.close();
        }

    }
}
     