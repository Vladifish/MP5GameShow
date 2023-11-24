# MP5GameShow
Who wants to be a millionaire clone, made with Java Servlets and JSPs.

## Features
- Error checking
- Question Randomizer Algorithm
- Session Manipulation

## Question Randomizer Algorithm
- Ensures that we get randomized questions with no duplicates
```java
// difficulty is based on range
// e.g. 0-5 is easy, 6-10 is medium, etc.
// holds questions as well as their possible answers
String[][][] QuestionBank;

private void selectRandomQuestions(int floor, int end, Deque<String[][]> queue) {
        
        // We generate three questions per difficulty
        for (int i=0; i < 3; i++) {
            int rand = (int)(Math.random() * end) + floor;
            queue.addLast(QuestionBank[rand]);
            
            // swap with last element to guarantee no repeat
            int lastEle = floor + end - 1;
            String[][] temp = QuestionBank[lastEle]; 
            QuestionBank[lastEle] = QuestionBank[rand];
            QuestionBank[rand] = temp;
            
            // adjust range so that we don't get duplicate questions
            end--;
        }
    }
```
