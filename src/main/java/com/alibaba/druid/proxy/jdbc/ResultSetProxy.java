/*
 * Copyright 1999-2011 Alibaba Group Holding Ltd.
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
package com.alibaba.druid.proxy.jdbc;

import java.sql.ResultSet;

import com.alibaba.druid.stat.JdbcSqlStat;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
public interface ResultSetProxy extends ResultSet, WrapperProxy {

    ResultSet getResultSetRaw();

    StatementProxy getStatementProxy();

    String getSql();
    
    JdbcSqlStat getSqlStat();

    int getCursorIndex();

    int getFetchRowCount();

    long getConstructNano();

    void setConstructNano(long constructNano);

    void setConstructNano();
    
    int getCloseCount();
}
