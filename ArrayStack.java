//the implementation of the StackADT interface, using an Array.
//items will be added to the right-most location. 
public class ArrayStack<T> {
    //holds the stack items
    private T[] array; 
    //holds the index of next available location in stack
    private int top;

    //default constructor
    public ArrayStack() {
        //init with capacity 10 (type not known at runtime so cast to generic type)
        this.array = (T[]) new Object[10];
        this.top = this.array.length - 1;
    }

    //constructor for specified initial capacity (overloading) 
    public ArrayStack(int initCapacity) {
        //init with given capacity instead
        this.array = (T[]) new Object[initCapacity];
        this.top = this.array.length - 1;
    }

    //method to push onto the stack
    public void push(T element) {
        //check if there is any available space
        if(this.top < 0) {
            //we have to expand capacity
            this.expandCapacity();
        }
        //add to next available location
        this.array[this.top] = element;
        //update top
        this.top--;
    }

    //method to pop element off the stack
    public T pop() throws CollectionException {
        //check if the stack is empty
        if(this.isEmpty()) {
            //throw the exception
            throw new CollectionException("Stack is empty");
        }
        else {
            //get the element on stack
            T element = this.array[this.top + 1];
            //update the location in array
            this.array[this.top + 1] = null;
            //update top
            this.top++;
            //return it
            return element;
        }
    }

    //peeks at first element on stack
    public T peek() throws CollectionException {
        //check if the stack is empty
        if(this.isEmpty()) {
            //throw the exception
            throw new CollectionException("Stack is empty");
        }
        else {
            //return the element on top of stack
            return this.array[this.top + 1];
        }
    }

    //method to check if stack is empty
    public boolean isEmpty() {
        //empty if next available location is same as array length - 1
        return (this.array.length - 1) == this.top;
    }

    //method to check size of the stack
    public int size() {
        //find from difference between last index and next available index
        return (this.array.length - 1) - this.top;
    }

    //method to get the capacity of the stack (length of array that is being used to implement the stack)
    public int getCapacity() {
        return this.array.length;
    }

    //method to get top index
    public int getTop() {
        return this.top;
    }

    //toString
    public String toString() {
        String s = "";

        //if the stack is empty
        if(this.isEmpty()) {
            return "Empty stack.";
        }
        else {
            //list out from top of stack to bottom
            for(int i = this.top + 1; i < this.array.length; i++) {
                //add to the string
                s += this.array[i];

                //if on the last item
                if(i == this.array.length - 1) break;
                
                //add commas between
                s += ", ";
            }
        }
        //return the string
        return s;
    }

    //helper method to get more capacity for the array
    private void expandCapacity() {
        //if capacity is 15 or less
        if(this.getCapacity() <= 15) {
            //expand by doubling current capacity
            T[] newArray = (T[]) new Object[this.array.length * 2];
            //copy values into the new array (start at midway index for newArray)
            for(int i = 0, j = this.array.length; i < this.array.length; i++, j++) {
                newArray[j] = this.array[i];
            }
            //fix top!
            this.top = this.array.length - 1;
            //make this.array reference the new array
            this.array = newArray;
        }
        else {
            //expand by 10 spaces
            T[] newArray = (T[]) new Object[this.array.length + 10];
            //copy values into the new array
            for(int i = 0, j = 10; i < this.array.length; i++, j++) {
                newArray[j] = this.array[i];
            }
            //fix top!
            this.top = 9;
            //make this.array reference the new array
            this.array = newArray;
        }
    }

    public static void main(String[] args) {
        //lets test the stack
        ArrayStack<Integer> myStack = new ArrayStack<>(5);

    }
}
