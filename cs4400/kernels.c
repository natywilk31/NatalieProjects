/*******************************************
 * Solutions for the CS:APP Performance Lab
 ********************************************/

#include <stdio.h>
#include <stdlib.h>
#include "defs.h"
#include <unistd.h>

/* 
 * Please fill in the following student struct 
 */
student_t student = {
  "Natalie Wilkins",     /* Full name */
  "natalie.wilkins@utah.edu",  /* Email address */
};

/***************
 * COMPLEX KERNEL
 ***************/

/******************************************************
 * Your different versions of the complex kernel go here
 ******************************************************/

/* 
 * naive_complex - The naive baseline version of complex 
 */
char naive_complex_descr[] = "naive_complex: Naive baseline implementation";
void naive_complex(int dim, pixel *src, pixel *dest)
{
  int i, j;

  for(i = 0; i < dim; i++)
    for(j = 0; j < dim; j++)
    {

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].red = ((int)src[RIDX(i, j, dim)].red +
						      (int)src[RIDX(i, j, dim)].green +
						      (int)src[RIDX(i, j, dim)].blue) / 3;
      
      dest[RIDX(dim - j - 1, dim - i - 1, dim)].green = ((int)src[RIDX(i, j, dim)].red +
							(int)src[RIDX(i, j, dim)].green +
							(int)src[RIDX(i, j, dim)].blue) / 3;
      
      dest[RIDX(dim - j - 1, dim - i - 1, dim)].blue = ((int)src[RIDX(i, j, dim)].red +
						       (int)src[RIDX(i, j, dim)].green +
						       (int)src[RIDX(i, j, dim)].blue) / 3;

    }
}


char improved_complex_descr[] = "improved_complex: optimized complex";
void improved_complex(int dim, pixel *src, pixel *dest)
{
  int i, j, ii, jj;
  int dim_subtract = dim - 1;

  const int TILE_SIZE = 8;
  for (i = 0; i < dim; i +=TILE_SIZE)
    {
      int tile_end_i = i + TILE_SIZE;
      for (j = 0; j < dim; j +=TILE_SIZE)
	{
	  int tile_end_j = j + TILE_SIZE;
	  for (ii = i; ii < tile_end_i; ii +=2)
	    {
	      for(jj=j; jj < tile_end_j; jj +=1)
		{
		  int val1 = ((int)src[RIDX(ii, jj, dim)].red + (int)src[RIDX(ii, jj, dim)].green + (int)src[RIDX(ii, jj, dim)].blue) / 3;
		  int val2 = ((int)src[RIDX(ii+1, jj, dim)].red + (int)src[RIDX(ii+1, jj, dim)].green + (int)src[RIDX(ii+1, jj, dim)].blue) / 3;
		  int idx1 = RIDX(dim_subtract - jj, dim_subtract - ii, dim);
		  dest[idx1].red = dest[idx1].green = dest[idx1].blue = val1;
		  int idx2 = RIDX(dim_subtract - jj, dim_subtract - (ii+1), dim);
		  dest[idx2].red = dest[idx2].green = dest[idx2].blue = val2;
		}
	      jj = j;
	    }
	}
    }		  
}


/* 
 * complex - Your current working version of complex
 * IMPORTANT: This is the version you will be graded on
 */
char complex_descr[] = "complex: Current working version";
void complex(int dim, pixel *src, pixel *dest)
{
  improved_complex(dim, src, dest);
}

/*********************************************************************
 * register_complex_functions - Register all of your different versions
 *     of the complex kernel with the driver by calling the
 *     add_complex_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.  
 *********************************************************************/

void register_complex_functions() {
  add_complex_function(&complex, complex_descr);
  add_complex_function(&naive_complex, naive_complex_descr);
  add_complex_function(&improved_complex, improved_complex_descr);
}


/***************
 * MOTION KERNEL
 **************/

/***************************************************************
 * Various helper functions for the motion kernel
 * You may modify these or add new ones any way you like.
 **************************************************************/


/* 
 * weighted_combo - Returns new pixel value at (i,j) 
 */
static pixel weighted_combo(int dim, int i, int j, pixel *src) 
{
  int ii, jj;
  pixel current_pixel;

  int red, green, blue;
  red = green = blue = 0;

  int num_neighbors = 0;
  for(ii=0; ii < 3; ii++)
    for(jj=0; jj < 3; jj++) 
      if ((i + ii < dim) && (j + jj < dim)) 
      {
	num_neighbors++;
	red += (int) src[RIDX(i+ii,j+jj,dim)].red;
	green += (int) src[RIDX(i+ii,j+jj,dim)].green;
	blue += (int) src[RIDX(i+ii,j+jj,dim)].blue;
      }
  
  current_pixel.red = (unsigned short) (red / num_neighbors);
  current_pixel.green = (unsigned short) (green / num_neighbors);
  current_pixel.blue = (unsigned short) (blue / num_neighbors);
  
  return current_pixel;
}



/******************************************************
 * Your different versions of the motion kernel go here
 ******************************************************/


/*
 * naive_motion - The naive baseline version of motion 
 */
char naive_motion_descr[] = "naive_motion: gubbaNaive baseline implementation";
void naive_motion(int dim, pixel *src, pixel *dst) 
{
  int i, j;  
  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++)
      dst[RIDX(i, j, dim)] = weighted_combo(dim, i, j, src);
}

char improved_motion_descr[] = "improved_motion: better motion implementatoin";

void improved_motion(int dim, pixel *src, pixel *dest)
{
  int i, j;
  int col1red=0, col1green = 0, col1blue= 0;
  int col2red = 0, col2green = 0, col2blue = 0;
  int col3red = 0, col3green = 0, col3blue = 0;

  for (i = 0; i < dim-2; i++)
    {
      col1red = (int) (src[RIDX(i,0,dim)].red + src[RIDX(i+1,0,dim)].red + src[RIDX(i+2, 0, dim)].red);
      col1blue = (int) (src[RIDX(i,0,dim)].blue + src[RIDX(i+1,0,dim)].blue + src[RIDX(i+2,0,dim)].blue);
      col1green = (int) (src[RIDX(i,0,dim)].green + src[RIDX(i+1, 0, dim)].green + src[RIDX(i+2,0,dim)].green);

      col2red = (int) (src[RIDX(i, 1,dim)].red + src[RIDX(i+1,1,dim)].red + src[RIDX(i+2,1,dim)].red);
      col2green = (int) (src[RIDX(i,1,dim)].green + src[RIDX(i+1,1,dim)].green + src[RIDX(i+2,1,dim)].green);
      col2blue = (int) (src[RIDX(i,1,dim)].blue + src[RIDX(i+1,1,dim)].blue + src[RIDX(i+2,1,dim)].blue);

      for (j = 0; j < dim - 2; j++)
	{
	  col3red = (int) (src[RIDX(i,j+2,dim)].red + src[RIDX(i+1,j+2,dim)].red + src[RIDX(i+2,j+2,dim)].red);
	  col3blue = (int) (src[RIDX(i,j+2,dim)].blue + src[RIDX(i+1,j+2,dim)].blue + src[RIDX(i+2,j+2, dim)].blue);
	  col3green = (int) (src[RIDX(i,j+2,dim)].green + src[RIDX(i+1,j+2,dim)].green + src[RIDX(i+2,j+2,dim)].green);

	  dest[RIDX(i,j,dim)].red = (unsigned short) ((col1red + col2red + col3red) / 9);
	  dest[RIDX(i,j,dim)].green = (unsigned short) ((col1green + col2green + col3green) / 9);
	  dest[RIDX(i,j,dim)].blue = (unsigned short) ((col1blue + col2blue + col3blue) / 9);

	  col1red = col2red;
	  col1green = col2green;
	  col1blue = col2blue;

	  col2red = col3red;
	  col2green = col3green;
	  col2blue = col3blue;

	}
      dest[RIDX(i,j,dim)].red = (unsigned short) (( col1red + col2red) / 6);
      dest[RIDX(i,j,dim)].green = (unsigned short) ((col1green + col2green) / 6);
      dest[RIDX(i,j,dim)].blue = (unsigned short) ((col1blue + col2blue) / 6);

      col1red = col2red;
      col1green = col2green;
      col1blue = col2blue;

      j++;

      dest[RIDX(i,j,dim)].red = (unsigned short) (col1red/3);
      dest[RIDX(i,j,dim)].blue = (unsigned short) (col1blue / 3);
      dest[RIDX(i,j,dim)].green = (unsigned short) (col1green / 3);

    }
  col1red = (int) (src[RIDX(i,0,dim)].red + src[RIDX(i+1,0,dim)].red);
  col1green = (int) (src[RIDX(i,0,dim)].green + src[RIDX(i+1,0,dim)].green);
  col1blue = (int) (src[RIDX(i,0,dim)].blue + src[RIDX(i+1,0,dim)].blue);

  col2red = (int) (src[RIDX(i,1,dim)].red + src[RIDX(i+1,1,dim)].red);
  col2blue = (int) (src[RIDX(i,1,dim)].blue + src[RIDX(i+1,1,dim)].blue);
  col2green = (int) (src[RIDX(i,1,dim)].green + src[RIDX(i+1,1,dim)].green);

  for (j = 0; j < dim-2; j++)
    {
      col3red = (int) (src[RIDX(i,j+2,dim)].red + src[RIDX(i+1,j+2,dim)].red);
       col3green =  (int) (src[RIDX(i,j+2,dim)].green + src[RIDX(i+1,j+2,dim)].green);
      col3blue = (int) (src[RIDX(i,j+2,dim)].blue + src[RIDX(i+1,j+2,dim)].blue);

       dest[RIDX(i, j, dim)].red = (unsigned short) ((col1red + col2red + col3red) / 6);
      dest[RIDX(i, j, dim)].green = (unsigned short) ((col1green + col2green + col3green) / 6);
      dest[RIDX(i, j, dim)].blue = (unsigned short) ((col1blue + col2blue + col3blue) / 6);


      col1red = col2red;
      col1green = col2green;
      col1blue = col2blue;

      col2red = col3red;
      col2green = col3green;
      col2blue = col3blue;

    }

  i++;
  col1red = (int) src[RIDX(i,0,dim)].red;
  col1green = (int) src[RIDX(i,0,dim)].green;
  col1blue = (int) src[RIDX(i,0,dim)].blue;

  col2red = src[RIDX(i,1,dim)].red;
  col2blue = src[RIDX(i,1,dim)].blue;
  col2green = src[RIDX(i,1,dim)].green;

  for (j = 0; j < dim-2; j++)
    {
      col3red = (int) (src[RIDX(i,j+2,dim)].red);
      col3green = (int) src[RIDX(i,j+2,dim)].green;
      col3blue = (int) src[RIDX(i, j+2,dim)].blue;

      dest[RIDX(i,j,dim)].red = (unsigned short) ((col1red + col2red + col3red) / 3);
      dest[RIDX(i,j,dim)].blue = (unsigned short) ((col1blue + col2blue + col3blue) / 3);
      dest[RIDX(i,j,dim)].green = (unsigned short) ((col1green + col2green + col3green) / 3);

      col1red = col2red;
      col1green = col2green;
      col1blue = col2blue;

      col2red = col3red;
      col2green = col3green;
      col2blue = col3blue;
    }
  i = dim - 2;
  j = dim - 2;


  pixel new_pixel2 = {0,0,0};
  int red, green, blue;
  red = green = blue = 0;

  pixel pixel1 = src[RIDX(i,j,dim)];
  pixel pixel2 = src[RIDX(i,j+1,dim)];
  pixel pixel3 = src[RIDX(i+1,j,dim)];
  pixel pixel4 = src[RIDX(i+1,j+1,dim)];

  red += (int) (pixel1.red + pixel2.red + pixel3.red + pixel4.red);
  blue += (int) (pixel1.blue + pixel2.blue + pixel3.blue + pixel4.blue);
  green += (int) (pixel1.green + pixel3.green + pixel2.green + pixel4.green);
  new_pixel2.red = (unsigned short) (red / 4);
  new_pixel2.green = (unsigned short) (green/4);
  new_pixel2.blue = (unsigned short) (blue/4);
  
  dest[RIDX(i,j,dim)] = new_pixel2;
  
  i = dim - 1;
  red = blue = green = 0;
  pixel1 = src[RIDX(i,j,dim)];
  pixel2 = src[RIDX(i,j+1,dim)];
  red += (int) (pixel1.red + pixel2.red);
  green += (int) (pixel1.green + pixel2.green);
  blue += (int) (pixel1.blue + pixel2.blue);

  dest[RIDX(i,j,dim)].red = (unsigned short) (red/2);
  dest[RIDX(i,j,dim)].blue = (unsigned short) (blue / 2);
  dest[RIDX(i,j,dim)].green = (unsigned short) (green / 2);

  i = dim - 2;
  j = dim - 1;
  red = green = blue = 0;
  pixel1 = src[RIDX(i,j,dim)];
  pixel2 = src[RIDX(i+1,j,dim)];

  red += (int) (pixel1.red + pixel2.red);
  blue += (int) (pixel1.blue + pixel2.blue);
  green += (int) (pixel1.green + pixel2.green);

  dest[RIDX(i,j,dim)].red = (unsigned short) (red/2);
  dest[RIDX(i,j,dim)].blue = (unsigned short) (blue/2);
  dest[RIDX(i,j,dim)].green = (unsigned short) (green / 2);
							   
  dest[RIDX(dim-1, dim-1, dim)] = src[RIDX(dim-1,dim-1,dim)];
}

 
void trash_trash(int dim, pixel *src, pixel *dst)
{
  // go into loop: iterate across all rows

  for (int i = 0; i < dim; i++)
    {
      int col1red=0, col1green = 0, col1blue = 0;
      int col2red=0, col2green=0, col2blue=0;
      int col3red=0, col3green=0, col3blue=0;

      pixel pixel1_1, pixel1_2, pixel1_3, pixel2_1, pixel2_2, pixel2_3, pixel3_1, pixel3_2, pixel3_3;
      pixel1_1 = src[RIDX(i, 0, dim)];
      pixel1_2 = src[RIDX(i, 1, dim)];
      pixel1_3 = src[RIDX(i, 2, dim)];

      pixel2_1 = src[RIDX(i+1, 0, dim)];
      pixel2_2 = src[RIDX(i+1, 1, dim)];
      pixel2_3 = src[RIDX(i+1, 2, dim)];

      pixel3_1 = src[RIDX(i+2, 0, dim)];
      pixel3_2 = src[RIDX(i+2, 1, dim)];
      pixel3_3 = src[RIDX(i+2, 2, dim)];
      
      
      
  // first, find col1red, col1green, col1blue, col2red, col2green ....

      col1red = pixel1_1.red + pixel2_1.red + pixel3_1.red;
      col2red = pixel1_2.red + pixel2_2.red + pixel3_2.red;
      col3red = pixel1_3.red + pixel2_3.red + pixel3_3.red;

      col1blue = pixel1_1.blue + pixel2_1.blue + pixel3_1.blue;
      col2blue = pixel1_2.blue + pixel2_2.blue + pixel3_2.blue;
      col3blue = pixel1_3.blue + pixel2_3.blue + pixel3_3.blue;

      col1green = pixel1_1.green + pixel2_1.green + pixel3_1.green;
      col2green = pixel1_2.green + pixel2_2.green + pixel3_2.green;
      col3green = pixel1_3.green + pixel2_3.green + pixel3_3.green;
      
  // iterate across all columns:
      for (int j = 0; j < dim; j++)
	{
	  // aggregate red, blue, green
	  int red = col1red + col2red + col3red;
	  int blue = col1blue + col2blue + col3blue;
	  int green = col1green + col2green + col3green;

	  int num_neighbors = 9;
	  if ( i + 1 >= dim && j+1 >= dim)
	    {
	      num_neighbors = 1;
	    }
	  else if (i + 1 >= dim && j + 2 >= dim)
	    {
	      num_neighbors = 2;
	    }
	  else if (i + 1 >= dim && j + 2 < dim)
	    {
	      num_neighbors = 3;
	    }
	  else if (i + 2 >= dim && j + 1 >= dim)
	    {
	      num_neighbors = 2;
	    }
	  else if (i + 2 >= dim && j + 2 >= dim)
	    {
	      num_neighbors=4;
	    }
	  else if (i + 2 >= dim && j+2 < dim)
	    {
	      num_neighbors = 6;
	    }
	  
	  else if (i + 2 < dim && j + 1 >= dim)
	    {
	      num_neighbors = 3;
	    }
	  else if (i + 2 < dim && j + 2 >= dim)
	    {
	      num_neighbors = 6;
	      printf("col3green is %d\n", col3green);
	    }
	  else if (i + 2 < dim && j + 2 < dim)
	    {
	      num_neighbors = 9;
	    }	  
 
	  red /= num_neighbors;
	  blue /= num_neighbors;
	  green /= num_neighbors;
	  
  // calculate num_neighbors and divide red,green,blue by that

	  // assigning the pixel
	  dst[RIDX(i, j, dim)].green = green;
	  dst[RIDX(i,j,dim)].blue = blue;
	  dst[RIDX(i,j,dim)].red = red;
	  
  // calculate the next columns
	  col1blue = col2blue;
	  col2blue = col3blue;
	  col1green = col2green;
	  col2green = col3green;
	  col1red = col2red;
	  col2red = col3red;

	  col3red = col3blue = col3green = 0;
	  
	  // grab the new pixels
	  if (i >= 0 && i < dim-1 && j >= 0 && j + 3 < dim)
	    {
	      pixel1_3 = src[RIDX(i, j+2, dim)];
	      col3red += pixel1_3.red;
	      col3blue += pixel1_3.blue;
	      col3green += pixel1_3.green;

	      
	      if (i + 1 < dim)
		{
		  pixel2_3 = src[RIDX(i+1, j+2, dim)];
		  col3red+= pixel2_3.red;
		  col3blue+=pixel2_3.blue;
		  col3green+=pixel2_3.green;
		}
	      if (i+2 < dim)
		{
		  pixel3_3 = src[RIDX(i+2, j+2, dim)];
		  col3red+=pixel3_3.red;
		  col3blue+=pixel3_3.blue;
		  col3green+=pixel3_3.green;
		}
	    }

	
	  if (j >= dim)
	    {
	      col3red = col3blue = col3green = 0;
	      col2red = col2blue = col2green = 0;
	      printf("resetting all");
	    }

	    if (j + 1 >= dim)
	    {
	      col3red = col3blue = col3green = 0;
	    }
	    
  // but make sure we don't go out of bounds.
  // if its gonna go out of bounds assign that col to 0
	}
    }
}

  

/*
 * motion - Your current working version of motion. 
 * IMPORTANT: This is the version you will be graded on
 */
char motion_descr[] = "motion: Current working version";
void motion(int dim, pixel *src, pixel *dst) 
{
  improved_motion(dim, src, dst);
}


/********************************************************************* 
 * register_motion_functions - Register all of your different versions
 *     of the motion kernel with the driver by calling the
 *     add_motion_function() for each test function.  When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.  
 *********************************************************************/

void register_motion_functions() {
  add_motion_function(&motion, motion_descr);
  add_motion_function(&naive_motion, naive_motion_descr);
  add_motion_function(&improved_motion, improved_motion_descr);
}
