package cs355;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;

public class MyImage
{
	int width;
	int height;
	int [][] image;
	
	public MyImage(int [][] image)
	{
		this.width = image.length;
		this.height = image[0].length;
		this.image = image;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int[][] getImage()
	{
		return image;
	}

	public void setImage(int[][] image)
	{
		this.image = image;
	}

	public BufferedImage getBufferedImage()
	{
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = image.getRaster();
		raster.setPixels(0, 0, width, height, this.getSingleArray());
		return image;
	}
	
	private int[] getSingleArray()
	{
		int [] temp = new int[width * height];
		for(int i = 0, w = 0, h = 0; i < temp.length; i ++)
		{
			temp[i] = this.image[w][h];
			w ++;
			if(w == this.width)
			{
				w = 0;
				h ++;
			}
		}
		return temp;
	}

	public void brightenImage(int brightnessAmountNum)
	{
		for(int i = 0, col = 0, row = 0; i < width * height; i ++)
		{
			int temp = brightnessAmountNum + this.image[col][row];
			if(temp < 0) temp = 0;
			if(temp > 255) temp = 255;
			this.image[col][row] = temp;
			col ++;
			if(col == width)
			{
				col = 0;
				row ++;
			}
		}
	}

	public void changeContrast(int contrastAmountNum)
	{
		for(int i = 0, col = 0, row = 0; i < width * height; i ++)
		{
			int temp = (int) Math.round(Math.pow(((contrastAmountNum + 100.0)/ 100.0), 4.0) * (this.image[col][row] - 128) + 128);
			if(temp > 255) temp = 255;
			if(temp < 0) temp = 0;
			this.image[col][row] = temp;
			col ++;
			if(col == width)
			{
				col = 0;
				row ++;
			}
		}
	}

	public void doUniformBlur()
	{
		int [][] tempArray = new int[width][height];
		for(int i = 0, col = 0, row = 0; i < width * height; i ++)
		{
			int temp;
			if(row == 0)
			{
				if(col == 0)
				{
					temp = (int) Math.round((this.image[col][row + 1] + this.image[col + 1][row + 1] + this.image[col + 1][row] + this.image[col][row]) / 4.0);
				}
				else if(col == width - 1)
				{
					temp = (int) Math.round((this.image[col - 1][row] + this.image[col - 1][row + 1] + this.image[col][row + 1] + this.image[col][row]) / 4.0);
				}
				else
				{
					temp = (int) Math.round((this.image[col - 1][row] + this.image[col - 1][row + 1] + 
							this.image[col][row + 1] + this.image[col + 1][row + 1] + this.image[col + 1][row] + this.image[col][row]) / 6.0);
				}
			}
			else if(row == height - 1)
			{
				if(col == 0)
				{
					temp = (int) Math.round((this.image[col][row - 1] + this.image[col + 1][row - 1] + this.image[col + 1][row] + this.image[col][row]) / 4.0);
				}
				else if(col == width - 1)
				{
					temp = (int) Math.round((this.image[col - 1][row] + this.image[col - 1][row - 1] + this.image[col][row - 1] + this.image[col][row]) / 4.0);
				}
				else
				{
					temp = (int) Math.round((this.image[col - 1][row] + this.image[col - 1][row - 1] + 
							this.image[col][row - 1] + this.image[col + 1][row - 1] + this.image[col + 1][row] + this.image[col][row]) / 6.0);
				}
			}
			else if(col == 0)
			{
				temp = (int) Math.round((this.image[col][row] + this.image[col][row - 1] + this.image[col + 1][row -1] + 
						this.image[col + 1][row] + this.image[col + 1][row + 1] + this.image[col][row + 1]) / 6.0);
			}
			else if(col == width - 1)
			{
				temp = (int) Math.round((this.image[col][row] + this.image[col][row - 1] + this.image[col - 1][row -1] + 
						this.image[col - 1][row] + this.image[col - 1][row + 1] + this.image[col][row + 1]) / 6.0);
			}
			else
			{
				temp = (int) Math.round((this.image[col - 1][row -1] + this.image[col][row -1] + this.image[col + 1][row -1] + this.image[col - 1][row] + this.image[col - 1][row + 1] + 
						this.image[col][row + 1] + this.image[col + 1][row + 1] + this.image[col + 1][row] + this.image[col][row]) / 9.0);
			}
			if(temp > 255) temp = 255;
			if(temp < 0) temp = 0;
			tempArray[col][row] = temp;
			col ++;
			if(col == width)
			{
				col = 0;
				row ++;
			}
		}
		
		this.image = tempArray;
	}

	public void doMedianBlur()
	{
		int [][] tempArray = new int[width][height];
		for(int i = 0, col = 0, row = 0; i < width * height; i ++)
		{
			int temp;
			if(row == 0)
			{
				if(col == 0)
				{
					
					temp = this.sortFourPixels(this.image[col][row + 1], this.image[col + 1][row + 1], this.image[col + 1][row], this.image[col][row]);
				}
				else if(col == width - 1)
				{
					temp = this.sortFourPixels(this.image[col - 1][row], this.image[col - 1][row + 1], this.image[col][row + 1], this.image[col][row]);
				}
				else
				{
					temp = this.sortSixPixels(this.image[col - 1][row], this.image[col - 1][row + 1], 
							this.image[col][row + 1], this.image[col + 1][row + 1], this.image[col + 1][row], this.image[col][row]);
				}
			}
			else if(row == height - 1)
			{
				if(col == 0)
				{
					temp = this.sortFourPixels(this.image[col][row - 1], this.image[col + 1][row - 1], this.image[col + 1][row], this.image[col][row]);
				}
				else if(col == width - 1)
				{
					temp = this.sortFourPixels(this.image[col - 1][row], this.image[col - 1][row - 1], this.image[col][row - 1], this.image[col][row]);
				}
				else
				{
					temp = this.sortSixPixels(this.image[col - 1][row], this.image[col - 1][row - 1], 
							this.image[col][row - 1], this.image[col + 1][row - 1], this.image[col + 1][row], this.image[col][row]);
				}
			}
			else if(col == 0)
			{
				temp = this.sortSixPixels(this.image[col][row], this.image[col][row - 1], this.image[col + 1][row -1], 
						this.image[col + 1][row], this.image[col + 1][row + 1], this.image[col][row + 1]);
			}
			else if(col == width - 1)
			{
				temp = this.sortSixPixels(this.image[col][row], this.image[col][row - 1], this.image[col - 1][row -1], 
						this.image[col - 1][row], this.image[col - 1][row + 1], this.image[col][row + 1]);
			}
			else
			{
				temp = this.sortNinePixels(this.image[col - 1][row -1], this.image[col][row -1], this.image[col + 1][row -1], this.image[col - 1][row], this.image[col - 1][row + 1], 
						this.image[col][row + 1], this.image[col + 1][row + 1], this.image[col + 1][row], this.image[col][row]);
			}
			if(temp > 255) temp = 255;
			if(temp < 0) temp = 0;
			tempArray[col][row] = temp;
			col ++;
			if(col == width)
			{
				col = 0;
				row ++;
			}
		}
		
		this.image = tempArray;
	}

	private int sortNinePixels(int i, int j, int k, int l, int m, int n, int o,
			int p, int q)
	{
		ArrayList<Integer> sort = new ArrayList<Integer>();
		sort.add(i);
		sort.add(j);
		sort.add(k);
		sort.add(l);
		sort.add(m);
		sort.add(n);
		sort.add(o);
		sort.add(p);
		sort.add(q);
		Collections.sort(sort);
		return sort.get(4);
	}

	private int sortSixPixels(int i, int j, int k, int l, int m, int n)
	{
		ArrayList<Integer> sort = new ArrayList<Integer>();
		sort.add(i);
		sort.add(j);
		sort.add(k);
		sort.add(l);
		sort.add(m);
		sort.add(n);
		Collections.sort(sort);
		double temp = sort.get(2) + sort.get(3);
		temp /= 2;
		return (int) Math.round(temp);
	}

	private int sortFourPixels(int i, int j, int k, int l)
	{
		ArrayList<Integer> sort = new ArrayList<Integer>();
		sort.add(i);
		sort.add(j);
		sort.add(k);
		sort.add(l);
		Collections.sort(sort);
		double temp = sort.get(1) + sort.get(2);
		temp /= 2;
		return (int) Math.round(temp);
	}
	

	public void doSharpen()
	{
		int [][] tempArray = new int[width][height];
		for(int i = 0, col = 0, row = 0; i < width * height; i ++)
		{
			int temp;
			if(row == 0)
			{
				if(col == 0)
				{
					temp = (int) Math.round((6 * this.image[col][row] - this.image[col][row + 1] - this.image[col + 1][row]) / 2.0);
				}
				else if(col == width - 1)
				{
					temp = (int) Math.round((6 * this.image[col][row] - this.image[col - 1][row] - this.image[col][row + 1]) / 2.0);
				}
				else
				{
					temp = (int) Math.round((6 * this.image[col][row] - this.image[col - 1][row] - this.image[col][row + 1] - this.image[col + 1][row]) / 2.0);
				}
			}
			else if(row == height - 1)
			{
				if(col == 0)
				{
					temp = (int) Math.round((6 * this.image[col][row] - this.image[col][row - 1] - this.image[col + 1][row]) / 2.0);
				}
				else if(col == width - 1)
				{
					temp = (int) Math.round((6 * this.image[col][row] - this.image[col - 1][row] - this.image[col][row - 1] ) / 2.0);
				}
				else
				{
					temp = (int) Math.round((6 * this.image[col][row] - this.image[col - 1][row] - this.image[col][row - 1] - this.image[col + 1][row]) / 2.0);
				}
			}
			else if(col == 0)
			{
				temp = (int) Math.round((6 * this.image[col][row] - this.image[col][row - 1] - this.image[col + 1][row] - this.image[col][row + 1]) / 2.0);
			}
			else if(col == width - 1)
			{
				temp = (int) Math.round((6 * this.image[col][row] - this.image[col][row - 1] - this.image[col - 1][row] - this.image[col][row + 1]) / 2.0);
			}
			else
			{
				temp = (int) Math.round((6 * this.image[col][row] - this.image[col][row -1] - this.image[col - 1][row] - this.image[col][row + 1] - this.image[col + 1][row]) / 2.0);
			}
			if(temp > 255) temp = 255;
			if(temp < 0) temp = 0;
			tempArray[col][row] = temp;
			col ++;
			if(col == width)
			{
				col = 0;
				row ++;
			}
		}
		
		this.image = tempArray;
	}

	public void doEdgeDetection()
	{
		int [][] tempArray = new int[width][height];
		for(int i = 0, col = 0, row = 0; i < width * height; i ++)
		{
			int tempx;
			int tempy;
			if(row == 0)
			{
				if(col == 0)
				{
					tempx = (int) Math.round((this.image[col + 1][row + 1] + 2 * this.image[col + 1][row]) / 8.0);
					tempy = (int) Math.round((2 * this.image[col][row + 1] + this.image[col + 1][row + 1]) / 8.0);
				}
				else if(col == width - 1)
				{
					tempx = (int) Math.round((-2 * this.image[col - 1][row] - this.image[col - 1][row + 1]) / 8.0);
					tempy = (int) Math.round((2 * this.image[col][row + 1] + this.image[col - 1][row + 1]) / 8.0);
				}
				else
				{
					tempx = (int) Math.round((-2 * this.image[col - 1][row] - this.image[col - 1][row + 1] + this.image[col + 1][row + 1] + 2 * this.image[col + 1][row]) / 8.0);
					tempy = (int) Math.round((this.image[col - 1][row + 1] + 2 * this.image[col][row + 1] + this.image[col + 1][row + 1]) / 8.0);
				}
			}
			else if(row == height - 1)
			{
				if(col == 0)
				{
					tempx = (int) Math.round((this.image[col + 1][row - 1] + 2 * this.image[col + 1][row]) / 8.0);
					tempy = (int) Math.round((-2 * this.image[col][row - 1] - this.image[col + 1][row - 1]) / 8.0);
				}
				else if(col == width - 1)
				{
					tempx = (int) Math.round((-1 * this.image[col - 1][row - 1] - 2 * this.image[col - 1][row]) / 8.0);
					tempy = (int) Math.round((-1 * this.image[col - 1][row - 1] - 2 * this.image[col][row - 1]) / 8.0);
				}
				else
				{
					tempx = (int) Math.round((-2 * this.image[col - 1][row] - this.image[col - 1][row - 1] + this.image[col + 1][row - 1] + 2 * this.image[col + 1][row]) / 8.0);
					tempy = (int) Math.round((-1 * this.image[col - 1][row - 1] - 2 * this.image[col][row - 1] - this.image[col + 1][row - 1]) / 8.0);
				}
			}
			else if(col == 0)
			{
				tempx = (int) Math.round((this.image[col + 1][row -1] + 2 * this.image[col + 1][row] + this.image[col + 1][row + 1]) / 8.0);
				tempy = (int) Math.round((-2 * this.image[col][row - 1] - this.image[col + 1][row - 1] + this.image[col + 1][row + 1]+ 2 * this.image[col][row + 1]) / 8.0);
			}
			else if(col == width - 1)
			{
				tempx = (int) Math.round((-1 * this.image[col - 1][row -1] - 2 * this.image[col - 1][row] - this.image[col - 1][row + 1]) / 8.0);
				tempy = (int) Math.round((-2 * this.image[col][row - 1] - this.image[col - 1][row - 1] + this.image[col - 1][row + 1] + 2 * this.image[col][row + 1]) / 8.0);
			}
			else
			{
				tempx = (int) Math.round((this.image[col + 1][row -1] + 2 * this.image[col + 1][row] + this.image[col + 1][row + 1] - 
						this.image[col - 1][row -1] - 2 * this.image[col - 1][row] - this.image[col - 1][row + 1]) / 8.0);
				tempy = (int) Math.round((this.image[col - 1][row + 1] + 2 * this.image[col][row + 1] + this.image[col + 1][row + 1] -
						this.image[col + 1][row - 1] - 2 * this.image[col][row - 1] - this.image[col - 1][row - 1]) / 8.0);
			}
			tempx = (int) Math.round(Math.sqrt(Math.pow(tempx, 2) + Math.pow(tempy, 2)));
			if(tempx > 255) tempx = 255;
			if(tempx < 0) tempx = 0;
			tempArray[col][row] = tempx;
			col ++;
			if(col == width)
			{
				col = 0;
				row ++;
			}
		}
		
		this.image = tempArray;
	}
}
