public class CompariStack {
    private int[] a;          
    private int last;
    public CompariStack(int num){
        this.a = new int [num];
        this.last = -1;
    }
    public boolean isEmpty(){
        return last == -1;
    }
    public boolean isFull(){
        return (last == a.length);
    }
    public void push(int num){
        if(!this.isFull()){
            last++;
            a[last] = num;
        }
    }
    public int top(){
        if(!this.isEmpty()){
            //System.out.println("Top is "+  a[last]);
            return a[last];

        }
        //System.out.print("Error array is empty :");
        return last;
    }
    public int pop(){
        if(!this.isEmpty()){
            int tmp = a[last];
            --last;
            //System.out.println("Removed "+ tmp);
            return tmp;
        }else{
             System.out.print("Error array is empty :");
            return last;
        }
    }
}