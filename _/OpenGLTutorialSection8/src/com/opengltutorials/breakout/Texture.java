package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Texture {
	// Function that loads the texture
	public static int loadTexture(Context context, int id) {
		// Generate the texture
		final int[] textureID = new int [1];
		glGenTextures(1, textureID, 0);
				
		// If something went wrong return 0
		if(textureID[0] == 0) {
			return 0;
		}

		// Load the bitmap from the resources
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

		// If something went wrong delete the texture and return 0
		if(bitmap == null) {
			glDeleteTextures(1, textureID, 0);
			return 0;
		}

		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, textureID[0]);
		
		// Set the texture filtering to trilinear filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// Set the bitmap to the texture
		texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
		
		// Generate the mipmaps for the texture
		glGenerateMipmap(GL_TEXTURE_2D);
		
		// Delete the bitmap 
		bitmap.recycle();
		glBindTexture(GL_TEXTURE_2D, 0);
			
		// Return the final texture
		return textureID[0];
	}
}
