/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wenwen.sweet.druid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlExportParameterVisitor;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.visitor.ExportParameterVisitor;
import com.alibaba.druid.util.JdbcConstants;
import junit.framework.TestCase;

import java.util.List;


public class MySqlPerfTest extends TestCase {

    private String sql;

    protected void setUp() throws Exception {
        sql = "SELECT * FROM T";
        sql = "SELECT ID, NAME, AGE FROM USER WHERE ID = ?";

//        sql = Utils.readFromResource("benchmark/sql/ob_sql.txt");
    }

    public void test_pert() throws Exception {
        for (int i = 0; i < 10; ++i) {
            perfMySql(sql);
        }
    }

    long perfMySql(String sql) {
        long startYGC = TestUtils.getYoungGC();
        long startYGCTime = TestUtils.getYoungGCTime();
        long startFGC = TestUtils.getFullGC();

        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000 * 1000; ++i) {
            execMySql(sql);
        }
        long millis = System.currentTimeMillis() - startMillis;

        long ygc = TestUtils.getYoungGC() - startYGC;
        long ygct = TestUtils.getYoungGCTime() - startYGCTime;
        long fgc = TestUtils.getFullGC() - startFGC;

        System.out.println("MySql\t" + millis + ", ygc " + ygc + ", ygct " + ygct + ", fgc " + fgc);
        return millis;
    }

    private String execMySql(String sql) {
        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        for (SQLStatement statement : statementList) {
            statement.accept(visitor);
            visitor.println();
        }
        System.out.println(out.toString());
        return out.toString();
    }

    //AST是Abstract Syntax Tree的缩写，也就是抽象语法树。AST是parser输出的结果。下面是获得抽象语法树的一个例子
    private void xxx() {
        final String dbType = JdbcConstants.MYSQL; // 可以是ORACLE、POSTGRESQL、SQLSERVER、ODPS等
        String sql = "select * from t";
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
    }

    //在Druid中，可以使用ExportParameterVisitor参数化SQL，并且获得参数列表
    private static void MySqlExportParameterVisitor() {
        String sql = "select * from t where id = 3 and name = 'abc'";

        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);

        StringBuilder out = new StringBuilder();
        ExportParameterVisitor visitor = new MySqlExportParameterVisitor(out);
        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }

        String paramteredSql = out.toString();
        System.out.println(paramteredSql);

        List<Object> paramters = visitor.getParameters(); // [3, "abc"]
        for (Object param : paramters) {
            System.out.println(param);
        }
    }

    public static void main(String args[]) {
        System.out.println("............");

//        new MySqlPerfTest().execMySql("SELECT ID, NAME, AGE FROM USER WHERE ID = ?");

//        System.out.println(SQLEvalVisitorUtils.evalExpr(JdbcConstants.MYSQL, "? between 1 and 3", 0));

        MySqlExportParameterVisitor();

        System.out.println("............end");
    }
}