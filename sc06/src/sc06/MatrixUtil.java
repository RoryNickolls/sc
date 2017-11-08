package sc06;

public class MatrixUtil {
	
	public static int[][] multiply(int[][] firstMatrix, int[][] secondMatrix)
	{
		// create a new matrix with the same number of rows as firstMatrix, and columns as secondMatrix
		int[][] result = new int[firstMatrix.length][secondMatrix[0].length];
		
		// this loop performs standard matrix multiplication
		// for each row in the first matrix
		for(int i = 0; i < firstMatrix.length; i++)
		{
			// for each column in the second matrix
			for(int j = 0; j < secondMatrix[0].length; j++)
			{
				// for each column in the first matrix
				for(int k = 0; k < firstMatrix[0].length; k++)
				{
					result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		int[][] matrix1 = new int[][] { { 1, 2, 3 },
										{ 4, 5, 6 } };
		int[][] matrix2 = new int[][] { { 7, 8 },
										{ 9, 10 },
										{ 11, 12 } };
		int[][] result = multiply(matrix1, matrix2);
		for(int row = 0; row < result.length; row++)
		{
			String line = "";
			for(int col = 0; col < result[0].length; col++)
			{
				line += result[row][col] + ",";
			}
			System.out.println(line);
		}
	}

}
