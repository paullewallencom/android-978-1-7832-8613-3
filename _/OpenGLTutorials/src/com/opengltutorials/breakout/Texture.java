package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Texture {
	public static int loadTexture(Context context, int id) {
		final int[] textureID = new int [1];
		glGenTextures(1, textureID, 0);
				
		if(textureID[0] == 0) {
			return 0;
		}

		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

		if(bitmap == null) {
			glDeleteTextures(1, textureID, 0);
			return 0;
		}

		glBindTexture(GL_TEXTURE_2D, textureID[0]);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
		
		glGenerateMipmap(GL_TEXTURE_2D);
		
		bitmap.recycle();
		glBindTexture(GL_TEXTURE_2D, 0);
			

		return textureID[0];
	}
}
