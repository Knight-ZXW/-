package com.think.pintugame.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${XiuWuZhuo} on 2016/1/11.
 * Emial:nimdanoob@163.com
 */
public class ImageSplitterUtil {
    /**
     * 传入bitmap 切成piece * piece块
     * @param bitmap  传入的图片
     * @param piece  返回 piece * pieces块图片的集合
     * @return
     */
    public static List<ImagePiece> splitImage(Bitmap bitmap,int piece){

        List<ImagePiece> imagePieces = new ArrayList<>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int pieceWidth = Math.min(width, height) / piece;

        for (int i = 0; i < piece; i++) {
            for (int j = 0; j < piece; j++) {

                ImagePiece imagePiece = new ImagePiece();
                imagePiece.setIndex( j + i*piece);

                int x = j * pieceWidth;
                int y = i *pieceWidth;

                imagePiece.setBitmap(Bitmap.createBitmap(bitmap,x,y,pieceWidth,pieceWidth));
                imagePieces.add(imagePiece);
            }
        }

        return imagePieces;
    }
}
