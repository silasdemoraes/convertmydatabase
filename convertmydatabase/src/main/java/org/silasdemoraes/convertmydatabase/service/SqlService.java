/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.silasdemoraes.convertmydatabase.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.silasdemoraes.convertmydatabase.util.SqlUtil;

/**
 *
 * @author silas.moraes
 */
@Path("sql")
public class SqlService {

    @POST
    @Path("mysql-to-postgres")
    public Response mysqlToPostgres(String sql) {
		System.out.println("teste");
        return Response.ok(new SqlUtil().mysqlToPostgres(sql)).build();
    }

}
