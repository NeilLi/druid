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
package com.alibaba.druid.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * 
 * @author shaojin.wensj
 *
 */
public final class JdbcUtils {
	public final static void close(Connection x) {
		if (x != null) {
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public final static void close(Statement x) {
		if (x != null) {
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public final static void close(ResultSet x) {
		if (x != null) {
			try {
				x.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public final static void close(Closeable x) {
		if (x != null) {
			try {
				x.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public final static void printResultSet(ResultSet rs) throws SQLException {
		printResultSet(rs, System.out);
	}

	public final static void printResultSet(ResultSet rs, PrintStream out) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		for (int columnIndex = 1; columnIndex <= columnCount; ++columnIndex) {
			if (columnIndex != 1) {
				out.print('\t');
			}
			out.print(metadata.getColumnName(columnIndex));
		}

		out.println();

		while (rs.next()) {

			for (int columnIndex = 1; columnIndex <= columnCount; ++columnIndex) {
				if (columnIndex != 1) {
					out.print('\t');
				}

				int type = metadata.getColumnType(columnIndex);

				if (type == Types.VARCHAR || type == Types.CHAR || type == Types.NVARCHAR || type == Types.NCHAR) {
					out.print(rs.getString(columnIndex));
				} else if (type == Types.DATE) {
					Date date = rs.getDate(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(date.toString());
					}
				} else if (type == Types.BIT) {
					boolean value = rs.getBoolean(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(Boolean.toString(value));
					}
				} else if (type == Types.BOOLEAN) {
					boolean value = rs.getBoolean(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(Boolean.toString(value));
					}
				} else if (type == Types.TINYINT) {
					byte value = rs.getByte(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(Byte.toString(value));
					}
				} else if (type == Types.SMALLINT) {
					short value = rs.getShort(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(Short.toString(value));
					}
				} else if (type == Types.INTEGER) {
					int value = rs.getInt(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(Integer.toString(value));
					}
				} else if (type == Types.BIGINT) {
					long value = rs.getLong(columnIndex);
					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(Long.toString(value));
					}
				} else if (type == Types.TIMESTAMP) {
					out.print(String.valueOf(rs.getTimestamp(columnIndex)));
				} else if (type == Types.DECIMAL) {
					out.print(String.valueOf(rs.getBigDecimal(columnIndex)));
				} else if (type == Types.CLOB) {
					out.print(String.valueOf(rs.getString(columnIndex)));
				} else if (type == Types.JAVA_OBJECT) {
					Object objec = rs.getObject(columnIndex);

					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(String.valueOf(objec));
					}
				} else if (type == Types.LONGVARCHAR) {
					Object objec = rs.getString(columnIndex);

					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(String.valueOf(objec));
					}
				} else {
					Object objec = rs.getObject(columnIndex);

					if (rs.wasNull()) {
						out.print("null");
					} else {
						out.print(String.valueOf(objec));
					}
				}
			}
			out.println();
		}
	}

	public static String getTypeName(int sqlType) {
		switch (sqlType) {
		case Types.ARRAY:
			return "ARRAY";

		case Types.BIGINT:
			return "BIGINT";

		case Types.BINARY:
			return "BINARY";

		case Types.BIT:
			return "BIT";

		case Types.BLOB:
			return "BLOB";

		case Types.BOOLEAN:
			return "BOOLEAN";

		case Types.CHAR:
			return "CHAR";

		case Types.CLOB:
			return "CLOB";

		case Types.DATALINK:
			return "DATALINK";

		case Types.DATE:
			return "DATE";

		case Types.DECIMAL:
			return "DECIMAL";

		case Types.DISTINCT:
			return "DISTINCT";

		case Types.DOUBLE:
			return "DOUBLE";

		case Types.FLOAT:
			return "FLOAT";

		case Types.INTEGER:
			return "INTEGER";

		case Types.JAVA_OBJECT:
			return "JAVA_OBJECT";

		case Types.LONGNVARCHAR:
			return "LONGNVARCHAR";

		case Types.LONGVARBINARY:
			return "LONGVARBINARY";

		case Types.NCHAR:
			return "NCHAR";

		case Types.NCLOB:
			return "NCLOB";

		case Types.NULL:
			return "NULL";

		case Types.NUMERIC:
			return "NUMERIC";

		case Types.NVARCHAR:
			return "NVARCHAR";

		case Types.REAL:
			return "REAL";

		case Types.REF:
			return "REF";

		case Types.ROWID:
			return "ROWID";

		case Types.SMALLINT:
			return "SMALLINT";

		case Types.SQLXML:
			return "SQLXML";

		case Types.STRUCT:
			return "STRUCT";

		case Types.TIME:
			return "TIME";

		case Types.TIMESTAMP:
			return "TIMESTAMP";

		case Types.TINYINT:
			return "TINYINT";

		case Types.VARBINARY:
			return "VARBINARY";

		case Types.VARCHAR:
			return "VARCHAR";

		default:
			return "OTHER";

		}
	}

	public static String read(Reader reader) {
		try {
			final int DEFAULT_BUFFER_SIZE = 1024 * 4;

			StringWriter writer = new StringWriter();

			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			long count = 0;
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
				count += n;
			}

			return writer.toString();
		} catch (IOException ex) {
			throw new IllegalStateException("read error", ex);
		}
	}

	public static String read(Reader reader, int length) {
		try {
			char[] buffer = new char[length];

			int offset = 0;
			int rest = length;
			int len;
			while ((len = reader.read(buffer, offset, rest)) != -1) {
				rest -= len;
				offset += len;

				if (rest == 0) {
					break;
				}
			}

			return new String(buffer, 0, length - rest);
		} catch (IOException ex) {
			throw new IllegalStateException("read error", ex);
		}
	}
	
    public static String getDriverClassName(String rawUrl) throws SQLException {
        if (rawUrl.startsWith("jdbc:derby:")) {
            return "org.apache.derby.jdbc.EmbeddedDriver";
        } else if (rawUrl.startsWith("jdbc:mysql:")) {
            return "com.mysql.jdbc.Driver";
        } else if (rawUrl.startsWith("jdbc:oracle:")) {
            return "oracle.jdbc.driver.OracleDriver";
        } else if (rawUrl.startsWith("jdbc:microsoft:")) {
            return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if (rawUrl.startsWith("jdbc:jtds:")) {
            return "net.sourceforge.jtds.jdbc.Driver";
        } else if (rawUrl.startsWith("jdbc:fake:")) {
            return "com.alibaba.druid.mock.MockDriver";
        } else if (rawUrl.startsWith("jdbc:postgresql:")) {
        	return "org.postgresql.Driver";
        } else {
            throw new SQLException("unkow jdbc driver");
        }
    }
}