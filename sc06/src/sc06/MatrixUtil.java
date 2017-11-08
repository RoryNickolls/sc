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
	
	public static double convolve(int[][] kernel, int[][] pixelData)
	{
		double result = 0;
		
		// first flip the rows and columns of the kernel
		int[][] flippedKernel = new int[kernel.length][kernel[0].length];
		for(int row = 0; row < kernel.length; row++)
		{
			for(int col = 0; col < kernel[0].length; col++)
			{
				flippedKernel[kernel.length - row - 1][kernel[0].length - col - 1] = kernel[row][col];
			}
		}
		
		// then multiply 'locally similar' entries of the flippedKernel matrix and the pixelData matrix
		for(int row = 0; row < kernel.length; row++)
		{
			for(int col = 0; col < kernel[0].length; col++)
			{
				result += flippedKernel[row][col] * pixelData[row][col];
			}
		}
		
		return result;
	}
}
