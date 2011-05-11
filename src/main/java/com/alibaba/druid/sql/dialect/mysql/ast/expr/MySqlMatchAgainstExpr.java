package com.alibaba.druid.sql.dialect.mysql.ast.expr;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLExprImpl;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class MySqlMatchAgainstExpr extends SQLExprImpl implements MySqlExpr {
    private static final long serialVersionUID = 1L;

    private List<SQLExpr> columns = new ArrayList<SQLExpr>();

    private SQLExpr against;

    private SearchModifier searchModifier;

    public List<SQLExpr> getColumns() {
        return columns;
    }

    public void setColumns(List<SQLExpr> columns) {
        this.columns = columns;
    }

    public SQLExpr getAgainst() {
        return against;
    }

    public void setAgainst(SQLExpr against) {
        this.against = against;
    }

    public SearchModifier getSearchModifier() {
        return searchModifier;
    }

    public void setSearchModifier(SearchModifier searchModifier) {
        this.searchModifier = searchModifier;
    }

    public static enum SearchModifier {
        IN_BOOLEAN_MODE("IN BOOLEAN MODE"),
        IN_NATURAL_LANGUAGE_MODE("IN NATURAL LANGUAGE MODE"),
        IN_NATURAL_LANGUAGE_MODE_WITH_QUERY_EXPANSION("IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION"),
        WITH_QUERY_EXPANSION("WITH QUERY EXPANSION"), ;

        public final String name;

        SearchModifier() {
            this(null);
        }

        SearchModifier(String name) {
            this.name = name;
        }
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        MySqlASTVisitor mysqlVisitor = (MySqlASTVisitor) visitor;
        if (mysqlVisitor.visit(this)) {
            acceptChild(visitor, this.columns);
            acceptChild(visitor, this.against);
        }
        mysqlVisitor.endVisit(this);
    }
}