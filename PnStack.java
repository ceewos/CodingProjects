public class PnStack {
    private boolean[] a;          
    private int last;

    public PnStack(int num){
        this.a = new boolean [num];
        this.last = -1;
    }

    public boolean isEmpty(){
        return last == -1;
    }

    public boolean isFull(){
        return (last == a.length);
    }

    public void push(boolean elem){
        if(!this.isFull()){
            last++;
            a[last] = elem;
        }
    }

    public boolean top(){
        if(!this.isEmpty()){
            //System.out.println("Top is "+  a[last]);
            return a[last];

        }
        System.out.print("Error array is empty :");
        return false;
    }

    public boolean pop(){
        if(!this.isEmpty()){
            boolean tmp = a[last];
            --last;
            //System.out.println("Removed "+ tmp);
            return tmp;
        }else{
            System.out.print("Error array is empty :");
            return false;
        }
    }
}

