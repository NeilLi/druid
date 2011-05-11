/**
 * Project: druid
 * 
 * File Created at 2010-12-2
 * $Id$
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.druid.filter.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;

/**
 * 
 * @author shaojin.wensj
 *
 */
public class CommonsLogFilter extends LogFilter {
	private String dataSourceLoggerName = "druid.sql.DataSource";
	private String connectionLoggerName = "druid.sql.Connection";
	private String statementLoggerName = "druid.sql.Statement";
	private String resultSetLoggerName = "druid.sql.ResultSet";

	private Log dataSourceLogger = LogFactory.getLog(dataSourceLoggerName);
	private Log connectionLogger = LogFactory.getLog(connectionLoggerName);
	private Log statementLogger = LogFactory.getLog(statementLoggerName);
	private Log resultSetLogger = LogFactory.getLog(resultSetLoggerName);
	
	public void setDataSourceLogger(Log dataSourceLogger) {
		this.dataSourceLogger = dataSourceLogger;
		if (dataSourceLogger instanceof Log4JLogger) {
			this.dataSourceLoggerName = ((Log4JLogger) dataSourceLogger).getLogger().getName();
		}
	}

	@Override
	public String getDataSourceLoggerName() {
		return dataSourceLoggerName;
	}

	@Override
	public void setDataSourceLoggerName(String dataSourceLoggerName) {
		this.dataSourceLoggerName = dataSourceLoggerName;
		dataSourceLogger = LogFactory.getLog(dataSourceLoggerName);
	}

	@Override
	public String getConnectionLoggerName() {
		return connectionLoggerName;
	}

	@Override
	public void setConnectionLoggerName(String connectionLoggerName) {
		this.connectionLoggerName = connectionLoggerName;
		connectionLogger = LogFactory.getLog(connectionLoggerName);
	}
	
	public void setConnectionLogger(Log connectionLogger) {
		this.connectionLogger = connectionLogger;
		if (connectionLogger instanceof Log4JLogger) {
			this.connectionLoggerName = ((Log4JLogger) connectionLogger).getLogger().getName();
		}
	}

	@Override
	public String getStatementLoggerName() {
		return statementLoggerName;
	}

	@Override
	public void setStatementLoggerName(String statementLoggerName) {
		this.statementLoggerName = statementLoggerName;
		statementLogger = LogFactory.getLog(statementLoggerName);
	}
	
	public void setStatementLogger(Log statementLogger) {
		this.statementLogger = statementLogger;
		if (statementLogger instanceof Log4JLogger) {
			this.statementLoggerName = ((Log4JLogger) statementLogger).getLogger().getName();
		}
	}

	@Override
	public String getResultSetLoggerName() {
		return resultSetLoggerName;
	}

	@Override
	public void setResultSetLoggerName(String resultSetLoggerName) {
		this.resultSetLoggerName = resultSetLoggerName;
		resultSetLogger = LogFactory.getLog(resultSetLoggerName);
	}
	
	public void setResultSetLogger(Log resultSetLogger) {
		this.resultSetLogger = statementLogger;
		if (resultSetLogger instanceof Log4JLogger) {
			this.resultSetLoggerName = ((Log4JLogger) resultSetLogger).getLogger().getName();
		}
	}

	@Override
	public boolean isDataSourceLogEnabled() {
		return dataSourceLogger.isDebugEnabled() && super.isDataSourceLogEnabled();
	}
	
	public boolean isConnectionLogErrorEnabled() {
		return connectionLogger.isErrorEnabled() && super.isConnectionLogErrorEnabled();
	}

	@Override
	public boolean isConnectionLogEnabled() {
		return connectionLogger.isDebugEnabled() && super.isConnectionLogEnabled();
	}

	@Override
	public boolean isStatementLogEnabled() {
		return statementLogger.isDebugEnabled() && super.isStatementLogEnabled();
	}

	@Override
	public boolean isResultSetLogEnabled() {
		return resultSetLogger.isDebugEnabled() && super.isResultSetLogEnabled();
	}

	@Override
	public boolean isResultSetLogErrorEnabled() {
		return resultSetLogger.isErrorEnabled() && super.isResultSetLogErrorEnabled();
	}

	@Override
	public boolean isStatementLogErrorEnabled() {
		return statementLogger.isErrorEnabled() && super.isStatementLogErrorEnabled();
	}

	@Override
	protected void connectionLog(String message) {
		connectionLogger.debug(message);
	}

	@Override
	protected void statementLog(String message) {
		statementLogger.debug(message);
	}

	@Override
	protected void resultSetLog(String message) {
		resultSetLogger.debug(message);
	}

	@Override
	protected void resultSetLogError(String message, Throwable error) {
		resultSetLogger.error(message, error);
	}

	@Override
	protected void statementLogError(String message, Throwable error) {
		statementLogger.error(message, error);
	}
}