package com.example.jeanpaucar.litorobotapp.model;

/**
 * Created by JeanPaucar on 04/08/2015.
 */
public class PiecesItem {
    private String pieceName;
    private int pieceImage;

    public PiecesItem(String pieceName, int pieceImage) {
        this.pieceName = pieceName;
        this.pieceImage = pieceImage;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public int getPieceImage() {
        return pieceImage;
    }

    public void setPieceImage(int pieceImage) {
        this.pieceImage = pieceImage;
    }
}
