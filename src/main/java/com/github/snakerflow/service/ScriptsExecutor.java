/*
 *  Copyright 2013-2014 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.github.snakerflow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.access.ScriptRunner;
import org.snaker.engine.access.jdbc.JdbcHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 脚本执行类
 * @author yuqs
 * @since 1.0
 */
public class ScriptsExecutor {
    private static final Logger log = LoggerFactory.getLogger(ScriptsExecutor.class);
    //数据源
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run() {
        log.info("scripts running......");
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            if(JdbcHelper.isExec(conn)) {
                log.info("script has completed execution.skip this step");
                return;
            }
            String databaseType = JdbcHelper.getDatabaseType(conn);
            String[] schemas = new String[]{"db/schema-" + databaseType + ".sql"};
            ScriptRunner runner = new ScriptRunner(conn, true);
            for(String schema : schemas) {
                runner.runScript(schema);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                JdbcHelper.close(conn);
            } catch (SQLException e) {
                //ignore
            }
        }
    }
}
