package org.yinwang.pysonar.ast;

import org.jetbrains.annotations.NotNull;
import org.yinwang.pysonar.Scope;
import org.yinwang.pysonar.types.ListType;
import org.yinwang.pysonar.types.Type;

import java.util.List;


public class NList extends Sequence
{

    public NList(List<Node> elts, int start, int end)
    {
        super(elts, start, end);
    }


    @NotNull
    @Override
    public Type resolve(Scope s)
    {
        if (elts.size() == 0)
        {
            return new ListType();  // list<unknown>
        }

        ListType listType = new ListType();
        for (Node elt : elts)
        {
            listType.add(resolveExpr(elt, s));
            if (elt instanceof Str)
            {
                listType.addValue(((Str) elt).getStr());
            }
        }

        return listType;
    }


    @NotNull
    @Override
    public String toString()
    {
        return "<List:" + start + ":" + elts + ">";
    }


    @Override
    public void visit(@NotNull NodeVisitor v)
    {
        if (v.visit(this))
        {
            visitNodeList(elts, v);
        }
    }
}
