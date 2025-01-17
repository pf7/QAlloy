/*
 * Kodkod -- Copyright (c) 2005-present, Emina Torlak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package kodkod.ast;

import kodkod.ast.operator.ExprOperator;
import kodkod.ast.visitor.ReturnVisitor;
import kodkod.ast.visitor.VoidVisitor;

/**
 * A relational {@link kodkod.ast.Expression expression} with two children.
 *
 * @specfield left: Expression
 * @specfield right: Expression
 * @specfield op: ExprOperator
 * @specfield op.binary()
 * @invariant children = 0->left + 1->right
 * @author Emina Torlak
 */
public final class BinaryExpression extends Expression {

    private final ExprOperator op;
    private final Expression   left;
    private final Expression   right;
    private final int          arity;

    /**
     * Constructs a new binary expression: left op right
     *
     * @ensures this.left' = left && this.right' = right && this.op' = op
     * @throws NullPointerException left = null || right = null || op = null
     * @throws IllegalArgumentException left and right cannot be combined with the
     *             specified operator.
     */
    BinaryExpression(final Expression left, final ExprOperator op, final Expression right) {
        switch (op) {
            case HADAMARD_PRODUCT: // TODO cardinality
                this.arity = right.arity();
                break;
            case UNION :
            case INTERSECTION :
            case ADDITION:
            case MINUS:
            case LEFT_INTERSECTION:
            case RIGHT_INTERSECTION:
                //case HADAMARD_PRODUCT: TODO cardinality
            case HADAMARD_DIVISION:
            case DIFFERENCE :
            case OVERRIDE :
                this.arity = left.arity();
                if (arity != right.arity())
                    throw new IllegalArgumentException("Incompatible arities: " + left + " and " + right);
                break;
            case JOIN :
            case MULTIJOIN:
                this.arity = left.arity() + right.arity() - 2;
                if (arity < 1)
                    throw new IllegalArgumentException("Incompatible arities: " + left + " and " + right);
                break;
            case PRODUCT :
                this.arity = left.arity() + right.arity();
                break;
            case DOMAIN:
            case RANGE:
                if(right.arity() != 1)
                    throw new IllegalArgumentException("Right operand must have arity 1, but instead it has arity: " + right.arity());
                this.arity = left.arity();
                break;
            case KHATRI_RAO:
                this.arity = left.arity() + right.arity() - 1;
                break;
            case SCALAR:
                this.arity = right.arity();
                break;
            default :
                throw new IllegalArgumentException("Not a binary operator: " + op);
        }

        this.op = op;
        this.left = left;
        this.right = right;

    }

    /**
     * Returns the arity of this binary expression.
     *
     * @return this.arity
     * @see kodkod.ast.Expression#arity()
     */
    @Override
    public int arity() {
        return arity;
    }

    /**
     * Returns this.op.
     *
     * @return this.op
     */
    public ExprOperator op() {
        return op;
    }

    /**
     * Returns the left child of this.
     *
     * @return this.left
     */
    public Expression left() {
        return left;
    }

    /**
     * Returns the right child of this.
     *
     * @return this.right
     */
    public Expression right() {
        return right;
    }

    /**
     * {@inheritDoc}
     *
     * @see kodkod.ast.Expression#accept(kodkod.ast.visitor.ReturnVisitor)
     */
    @Override
    public <E, F, D, I> E accept(ReturnVisitor<E,F,D,I> visitor) {
        return visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     *
     * @see kodkod.ast.Node#accept(kodkod.ast.visitor.VoidVisitor)
     */
    @Override
    public void accept(VoidVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * {@inheritDoc}
     *
     * @see kodkod.ast.Node#toString()
     */
    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
