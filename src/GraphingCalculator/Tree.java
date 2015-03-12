package GraphingCalculator;

public class Tree {

    abstract class Node {

        String data;
        Node right, left;

        abstract float calc();
    }

    Node root;
    String eqn;
    int pos;
    char ch;

    public void parse(String e) {
        eqn = e;
        pos = 0;
        getChar();
        root = findExpr();
    }

    protected char getChar() {
        if (pos < eqn.length()) {
            return ch = eqn.charAt(pos++);
        }
        return ch = '\0';

    }

    protected Node findExpr() {
        Node tmproot = findProd();
        while (ch == '+' || ch == '-') {
            Node tmp;
            if (ch == '+') {
                tmp = new Node() {

                    public float calc() {
                        return left.calc() + right.calc();
                    }
                };
            } else {
                tmp = new Node() {

                    public float calc() {
                        return left.calc() - right.calc();
                    }
                };
            }
            getChar();
            tmp.left = tmproot;
            tmp.right = findProd();
            tmproot = tmp;
        }
        return tmproot;
    }

    protected Node findProd() {
        Node tmproot = findTerm();
        while (ch == '*' || ch == '/') {
            Node tmp;
            if (ch == '*') {
                tmp = new Node() {

                    public float calc() {
                        return left.calc() * right.calc();
                    }

                };
            } else {
                tmp = new Node() {

                    public float calc() {
                        return left.calc() / right.calc();
                    }
                };
            }

            getChar();
            tmp.left = tmproot;
            tmp.right = findTerm();
            tmproot = tmp;
        }
        return tmproot;
    }

    protected Node findTerm() {
        if (ch == 'x') {
            Node tmp = new Node() {
                public float calc() {
                    return x;
                }
            };
            getChar();
            return tmp;
        }
        if (ch >= '0' && ch <= '9') {
            StringBuilder buffer = new StringBuilder();
            for (; ch >= '0' && ch <= '9'; getChar()) {
                buffer.append(ch);
            }
            final float z = Float.parseFloat(buffer.toString());

            return new Node() {
                public float calc() {
                    return z;
                }
            };
        }
        if (ch == '(') {
            getChar();
            Node tmp = findExpr();
            getChar();
            return tmp;
        }
        if (ch == '-') {
            getChar();
            Node tmp = new Node() {
                public float calc() {
                    return -right.calc();
                }
            };
            tmp.right = findTerm();
            return tmp;
        }
        if (ch == 's' && eqn.substring(pos).startsWith("in(")) {
            //pos is position of characters after s
            pos += 3;
            Node tmp = new Node() {

                public float calc() {
                    return (float) Math.sin(right.calc());
                }
            };
            getChar();
            tmp.right = findExpr();
            getChar();
            return tmp;
        }
        if (ch == 'c' && eqn.substring(pos).startsWith("os(")) {
            //pos is position of characters after s
            pos += 3;
            Node tmp = new Node() {

                public float calc() {
                    return (float) Math.cos(right.calc());
                }
            };
            getChar();
            tmp.right = findExpr();
            getChar();
            return tmp;
        }
        if (ch == 't' && eqn.substring(pos).startsWith("an(")) {
            //pos is position of characters after s
            //pos is originally zero, incrimented to 2
            pos += 3;
            Node tmp = new Node() {

                public float calc() {
                    return (float) Math.tan(right.calc());
                }
            };
            getChar();
            tmp.right = findExpr();
            getChar();
            return tmp;
        }

        return null;
    }
    protected float x;

    public float calc(float x) {
        this.x = x;
        return root.calc();

    }
//protected float calc(Node root){
//    switch(root.data.charAt(0)){
//        case '+':
//            return calc(root.left)+calc(root.right);
//        case '-':
//            return calc(root.left)-calc(root.right);
//        case '*':
//            return calc(root.left)*calc(root.right);
//        case 'x':
//                return  x;
//             }
//    return Float.parseFloat(root.data);
//}
}
