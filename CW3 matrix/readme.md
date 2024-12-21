# Matrix Operations (100 marks account 70% of the CW3 grade)

This year, you are required to complete a class that implements matrix operations. The tasks involve creating a **Matrix** class with various methods to handle operations such as addition, subtraction, multiplication, transposition, and determinant calculation. Please download the provided code template from the Learning Mall before starting the assignment. The template will give you a structured starting point for your implementation. Once you have completed writing your code, paste the code for each method into the respective submission boxes on the Learning Mall. The following is a detailed description of the task.

 

**Important Note:** Pay close attention to edge cases and ensure that your methods handle invalid inputs gracefully. Failure to do so will result in significant point deductions.

 

 

**Task 1:** Matrix Class Implementation (10 marks)

Implement the constructor for the class Matrix that will handle basic matrix operations.

l A constructor **public Matrix(double[][] data)** to initialize the matrix. If the input data is null or not a proper 2D array (rows with different lengths), throw an **IllegalArgumentException**.



 

 

**Task 2:** Accessor Method (10 marks)

Implement a method to retrieve the matrix data:

l **public double[][] getData()**: This method should return a deep copy of the **data** field to prevent external modification.

 

 

**Task 3:** Matrix Addition (15 marks)

Implement a method to add two matrices:

l **public Matrix add(Matrix other):** This method should add the corresponding elements of the matrices and return a new Matrix object with the result. If the matrices are not of the same size, throw an **IllegalArgumentException**.

 

 

**Task 4:** Matrix Subtraction (15 marks)

Implement a method to subtract another matrix from the current matrix:

l **public Matrix subtract(Matrix other):** This method should subtract the corresponding elements of the other matrix from the current matrix and return a new **Matrix** object with the result. If the matrices are not of the same size, throw an **IllegalArgumentException**.

 

 

**Task 5:** Matrix Multiplication (20 marks)

Implement a method to multiply the current matrix with another matrix:

l **public Matrix multiply(Matrix other):** This method should perform matrix multiplication and return a new **Matrix** object with the result. If the number of columns in the current matrix does not match the number of rows in the other matrix, throw an **IllegalArgumentException**.

 

 

**Task 6:** Matrix Transposition (10 marks)

Implement a method to transpose the current matrix:

l **public Matrix transpose():** This method should return a new **Matrix** object with the transposed matrix.

 

 

**Task 7:** Matrix Determinant (20 marks)

Implement a method to calculate the determinant of the matrix:

l **public double determinant():** This method should return the determinant of the matrix. For non-square matrices, throw an **IllegalArgumentException**. Implement determinant calculation for 2x2 and 3x3 matrices. For matrices larger than 3x3, return **Double.NaN**.

 

**Example Usage and Output**

This section does not need to be submitted.

 

public static void main(String[] args) {

  double[][] data1 = {{1, 2}, {3, 4}};

  double[][] data2 = {{5, 6}, {7, 8}};

  

  Matrix matrix1 = new Matrix(data1);

  Matrix matrix2 = new Matrix(data2);

  

  Matrix sum = matrix1.add(matrix2);

  Matrix difference = matrix1.subtract(matrix2);

  Matrix product = matrix1.multiply(matrix2);

  Matrix transpose = matrix1.transpose();

  double determinant = matrix1.determinant();

  

  // Print results

  System.out.println("Sum:");

  printMatrix(sum.getData());

  System.out.println("Difference:");

  printMatrix(difference.getData());

  System.out.println("Product:");

  printMatrix(product.getData());

  System.out.println("Transpose:");

  printMatrix(transpose.getData());

  System.out.println("Determinant:");

  System.out.println(determinant);

}

 

private static void printMatrix(double[][] matrix) {

  for (double[] row : matrix) {

​    for (double val : row) {

​      System.out.print(val + " ");

​    }

​    System.out.println();

  }

}

 

The output should be:

Sum:

6.0 8.0 

10.0 12.0 

Difference:

-4.0 -4.0 

-4.0 -4.0 

Product:

19.0 22.0 

43.0 50.0 

Transpose:

1.0 3.0 

2.0 4.0 

Determinant:

-2.0

# Report (100 marks account 30% of the CW3 grade)

In this report, you should document your understanding of the task and provide a justification for your solution. You should also explain why you believe your code structure is well-designed and clear. Additionally, you need to test edge cases and provide a technically sound report on how your code handles these cases.. 

 

The marking is distrubuted as follows:

 

l Understanding of the Task: 30 marks

l Justification of Solution: 30 marks

l Clarity and Structure: 20 marks

l Handling of Edge Cases: 10 marks

l Technical Accuracy: 10 marks