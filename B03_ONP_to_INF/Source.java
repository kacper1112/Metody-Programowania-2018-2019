import java.util.Scanner;

class Stack {
    String[] tab;
    int len;

    Stack(int _len) {
        tab = new String[_len];
        len = 0;
    }

    public static int priority(char t)
    {
        if(t == '=')
            return 0;
        else if(t == '<' || t == '>')
            return 1;
        else if(t == '+' || t == '-')
            return 2;
        else if(t == '*' || t == '/' || t == '%')
            return 3;
        else if(t == '^')
            return 4;
        else if(t == '~')
            return 5;
        else if(Character.isLetter(t))
            return 6;

        return -1;
    }

    public boolean isEmpty()
    {
        if(len == 0)
            return true;
        else
            return false;
    }

    public void pushBack(String val)
    {
        if(len < 255)
            tab[len++] = val;
        else
            System.out.println("Max size exceeded");
    }

    public String pop()
    {
        if(!this.isEmpty())
        {
            String temp = tab[len - 1];
            tab[len - 1] = "";
            len--;
            return temp;
        }

        return "x";
    }

    public String top()
    {
        if(!this.isEmpty())
            return tab[len - 1];
        return "x";
    }

    public int getSize()
    {
        return len;
    }
}

class Stack2 {
    int[] tab;
    int len;

    Stack2(int _len) {
        tab = new int[_len];
        len = 0;
    }

    public boolean isEmpty()
    {
        if(len == 0)
            return true;
        else
            return false;
    }

    public void pushBack(int val)
    {
        if(len < 255)
            tab[len++] = val;
        else
            System.out.println("Max size exceeded");
    }

    public int pop()
    {
        if(!this.isEmpty())
        {
            int temp = tab[len - 1];
            tab[len - 1] = 0;
            len--;
            return temp;
        }
        return 0;
    }

    public int top()
    {
        if(!this.isEmpty())
            return tab[len - 1];
        return 0;
    }

    public int getSize()
    {
        return len;
    }
}

class convert {

    public static String infToONP(String input)
    {
        String result = "";
        Stack ops = new Stack(input.length());
        int open = 0;
        int close = 0;
        int equal = 0;

        for(int i = 0; i < input.length(); i++)
        {
            if(open - close < 0)
            {
                result = "error";
                break;
            } 

            if(Character.isLetter(input.charAt(i)))
            {   
                result += input.charAt(i);
            }
            else if(input.charAt(i) != '(' && input.charAt(i) != ')')
            {
                if(input.charAt(i) == '=')
                    equal++;

                while(!ops.isEmpty() && Stack.priority(ops.top().charAt(0)) >= Stack.priority(input.charAt(i)) && input.charAt(i) != '~' && input.charAt(i) != '^')
                    result += ops.pop();

                ops.pushBack(String.valueOf(input.charAt(i))); 
            }
            else
            {
                if(input.charAt(i) == '(')
                {
                    ops.pushBack(String.valueOf(input.charAt(i)));
                    open++;
                }
                else
                {
                    close++;

                    while(ops.top().charAt(0) != '(' && !ops.isEmpty())
                        result += ops.pop();

                    ops.pop();
                }
            }
        }

        while(!ops.isEmpty())
            result += ops.pop();

        result = result.replaceAll("[=]", "");
        while(equal-- > 0)
            result += "=";

        if(open != close)
            result = "error";


        return "ONP: " + result;
    }

    public static String ONPToInf(String input)
    {
        Stack stack = new Stack(input.length());
        Stack2 pro = new Stack2(input.length());

        int operands = 0;
        int operators = 0;
        String temp = "";

        for(int i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) != '(' && input.charAt(i) != ')')
            {
                if(Character.isLetter(input.charAt(i)))
                {
                    stack.pushBack(String.valueOf(input.charAt(i)));
                    pro.pushBack(Stack.priority(input.charAt(i)));
                    operands++;
                }
                else
                {
                    temp = "";

                    if(input.charAt(i) != '~' && input.charAt(i) != '^') 
                    {
                        operators++;

                        if(pro.top() <= Stack.priority(input.charAt(i)) && input.charAt(i) != '=')
                            temp = "(" + stack.pop() + ")";
                        else
                            temp = stack.pop();

                        pro.pop();

                             
                        if(pro.top() < Stack.priority(input.charAt(i)))
                            temp = "(" + stack.pop() + ")" + input.charAt(i) + temp;
                        else 
                            temp = stack.pop() + input.charAt(i) + temp;

                        pro.pop();
                    }
                    else if(input.charAt(i) == '^')
                    {
                        operators++;

                        if(pro.top() >= Stack.priority(input.charAt(i)))
                            temp = input.charAt(i) + stack.pop();
                        else
                            temp = input.charAt(i) + "(" + stack.pop() + ")";

                        pro.pop();

                             
                        if(pro.top() >= Stack.priority(input.charAt(i)))
                            temp = stack.pop() + temp;
                        else 
                            temp = "(" + stack.pop() + ")" + temp;

                        pro.pop();
                    }
                    else
                    {
                        if(pro.top() == Stack.priority(input.charAt(i)))
                            temp = input.charAt(i) + stack.pop();
                        else if(pro.top() < Stack.priority(input.charAt(i)))
                            temp = input.charAt(i) + "(" + stack.pop() + ")";
                        else
                            temp = input.charAt(i) + stack.pop();

                        pro.pop();
                    }

                    stack.pushBack(temp);
                    pro.pushBack(Stack.priority(input.charAt(i)));
                }
            }
        }

        if(operands - 1 == operators)
            return "INF: " + stack.pop();
        else    
            return "INF: error";
    }

    public static boolean turing(String in)
    {
        int state = 0;

        for(int i = 0; i < in.length(); i++)
        {
            if(state == 0)
            {
                if(Character.isLetter(in.charAt(i)) == true)
                    state = 1;
                else if(in.charAt(i) == '~')
                    state = 2;
                else if(in.charAt(i) != '(')
                    return false;
            }
            else if(state == 1)
            {
                if("^*/%+-<>=".indexOf(in.charAt(i)) != -1)
                    state = 0;
                else if(in.charAt(i) != ')')
                    return false;
            }
            else if(state == 2)
            {
                if(Character.isLetter(in.charAt(i)) == true)
                    state = 1;
                else if(in.charAt(i) == '(')
                    state = 0;
                else if(in.charAt(i) != '~')
                    return false;
            }
        }

        if(state == 1)
            return true;

        return false;
    }
}



public class Source {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int sets = in.nextInt();
        String mode = in.nextLine(); //smieci

        while(sets-- > 0)
        {
            String result = "";
            mode = in.nextLine();
            String line = mode.substring(5);
            line = line.replaceAll("[^a-z\\(\\)\\-*=<>/%^~+]", "");

            if(mode.startsWith("INF"))
            {
                //if(convert.check(line) == true)
                if(convert.turing(line) == true)
                    result = convert.infToONP(line);
                else
                    result = "ONP: error";
            }
            else
                result = convert.ONPToInf(line);

            System.out.println(result);
        }

        in.close();
    }
}
