package com.alibaba.druid.sql.dialect.oracle.ast.expr;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLExprImpl;
import com.alibaba.druid.sql.dialect.oracle.ast.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class OracleArrayAccessExpr extends SQLExprImpl {
    private SQLExpr ownner;
    private final List<SQLExpr> arguments = new ArrayList<SQLExpr>();

    public OracleArrayAccessExpr() {

    }

    public SQLExpr getOwnner() {
        return this.ownner;
    }

    public void setOwnner(SQLExpr ownner) {
        this.ownner = ownner;
    }

    public List<SQLExpr> getArguments() {
        return this.arguments;
    }

    public void output(StringBuffer buf) {
        this.ownner.output(buf);
        buf.append("[");
        int i = 0;
        for (int size = this.arguments.size(); i < size; ++i) {
            if (i != 0) {
                buf.append(", ");
            }
            ((SQLExpr) this.arguments.get(i)).output(buf);
        }
        buf.append("]");
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        this.accept0((OracleASTVisitor) visitor);
    }

    protected void accept0(OracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.ownner);
            acceptChild(visitor, this.arguments);
        }
        visitor.endVisit(this);
    }
}