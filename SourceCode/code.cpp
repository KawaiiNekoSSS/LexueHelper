#include <iostream>  
#include <string>  
#include <cmath>  
#include <climits>  
using namespace std;  
 
#define STATUS int  
#define OK 1  
#define ERROR 0  
#define INFEASIBLE -1  
  
template<typename T>  
class mStack {  
    public:  
        mStack();  
        ~mStack();  
        STATUS push(const T x);  
        void pop();  
        T top();  
        bool isEmpty() const;  
        int size() const;  
        void clear();  
        void showStack();  
    private:  
        struct Node {  
            T data;  
            Node* nxt;  
            Node(T newData, Node* nxtNode) {  
                data = newData;  
                nxt = nxtNode;  
            }  
        };  
        Node *stk, *tp;  
        int siz;  
          
};  
  
template<typename T>  
mStack<T>::mStack() {  
    tp = NULL;  
    siz = 0;      
}  
  
template<typename T>  
mStack<T>::~mStack() {  
    clear();  
}  
  
template<typename T>  
STATUS mStack<T>::push(const T x) {  
    tp = new Node(x, tp);  
    siz++;  
    return OK;  
}  
  
template<typename T>  
T mStack<T>::top() {  
    return tp -> data;  
}  
  
template<typename T>  
void mStack<T>::pop() {  
    auto p = tp;  
    tp = tp -> nxt;  
    delete p;  
    p = NULL;  
    siz--;  
}  
  
template<typename T>  
int mStack<T>::size()const {  
    return siz;  
}  
  
template<typename T>   
bool mStack<T>::isEmpty()const {  
    return (siz ? false : true);  
}  
  
template<typename T>  
void mStack<T>::clear() {  
    for (auto i = tp; i != NULL;) {  
        auto j = i;  
        i = i -> nxt;  
        delete j;  
        --siz;  
    }  
}  
  
template<typename T>  
void mStack<T>::showStack() {  
    for (auto i = tp; i != NULL; i = i -> nxt) {  
        cout << (i -> data) << " ";  
    }  
    cout << endl;  
}  
  
char priority_table[9][9] = {  
//+   -   *   /   %  ^   (   )   #  
{'>','>','<','<','<','<','<','>','>'},  
{'>','>','<','<','<','<','<','>','>'},  
{'>','>','>','>','>','<','<','>','>'},  
{'>','>','>','>','>','<','<','>','>'},  
{'>','>','>','>','>','<','<','>','>'},  
{'>','>','>','>','>','<','<','>','>'},  
{'<','<','<','<','<','<','<','=','E'},  
{'>','>','>','>','>','>','E','>','>'},  
{'<','<','<','<','<','<','<','E','='}  
};  
  
int getID(char c) {  
    switch(c) {  
        case '+': return 0;  
        case '-': return 1;  
        case '*': return 2;  
        case '/': return 3;  
        case '%': return 4;  
        case '^': return 5;  
        case '(': return 6;  
        case ')': return 7;  
        case '#': return 8;  
        default: return -1;  
    }  
}  
  
char getPriority(char a, char b) {  
    return priority_table[getID(a)][getID(b)];  
}  
  
bool isOperate(char c) {  
    char op[9] = {'+','-','*','/','%','^','(',')','#'};  
    for (int i = 0; i < 9; ++i)   
        if(c == op[i])  
            return true;  
    return false;  
}  
  
int calculate(int a, char op, int b) {  
    int t = a;  
    switch(op) {  
        case '+':  
            return a + b;  
        case '-':  
            return a - b;  
        case '*':  
            return a * b;  
        case '/':  
            if (b == 0) return INT_MAX;  
            return a / b;  
        case '%':  
            if (b == 0) return INT_MAX;  
            return a % b;  
        case '^':  
            if (b < 0) return INT_MIN;  
            return (int)pow(a,b);  
        default:  
            return 0;  
    }  
}  
  
int evaluateExpression(string eq) {  
    for (int i = 0; i < eq.size()-1; ++i) {  
        if (eq[i] == '-' && (i == 0 || isOperate(eq[i-1]))) {  
            eq.insert(i+1,"1*");  
        }  
    }  
    // cout << eq << endl;  
    for (int i = 0; i < eq.size()-1; ++i) {  
        if (eq[i] == '(') {  
            if (isOperate(eq[i+1]) && eq[i+1]!='-' && eq[i+1]!='(')  
                return INT_MIN;  
        }  
    }  
    mStack<char> OPTR;  
    mStack<int> OPND;  
    OPTR.push('#');  
    char c = eq[0];  
    int q = 0;  
    bool mFlag = false;  
    while (c != '#' || OPTR.top() != '#') {  
//      cout << c << endl;  
//      if (c == '#' && q != 0 && OPTR.top() != '#') {  
//          return INT_MIN;  
//      }  
        if (c == '-' && (q == 0 || isOperate(eq[q-1]))) {  
            mFlag = true;  
            c = eq[++q];  
        }  
        if (!isOperate(c)) {  
            int t = 0;  
            while (!isOperate(c)) {  
                t = t * 10 + c - '0';  
                c = eq[++q];  
            }  
            if (mFlag) {  
                t *= -1;  
                mFlag = false;  
            }  
            OPND.push(t);  
        } else {  
            int a, b; char cmd; int ans;  
//          cout << OPTR.top() << " " << c << " " << getPriority(OPTR.top(), c) << endl;  
            switch(getPriority(OPTR.top(), c)) {  
                case '<':  
                    OPTR.push(c);  
                    c = eq[++q];  
                    break;  
                case '=':  
                    OPTR.pop();  
                    c = eq[++q];  
                    break;  
                case '>':  
                    if (OPTR.isEmpty()) return INT_MIN;  
                    cmd = OPTR.top();  
                    OPTR.pop();  
                    if (OPND.isEmpty()) return INT_MIN;  
                    b = OPND.top();  
                    OPND.pop();  
                    if (OPND.isEmpty()) return INT_MIN;  
                    a = OPND.top();  
                    OPND.pop();  
                    // cout << a << " " << cmd << " " << b << endl;  
                    ans = calculate(a,cmd,b);  
                    if (ans == INT_MIN) return INT_MIN;  
                    else if (ans == INT_MAX) return INT_MAX;  
                    OPND.push(calculate(a,cmd,b));  
                    break;  
                case 'E':  
                    // puts("thisE");  
                    return INT_MIN;  
            }  
        }  
//      OPND.showStack();  
//      OPTR.showStack();  
    }  
    if (OPND.size() > 1) return INT_MIN;  
    if (OPTR.size() > 1) return INT_MIN;  
    return OPND.top();  
}  
  
  
  
int main() {  
    int n; cin >> n;  
    while (n--) {  
        string eq; cin >> eq;  
        eq = eq + '#';  
        int ans = evaluateExpression(eq);  
        if (ans == INT_MIN) puts("error.");  
        else if(ans == INT_MAX) puts("Divide 0.");  
        else cout << evaluateExpression(eq) << endl;  
    }  
    return 0;  
}  