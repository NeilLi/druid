package com.alibaba.druid.sql.ast.expr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLExprImpl;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class SQLInListExpr extends SQLExprImpl implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean not = false;
    private SQLExpr expr;
    private List<SQLExpr> targetList = new ArrayList<SQLExpr>();

    public SQLInListExpr() {

    }

    public SQLInListExpr(SQLExpr expr) {

        this.expr = expr;
    }

    public SQLInListExpr(SQLExpr expr, boolean not) {

        this.expr = expr;
        this.not = not;
    }

    public boolean isNot() {
        return this.not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public SQLExpr getExpr() {
        return this.expr;
    }

    public void setExpr(SQLExpr expr) {
        this.expr = expr;
    }

    public List<SQLExpr> getTargetList() {
        return this.targetList;
    }

    public void setTargetList(List<SQLExpr> targetList) {
        this.targetList = targetList;
    }

    public void output(StringBuffer buf) {
        this.expr.output(buf);

        if (this.not) buf.append("NOT IN ");
        else {
            buf.append("IN ");
        }

        buf.append("(");
        int i = 0;
        for (int size = this.targetList.size(); i < size; ++i) {
            if (i != 0) {
                buf.append(", ");
            }
            ((SQLExpr) this.targetList.get(i)).output(buf);
        }
        buf.append(")");
    }

    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.expr);
            acceptChild(visitor, this.targetList);
        }

        visitor.endVisit(this);
    }
}