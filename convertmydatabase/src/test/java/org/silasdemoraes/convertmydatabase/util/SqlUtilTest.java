package org.silasdemoraes.convertmydatabase.util;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author silas.moraes
 */
public class SqlUtilTest {

    private String sql;
    private SqlUtil sqlUtil;

    public SqlUtilTest() {
    }

    @Before
    public void setUp() {
        sqlUtil = new SqlUtil();
        this.sql = "-- MySQL Workbench Forward Engineering\n";
    }

    @Test
    public void mysqlToPostgresTest() {
        sqlUtil.mysqlToPostgres(sql);
    }

}
